package com.markevich;

import com.markevich.dal.FileDataWriter;
import com.markevich.dal.Writer;
import com.markevich.entity.Matrix;
import com.markevich.exception.DataSourceAccessException;
import com.markevich.exception.MatrixIndexOutBoundException;
import com.markevich.thread.MatrixSetterThread;
import com.markevich.util.Converter;
import com.markevich.util.SumCalculator;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Controller {
    private static final int MATRIX_SIZE = 5;
    private static final int AMOUNT_OF_ITERATION = 2;
    private static final int DEFAULT_RANDOM_MAX_BOUND = 10;
    private static final Logger LOGGER = Logger.getLogger(Controller.class);

    public static void main(String[] args) {
        Matrix matrix = Matrix.getInstance();
        Writer writer = new FileDataWriter();
        CountDownLatch countDownLatch = new CountDownLatch(MATRIX_SIZE);
        try {
            writer.clearOutFile();
            matrix.resizeMatrix(MATRIX_SIZE);
            for (int i = 0; i < AMOUNT_OF_ITERATION; i++) {
                for (int j = 0; j < MATRIX_SIZE; j++) {
                    Thread thread = new MatrixSetterThread(generateThreadId(DEFAULT_RANDOM_MAX_BOUND), countDownLatch);
                    thread.start();
                }
                countDownLatch.await();
                matrix.unlockAll();
                String sums = Converter.convertSumsArrayToString(new SumCalculator().calculateSumOfRowsAndColumns());
                writer.writeState(sums);
            }
        } catch (InterruptedException e) {
            LOGGER.warn(e);
        } catch (MatrixIndexOutBoundException ex) {
            LOGGER.warn(ex);
        } catch (DataSourceAccessException exc) {
            LOGGER.warn(exc);
        }
    }

    private static int generateThreadId(int maxBound) {
        int bound = DEFAULT_RANDOM_MAX_BOUND;
        if (maxBound > 0) {
            bound = new Random().nextInt(maxBound);
        }
        return bound;
    }
}
