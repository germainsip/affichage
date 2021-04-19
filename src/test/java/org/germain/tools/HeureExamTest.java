package org.germain.tools;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


public class HeureExamTest {
    @Test
    public void heureExamTest(){
        System.out.println("=======Test HeureExam===========");

        System.out.println("Pour une ouverture de pli à 8:30");
        HeureExam heureExam = new HeureExam(8,30);
        List<HeureExam> heureExamList=heureExam.heureExamList(4);
        System.out.println(heureExamList);
        Assertions.assertEquals(6,heureExamList.size());
        System.out.println("=================================");

    }

}