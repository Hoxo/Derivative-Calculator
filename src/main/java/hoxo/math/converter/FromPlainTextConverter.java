package hoxo.math.converter;

import hoxo.math.expression.Expression;
import hoxo.math.expression.function.Functions;
import hoxo.math.parser.*;
import hoxo.math.parser.rule.Rules;
import hoxo.math.parser.tree.AbstractSyntaxTree;

import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

public class FromPlainTextConverter implements InputConverter {
    private GrammarChecker grammarChecker;
    private Lexer lexer;
    private AstParser parser;
    private AstToExpressionVisitor astVisitor;

    public FromPlainTextConverter() {
        this(Functions.functions());
    }

    public FromPlainTextConverter(Map<String, UnaryOperator<Expression>> functionConstructors) {
        grammarChecker = new GrammarChecker(functionConstructors.keySet());
        lexer = new Lexer(Rules.BASIC_RULES);
        parser = new AstParser();
        astVisitor = new AstToExpressionVisitor(functionConstructors);
    }

    @Override
    public Expression convert(String raw) {
        List<Lexeme> lexemes = lexer.parse(raw);
        List<String> errors = grammarChecker.check(lexemes);
        if (!errors.isEmpty())
            throw new ExpressionException(errors.toString());
        AbstractSyntaxTree ast = parser.parse(lexemes);
        return ast.convert(astVisitor);
    }
}
