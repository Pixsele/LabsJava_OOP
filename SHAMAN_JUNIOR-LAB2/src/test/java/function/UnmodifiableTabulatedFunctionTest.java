package function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnmodifiableTabulatedFunctionTest {

    @Test
    public void testApplyMethodOnArrayTabulatedFunction() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        UnmodifiableTabulatedFunction unmodifiableFunction = new UnmodifiableTabulatedFunction(arrayFunction);

        // Проверка работы метода apply()
        assertEquals(4.0, unmodifiableFunction.apply(1.0), 0.0001);
        assertEquals(5.0, unmodifiableFunction.apply(2.0), 0.0001);
        assertEquals(6.0, unmodifiableFunction.apply(3.0), 0.0001);
    }

    @Test
    public void testApplyMethodOnLinkedListTabulatedFunction() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};
        LinkedListTabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(xValues, yValues);
        UnmodifiableTabulatedFunction unmodifiableFunction = new UnmodifiableTabulatedFunction(linkedListFunction);

        // Проверка работы метода apply()
        assertEquals(4.0, unmodifiableFunction.apply(1.0), 0.0001);
        assertEquals(5.0, unmodifiableFunction.apply(2.0), 0.0001);
        assertEquals(6.0, unmodifiableFunction.apply(3.0), 0.0001);
    }

    @Test
    public void testSetYThrowsExceptionOnArrayTabulatedFunction() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        UnmodifiableTabulatedFunction unmodifiableFunction = new UnmodifiableTabulatedFunction(arrayFunction);

        // Проверка, что setY выбрасывает UnsupportedOperationException
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableFunction.setY(0, 10.0));
    }

    @Test
    public void testSetYThrowsExceptionOnLinkedListTabulatedFunction() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};
        LinkedListTabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(xValues, yValues);
        UnmodifiableTabulatedFunction unmodifiableFunction = new UnmodifiableTabulatedFunction(linkedListFunction);

        // Проверка, что setY выбрасывает UnsupportedOperationException
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableFunction.setY(0, 10.0));
    }
}