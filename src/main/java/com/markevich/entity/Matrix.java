package com.markevich.entity;

import com.markevich.exception.MatrixIndexOutBoundException;

import java.util.concurrent.locks.ReentrantLock;

public class Matrix {

    private Element[][] matrix;
    private int size;

    private Matrix() {
    }

    private static class Singleton{
        private static final Matrix INSTANCE = new Matrix();
    }

    private static class Element{
        private int value;
        private ReentrantLock lock;

        private Element() {
            value = 0;
            lock = new ReentrantLock();
        }
    }

    public static Matrix getInstance() {
        return Singleton.INSTANCE;
    }

    public boolean resizeMatrix(int newSize) {
        if (size != 0 || newSize <= 0) {
            return false;
        }
        size = newSize;
        matrix = new Element[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = new Element();
            }
        }
        return true;
    }

    public int getSize() {
        return size;
    }

    public boolean setValue(int row, int column, int value) throws MatrixIndexOutBoundException{
        if (!isValidCoordinates(row, column)) {
            throw new MatrixIndexOutBoundException("Invalid index while setting element value");
        }
        Element updatedElement = matrix[row][column];
        if (updatedElement.lock.tryLock()) {
            updatedElement.value = value;
            return true;
        }
        return false;
    }

    public int getElementValue(int row, int column) throws MatrixIndexOutBoundException{
        if (!isValidCoordinates(row, column)) {
            throw new MatrixIndexOutBoundException("Invalid index while getting element value");
        }
        return matrix[row][column].value;
    }

    public void unlockAll() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j].lock.isLocked()) {
                    matrix[i][j].lock.unlock();
                }
            }
        }
    }

    private boolean isValidCoordinates(int row, int column) {
        return row >= 0 && column >= 0 && row < size && column < size;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                stringBuilder.append(String.format("%4d", matrix[i][j].value));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
