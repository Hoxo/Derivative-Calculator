package hoxo.math.expression;

import hoxo.math.expression.function.AbstractFunction;
import hoxo.math.expression.operation.*;

public interface ExpressionVisitor<T> {
    T visitMinus(Minus minus);
    T visitPlus(Plus plus);
    T visitMultiply(Multiply multiply);
    T visitDivide(Divide divide);
    T visitPower(Power power);
    T visitConstant(Constant constant);
    T visitVariable(Variable variable);
    T visitFunction(AbstractFunction function);
    T visitNegative(AbstractFunction.Negative negative);
}
