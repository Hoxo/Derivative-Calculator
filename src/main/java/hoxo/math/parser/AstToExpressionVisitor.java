package hoxo.math.parser;


import hoxo.math.expression.Constant;
import hoxo.math.expression.Expression;
import hoxo.math.expression.function.Functions;
import hoxo.math.parser.tree.*;

import java.util.Map;
import java.util.function.UnaryOperator;

public class AstToExpressionVisitor implements ASTVisitor<Expression> {
    private Map<String, UnaryOperator<Expression>> allowedFunctions;

    public AstToExpressionVisitor(Map<String, UnaryOperator<Expression>> allowedFunctions) {
        this.allowedFunctions = allowedFunctions;
    }

    @Override
    public Expression visitPlus(Plus plus) {
        return Functions.sum(plus.getLeftChild().visit(this), plus.getRightChild().visit(this));
    }

    @Override
    public Expression visitFunction(Function function) {
        if (allowedFunctions.containsKey(function.getValue())) {
            return allowedFunctions.get(function.getValue()).apply(function.getChild().visit(this));
        } else {
            throw new ExpressionException("Unknown expression: " + function.getValue());
        }
    }

    @Override
    public Expression visitMinus(Minus minus) {
        return Functions.minus(minus.getLeftChild().visit(this), minus.getRightChild().visit(this));
    }

    @Override
    public Expression visitPower(Power power) {
        return Functions.pow(power.getLeftChild().visit(this), power.getRightChild().visit(this));
    }

    @Override
    public Expression visitMultiply(Multiply multiply) {
        return Functions.multiply(multiply.getLeftChild().visit(this), multiply.getRightChild().visit(this));
    }

    @Override
    public Expression visitDivide(Divide divide) {
        return Functions.divide(divide.getLeftChild().visit(this), divide.getRightChild().visit(this));
    }

    @Override
    public Constant visitValue(Value value) {
        double val = Double.parseDouble(value.getValue());
        return Functions.c(val);
    }

    @Override
    public Expression visitVariable(Variable variable) {
        //TODO Replace!
        switch (variable.getValue()) {
            case "E":
                return Constant.E;
            case "PI":
                return Constant.PI;
        }
        return hoxo.math.expression.Variable.byName(variable.getValue());
    }

    @Override
    public Expression visitUnaryMinus(UnaryMinus minus) {
        return Functions.neg(minus.getChild().visit(this));
    }

    @Override
    public Expression visitScope(Scope scope) {
        return scope.getChild().visit(this);
    }
}
