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

    double avgBall() {
        return (ex1 + ex2 + ex3) / 3.0;
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

    public static ControlGroup[] parseControlGroup(String fileName) {
        JSONParser parser = new JSONParser();
        ControlGroup[] controlGroup = null;
        try (FileReader reader = new FileReader(fileName)) {
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            controlGroup = new ControlGroup[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Long ex1long, ex2long, ex3long;
                ex1long = (Long) jsonObject.get("exam1");
                ex2long = (Long) jsonObject.get("exam2");
                ex3long = (Long) jsonObject.get("exam3");

                int ex1, ex2, ex3;
                ex1 = ex1long.intValue();
                ex2 = ex2long.intValue();
                ex3 = ex3long.intValue();
                controlGroup[i] = new ControlGroup(ex1, ex2, ex3, null);
//                System.out.println(controlGroup[i]);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла " + fileName + ": " + e.getMessage());
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Ошибка при парсинге JSON файла " + fileName + ": " + e.getMessage());
            e.printStackTrace();
        } catch (ClassCastException e) {
            System.err.println("Ошибка приведения типов в файле " + fileName);
            e.printStackTrace();
        }
        return controlGroup;
    }
}
