package com.markevich.util;

import com.markevich.entity.Matrix;
import com.markevich.exception.MatrixIndexOutBoundException;

public class SumCalculator {
    private Matrix matrix = Matrix.getInstance();
    public static final int THREAD_ID_INDEX = 0;
    public static final int THREAD_SUM_INDEX = 1;

    public int[][] calculateSumOfRowsAndColumns() throws MatrixIndexOutBoundException{
        int[][] result = new int[matrix.getSize()][2];
        for (int i = 0; i < matrix.getSize(); i++) {
            result[i][THREAD_ID_INDEX] = matrix.getElementValue(i, i);
            result[i][THREAD_SUM_INDEX] = calculateSumOfColumn(i) + calculateSumOfRow(i);
        }
        return result;
    }

    private int calculateSumOfRow(int rowIndex) throws MatrixIndexOutBoundException {
        int sum = 0;
        for (int i = 0; i < matrix.getSize(); i++) {
            sum += matrix.getElementValue(rowIndex, i);
        }
        return sum;
    }

    private int calculateSumOfColumn(int columnIndex) throws MatrixIndexOutBoundException {
        int sum = 0;
        for (int i = 0; i < matrix.getSize(); i++) {
            sum += matrix.getElementValue(i, columnIndex);
        }
        return sum;
    }
}
