package io;

import function.api.TabulatedFunction;
import function.Point;

import java.io.*;

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

    public static void writeTabulatedFunction(BufferedOutputStream outputStream, TabulatedFunction function) throws IOException {
        DataOutputStream stream = new DataOutputStream(outputStream);
        stream.writeInt(function.getCount());

        for(Point point: function){
            stream.writeDouble(point.x);
            stream.writeDouble(point.y);
        }

        stream.flush();
    }
}
