package com.cprohinig.assertj.testutils;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtils {

    private TestUtils() {}

    public static String getFileContent(String fileName) throws Exception {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        URL resource = contextClassLoader.getResource("testData/" + fileName);
        Path path = Paths.get(resource.getPath());
        return new String(Files.readAllBytes(path));
    }
}
