package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ExpGroup1Sem extends BaseStudentGroup{
    private final int ex1,ex2;

    public ExpGroup1Sem(int ex1, int ex2) {
        super();
        this.ex1 = ex1;
        this.ex2 = ex2;
    }

    @Override
    public double avgBall() {
        return (ex1+ex2) / 2.0;
    }

    @Override
    public int[] getScores() {
        return new int[]{ex1,ex2};
    }

    @Override
    public String toString() {
        return "ExpGroup1Sem{" +
                "ex1=" + ex1 +
                ", ex2=" + ex2 +
                '}';
    }
    public static ExpGroup1Sem[] parseExpGroup1Sem(String fileName){
        ExpGroup1Sem[] groups = null;
        JSONParser parser = new JSONParser();
        try(FileReader reader = new FileReader(fileName)){
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            groups = new ExpGroup1Sem[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Long ex1Long, ex2Long;
                ex1Long = (Long) jsonObject.get("exam1");
                ex2Long = (Long) jsonObject.get("exam2");
                int ex1, ex2;
                ex1 = ex1Long.intValue();
                ex2 = ex2Long.intValue();
                groups[i] = new ExpGroup1Sem(ex1, ex2);
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
