package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpGroups extends BaseStudentGroup{

    private int[] examScores;

    public ExpGroups(int... examScores) {
        super();
        this.examScores = examScores.clone();
    }

    @Override
    public double avgBall() {
        if(examScores.length == 0) return 0.0;
        int sum = 0;
        for (int score : examScores){
            sum += score;
        }
        return sum / (double) examScores.length;
    }

    @Override
    public int[] getScores() {
        return examScores.clone();
    }

    @Override
    public String toString() {
        return "ExpGroups{" +
                "examScores=" + Arrays.toString(examScores) +
                '}';
    }

    public static ExpGroups[] parseExpGroup(String fileName){
        ExpGroups[] groups = null;
        JSONParser parser = new JSONParser();

        try(FileReader reader = new FileReader(fileName)){
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            groups = new ExpGroups[jsonArray.size()];

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                List<Integer> scoreList = new ArrayList<>();
                for(Object key: jsonObject.keySet()){
                    String keyStr = (String) key;
                    if(keyStr.startsWith("exam")){
                        Long scoreLong = (Long) jsonObject.get(keyStr);
                        scoreList.add(scoreLong.intValue());
                    }
                }
                int[] scores = new int[scoreList.size()];
                for(int j = 0; j < scoreList.size(); j++){
                    scores[j] = scoreList.get(j);
                }
                groups[i] = new ExpGroups(scores);
            }

            System.out.println("Успешно загружено " + groups.length +
                    " объктов из файла " + fileName);
        } catch (IOException | ParseException e){
            e.printStackTrace();
        }
        return groups;
    }
}
