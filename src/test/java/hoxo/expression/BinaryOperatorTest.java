package hoxo.expression;

import org.junit.Assert;
import org.junit.Test;

import static hoxo.expression.Functions.X;
import static hoxo.expression.Functions.c;
import static hoxo.expression.Functions.cos;
import static hoxo.expression.Functions.divide;
import static hoxo.expression.Functions.ln;
import static hoxo.expression.Functions.multiply;
import static hoxo.expression.Functions.pow;
import static hoxo.expression.Functions.sin;
import static hoxo.expression.Functions.sum;

public class BinaryOperatorTest {

    @Test
    public void multiplyByZeroSimplifyTest() {
        Expression f = function();
        Expression zero = Constant.ZERO;
        Assert.assertEquals(zero, multiply(f, zero));
        Assert.assertEquals(zero, multiply(zero, f));
        Assert.assertEquals(zero, multiply(zero, zero));
    }

    @Test
    public void multiplyByOneSimplifyTest() {
        Expression f = function();
        Expression one = Constant.ONE;
        Assert.assertEquals(f, multiply(f, one));
        Assert.assertEquals(f, multiply(one, f));
        Assert.assertEquals(one, multiply(one, one));
    }

    @Test
    public void multiplyEqualsTest() {
        Expression f = function();
        Assert.assertEquals(pow(f, c(2)), multiply(f, f));
    }

    @Test
    public void sumSimplifyTest() {
        Expression f = function();
        Expression zero = Constant.ZERO;
        Assert.assertEquals(f, sum(f, zero));
        Assert.assertEquals(f, sum(zero, f));
        Assert.assertEquals(zero, sum(zero, zero));
    }

    @Test
    public void sumEqualsTest() {
        Expression f = function();
        Assert.assertEquals(multiply(c(2), f), sum(f, f));
    }

    @Test
    public void divideEqualsTest() {
        Expression f = function();
        Assert.assertEquals(c(1), divide(f, f));
    }

    @Test
    public void divideConstantsTest() {
        Constant c1 = c(4);
        Constant c2 = c(2);
        Assert.assertEquals(c(2), divide(c1, c2));
    }

    private static Expression function() {
        return sin(cos(ln(pow(X, c(2)))));
    }
}
