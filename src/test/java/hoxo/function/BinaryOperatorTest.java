package hoxo.function;

import org.junit.Assert;
import org.junit.Test;

import static hoxo.function.Functions.X;
import static hoxo.function.Functions.c;
import static hoxo.function.Functions.cos;
import static hoxo.function.Functions.divide;
import static hoxo.function.Functions.ln;
import static hoxo.function.Functions.multiply;
import static hoxo.function.Functions.pow;
import static hoxo.function.Functions.sin;
import static hoxo.function.Functions.sum;

public class BinaryOperatorTest {

    @Test
    public void multiplyByZeroSimplifyTest() {
        Function f = function();
        Function zero = Constant.ZERO;
        Assert.assertEquals(zero, multiply(f, zero));
        Assert.assertEquals(zero, multiply(zero, f));
        Assert.assertEquals(zero, multiply(zero, zero));
    }

    @Test
    public void multiplyByOneSimplifyTest() {
        Function f = function();
        Function one = Constant.ONE;
        Assert.assertEquals(f, multiply(f, one));
        Assert.assertEquals(f, multiply(one, f));
        Assert.assertEquals(one, multiply(one, one));
    }

    @Test
    public void multiplyEqualsTest() {
        Function f = function();
        Assert.assertEquals(pow(f, 2), multiply(f, f));
    }

    @Test
    public void sumSimplifyTest() {
        Function f = function();
        Function zero = Constant.ZERO;
        Assert.assertEquals(f, sum(f, zero));
        Assert.assertEquals(f, sum(zero, f));
        Assert.assertEquals(zero, sum(zero, zero));
    }

    @Test
    public void sumEqualsTest() {
        Function f = function();
        Assert.assertEquals(multiply(c(2), f), sum(f, f));
    }

    @Test
    public void divideEqualsTest() {
        Function f = function();
        Assert.assertEquals(c(1), divide(f, f));
    }

    @Test
    public void divideConstantsTest() {
        Constant c1 = c(4);
        Constant c2 = c(2);
        Assert.assertEquals(c(2), divide(c1, c2));
    }

    private static Function function() {
        return sin(cos(ln(pow(X, 2))));
    }
}
