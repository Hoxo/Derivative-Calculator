package hoxo.expression;

import org.junit.Assert;
import org.junit.Test;

import static hoxo.expression.Functions.*;

public class DerivativeTest {

    @Test
    public void constantDerivativeTest() {
        Expression constant = c(1);
        Assert.assertEquals(Constant.ZERO, constant.derivative());
    }

    @Test
    public void functionWithConstantValueDerivativeTest() {
        Constant c = c(1);
        Expression cos = cos(c);
        Expression sin = sin(c);
        Expression log = log(42, c);
        Expression pow = pow(c, c(2));
        Expression exp = exp(c);

        Assert.assertEquals(Constant.ZERO, cos.derivative());
        Assert.assertEquals(Constant.ZERO, neg(cos).derivative());
        Assert.assertEquals(Constant.ZERO, sin.derivative());
        Assert.assertEquals(Constant.ZERO, neg(sin).derivative());
        Assert.assertEquals(Constant.ZERO, log.derivative());
        Assert.assertEquals(Constant.ZERO, neg(log).derivative());
        Assert.assertEquals(Constant.ZERO, pow.derivative());
        Assert.assertEquals(Constant.ZERO, neg(pow).derivative());
        Assert.assertEquals(Constant.ZERO, exp.derivative());
        Assert.assertEquals(Constant.ZERO, neg(exp).derivative());
    }

}
