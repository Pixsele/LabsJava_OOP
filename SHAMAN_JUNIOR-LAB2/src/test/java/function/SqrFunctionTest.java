package function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SqrFunctionTest {

    @Test
    void apply() {
        SqrFunction a = new SqrFunction();

        assertEquals(25, a.apply(5), 1e-9);
        assertEquals(16, a.apply(-4), 1e-9);
        assertEquals(6.25, a.apply(2.5), 1e-9);
        assertEquals(0, a.apply(0), 1e-9);
    }
}