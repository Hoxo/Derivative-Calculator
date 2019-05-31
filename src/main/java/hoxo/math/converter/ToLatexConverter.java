package hoxo.math.converter;

import hoxo.math.expression.Expression;

public class ToLatexConverter implements OutputConverter {
    private ToLatexConverter INSTANCE = new ToLatexConverter();

    private ToLatexVisitor visitor;

    private ToLatexConverter() {
        visitor = ToLatexVisitor.getInstance();
    }

    public String convert(Expression expression) {
        return expression.visit(visitor);
    }
}
