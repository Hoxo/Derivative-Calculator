package hoxo.math.expression;

public interface Expression {
    Expression derivative();
    double evaluate(double x);
}
