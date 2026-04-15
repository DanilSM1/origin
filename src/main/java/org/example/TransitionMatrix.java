package org.example;

import java.util.Arrays;

public class TransitionMatrix {
    private final int size = 7;
    private int[][] transitionCounts;
    private double[][] transitionProbabilities;

    public TransitionMatrix(BaseStudentGroup[] current, BaseStudentGroup[] next) {
        transitionCounts = new int[size][size];
        int minSize = Math.min(current.length, next.length);

        for (int i = 0; i < minSize; i++) {
            if (current[i] != null && next[i] != null) {
                int currentIndex = conditionToIndex(current[i].getCondition());
                int nextIndex = conditionToIndex(next[i].getCondition());

                if (currentIndex != -1 && nextIndex != -1) {
                    transitionCounts[currentIndex][nextIndex]++;
                }
            }
        }

        calculateProbabilities();
    }
    public TransitionMatrix(double[][] matrix){
        this.transitionProbabilities = matrix;
    }

    private void calculateProbabilities() {
        transitionProbabilities = new double[size][size];
        for (int i = 0; i < size; i++) {
            int rowSum = sumRow(i);
            if (rowSum > 0) {
                for (int j = 0; j < size; j++) {
                    transitionProbabilities[i][j] = Math.round(
                            (double) transitionCounts[i][j] / rowSum * 10000.0
                    ) / 10000.0;
                }
            }
        }
    }

    private int sumRow(int row) {
        int sum = 0;
        for (int value : transitionCounts[row]) {
            sum += value;
        }
        return sum;
    }

    private int conditionToIndex(String condition) {
        switch (condition) {
            case "T1": return 0;
            case "T2": return 1;
            case "T3": return 2;
            case "T4": return 3;
            case "T5": return 4;
            case "T6": return 5;
            case "T7": return 6;
            default: return -1;
        }
    }

    public int[][] getTransitionCounts() {
        return transitionCounts;
    }

    public double[][] getTransitionProbabilities() {
        return transitionProbabilities;
    }

    public void printTransitionCounts() {
        System.out.print("      T1  T2  T3  T4  T5  T6  T7\n");
        String[] states = {"T1", "T2", "T3", "T4", "T5", "T6", "T7"};
        for (int i = 0; i < size; i++) {
            System.out.printf("%s ", states[i]);
            for (int j = 0; j < size; j++) {
                System.out.printf("%3d ", transitionCounts[i][j]);
            }
            System.out.printf(" | сумма = %d\n", sumRow(i));
        }
    }
    public double[][] buildPredictionMatrix(int[] currentCounts) {
        double[][] predictionMatrix = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Количество студентов из i, которые перейдут в j
                predictionMatrix[i][j] = transitionProbabilities[i][j] * currentCounts[i];
                // Округляем для красоты
                predictionMatrix[i][j] = Math.round(predictionMatrix[i][j] * 10000.0) / 10000.0;
            }
        }

        return predictionMatrix;
    }
    public double[][] buildPredictionMatrix(double[] currentDistribution) {
        double[][] predictionMatrix = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                predictionMatrix[i][j] = transitionProbabilities[i][j] * currentDistribution[i];
                predictionMatrix[i][j] = Math.round(predictionMatrix[i][j] * 10000.0) / 10000.0;
            }
        }

        return predictionMatrix;
    }
    public double[] getPredictionVector(double[][] predictionMatrix) {
        double[] result = new double[size];

        for (int j = 0; j < size; j++) {          // по столбцам (будущие состояния)
            for (int i = 0; i < size; i++) {      // по строкам (текущие состояния)
                result[j] += predictionMatrix[i][j];
            }
            result[j] = Math.round(result[j] * 10000.0) / 10000.0;
        }

        return result;
    }
    public static void printTransitionMatrix(double[][] matrix){
        for (int i = 0; i < 7; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }
}