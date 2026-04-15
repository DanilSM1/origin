package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Загрузка данных
        ControlGroups[] controlGroup = ControlGroups.parseControlGroup(
                "D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\controlGroup.json");
        ExpGroup1Sem[] group1Sem = ExpGroup1Sem.parseExpGroup1Sem(
                "D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\1Sem.json");
        ExpGroup2Sem[] group2Sem = ExpGroup2Sem.parseExpGroup2Sem(
                "D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\2Sem.json");
        ExpGroup3to7Sem[] group3Sem = ExpGroup3to7Sem.parseExpGroup3to7Sem(
                "D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\3Sem.json");
        ExpGroup4Sem[] group4Sem = ExpGroup4Sem.parseExpGroup4Sem(
                "D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\4Sem.json");
        ExpGroup3to7Sem[] group5Sem = ExpGroup3to7Sem.parseExpGroup3to7Sem(
                "D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\5Sem.json");
        ExpGroup3to7Sem[] group6Sem = ExpGroup3to7Sem.parseExpGroup3to7Sem(
                "D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\6Sem.json");
        ExpGroup3to7Sem[] group7Sem = ExpGroup3to7Sem.parseExpGroup3to7Sem(
                "D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\7Sem.json");
        ExpGroup8Sem[] group8Sem = ExpGroup8Sem.parseExpGroup8Sem(
                "D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\8Sem.json");

        // Подсчет студентов по состояниям
        int[] controlCounts = BaseStudentGroup.countConditions(controlGroup);
        int[] sem1Counts = BaseStudentGroup.countConditions(group1Sem);
        int[] sem2Counts = BaseStudentGroup.countConditions(group2Sem);
        int[] sem3Counts = BaseStudentGroup.countConditions(group3Sem);
        int[] sem4Counts = BaseStudentGroup.countConditions(group4Sem);
        int[] sem5Counts = BaseStudentGroup.countConditions(group5Sem);
        int[] sem6Counts = BaseStudentGroup.countConditions(group6Sem);
        int[] sem7Counts = BaseStudentGroup.countConditions(group7Sem);
        int[] sem8Counts = BaseStudentGroup.countConditions(group8Sem);

        // Построение матриц переходов
        TransitionMatrix matrix1to2 = new TransitionMatrix(group1Sem, group2Sem);
        TransitionMatrix matrix2to3 = new TransitionMatrix(group2Sem, group3Sem);
        TransitionMatrix matrix3to4 = new TransitionMatrix(group3Sem, group4Sem);
        TransitionMatrix matrix4to5 = new TransitionMatrix(group4Sem, group5Sem);
        TransitionMatrix matrix5to6 = new TransitionMatrix(group5Sem, group6Sem);
        TransitionMatrix matrix6to7 = new TransitionMatrix(group6Sem, group7Sem);
        TransitionMatrix matrix7to8 = new TransitionMatrix(group7Sem, group8Sem);

        // Прогнозирование
        double[] prediction = matrix1to2.predictFromCounts(controlCounts);
        prediction = matrix2to3.predict(prediction);
        prediction = matrix3to4.predict(prediction);
        prediction = matrix4to5.predict(prediction);
        prediction = matrix5to6.predict(prediction);
        prediction = matrix6to7.predict(prediction);
        prediction = matrix7to8.predict(prediction);

        System.out.println("Прогноз распределения студентов по состояниям в 8 семестре:");
        String[] states = {"T1", "T2", "T3", "T4", "T5", "T6", "T7"};
        for (int i = 0; i < states.length; i++) {
            System.out.printf("%s: %.4f (%.2f%%)\n", states[i], prediction[i], prediction[i] * 100);
        }

        // Вывод матрицы вероятностей переходов
        System.out.println("\nМатрица вероятностей перехода 7→8:");
        double[][] probs = matrix7to8.getTransitionProbabilities();
        for (double[] row : probs) {
            System.out.println(Arrays.toString(row));
        }
    }
}