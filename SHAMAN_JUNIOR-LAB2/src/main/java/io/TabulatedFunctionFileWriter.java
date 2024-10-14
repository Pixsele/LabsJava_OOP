package io;

import function.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TabulatedFunctionFileWriter {
    public static void main(String[] args) {
        try (BufferedWriter arrayWriter = new BufferedWriter(new FileWriter("output/array function.txt"));
             BufferedWriter linkedListWriter = new BufferedWriter(new FileWriter("output/linked list function.txt"))) {

            AbstractTabulatedFunction arrayFunction = new ArrayTabulatedFunction(new SqrFunction(), 0.5, 5.0, 20);
            AbstractTabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(Math::sin, 0.0, Math.PI, 15);

            FunctionsIO.writeTabulatedFunction(arrayWriter, arrayFunction);
            FunctionsIO.writeTabulatedFunction(linkedListWriter, linkedListFunction);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
