package org.example;

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

    public double[] predict(double[] currentDistribution) {
        double[] nextDistribution = new double[size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                nextDistribution[j] += currentDistribution[i] * transitionProbabilities[i][j];
            }
        }
        // Округление
        for (int i = 0; i < size; i++) {
            nextDistribution[i] = Math.round(nextDistribution[i] * 10000.0) / 10000.0;
        }
        return nextDistribution;
    }

    public double[] predictFromCounts(int[] currentCounts) {
        double[] currentDistribution = new double[size];
        int total = 0;
        for (int count : currentCounts) total += count;

        if (total > 0) {
            for (int i = 0; i < size; i++) {
                currentDistribution[i] = (double) currentCounts[i] / total;
            }
        }

        return predict(currentDistribution);
    }
}