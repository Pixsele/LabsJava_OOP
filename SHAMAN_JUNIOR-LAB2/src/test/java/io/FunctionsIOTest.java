package io;

import function.ArrayTabulatedFunction;
import function.api.TabulatedFunction;
import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.TabulatedFunctionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FunctionsIOTest {

    private static final Path directory = Paths.get("temp");
    private static final double[] xValues = {1.0, 2.0, 3.0};
    private static final double[] yValues = {1.0, 4.0, 9.0};


    @BeforeEach
    void setup() throws IOException {
        Files.createDirectory(directory);
    }

    @AfterEach
    public void cleanUp() throws IOException {
        try (Stream<Path> files = Files.walk(directory)) {
            files.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(file -> {
                if (!file.delete()) {
                    System.err.println("Failed to delete file: " + file.getPath());
                }
            });
        }
    }

    @Test
    public void testWriteAndReadTabulatedFunction() throws IOException {
        Path tempFile = directory.resolve("test_function.txt");



        TabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            FunctionsIO.writeTabulatedFunction(writer, function);
        }

        TabulatedFunction readFunction;
        try (BufferedReader reader = Files.newBufferedReader(tempFile)) {
            TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
            readFunction = FunctionsIO.readTabulatedFunction(reader, factory);
        }

        assertEquals(function.getCount(), readFunction.getCount());
        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(function.getX(i), readFunction.getX(i), 1e-9);
            assertEquals(function.getY(i), readFunction.getY(i), 1e-9);
        }
    }

    @Test
    public void testSerializeAndDeserializeFunction() throws IOException, ClassNotFoundException {
        Path tempFile = directory.resolve("test_function.bin");

        TabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(tempFile))) {
            FunctionsIO.serialize(bos, function);
        }

        TabulatedFunction deserializedFunction;
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(tempFile))) {
            deserializedFunction = FunctionsIO.deserialize(bis);
        }

        assertEquals(function.getCount(), deserializedFunction.getCount());
        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(function.getX(i), deserializedFunction.getX(i), 1e-9);
            assertEquals(function.getY(i), deserializedFunction.getY(i), 1e-9);
        }
    }

    @Test
    public void testWriteAndReadBinaryFunction() throws IOException {
        Path tempFile = directory.resolve("test_function_binary.bin");

        TabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(tempFile))) {
            FunctionsIO.writeTabulatedFunction(bos, function);
        }

        TabulatedFunction readFunction;
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(tempFile))) {
            TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
            readFunction = FunctionsIO.readTabulatedFunction(bis, factory);
        }

        assertEquals(function.getCount(), readFunction.getCount());
        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(function.getX(i), readFunction.getX(i), 1e-9);
            assertEquals(function.getY(i), readFunction.getY(i), 1e-9);
        }
    }
}