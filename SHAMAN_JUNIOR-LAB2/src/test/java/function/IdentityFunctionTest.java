package function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentityFunctionTest {

    @Test
    void apply() {

        IdentityFunction obj = new IdentityFunction();

        assertEquals(2,obj.apply(2));
        assertEquals(3,obj.apply(3));
        assertEquals(2.1,obj.apply(2.1));
        assertEquals(-11111,obj.apply(-11111));
    }
}