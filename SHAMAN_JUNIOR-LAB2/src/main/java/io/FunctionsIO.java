package io;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.security.NoTypePermission;
import function.api.TabulatedFunction;
import function.ArrayTabulatedFunction;
import function.factory.TabulatedFunctionFactory;
import com.thoughtworks.xstream.XStream;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import function.Point;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.io.*;

public final class FunctionsIO {
    private FunctionsIO() {
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

        for (Point point : function) {
            stream.writeDouble(point.x);
            stream.writeDouble(point.y);
        }

        stream.flush();
    }

    public static TabulatedFunction readTabulatedFunction(BufferedReader reader, TabulatedFunctionFactory factory) throws IOException {
        try {
            int count = Integer.parseInt(reader.readLine());

            double[] xValues = new double[count];
            double[] yValues = new double[count];

            NumberFormat numberFormat = NumberFormat.getInstance(Locale.forLanguageTag("ru"));

            for (int i = 0; i < count; i++) {
                String line = reader.readLine();
                String[] values = line.split(" ");

                try {
                    xValues[i] = numberFormat.parse(values[0]).doubleValue();
                    yValues[i] = numberFormat.parse(values[1]).doubleValue();
                } catch (ParseException e) {
                    throw new IOException(e);
                }
            }
            return factory.create(xValues, yValues);
        } catch (IOException | NumberFormatException e) {
            throw new IOException(e);
        }
    }

    public static TabulatedFunction readTabulatedFunction(BufferedInputStream inputStream, TabulatedFunctionFactory factory) throws IOException {
        DataInputStream input = new DataInputStream(inputStream);
        int count = input.readInt();

        double[] xValues = new double[count];
        double[] yValues = new double[count];

        for (int i = 0; i < count; i++) {
            xValues[i] = input.readDouble();
            yValues[i] = input.readDouble();
        }

        return factory.create(xValues, yValues);
    }


    public static void serialize(BufferedOutputStream stream, TabulatedFunction function) throws IOException {
        // Оборачиваем поток в ObjectOutputStream для записи объекта
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream);

        // Записываем объект
        objectOutputStream.writeObject(function);

        // Пробрасываем данные из буфера
        objectOutputStream.flush();
    }

    static TabulatedFunction deserialize(BufferedInputStream stream) throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(stream);

        Object obj = inputStream.readObject();
        return (TabulatedFunction) obj;
    }

    public static void serializeXml(BufferedWriter writer, ArrayTabulatedFunction function) throws IOException {
        XStream xstream = new XStream();
        String xml = xstream.toXML(function);
        writer.write(xml);
        writer.flush();
    }

    public static ArrayTabulatedFunction deserializeXml(BufferedReader reader) {
        XStream xstream = new XStream();

        xstream.addPermission(NoTypePermission.NONE);
        xstream.allowTypes(new Class[]{
                ArrayTabulatedFunction.class,
                double[].class,
                Double.class
        });

        return (ArrayTabulatedFunction) xstream.fromXML(reader);
    }

    public static void serializeJson(BufferedWriter writer, ArrayTabulatedFunction function) throws IOException {
        ObjectMapper obj = new ObjectMapper();
        writer.write(obj.writeValueAsString(function));
        writer.flush();
    }

    public static ArrayTabulatedFunction deserializeJson(BufferedReader reader) throws IOException {
        ObjectMapper obj = new ObjectMapper();
        return obj.readerFor(ArrayTabulatedFunction.class).readValue(reader);
    }
}
