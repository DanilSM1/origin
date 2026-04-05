package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ExperimentalGroups1Sem{
    int ex1, ex2;
    String condition;
    double avgBall(){
        return (ex1 + ex2) / 2.0;
    }
    // Расчет состояния для каждого объекта
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

    public ExperimentalGroups1Sem(int ballPhysics, int ballProgramming, String condition) {
        this.ex1 = ballPhysics;
        this.ex2 = ballProgramming;
        this.condition = setsConditions();
    }

    @Override
    public String toString() {
        return "ExperimentalGroups{" +
                "ballPhysics=" + ex1 +
                ", ballProgramming=" + ex2 +
                ", condition='" + condition + '\'' +
                '}';
    }

    public static int[] countConditions(ExperimentalGroups1Sem[] groups){
        if (groups == null || groups.length == 0) {
            return new int[7]; // возвращаем пустой массив
        }

        int[] counts = new int[7]; // [T1, T2, T3, T4, T5, T6, T7]

        for (ExperimentalGroups1Sem group : groups) {
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
    public static ExperimentalGroups1Sem[] parseExperimentalGroups1Sem(String fileName) {
        ExperimentalGroups1Sem[] groups = null;
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(fileName)) {
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            groups = new ExperimentalGroups1Sem[jsonArray.size()];

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                Long physicsLong = (Long) jsonObject.get("Physics");
                Long programmingLong = (Long) jsonObject.get("Programming");

                int physics = physicsLong.intValue();
                int programming = programmingLong.intValue();

                groups[i] = new ExperimentalGroups1Sem(physics, programming, null);
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