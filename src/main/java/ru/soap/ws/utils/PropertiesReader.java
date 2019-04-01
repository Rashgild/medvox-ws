package ru.soap.ws.utils;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    public final Properties properties = new Properties();

    public PropertiesReader() {
        final ClassLoader loader = getClass().getClassLoader();
        try (InputStream config = loader.getResourceAsStream("config.properties")) {
            properties.load(config);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }
}
