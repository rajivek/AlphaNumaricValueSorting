package com.sort.alphaNumaricValueSorting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TestSortUtil {

    @Test
    public void testSortWithDifferentMonth(){

        String value1 = "16OCT00005C001";
        String value2 = "16SEP00005C002";
        List<String> messageList = Arrays.asList(value1, value2);
        List<String> sort = SortUtil.sort(messageList);

        Assertions.assertEquals(value2,sort.get(0));
        Assertions.assertEquals(value1,sort.get(1));

    }

    @Test
    public void testSortWithDifferentSequence(){

        String value2 = "16OCT00003C002";
        String value1 = "16OCT00005C001";
        List<String> messageList = Arrays.asList(value1, value2);
        List<String> sort = SortUtil.sort(messageList);

        Assertions.assertEquals(value2,sort.get(0));
        Assertions.assertEquals(value1,sort.get(1));

    }

    @Test
    public void testSortWithDifferentSequencePartNo(){

        String value1 = "16OCT00003C001";
        String value2 = "16OCT00003C002";
        List<String> messageList = Arrays.asList(value1, value2);
        List<String> sort = SortUtil.sort(messageList);

        Assertions.assertEquals(value1,sort.get(0));
        Assertions.assertEquals(value2,sort.get(1));

    }

    @Test
    public void testSortWithDifferentSequencePart(){

        String value1 = "16OCT00003E001";
        String value2 = "16OCT00003C002";
        List<String> messageList = Arrays.asList(value1, value2);
        List<String> sort = SortUtil.sort(messageList);

        Assertions.assertEquals(value2,sort.get(0));
        Assertions.assertEquals(value1,sort.get(1));

    }

    @Test
    public void testSortWithDifferentDate(){

        String value1 = "16OCT00003C001";
        String value2 = "14OCT00003C002";
        List<String> messageList = Arrays.asList(value1, value2);
        List<String> sort = SortUtil.sort(messageList);

        Assertions.assertEquals(value2,sort.get(0));
        Assertions.assertEquals(value1,sort.get(1));

    }
}
