package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ControlGroup {
    int ex1, ex2, ex3;
    String condition;

    public double avgBall() {
        return (ex1 + ex2 + ex3) / 3.0;
    }

    public int[] getScores() {
        return new int[0];
    }

    // Расчет состояния для каждого объекта
    String setsConditions() {
        if (avgBall() <= 5 && avgBall() >= 4.5) {
            return "T1";
        } else if (avgBall() >= 4 && avgBall() < 4.5) {
            return "T2";
        } else if (avgBall() < 4 && avgBall() >= 3.5) {
            return "T3";
        } else if (avgBall() < 3.5 && avgBall() >= 3) {
            return "T4";
        } else if (avgBall() < 3 && avgBall() >= 2.5) {
            return "T5";
        } else if (avgBall() < 2.5 && avgBall() >= 1.5) {
            return "T6";
        } else return "T7";
    }

    public ControlGroup(int ex1, int ex2, int ex3, String condition) {
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.ex3 = ex3;
        this.condition = setsConditions();
    }

    @Override
    public String toString() {
        return "ControlGroup{" +
                "ex1=" + ex1 +
                ", ex2=" + ex2 +
                ", ex3=" + ex3 +
                ", condition='" + condition + '\'' +
                '}';
    }

    public static int[] countConditions(ControlGroup[] groups) {
        if (groups == null || groups.length == 0) {
            return new int[7]; // возвращаем пустой массив
        }

        int[] counts = new int[7]; // [T1, T2, T3, T4, T5, T6, T7]

        for (ControlGroup group : groups) {
            if (group != null) {
                switch (group.condition) {
                    case "T1":
                        counts[0]++;
                        break;
                    case "T2":
                        counts[1]++;
                        break;
                    case "T3":
                        counts[2]++;
                        break;
                    case "T4":
                        counts[3]++;
                        break;
                    case "T5":
                        counts[4]++;
                        break;
                    case "T6":
                        counts[5]++;
                        break;
                    case "T7":
                        counts[6]++;
                        break;
                }
            }
        }
        return counts;
    }


}
