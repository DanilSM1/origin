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

        int[] controlCounts = BaseStudentGroup.countConditions(controlGroup);


        // Построение матриц переходов
        TransitionMatrix matrix1to2 = new TransitionMatrix(group1Sem, group2Sem);
        TransitionMatrix matrix2to3 = new TransitionMatrix(group2Sem, group3Sem);
        TransitionMatrix matrix3to4 = new TransitionMatrix(group3Sem, group4Sem);
        TransitionMatrix matrix4to5 = new TransitionMatrix(group4Sem, group5Sem);
        TransitionMatrix matrix5to6 = new TransitionMatrix(group5Sem, group6Sem);
        TransitionMatrix matrix6to7 = new TransitionMatrix(group6Sem, group7Sem);
        TransitionMatrix matrix7to8 = new TransitionMatrix(group7Sem, group8Sem);

        // Вывод матрицы колличественных переходов
        System.out.println("Матрица колличественного перехода 1→2");
        matrix1to2.printTransitionCounts();
        System.out.println("Матрица колличественного перехода 2→3");
        matrix2to3.printTransitionCounts();
        System.out.println("Матрица колличественного перехода 3→4");
        matrix3to4.printTransitionCounts();
        System.out.println("Матрица колличественного перехода 4→5");
        matrix4to5.printTransitionCounts();
        System.out.println("Матрица колличественного перехода 5→6");
        matrix5to6.printTransitionCounts();
        System.out.println("Матрица колличественного перехода 6→7");
        matrix6to7.printTransitionCounts();
        System.out.println("Матрица колличественного перехода 7→8");
        matrix7to8.printTransitionCounts();

        // Вывод матрицы вероятностей переходов
        double[][] probs;
        System.out.println("\nМатрица вероятностей перехода 1→2:");
        probs = matrix1to2.getTransitionProbabilities();
        for (double[] row : probs) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("\nМатрица вероятностей перехода 2→3:");
        probs = matrix2to3.getTransitionProbabilities();
        for (double[] row : probs) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("\nМатрица вероятностей перехода 3→4:");
        probs = matrix3to4.getTransitionProbabilities();
        for (double[] row : probs) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("\nМатрица вероятностей перехода 4→5:");
        probs = matrix4to5.getTransitionProbabilities();
        for (double[] row : probs) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("\nМатрица вероятностей перехода 5→6:");
        probs = matrix5to6.getTransitionProbabilities();
        for (double[] row : probs) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("\nМатрица вероятностей перехода 6→7:");
        probs = matrix6to7.getTransitionProbabilities();
        for (double[] row : probs) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("\nМатрица вероятностей перехода 7→8:");
        probs = matrix7to8.getTransitionProbabilities();
        for (double[] row : probs) {
            System.out.println(Arrays.toString(row));
        }

        // Построение прогнозных матриц
        System.out.println("\n=== ПРОГНОЗ 2 СЕМЕСТР ===");
        double[][] predMatrix1 = matrix1to2.buildPredictionMatrix(controlCounts);
        TransitionMatrix.printTransitionMatrix(predMatrix1);
        double[] vector1 = matrix1to2.getPredictionVector(predMatrix1);
        System.out.println("Прогноз количества студентов по состояниям 2 семестра");
        System.out.println(Arrays.toString(vector1));

        System.out.println("\n=== ПРОГНОЗ 3 СЕМЕСТР ===");
        double[][] predMatrix2 = matrix2to3.buildPredictionMatrix(vector1);
        TransitionMatrix.printTransitionMatrix(predMatrix2);
        double[] vector2 = matrix2to3.getPredictionVector(predMatrix2);
        System.out.println("Прогноз количества студентов по состояниям 3 семестра");
        System.out.println(Arrays.toString(vector2));

        System.out.println("\n=== ПРОГНОЗ 4 СЕМЕСТР ===");
        double[][] predMatrix3 = matrix3to4.buildPredictionMatrix(vector2);
        TransitionMatrix.printTransitionMatrix(predMatrix3);
        double[] vector3 = matrix3to4.getPredictionVector(predMatrix3);
        System.out.println("Прогноз количества студентов по состояниям 4 семестра");
        System.out.println(Arrays.toString(vector3));

        System.out.println("\n=== ПРОГНОЗ 5 СЕМЕСТР ===");
        double[][] predMatrix4 = matrix4to5.buildPredictionMatrix(vector3);
        TransitionMatrix.printTransitionMatrix(predMatrix4);
        double[] vector4 = matrix4to5.getPredictionVector(predMatrix4);
        System.out.println("Прогноз количества студентов по состояниям 5 семестра");
        System.out.println(Arrays.toString(vector4));

        System.out.println("\n=== ПРОГНОЗ 6 СЕМЕСТР ===");
        double[][] predMatrix5 = matrix5to6.buildPredictionMatrix(vector4);
        TransitionMatrix.printTransitionMatrix(predMatrix5);
        double[] vector5 = matrix5to6.getPredictionVector(predMatrix5);
        System.out.println("Прогноз количества студентов по состояниям 6 семестра");
        System.out.println(Arrays.toString(vector5));

        System.out.println("\n=== ПРОГНОЗ 7 СЕМЕСТР ===");
        double[][] predMatrix6 = matrix6to7.buildPredictionMatrix(vector5);
        TransitionMatrix.printTransitionMatrix(predMatrix6);
        double[] vector6 = matrix6to7.getPredictionVector(predMatrix6);
        System.out.println("Прогноз количества студентов по состояниям 7 семестра");
        System.out.println(Arrays.toString(vector6));

        System.out.println("\n=== ПРОГНОЗ 8 СЕМЕСТР ===");
        double[][] predMatrix7 = matrix7to8.buildPredictionMatrix(vector6);
        TransitionMatrix.printTransitionMatrix(predMatrix7);
        double[] vector7 = matrix6to7.getPredictionVector(predMatrix7);
        System.out.println("Прогноз количества студентов по состояниям 8 семестра");
        System.out.println(Arrays.toString(vector7));

        double[][] finalTable = new double[7][7];
        finalTable[0] = vector1;
        finalTable[1] = vector2;
        finalTable[2] = vector3;
        finalTable[3] = vector4;
        finalTable[4] = vector5;
        finalTable[5] = vector6;
        finalTable[6] = vector7;
        System.out.println("\nМатрица прогнозных значений: ");
        System.out.println("T1\t\tT2\t\tT3\t\tT4\t\tT5\t\tT6\t\tT7");
        TransitionMatrix.printTransitionMatrix(finalTable);
    }
}