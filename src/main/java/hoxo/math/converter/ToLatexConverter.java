package hoxo.math.converter;

import hoxo.math.expression.Expression;

import static hoxo.math.expression.function.Functions.*;

public class ToLatexConverter {
    private ToLatexVisitor visitor;

    public ToLatexConverter() {
        visitor = ToLatexVisitor.getInstance();
    }

    String convert(Expression expression) {
        return expression.visit(visitor);
    }

    public static void main(String[] args) {
        Expression expression = divide(
                sqr(var("v")),
                sqrt(
                        minus(
                                c(1),
                                divide(
                                        sqr(var("v")),
                                        sqr(var("c"))
                                )
                        )
                )
            );
        ToLatexConverter converter = new ToLatexConverter();
        System.out.println(converter.convert(expression));
    }
}
