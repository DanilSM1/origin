package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ExperimentalGroups2Sem {
    int ex1, ex2, ex3, ex4;
    String condition;
    double avgBall(){
        return (ex1 + ex2 + ex3 + ex4) / 4.0;
    }
    String setsConditions(){
        if (avgBall() <= 5 && avgBall() >= 4.5){
            return "T1";
        } else if (avgBall() < 4.5 && avgBall() >= 4 ){
            return "T2";
        } else if (avgBall() < 4 && avgBall() >= 3.5){
            return "T3";
        } else if (avgBall() < 3.5 && avgBall() >= 3){
            return "T4";
        } else if (avgBall() < 3 && avgBall() >= 2.5) {
            return "T5";
        } else if (avgBall() < 2.5 && avgBall() >= 1.5) {
            return "T6";
        } else return "T7";
    }

    public ExperimentalGroups2Sem(int ex1, int ex2, int ex3, int ex4, String condition) {
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.ex3 = ex3;
        this.ex4 = ex4;
        this.condition = setsConditions();
    }

    @Override
    public String toString() {
        return "ExperimentalGroups2Sem{" +
                "ex1=" + ex1 +
                ", ex2=" + ex2 +
                ", ex3=" + ex3 +
                ", ex4=" + ex4 +
                ", condition='" + condition + '\'' +
                '}';
    }
    public static int[] countConditions(ExperimentalGroups2Sem[] groups){
        if (groups == null || groups.length == 0) {
            return new int[7]; // возвращаем пустой массив
        }

        int[] counts = new int[7]; // [T1, T2, T3, T4, T5, T6, T7]

        for (ExperimentalGroups2Sem group : groups) {
            if (group != null) {
                switch (group.condition) {
                    case "T1": counts[0]++; break;
                    case "T2": counts[1]++; break;
                    case "T3": counts[2]++; break;
                    case "T4": counts[3]++; break;
                    case "T5": counts[4]++; break;
                    case "T6": counts[5]++; break;
                    case "T7": counts[6]++; break;
                }
            }
        }
        return counts;
    }
    public static ExperimentalGroups2Sem[] parseExperimentalGroups2Sem(String fileName) {
        ExperimentalGroups2Sem[] groups = null;
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(fileName)) {
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            groups = new ExperimentalGroups2Sem[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                Long exam1long = (Long) jsonObject.get("exam1");
                Long exam2long = (Long) jsonObject.get("exam2");
                Long exam3long = (Long) jsonObject.get("exam3");
                Long exam4long = (Long) jsonObject.get("exam4");

                int exam1, exam2, exam3, exam4;
                exam1 = exam1long.intValue();
                exam2 = exam2long.intValue();
                exam3 = exam3long.intValue();
                exam4 = exam4long.intValue();

                groups[i] = new ExperimentalGroups2Sem(exam1,exam2,exam3,exam4,null);
//                System.out.println(groups[i]);
            }
            System.out.println("Успешно загружено " + groups.length + " объектов из файла " + fileName);

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
        return groups;
    }
}
