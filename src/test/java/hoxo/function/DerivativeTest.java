package hoxo.function;

import org.junit.Assert;
import org.junit.Test;

import static hoxo.function.Functions.*;

public class DerivativeTest {

    @Test
    public void constantDerivativeTest() {
        Function constant = c(1);
        Assert.assertEquals(Constant.ZERO, constant.derivative());
    }

    @Test
    public void functionWithConstantValueDerivativeTest() {
        Constant c = c(1);
        Function cos = cos(c);
        Function sin = sin(c);
        Function log = log(42, c);
        Function pow = pow(c, 2);
        Function exp = exponent(1234, c);

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
