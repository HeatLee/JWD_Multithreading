package com.markevich.thread;

import com.markevich.entity.Matrix;
import com.markevich.exception.MatrixIndexOutBoundException;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class MatrixSetterThread extends Thread {
    private static final Logger LOGGER = Logger.getLogger(MatrixSetterThread.class);

    private final int nameValue;
    private CountDownLatch barrierBeforeSumCalculating;
    private Matrix matrix;

    public MatrixSetterThread(int name, CountDownLatch calculatingBarrier) {
        super(String.valueOf(name));
        nameValue = name;
        barrierBeforeSumCalculating = calculatingBarrier;
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
            barrierBeforeSumCalculating.countDown();
        } catch (MatrixIndexOutBoundException e) {
            LOGGER.warn(e);
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
