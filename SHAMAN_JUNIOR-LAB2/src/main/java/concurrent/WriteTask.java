package concurrent;

import function.api.TabulatedFunction;

public class WriteTask implements Runnable{

    private final TabulatedFunction function;
    private final double value;

    public WriteTask(double value, TabulatedFunction function) {
        this.value = value;
        this.function = function;
    }

    @Override
    public void run() {
        for(int i = 0; i < function.getCount();i++){
            synchronized (function){
            function.setY(i,value);
            System.out.printf("Writing for index %d complete\n",i);
            }
        }
    }
}
