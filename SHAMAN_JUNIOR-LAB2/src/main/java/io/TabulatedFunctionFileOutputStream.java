package io;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TabulatedFunctionFileOutputStream {
    public static void main(String[] args) {
        try (FileOutputStream outputStream1 = new FileOutputStream("output/array function.bin");
             FileOutputStream outputStream2 = new FileOutputStream("output/linked list function.bin")){

            BufferedOutputStream bufferedOutputStream1 = new BufferedOutputStream(outputStream1);
            BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(outputStream2);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
