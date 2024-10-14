package io;

import function.ArrayTabulatedFunction;

import java.io.*;

public class ArrayTabulatedFunctionJSON {
    public static void main(String[] args) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output/serialized array json.json"))) {
            ArrayTabulatedFunction function = new ArrayTabulatedFunction(new double[]{1.0, 2.0, 3.0}, new double[]{2.0, 4.0, 6.0});
            FunctionsIO.serializeJson(bufferedWriter, function);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("output/serialized array json.json"))) {
            ArrayTabulatedFunction function = FunctionsIO.deserializeJson(bufferedReader);
            System.out.println(function);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
