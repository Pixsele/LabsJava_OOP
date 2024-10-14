package io;

import function.LinkedListTabulatedFunction;
import function.api.TabulatedFunction;
import function.factory.LinkedListTabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;

import java.io.*;

public class LinkedListTabulatedFunctionSerialization {
    public static void main(String[] args) {
        try(FileOutputStream outputStream = new FileOutputStream("output/serialized linked list functions.bin")){

            BufferedOutputStream stream = new BufferedOutputStream(outputStream);

            double[] xValues = {1.0, 2.0, 3.0, 4.0, 5.0};
            double[] yValues = {1.0, 4.0, 9.0, 16.0, 25.0};

            LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues,yValues);

            TabulatedDifferentialOperator differentialOperator =new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());

            TabulatedFunction diffFunc = differentialOperator.derive(function);
            TabulatedFunction diffFunc2 = differentialOperator.derive(diffFunc);

            FunctionsIO.serialize(stream,function);
            FunctionsIO.serialize(stream,diffFunc);
            FunctionsIO.serialize(stream,diffFunc2);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileInputStream inputStream = new FileInputStream("output/serialized linked list functions.bin")){
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            System.out.println(FunctionsIO.deserialize(bufferedInputStream).toString());
            System.out.println(FunctionsIO.deserialize(bufferedInputStream).toString());
            System.out.println(FunctionsIO.deserialize(bufferedInputStream).toString());
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
