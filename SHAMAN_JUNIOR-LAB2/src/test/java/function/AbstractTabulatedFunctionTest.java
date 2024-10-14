package function;

import org.junit.jupiter.api.Test;
import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;

import static org.junit.jupiter.api.Assertions.*;

class AbstractTabulatedFunctionTest {

    @Test
    public void testCheckLengthIsTheSame_SameLength() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};
        assertDoesNotThrow(() -> AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues));
    }

    @Test
    public void testCheckLengthIsTheSame_DifferentLength() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0};
        assertThrows(DifferentLengthOfArraysException.class, () ->
                AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues)
        );
    }

    @Test
    public void testCheckSorted_SortedArray() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        assertDoesNotThrow(() -> AbstractTabulatedFunction.checkSorted(xValues));
    }

    @Test
    public void testCheckSorted_UnsortedArray() {
        double[] xValues = {1.0, 3.0, 2.0, 4.0};
        assertThrows(ArrayIsNotSortedException.class, () ->
                AbstractTabulatedFunction.checkSorted(xValues)
        );
    }

    @Test
    public void testCheckSorted_EqualElements() {
        double[] xValues = {1.0, 2.0, 2.0, 4.0};
        assertThrows(ArrayIsNotSortedException.class, () ->
                AbstractTabulatedFunction.checkSorted(xValues)
        );
    }

    @Test
    void testToString() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 8.0, 12.0};

        ArrayTabulatedFunction function1 = new ArrayTabulatedFunction(xValues,yValues);
        LinkedListTabulatedFunction function2 = new LinkedListTabulatedFunction(xValues,yValues);

        String strArrayRef = "ArrayTabulatedFunction size = 3\n[1.0; 4.0]\n[2.0; 8.0]\n[3.0; 12.0]\n";
        String strLinkRef = "LinkedListTabulatedFunction size = 3\n[1.0; 4.0]\n[2.0; 8.0]\n[3.0; 12.0]\n";

        assertEquals(strArrayRef,function1.toString());
        assertEquals(strLinkRef,function2.toString());
    }
}