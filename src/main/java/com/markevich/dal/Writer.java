package com.markevich.dal;

import com.markevich.exception.DataSourceAccessException;

public interface Writer {
    void writeState(String sums) throws DataSourceAccessException;

    void clearOutFile() throws DataSourceAccessException;
}
