package concurrent;

import function.ArrayTabulatedFunction;
import function.api.TabulatedFunction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class IntegralTaskExecutorTest {


    private IntegralTaskExecutor executor;

    @Test
    public void testIntegrationCountOfThreads() throws ExecutionException, InterruptedException {
        executor = new IntegralTaskExecutor(10);

        // Создаем функцию с константными значениями y = 5 на интервале [0, 10]
        double[] xValues = {0.0, 5.0, 10.0};
        double[] yValues = {5.0, 5.0, 5.0};
        TabulatedFunction constantFunction = new ArrayTabulatedFunction(xValues, yValues);

        // Интеграл от константной функции y = 5 на интервале [0, 10] должен быть 50
        double result = executor.itegrate(constantFunction);
        assertEquals(50.0, result, 0.1);

        executor.shutdown();
    }

    @BeforeEach
    void setup(){
        executor = new IntegralTaskExecutor();
    }

    @AfterEach
    void shutdown(){
        executor.shutdown();
    }

    @Test
    public void testIntegrationOfConstantFunction() throws ExecutionException, InterruptedException {
        // Создаем функцию с константными значениями y = 5 на интервале [0, 10]
        double[] xValues = {0.0, 5.0, 10.0};
        double[] yValues = {5.0, 5.0, 5.0};
        TabulatedFunction constantFunction = new ArrayTabulatedFunction(xValues, yValues);

        // Интеграл от константной функции y = 5 на интервале [0, 10] должен быть 50
        double result = executor.itegrate(constantFunction);
        assertEquals(50.0, result, 0.1);
    }

    @Test
    public void testIntegrationOfLinearFunction() throws ExecutionException, InterruptedException {
        // Создаем функцию y = x на интервале [0, 10]
        double[] xValues = {0, 1};
        double[] yValues = {0, 1};
        TabulatedFunction linearFunction = new ArrayTabulatedFunction(xValues, yValues);

        double result = executor.itegrate(linearFunction);
        assertEquals(0.5, result, 0.1);
    }

    @Test
    public void testIntegrationOfQuadraticFunction() throws ExecutionException, InterruptedException {
        // Создаем квадратичную функцию y = x^2 на интервале [0, 10]
        double[] xValues = {0.0, 0.5, 1.0};
        double[] yValues = {0.0, 0.25, 1.0};
        TabulatedFunction quadraticFunction = new ArrayTabulatedFunction(xValues, yValues);

        // Интеграл от квадратичной функции y = x^2 на интервале [0, 10] должен быть ~333.33
        double result = executor.itegrate(quadraticFunction);
        double res = 1.0/3.0;
        assertEquals(res, result, 0.1);
    }

    @Test
    public void testIntegrationOfNegativeConstantFunction() throws ExecutionException, InterruptedException {
        // Создаем функцию с константными значениями y = -3 на интервале [0, 10]
        double[] xValues = {0.0, 5.0, 10.0};
        double[] yValues = {-3.0, -3.0, -3.0};
        TabulatedFunction constantFunction = new ArrayTabulatedFunction(xValues, yValues);

        // Интеграл от константной функции y = -3 на интервале [0, 10] должен быть -30
        double result = executor.itegrate(constantFunction);
        assertEquals(-30.0, result, 0.1);
    }

    @Test
    public void testThows(){

        double[] xValues = {0.0, 5.0, 10.0};
        double[] yValues = {-3.0, -3.0, -3.0};
        TabulatedFunction constantFunction = new ArrayTabulatedFunction(xValues, yValues);

        assertThrows(IllegalArgumentException.class, () -> new IntegralTask(constantFunction,10,2));
        assertThrows(IllegalArgumentException.class, ()-> new IntegralTask(null,1,10));
        assertThrows(IllegalArgumentException.class, ()-> new IntegralTask(constantFunction,1,10).solveByTrapezoidalRule(-1));
    }



}