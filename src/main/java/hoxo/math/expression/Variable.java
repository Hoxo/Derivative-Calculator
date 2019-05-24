package hoxo.math.expression;

public class Variable implements Expression {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public static Variable byName(String name) {
        return new Variable(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public Expression derivative() {
        return Constant.ONE;
    }

    @Override
    public <T> T visit(ExpressionVisitor<T> visitor) {
        return visitor.visitVariable(this);
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

    @Override
    public String toString() {
        return name;
    }
}
