package io;

import function.api.TabulatedFunction;
import function.Point;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public final class FunctionsIO {
    private FunctionsIO (){
        throw new UnsupportedOperationException();
    }

    public static void writeTabulatedFunction(BufferedWriter writer, TabulatedFunction function) throws IOException {
        PrintWriter printWriter = new PrintWriter(writer);
        // Записываем количество точек
        printWriter.println(function.getCount());

        // Записываем каждую точку
        for (Point point : function) {
            printWriter.printf("%f %f%n", point.x, point.y);
        }

        // Пробрасываем данные из буфера в поток
        printWriter.flush();
    }
}
