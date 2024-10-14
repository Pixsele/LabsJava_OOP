package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.LinkedListTabulatedFunctionFactory;
import function.api.TabulatedFunction;

public class TabulatedFunctionFileReader {
    public static void main(String[] args) {
        try (BufferedReader arrayReader = new BufferedReader(new FileReader("input/function.txt"));
             BufferedReader linkedListReader = new BufferedReader(new FileReader("input/function.txt"))) {

            TabulatedFunction arrayFunction = FunctionsIO.readTabulatedFunction(arrayReader, new ArrayTabulatedFunctionFactory());
            System.out.println("ArrayTabulatedFunction:");
            System.out.println(arrayFunction);


            TabulatedFunction linkedListFunction = FunctionsIO.readTabulatedFunction(linkedListReader, new LinkedListTabulatedFunctionFactory());
            System.out.println("LinkedListTabulatedFunction:");
            System.out.println(linkedListFunction);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
