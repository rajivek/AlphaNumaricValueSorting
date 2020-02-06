package com.rnd;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MessageSequenceUtil {


    public static void main(String[] args) {

        //test next number
        System.out.println("Test next sequence number......");
        String no = "31JAN99999E001";
        System.out.println("Input no :" + no);
        List<String> nextPossiableSequenceNos = nextPossibleNumbers(no);
        System.out.println("Output nos :" + nextPossiableSequenceNos);


        //test sorting

        System.out.println("Test sorting..........");
        List<String> nos = Arrays.asList("31JAN99999E002", "31JAN99999C001");
        System.out.println("Input sort no : " + nos);
        List<String> strings = sortMessageSequenceNumbers(nos);
        System.out.println( "Output nos : " + strings);

    }

    public static List<String> nextPossibleNumbers(String s) {

        MessageSequenceDTO messageSequenceDTO = new MessageSequenceDTO(s);

        MessageSequenceDTO.Part currentPart = messageSequenceDTO.getPart();

        return currentPart.getNextMessageSequenceNos(messageSequenceDTO);


    }


    public static final List<String> sortMessageSequenceNumbers(List<String> values){

        List<MessageSequenceDTO> messageSequenceDTOS = values.stream().map(s -> new MessageSequenceDTO
                (s)).collect(Collectors.toList());


        Collections.sort(messageSequenceDTOS, Comparator.comparingInt(MessageSequenceDTO::getDate)
                .thenComparing(MessageSequenceDTO::getMonth)
                .thenComparing(MessageSequenceDTO::getSequenceNo)
                .thenComparing(MessageSequenceDTO::getPartValue)
                .thenComparing(MessageSequenceDTO::getPartNo)

        );

        return messageSequenceDTOS.stream().map(MessageSequenceDTO::getMessageSequenceNo).collect(Collectors.toList());

    }



}
