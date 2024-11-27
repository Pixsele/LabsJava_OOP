package function;

@FunctionInfo(name = "Константная функция 0", priority = 2)
public class ZeroFunction extends ConstantFunction{
    // Конструктор, который передает 0 в родительский класс
    public ZeroFunction() {
        super(0);
    }
}
