package com.rnd;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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


    private final String messageSequenceNo;
    private int date;
    private int monthValue;

    public Month getMonth() {
        return month;
    }

    private Month month;
    private int sequenceNo;
    private int partValue;
    private Part part;
    private int partNo;


    public MessageSequenceDTO(String value) {
        Matcher matcher = pattern.matcher(value);
        if (!matcher.find()) {
            throw new RuntimeException("string pattern not matching to " + pattern.pattern() );
        }
        this.messageSequenceNo = value;
        extractValue(value);
    }

    private void extractValue(String value) {
        setDate(value);
        setMonthValue(value);
        setSequenceNo(value);
        setPartValue(value);
        setPartNo(value);
    }

    public Part getPart() {
        return part;
    }

    public String getMessageSequenceNo() {
        return messageSequenceNo;
    }

    public int getDate() {
        return date;
    }

    public int getMonthValue() {
        return monthValue;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public int getPartValue() {
        return partValue;
    }

    public int getPartNo() {
        return partNo;
    }

    private void setDate(String value) {

        Matcher matcher = datePattern.matcher(value);
        this.date =  matcher.find()? Integer.parseInt(matcher.group(0)) : 0;

    }

    private void setMonthValue(String value) {
        Matcher matcher = monthPattern.matcher(value);
        Month month = matcher.find()? Month.valueOf(matcher.group(0)): null;
        this.month = month;
        this.monthValue =   month.getCode();
    }

    private void setSequenceNo(String value) {
        Matcher matcher = sequencePattern.matcher(value);
        String sequenceNo = matcher.find() ? (matcher.group(0)) : "0";
        this.sequenceNo = Integer.parseInt(sequenceNo);
    }

    private void setPartValue(String value) {
        Matcher matcher = partPattern.matcher(value);
        List<String> list = new ArrayList<>();
        while (matcher.find()){
            list.add(matcher.group());
        }
        Part part = Part.valueOf(list.get(3));
        this.part = part;
        this.partValue = part.getCode();
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

        C(0){
            @Override
            List<Part> getNextPossiablePartCode() {
                return Arrays.asList(Part.C, Part.E);
            }

            @Override
            List<String> getNextMessageSequenceNos(MessageSequenceDTO messageSequenceDTO) {

                MessageSequenceDTO.Part currentPart = messageSequenceDTO.getPart();
                //increace part no;
                String nextPartNo = String.format("%03d",messageSequenceDTO.getPartNo() + 1);

                List<MessageSequenceDTO.Part> nextPossiblePartCode = currentPart.getNextPossiablePartCode();

                return Part.generate(messageSequenceDTO, nextPossiblePartCode,messageSequenceDTO.getSequenceNo(),
                        nextPartNo);

            }
        }, E(1){
            @Override
            List<Part> getNextPossiablePartCode() {
                return Arrays.asList(Part.C, Part.E);
            }

            @Override
            List<String> getNextMessageSequenceNos(MessageSequenceDTO messageSequenceDTO) {

                MessageSequenceDTO.Part currentPart = messageSequenceDTO.getPart();

                List<MessageSequenceDTO.Part> nextPossiblePartCode = currentPart.getNextPossiablePartCode();
                //increase sequece no;
                int nextSequeceNo = messageSequenceDTO.getSequenceNo() + 1;
                String nextPartNo = "001";
                return generate(messageSequenceDTO,nextPossiblePartCode,nextSequeceNo,nextPartNo);



            }
        };

        private static List<String> generate(MessageSequenceDTO messageSequenceDTO, List<Part> nextPossiablePartCode,
                                             int nextSequenceNo, String nextPartNo) {

            List<String> nextPossiableNumbers = new ArrayList<>();
            String nextDate = String.format("%02d",messageSequenceDTO.getDate());
            String nextMonth = messageSequenceDTO.getMonth().name();

            for (Part part : nextPossiablePartCode) {

                String next = nextDate + nextMonth + getStringSequenceNo(nextSequenceNo) + part.name() + nextPartNo;
                nextPossiableNumbers.add(next);

                if(nextSequenceNo > 99999){

                    Calendar instance = Calendar.getInstance();
                    instance.set(Calendar.MONTH, messageSequenceDTO.getMonthValue());
                    instance.set(Calendar.DATE, messageSequenceDTO.getDate());
                    instance.add(Calendar.DATE, 1);

                    String nextChangeMonth = Month.values()[(instance.get(Calendar.MONTH))].name();

                    String nextChangeDate = String.format("%02d",instance.get(Calendar.DATE));
                    next = nextChangeDate + nextChangeMonth + getStringSequenceNo(nextSequenceNo) + part.name() + nextPartNo;
                    nextPossiableNumbers.add(next);

                }

            }

            return nextPossiableNumbers;
        }

        private static String getStringSequenceNo(int nextSequeceNo) {

            int sequence = nextSequeceNo > 99999 ? 1 : nextSequeceNo;
            return String.format("%05d",sequence);

        }

        private int code;

        abstract List<Part> getNextPossiablePartCode();

        abstract List<String> getNextMessageSequenceNos(MessageSequenceDTO messageSequenceDTO);

        Part(int i) {
            this.code = i;
        }

        public int getCode() {
            return code;
        }
    }
}
