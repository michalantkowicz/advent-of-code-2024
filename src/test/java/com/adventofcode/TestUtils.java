package com.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TestUtils {
    public static String readFile(String path) {
        try {
            Path filePath = Paths.get(Objects.requireNonNull(TestUtils.class.getResource(path)).toURI());
            return Files.readString(filePath);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getColumn(String path, int columnIndex) {
        String input = readFile(path);
        List<String> result = new ArrayList<>();
        for (String line : input.split("\n")) {
            result.add(line.split("\\s+")[columnIndex]);
        }
        return result;
    }

    public static List<Integer> getIntColumn(String path, int columnIndex) {
        return new ArrayList<>(getColumn(path, columnIndex).stream().map(Integer::valueOf).toList());
    }

    public static List<List<String>> getRows(String path, String delimiter) {
        String input = readFile(path);
        List<List<String>> result = new ArrayList<>();
        input.lines().forEach(line -> result.add(Arrays.asList(line.split(delimiter))));
        return result;
    }

    public static List<List<Integer>> getIntRows(String path, String delimiter) {
        return new ArrayList<>(
                getRows(path, delimiter).stream()
                        .map(line -> new ArrayList<>(
                                line.stream().map(Integer::parseInt).toList()
                        ))
                        .toList()
        );
    }

    /**
     * @return Lists of lists integers - in each line these integers are split by whitespaces
     */
    public static List<List<Integer>> getIntRows(String path) {
        return getIntRows(path, "\\s+");
    }
}
