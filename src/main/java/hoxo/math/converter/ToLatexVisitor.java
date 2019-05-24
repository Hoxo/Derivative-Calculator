package hoxo.math.converter;

import hoxo.math.expression.Constant;
import hoxo.math.expression.ExpressionVisitor;
import hoxo.math.expression.Variable;
import hoxo.math.expression.function.AbstractFunction;
import hoxo.math.expression.operation.*;

class ToLatexVisitor implements ExpressionVisitor<String> {
    private static final ToLatexVisitor INSTANCE = new ToLatexVisitor();

    private ToLatexVisitor() {}

    public static ToLatexVisitor getInstance() {
        return INSTANCE;
    }

    @Override
    public String visitMinus(Minus minus) {
        return String.format("%s - %s", minus.getLeft().visit(this), minus.getRight().visit(this));
    }

    @Override
    public String visitPlus(Plus plus) {
        return String.format("%s + %s", plus.getLeft().visit(this), plus.getRight().visit(this));
    }

    @Override
    public String visitMultiply(Multiply multiply) {
        return String.format("%s \\times %s", multiply.getLeft().visit(this), multiply.getRight().visit(this));
    }

    @Override
    public String visitDivide(Divide divide) {
        return String.format("\\frac{%s}{%s}", divide.getLeft().visit(this), divide.getRight().visit(this));
    }

    @Override
    public String visitPower(Power power) {
        return String.format("%s ^ {%s}", power.getLeft().visit(this), power.getRight().visit(this));
    }

    @Override
    public String visitConstant(Constant constant) {
        return String.format("%.0f", constant.getValue());
    }

    @Override
    public String visitVariable(Variable variable) {
        return variable.getName();
    }

    @Override
    public String visitFunction(AbstractFunction function) {
        String functionName = function.getName();
        if (functionName.equals("sqrt"))
            return String.format("\\sqrt{%s}", function.getArgument().visit(this));
        return String.format("%s(%s)", functionName, function.getArgument().visit(this));
    }

    @Override
    public String visitNegative(AbstractFunction.Negative negative) {
        return String.format("-%s",negative.getArg().visit(this));
    }
}
