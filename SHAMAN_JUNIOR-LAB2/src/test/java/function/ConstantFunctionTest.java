package function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConstantFunctionTest {
@Test
    public void constantFunction(){
    ConstantFunction constantFunction = new ConstantFunction(100);
    assertEquals(constantFunction.getConstant(), constantFunction.apply(8), 0.001);
    assertEquals(constantFunction.getConstant(), constantFunction.apply(-15), 0.001);
    assertEquals(constantFunction.getConstant(), constantFunction.apply(0), 0.001);
}
}