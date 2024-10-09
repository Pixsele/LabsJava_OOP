package function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ZeroFunctionTest {
    @Test
    public void zeroFunctionTest() {
        ZeroFunction zeroFunction = new ZeroFunction();
        assertEquals(zeroFunction.getConstant(), zeroFunction.apply(8), 1e-9);
        assertEquals(zeroFunction.getConstant(), zeroFunction.apply(-15), 1e-9);
        assertEquals(zeroFunction.getConstant(), zeroFunction.apply(0), 1e-9);
    }
}