package org.germain.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HeureExam {
    private int heure;
    private int minute;

    @Override
    public String toString() {
        return heure + ":" + minute;
    }

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

    public List<HeureExam> heureExamList(int nbCand) {
        List<HeureExam> list = new ArrayList();
        int h = heure;
        int m = minute+20;
        int compte = 0;
        int newTime =0;
        for (int i = 0; i < nbCand+2; i++) {
            HeureExam heureExam = new HeureExam(h, m);
            if(compte<2){

                newTime = (h * 60 + m + 60 + 55);

            compte++;
            }
            else {
                compte=0;
                newTime = (h * 60 + m )+45;
            }
            h = newTime / 60;
            m = newTime % 60;
            list.add(heureExam);

        }
        return list;
    }
}
