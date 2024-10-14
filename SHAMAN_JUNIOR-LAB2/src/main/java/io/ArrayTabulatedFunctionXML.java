package io;

import java.io.*;
import function.ArrayTabulatedFunction;
import function.SqrFunction;

public class ArrayTabulatedFunctionXML {
    public static void main(String[] args) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output/serialized array xml.xml"))) {
            ArrayTabulatedFunction function = new ArrayTabulatedFunction(new SqrFunction(), 10, 30, 3);
            FunctionsIO.serializeXml(bufferedWriter, function);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("output/serialized array xml.xml"))) {
            ArrayTabulatedFunction function = FunctionsIO.deserializeXml(bufferedReader);

            System.out.println(function);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}