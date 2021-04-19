package org.germain.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HeureExam {
    private int heure;
    private int minute;

    public HeureExam() {
    }

    public HeureExam(int heure, int minute) {
        this.heure = heure;
        this.minute = minute;
    }

    public int getHeure() {
        return heure;
    }

    public int getMinute() {
        return minute;
    }

    public List<HeureExam> heureExamList(){
        List<HeureExam> list = new ArrayList();

        for (int i = 0; i < 18; i++) {
            HeureExam heureExam = new HeureExam();


        }
        return list;
    }
}
