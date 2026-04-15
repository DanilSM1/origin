package org.example;

public abstract class BaseStudentGroup implements StudentGroup{
    private String condition;
    public BaseStudentGroup(){

    }
    public abstract double avgBall();

    @Override
    public String getCondition() {
        double avg = avgBall();

        if (avg <= 5 && avg >= 4.5) {
            return "T1";
        } else if (avg >= 4 && avg < 4.5) {
            return "T2";
        } else if (avg < 4 && avg >= 3.5) {
            return "T3";
        } else if (avg < 3.5 && avg >= 3) {
            return "T4";
        } else if (avg < 3 && avg >= 2.5) {
            return "T5";
        } else if (avg < 2.5 && avg >= 1.5) {
            return "T6";
        } else {
            return "T7";
        }
    }

    public static int[] countConditions(BaseStudentGroup[] groups){
        if(groups == null || groups.length == 0){
            return new int[7];
        }
        int[] counts = new int[7];
        for(BaseStudentGroup group : groups){
            if (group != null){
                String condition = group.getCondition();
                switch (condition){
                    case "T1" : counts[0]++;break;
                    case "T2" : counts[1]++;break;
                    case "T3" : counts[2]++;break;
                    case "T4" : counts[3]++;break;
                    case "T5" : counts[4]++;break;
                    case "T6" : counts[5]++;break;
                    case "T7" : counts[6]++;break;
                }

            }
        }
        return counts;
    }
}
