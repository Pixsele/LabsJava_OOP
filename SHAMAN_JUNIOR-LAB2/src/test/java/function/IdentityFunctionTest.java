package function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class IdentityFunctionTest {

    @Test
    void apply() {

        IdentityFunction obj = new IdentityFunction();

        double res = obj.apply(2);

        Assertions.assertEquals(2,res);
    }
}