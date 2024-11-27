package function;

@FunctionInfo(name = "Константная функция 1", priority = 2)
public class UnitFunction extends ConstantFunction{
    // Конструктор, который передает 1 в родительский класс
    public UnitFunction() {
        super(1);
    }
}
