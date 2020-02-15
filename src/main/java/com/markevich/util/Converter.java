package com.markevich.util;

public class Converter {
    private static final String STRING_FORMAT = "Thread %d has sum = %d;\n";

    public static String convertSumsArrayToString(int[][] sums) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] sum : sums) {
            stringBuilder.append(String.format(STRING_FORMAT,
                    sum[SumCalculator.THREAD_ID_INDEX],
                    sum[SumCalculator.THREAD_SUM_INDEX]));
        }
        return stringBuilder.toString();
    }
}
