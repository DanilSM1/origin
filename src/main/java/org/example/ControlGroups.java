package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ControlGroups extends BaseStudentGroup{
    private final int ex1,ex2,ex3;

    public ControlGroups(int ex1, int ex2, int ex3) {
        super();
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.ex3 = ex3;
    }

    @Override
    public double avgBall() {
        return (ex1+ex2+ex3) / 3.0;
    }

    @Override
    public int[] getScores() {
        return new int[]{ex1,ex2,ex3};
    }

    @Override
    public String toString() {
        return "ControlGroups{" +
                "ex1=" + ex1 +
                ", ex2=" + ex2 +
                ", ex3=" + ex3 +
                '}';
    }
    public static ControlGroups[] parseControlGroup(String fileName) {
        JSONParser parser = new JSONParser();
        ControlGroups[] controlGroup = null;
        try (FileReader reader = new FileReader(fileName)) {
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            controlGroup = new ControlGroups[jsonArray.size()];
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
                controlGroup[i] = new ControlGroups(ex1, ex2, ex3);
//                System.out.println(controlGroup[i]);
            }
            System.out.println("Успешно загружено " + controlGroup.length + " объектов из файла " + fileName);
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
