package hoxo.math.converter;

import com.google.common.math.DoubleMath;
import hoxo.math.expression.Constant;
import hoxo.math.expression.ExpressionVisitor;
import hoxo.math.expression.Variable;
import hoxo.math.expression.function.AbstractFunction;
import hoxo.math.expression.function.Log;
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
        if (DoubleMath.fuzzyEquals(Math.PI, constant.getValue(), delta())) {
            return "\\pi";
        }
        if (DoubleMath.fuzzyEquals(Math.E, constant.getValue(), delta())) {
            return "e";
        }
        return String.format("%s", format(constant.getValue()));
    }

    @Override
    public String visitVariable(Variable variable) {
        return variable.getName();
    }

    @Override
    public String visitFunction(AbstractFunction function) {
        String functionName = function.getName();
        if (functionName.equals("sqrt"))
            return String.format("\\sqrt{%s}", visitArg(function));
        if (function instanceof Log) {
            Log log = ((Log) function);
            if (DoubleMath.fuzzyEquals(Math.E, log.getBase(), delta())) {
                return String.format("ln(%s)", visitArg(function));
            }
            return String.format("log_{%s}(%s)", format(log.getBase()), visitArg(log));
        }

        return String.format("%s(%s)", functionName, visitArg(function));
    }

    @Override
    public String visitNegative(AbstractFunction.Negative negative) {
        return String.format("-%s",negative.getArg().visit(this));
    }

    private String visitArg(AbstractFunction function) {
        return function.getArgument().visit(this);
    }

    private static double delta() {
        return 1e-15;
    }

    private static String format(double d) {
        if (d == (long)d)
            return String.format("%d", (long)d);
        else
            return String.format("%s", d);
    }
}
