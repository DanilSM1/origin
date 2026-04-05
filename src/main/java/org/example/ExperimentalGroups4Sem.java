package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ExperimentalGroups4Sem {
    int ex1, ex2, ex3, ex4, ex5, ex6;
    String condition;
    double avgBall(){
        return (ex1 + ex2 + ex3 + ex4 + ex5 + ex6) / 6.0;
    }
    String setsConditions(){
        if (avgBall() <= 5 && avgBall() >= 4.5){
            return "T1";
        } else if (avgBall() >= 4 && avgBall() < 4.5 ){
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

    public ExperimentalGroups4Sem(int ex1, int ex2, int ex3, int ex4, int ex5, int ex6, String condition) {
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.ex3 = ex3;
        this.ex4 = ex4;
        this.ex5 = ex5;
        this.ex6 = ex6;
        this.condition = setsConditions();
    }

    @Override
    public String toString() {
        return "ExperimentalGroups4Sem{" +
                "ex1=" + ex1 +
                ", ex2=" + ex2 +
                ", ex3=" + ex3 +
                ", ex4=" + ex4 +
                ", ex5=" + ex5 +
                ", ex6=" + ex6 +
                ", condition='" + condition + '\'' +
                '}';
    }
    public static int[] countConditions(ExperimentalGroups4Sem[] groups){
        if (groups == null || groups.length == 0) {
            return new int[7]; // возвращаем пустой массив
        }

        int[] counts = new int[7]; // [T1, T2, T3, T4, T5, T6, T7]

        for (ExperimentalGroups4Sem group : groups) {
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
    public static ExperimentalGroups4Sem[] parseExperimentalGroups4Sem (String fileName){
        ExperimentalGroups4Sem[] groups = null;
        JSONParser parser = new JSONParser();

        try(FileReader reader = new FileReader(fileName)){
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            groups = new ExperimentalGroups4Sem[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                Long ex1Long = (Long) jsonObject.get("exam1");
                Long ex2Long = (Long) jsonObject.get("exam2");
                Long ex3Long = (Long) jsonObject.get("exam3");
                Long ex4Long = (Long) jsonObject.get("exam4");
                Long ex5Long = (Long) jsonObject.get("exam5");
                Long ex6Long = (Long) jsonObject.get("exam6");

                int exam1, exam2, exam3, exam4, exam5, exam6;
                exam1 = ex1Long.intValue();
                exam2 = ex2Long.intValue();
                exam3 = ex3Long.intValue();
                exam4 = ex4Long.intValue();
                exam5 = ex5Long.intValue();
                exam6 = ex6Long.intValue();

                groups[i] = new ExperimentalGroups4Sem(exam1, exam2, exam3, exam4, exam5, exam6, null);
//                System.out.println(groups[i]);
            }
            System.out.println("Успешно загружено " + groups.length + " объектов из файла " + fileName);
        }catch (IOException e) {
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
