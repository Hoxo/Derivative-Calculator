package hoxo.function;

import org.junit.Assert;
import org.junit.Test;

import static hoxo.function.Functions.X;

public class AbstractFunctionTest {

    @Test
    public void negativeFunctionTest() {
        Function x = X;
        x = AbstractFunction.Negative.wrap(x);
        x = AbstractFunction.Negative.wrap(x);
        Assert.assertNotEquals(AbstractFunction.Negative.class, x.getClass());
        Assert.assertEquals(X.toString(), x.toString());
    }

    @Test
    public void negativeDerivativeTest() {
        Function x = X;
        Function mx = AbstractFunction.Negative.wrap(x);
        Function der = mx.derivative();
        Assert.assertEquals("-1.0", der.toString());
    }
}
