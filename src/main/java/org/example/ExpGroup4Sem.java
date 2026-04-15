package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ExpGroup4Sem extends BaseStudentGroup{
    private final int ex1, ex2, ex3, ex4, ex5, ex6;

    public ExpGroup4Sem(int ex1, int ex2, int ex3, int ex4, int ex5, int ex6) {
        super();
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.ex3 = ex3;
        this.ex4 = ex4;
        this.ex5 = ex5;
        this.ex6 = ex6;
    }

    @Override
    public double avgBall() {
        return (ex1+ex2+ex3+ex4+ex5+ex6) / 6.0;
    }

    @Override
    public int[] getScores() {
        return new int[]{ex1,ex2,ex3,ex4,ex5,ex6};
    }

    @Override
    public String toString() {
        return "ExpGroup4Sem{" +
                "ex1=" + ex1 +
                ", ex2=" + ex2 +
                ", ex3=" + ex3 +
                ", ex4=" + ex4 +
                ", ex5=" + ex5 +
                ", ex6=" + ex6 +
                '}';
    }
    public static ExpGroup4Sem[] parseExpGroup4Sem(String fileName){
        ExpGroup4Sem[] groups = null;
        JSONParser parser = new JSONParser();
        try(FileReader reader = new FileReader(fileName)){
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            groups = new ExpGroup4Sem[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Long ex1Long, ex2Long, ex3Long, ex4Long, ex5Long, ex6Long;
                ex1Long = (Long) jsonObject.get("exam1");
                ex2Long = (Long) jsonObject.get("exam2");
                ex3Long = (Long) jsonObject.get("exam3");
                ex4Long = (Long) jsonObject.get("exam4");
                ex5Long = (Long) jsonObject.get("exam5");
                ex6Long = (Long) jsonObject.get("exam6");
                int ex1, ex2, ex3, ex4, ex5, ex6;
                ex1 = ex1Long.intValue();
                ex2 = ex2Long.intValue();
                ex3 = ex3Long.intValue();
                ex4 = ex4Long.intValue();
                ex5 = ex5Long.intValue();
                ex6 = ex6Long.intValue();
                groups[i] = new ExpGroup4Sem(ex1, ex2, ex3, ex4, ex5, ex6);
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
