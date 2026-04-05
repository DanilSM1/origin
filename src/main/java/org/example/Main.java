package org.example;

import java.util.Arrays;


import static org.example.ControlGroup.parseControlGroup;
import static org.example.ControlGroup.countConditions;
import static org.example.ExperimentalGroups1Sem.countConditions;
import static org.example.ExperimentalGroups1Sem.parseExperimentalGroups1Sem;
import static org.example.ExperimentalGroups2Sem.countConditions;
import static org.example.ExperimentalGroups2Sem.parseExperimentalGroups2Sem;
import static org.example.ExperimentalGroups3Sem.countConditions;
import static org.example.ExperimentalGroups3Sem.parseExperimentalGroups3Sem;
import static org.example.ExperimentalGroups4Sem.countConditions;
import static org.example.ExperimentalGroups4Sem.parseExperimentalGroups4Sem;
import static org.example.ExperimentalGroups5Sem.countConditions;
import static org.example.ExperimentalGroups5Sem.parseExperimentalGroups5Sem;
import static org.example.ExperimentalGroups6Sem.countConditions;
import static org.example.ExperimentalGroups6Sem.parseExperimentalGroups6Sem;
import static org.example.ExperimentalGroups7Sem.countConditions;
import static org.example.ExperimentalGroups7Sem.parseExperimentalGroups7Sem;
import static org.example.ExperimentalGroups8Sem.countConditions;
import static org.example.ExperimentalGroups8Sem.parseExperimentalGroups8Sem;

public class Main {
    private static int conditionToIndex(String condition) {
        switch (condition) {
            case "T1":
                return 0;
            case "T2":
                return 1;
            case "T3":
                return 2;
            case "T4":
                return 3;
            case "T5":
                return 4;
            case "T6":
                return 5;
            case "T7":
                return 6;
            default:
                return -1;
        }
    }

    public static int[][] buildTransitionMatrix1to2(ExperimentalGroups1Sem[] groups1, ExperimentalGroups2Sem[] groups2) {
        int size = Math.min(groups1.length, groups2.length);
        int[][] matrix = new int[7][7];

        for (int i = 0; i < size; i++) {
            String currentCondition = groups1[i].condition;
            String futureCondition = groups2[i].condition;

            int currentIndex = conditionToIndex(currentCondition);
            int futureIndex = conditionToIndex(futureCondition);

            if (currentIndex != -1 && futureIndex != -1) {
                matrix[currentIndex][futureIndex]++;
            }
        }
        return matrix;
    }

    public static int[][] buildTransitionMatrix2to3(ExperimentalGroups2Sem[] groups1, ExperimentalGroups3Sem[] groups2) {
        int size = Math.min(groups1.length, groups2.length);
        int[][] matrix = new int[7][7];

        for (int i = 0; i < size; i++) {
            String currentCondition = groups1[i].condition;
            String futureCondition = groups2[i].condition;

            int currentIndex = conditionToIndex(currentCondition);
            int futureIndex = conditionToIndex(futureCondition);

            if (currentIndex != -1 && futureIndex != -1) {
                matrix[currentIndex][futureIndex]++;
            }
        }
        return matrix;
    }

    public static int[][] buildTransitionMatrix3to4(ExperimentalGroups3Sem[] groups1, ExperimentalGroups4Sem[] groups2) {
        int size = Math.min(groups1.length, groups2.length);
        int[][] matrix = new int[7][7];

        for (int i = 0; i < size; i++) {
            String currentCondition = groups1[i].condition;
            String futureCondition = groups2[i].condition;

            int currentIndex = conditionToIndex(currentCondition);
            int futureIndex = conditionToIndex(futureCondition);

            if (currentIndex != -1 && futureIndex != -1) {
                matrix[currentIndex][futureIndex]++;
            }
        }
        return matrix;
    }

    public static int[][] buildTransitionMatrix4to5(ExperimentalGroups4Sem[] groups1, ExperimentalGroups5Sem[] groups2) {
        int size = Math.min(groups1.length, groups2.length);
        int[][] matrix = new int[7][7];

        for (int i = 0; i < size; i++) {
            String currentCondition = groups1[i].condition;
            String futureCondition = groups2[i].condition;

            int currentIndex = conditionToIndex(currentCondition);
            int futureIndex = conditionToIndex(futureCondition);

            if (currentIndex != -1 && futureIndex != -1) {
                matrix[currentIndex][futureIndex]++;
            }
        }
        return matrix;
    }

    public static int[][] buildTransitionMatrix5to6(ExperimentalGroups5Sem[] groups1, ExperimentalGroups6Sem[] groups2) {
        int size = Math.min(groups1.length, groups2.length);
        int[][] matrix = new int[7][7];

        for (int i = 0; i < size; i++) {
            String currentCondition = groups1[i].condition;
            String futureCondition = groups2[i].condition;

            int currentIndex = conditionToIndex(currentCondition);
            int futureIndex = conditionToIndex(futureCondition);

            if (currentIndex != -1 && futureIndex != -1) {
                matrix[currentIndex][futureIndex]++;
            }
        }
        return matrix;
    }

    public static int[][] buildTransitionMatrix6to7(ExperimentalGroups6Sem[] groups1, ExperimentalGroups7Sem[] groups2) {
        int size = Math.min(groups1.length, groups2.length);
        int[][] matrix = new int[7][7];

        for (int i = 0; i < size; i++) {
            String currentCondition = groups1[i].condition;
            String futureCondition = groups2[i].condition;

            int currentIndex = conditionToIndex(currentCondition);
            int futureIndex = conditionToIndex(futureCondition);

            if (currentIndex != -1 && futureIndex != -1) {
                matrix[currentIndex][futureIndex]++;
            }
        }
        return matrix;
    }

    public static int[][] buildTransitionMatrix7to8(ExperimentalGroups7Sem[] groups1, ExperimentalGroups8Sem[] groups2) {
        int size = Math.min(groups1.length, groups2.length);
        int[][] matrix = new int[7][7];

        for (int i = 0; i < size; i++) {
            String currentCondition = groups1[i].condition;
            String futureCondition = groups2[i].condition;

            int currentIndex = conditionToIndex(currentCondition);
            int futureIndex = conditionToIndex(futureCondition);

            if (currentIndex != -1 && futureIndex != -1) {
                matrix[currentIndex][futureIndex]++;
            }
        }
        return matrix;
    }

    public static int sumOfRow(int[][] matrix, int rowIndex) {
        int sum = 0;
        for (int value : matrix[rowIndex]
        ) {
            sum += value;
        }
        return sum;
    }

    public static double[][] calculateChanceOfChangeCondition(int[][] matrix) {
        double[][] doubleMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int sumOfLine = sumOfRow(matrix, i);
                if (sumOfLine != 0) {
                    double value = (double) matrix[i][j] / sumOfLine;
                    doubleMatrix[i][j] = Math.round(value * 10000.0) / 10000.0;
                } else {
                    doubleMatrix[i][j] = 0;
                }
            }
        }
        return doubleMatrix;
    }

    public static void main(String[] args) {

        ExperimentalGroups1Sem[] groups1Sem = parseExperimentalGroups1Sem("D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\input.json");
        ExperimentalGroups2Sem[] groups2Sem = parseExperimentalGroups2Sem("D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\2Sem.json");
        ExperimentalGroups3Sem[] groups3Sem = parseExperimentalGroups3Sem("D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\3Sem.json");
        ExperimentalGroups4Sem[] groups4Sem = parseExperimentalGroups4Sem("D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\4Sem.json");
        ExperimentalGroups5Sem[] groups5Sem = parseExperimentalGroups5Sem("D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\5Sem.json");
        ExperimentalGroups6Sem[] groups6Sem = parseExperimentalGroups6Sem("D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\6Sem.json");
        ExperimentalGroups7Sem[] groups7Sem = parseExperimentalGroups7Sem("D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\7Sem.json");
        ExperimentalGroups8Sem[] groups8Sem = parseExperimentalGroups8Sem("D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\8Sem.json");
        int[][] countOfConditionsStudents = new int[8][7];
        int[] counts1sem = countConditions(groups1Sem);
        int[] counts2sem = countConditions(groups2Sem);
        int[] counts3sem = countConditions(groups3Sem);
        int[] counts4sem = countConditions(groups4Sem);
        int[] counts5sem = countConditions(groups5Sem);
        int[] counts6sem = countConditions(groups6Sem);
        int[] counts7sem = countConditions(groups7Sem);
        int[] counts8sem = countConditions(groups8Sem);
        for (int i = 0; i < 7; i++) {
            countOfConditionsStudents[0][i] = counts1sem[i];
        }
        for (int i = 0; i < 7; i++) {
            countOfConditionsStudents[1][i] = counts2sem[i];
        }
        for (int i = 0; i < 7; i++) {
            countOfConditionsStudents[2][i] = counts3sem[i];
        }
        for (int i = 0; i < 7; i++) {
            countOfConditionsStudents[3][i] = counts4sem[i];
        }
        for (int i = 0; i < 7; i++) {
            countOfConditionsStudents[4][i] = counts5sem[i];
        }
        for (int i = 0; i < 7; i++) {
            countOfConditionsStudents[5][i] = counts6sem[i];
        }
        for (int i = 0; i < 7; i++) {
            countOfConditionsStudents[6][i] = counts7sem[i];
        }
        for (int i = 0; i < 7; i++) {
            countOfConditionsStudents[7][i] = counts8sem[i];
        }

//        for (int i = 0; i < countOfConditionsStudents.length; i++) {
//            System.out.println(Arrays.toString(countOfConditionsStudents[i]));
//        }
        // Построение матриц количества переходов из одного состояния в другое
        int[][] transitionMatrix1to2 = buildTransitionMatrix1to2(groups1Sem, groups2Sem);
        int[][] transitionMatrix2to3 = buildTransitionMatrix2to3(groups2Sem, groups3Sem);
        int[][] transitionMatrix3to4 = buildTransitionMatrix3to4(groups3Sem, groups4Sem);
        int[][] transitionMatrix4to5 = buildTransitionMatrix4to5(groups4Sem, groups5Sem);
        int[][] transitionMatrix5to6 = buildTransitionMatrix5to6(groups5Sem, groups6Sem);
        int[][] transitionMatrix6to7 = buildTransitionMatrix6to7(groups6Sem, groups7Sem);
        int[][] transitionMatrix7to8 = buildTransitionMatrix7to8(groups7Sem, groups8Sem);
        double[][] matrixOfChance1to2 = calculateChanceOfChangeCondition(transitionMatrix1to2);
        double[][] matrixOfChance2to3 = calculateChanceOfChangeCondition(transitionMatrix2to3);
        double[][] matrixOfChance3to4 = calculateChanceOfChangeCondition(transitionMatrix3to4);
        double[][] matrixOfChance4to5 = calculateChanceOfChangeCondition(transitionMatrix4to5);
        double[][] matrixOfChance5to6 = calculateChanceOfChangeCondition(transitionMatrix5to6);
        double[][] matrixOfChance6to7 = calculateChanceOfChangeCondition(transitionMatrix6to7);
        double[][] matrixOfChance7to8 = calculateChanceOfChangeCondition(transitionMatrix7to8);

        ControlGroup[] controlGroup = parseControlGroup("D:\\Учеба\\Мага\\PredictionTest2\\src\\main\\resources\\controlGroup.json");
        int [] countsOfControlGroupConditions = countConditions(controlGroup);


        System.out.println("Матрица вероятности перехода 1-2 сем");
        for (int i = 0; i < 7; i++) {
            System.out.print(Arrays.toString(matrixOfChance1to2[i]));
            System.out.println();}

//        System.out.println("Матрица количества переходов 1-2 семестр");
//        for (int i = 0; i < transitionMatrix1to2.length; i++) {
//            System.out.println(Arrays.toString(transitionMatrix1to2[i]));
//        }
//
//        System.out.println("Матрица количества переходов 2-3 семестр");
//        for (int i = 0; i < transitionMatrix2to3.length; i++) {
//            System.out.println(Arrays.toString(transitionMatrix2to3[i]));
//        }
//
//        System.out.println("Матрица количества переходов 3-4 семестр");
//        for (int i = 0; i < transitionMatrix3to4.length; i++) {
//            System.out.println(Arrays.toString(transitionMatrix3to4[i]));
//        }
//
//        System.out.println("Матрица количества переходов 4-5 семестр");
//        for (int i = 0; i < transitionMatrix4to5.length; i++) {
//            System.out.println(Arrays.toString(transitionMatrix4to5[i]));
//        }
//
//        System.out.println("Матрица количества переходов 5-6 семестр");
//        for (int i = 0; i < transitionMatrix5to6.length; i++) {
//            System.out.println(Arrays.toString(transitionMatrix5to6[i]));
//        }
//
//        System.out.println("Матрица количества переходов 6-7 семестр");
//        for (int i = 0; i < transitionMatrix6to7.length; i++) {
//            System.out.println(Arrays.toString(transitionMatrix6to7[i]));
//        }
//
//        System.out.println("Матрица количества переходов 7-8 семестр");
//        for (int i = 0; i < transitionMatrix7to8.length; i++) {
//            System.out.println(Arrays.toString(transitionMatrix7to8[i]));
//        }
    }
}



