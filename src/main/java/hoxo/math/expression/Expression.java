package hoxo.math.expression;

public interface Expression {
    Expression derivative();
    double evaluate(double x);
    <T> T visit(ExpressionVisitor<T> visitor);
}
