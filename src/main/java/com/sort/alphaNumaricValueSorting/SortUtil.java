package com.sort.alphaNumaricValueSorting;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortUtil {


    public static final List<String> sort(List<String> values){

        List<MessageSequenceDTO> messageSequenceDTOS = values.stream().map(s -> new MessageSequenceDTO
                (s)).collect(Collectors.toList());


        Collections.sort(messageSequenceDTOS, Comparator.comparingInt(MessageSequenceDTO::getDate)
                .thenComparing(MessageSequenceDTO::getMonth)
                .thenComparing(MessageSequenceDTO::getSequenceNo)
                .thenComparing(MessageSequenceDTO::getPart)
                .thenComparing(MessageSequenceDTO::getPartNo)

        );

        return messageSequenceDTOS.stream().map(MessageSequenceDTO::getKey).collect(Collectors.toList());

    }
}
