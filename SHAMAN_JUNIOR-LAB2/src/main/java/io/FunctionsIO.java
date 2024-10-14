package io;

import function.Point;
import function.api.TabulatedFunction;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class FunctionsIO {
    private FunctionsIO (){
        throw new UnsupportedOperationException();
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
