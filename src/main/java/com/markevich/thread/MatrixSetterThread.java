package com.markevich.thread;

import com.markevich.entity.Matrix;
import com.markevich.exception.MatrixIndexOutBoundException;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MatrixSetterThread extends Thread {
    private final int nameValue;
    private CyclicBarrier barrierBeforeSumCalculation;
    private Matrix matrix;

    public MatrixSetterThread(int name, CyclicBarrier barrier) {
        super(String.valueOf(name));
        nameValue = name;
        barrierBeforeSumCalculation = barrier;
        matrix = Matrix.getInstance();
    }

    @Override
    public void run() {
        try {
            int indexOnDiagonal = setElementOnMatrixDiagonal();
            if (isColumnOrRowChosen()) {
                setElementOnRandomPlaceInColumn(indexOnDiagonal);
            } else {
                setElementOnRandomPlaceInRow(indexOnDiagonal);
            }
            barrierBeforeSumCalculation.await();
        } catch (MatrixIndexOutBoundException e) {
            //todo log
        } catch (InterruptedException ex) {
            //todo log
        } catch (BrokenBarrierException exc) {
            //todo log
        }
    }

    private void setElementOnRandomPlaceInRow(int rowIndex) throws MatrixIndexOutBoundException {
        int index;
        do {
            index = generateRandomMatrixIndex();
        } while (index == rowIndex || !matrix.setValue(rowIndex, index, nameValue));
    }

    private void setElementOnRandomPlaceInColumn(int columnIndex) throws MatrixIndexOutBoundException {
        int index;
        do {
            index = generateRandomMatrixIndex();
        } while (index == columnIndex || !matrix.setValue(index, columnIndex, nameValue));
    }

    private int setElementOnMatrixDiagonal() throws MatrixIndexOutBoundException{
        int index;
        do {
            index = generateRandomMatrixIndex();
        } while (!matrix.setValue(index, index, nameValue));
        return index;
    }

    private int generateRandomMatrixIndex() {
        return new Random().nextInt(matrix.getSize());
    }

    private boolean isColumnOrRowChosen() {
        return new Random().nextBoolean();
    }
}
