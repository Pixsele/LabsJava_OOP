package io;

import org.junit.jupiter.api.Test;
import java.io.*;
import function.ArrayTabulatedFunction;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTabulatedFunctionJSONTest {
    @Test
    public void testSerializeDeserializeJSON() throws IOException {
        double[] xValues = {1.0, 3.0, 5.0};
        double[] yValues = {1.0, 9.0, 25.0};
        ArrayTabulatedFunction originalFunction = new ArrayTabulatedFunction(xValues, yValues);

        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        FunctionsIO.serializeJson(writer, originalFunction);

        StringReader stringReader = new StringReader(stringWriter.toString());
        BufferedReader reader = new BufferedReader(stringReader);
        ArrayTabulatedFunction deserializedFunction = FunctionsIO.deserializeJson(reader);

        assertArrayEquals(originalFunction.getXValues(), deserializedFunction.getXValues(), 1e-9);
        assertArrayEquals(originalFunction.getYValues(), deserializedFunction.getYValues(), 1e-9);
    }
}