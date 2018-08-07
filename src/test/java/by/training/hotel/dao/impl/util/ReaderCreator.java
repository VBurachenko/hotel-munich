package by.training.hotel.dao.impl.util;

import by.training.hotel.dao.impl.TestDatabaseManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public final class ReaderCreator {

    private ReaderCreator() {
    }

    public static BufferedReader getBufferedReaderForFile(String sourceFilePath){

        ClassLoader loader = TestDatabaseManager.class.getClassLoader();

        InputStream requiredInputStream = loader.getResourceAsStream(sourceFilePath);

        Reader reader = new InputStreamReader(requiredInputStream);

        return new BufferedReader(reader);
    }
}
