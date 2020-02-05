package com.sort.alphaNumaricValueSorting;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageSequenceDTO {



    private final static Pattern pattern = Pattern.compile("\\d{2}[A-Z]{3}\\d{5}[A-Z]\\d{3}");  //   \d{2}[A-Z]{3}\d{5}[A-Z]\d{3}
    private final static Pattern datePattern = Pattern.compile("^\\d{2}"); //      ^\d{2}
    private final static Pattern monthPattern = Pattern.compile("[A-Z]{3}"); // [A-Z]{3}
    private final static Pattern sequencePattern = Pattern.compile("\\d{5}"); // \d{5}
    private final static Pattern partPattern = Pattern.compile("[A-Z]{1}"); // [A-Z]{1} -> getting 4 th character
    private final static Pattern part_NO_Pattern = Pattern.compile("\\d{3}"); // \d{3} -> getting 2nd group


    private final String key;

    private int date;
    private int month;
    private int sequenceNo;
    private int part;
    private int partNo;


    public MessageSequenceDTO(String value) {
        Matcher matcher = pattern.matcher(value);
        if (!matcher.find()) {
            throw new RuntimeException("string pattern not matching to " + pattern.pattern() );
        }
        this.key = value;
        extractValue(value);
    }

    private void extractValue(String value) {
        setDate(value);
        setMonth(value);
        setSequenceNo(value);
        setPart(value);
        setPartNo(value);
    }

    public String getKey() {
        return key;
    }

    public int getDate() {
        return date;
    }

    public int getMonth() {
        return month;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public int getPart() {
        return part;
    }

    public int getPartNo() {
        return partNo;
    }

    private void setDate(String value) {

        Matcher matcher = datePattern.matcher(value);
        this.date =  matcher.find()? Integer.parseInt(matcher.group(0)) : 0;

    }

    private void setMonth(String value) {
        Matcher matcher = monthPattern.matcher(value);
        this.month =  matcher.find()? Month.valueOf(matcher.group(0)).getCode(): 0;
    }

    private void setSequenceNo(String value) {
        Matcher matcher = sequencePattern.matcher(value);
        this.sequenceNo = matcher.find()? Integer.parseInt(matcher.group(0)) : 0 ;
    }

    private void setPart(String value) {
        Matcher matcher = partPattern.matcher(value);
        List<String> list = new ArrayList<>();
        while (matcher.find()){
            list.add(matcher.group());
        }
        this.part = Part.valueOf(list.get(3)).getCode();
    }


    public void setPartNo(String value) {
        Matcher matcher = part_NO_Pattern.matcher(value);
        List<String> list = new ArrayList<>();
        while (matcher.find()){
            list.add(matcher.group());
        }
        this.partNo =  Integer.parseInt(list.get(1));
    }

    public enum Month{
        JAN(0),FEB(1),MAR(2),APR(3),MAY(4),JUN(5),JUL(6),AUG(7),SEP(8),OCT(9),NOV(10),DEC(11);


        public int getCode() {
            return code;
        }

        private int code;
        Month(int i) {
            code = i;
        }
    }

    public enum Part{

        C(0), E(1);

        private int code;

        Part(int i) {
            this.code = i;
        }

        public int getCode() {
            return code;
        }
    }
}
