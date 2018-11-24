package hoxo.expression;

import org.junit.Assert;
import org.junit.Test;

import static hoxo.expression.Functions.X;

public class AbstractFunctionTest {

    @Test
    public void negativeFunctionTest() {
        Expression x = X;
        x = AbstractFunction.Negative.wrap(x);
        x = AbstractFunction.Negative.wrap(x);
        Assert.assertNotEquals(AbstractFunction.Negative.class, x.getClass());
        Assert.assertEquals(X.toString(), x.toString());
    }

    @Test
    public void negativeDerivativeTest() {
        Expression mx = AbstractFunction.Negative.wrap(X);
        Expression der = mx.derivative();
        Assert.assertEquals("-1.0", der.toString());
    }
}
