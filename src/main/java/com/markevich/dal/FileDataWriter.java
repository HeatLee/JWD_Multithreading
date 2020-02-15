package com.markevich.dal;

import com.markevich.entity.Matrix;
import com.markevich.exception.DataSourceAccessException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileDataWriter implements Writer {
    private static final String EMPTY_STR = "";

    private Matrix matrix = Matrix.getInstance();
    private File file = new File("out.txt");

    @Override
    public void writeState(String sums) throws DataSourceAccessException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append(matrix.toString());
            writer.append(sums);
        } catch (IOException e) {
            throw new DataSourceAccessException(e);
        }
    }

    public void clearOutFile() throws DataSourceAccessException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(EMPTY_STR);
        } catch (IOException e) {
            throw new DataSourceAccessException(e);
        }
    }
}
