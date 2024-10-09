package function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitFunctionTest {
    @Test
    public void unitFunctionTest() {
        UnitFunction unitFunction = new UnitFunction();
        assertEquals(unitFunction.getConstant(), unitFunction.apply(8), 1e-9);
        assertEquals(unitFunction.getConstant(), unitFunction.apply(-15), 1e-9);
        assertEquals(unitFunction.getConstant(), unitFunction.apply(1), 1e-9);
    }
}