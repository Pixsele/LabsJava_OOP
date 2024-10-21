package concurrent;

import function.ConstantFunction;
import function.LinkedListTabulatedFunction;
import function.api.TabulatedFunction;

public class ReadWriteTaskExecutor {
    public static void main(String[] args) {

        LinkedListTabulatedFunction list = new LinkedListTabulatedFunction(new ConstantFunction(-1),1,1000,1000);

        Thread readThread = new Thread(new ReadTask(list));
        Thread writeThread = new Thread(new WriteTask(0.5,list));

        readThread.start();
        writeThread.start();
    }
}
