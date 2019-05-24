package hoxo;

import hoxo.math.expression.Expression;
import hoxo.math.expression.function.Functions;
import hoxo.math.parser.*;
import hoxo.math.parser.rule.Rules;
import hoxo.math.parser.tree.AbstractSyntaxTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class IntegrationTest {
    private static final double DELTA = 1e-10;

    private Lexer lexer;
    private GrammarChecker grammarChecker;
    private AstParser parser;
    private AstToExpressionVisitor visitor;

    @Before
    public void init() {
        lexer = new Lexer(Rules.BASIC_RULES);
        grammarChecker = new GrammarChecker(Functions.functions().keySet());
        parser = new AstParser();
        visitor = new AstToExpressionVisitor(Functions.functions());
    }

    @Test
    public void simpleSumTest() {
        Expression expression = parse("1 + 1");
        Assert.assertEquals(2, expression.evaluate(123), 0);
    }

    @Test
    public void associativityTest() {
        Expression expression = parse("2 + 2 * 2");
        Assert.assertEquals(6, expression.evaluate(123), 0);
    }

    @Test
    public void distributivityTest() {
        Expression expression = parse("(2 + 2) * 2");
        Assert.assertEquals(8, expression.evaluate(123), 0);
    }

    @Test
    public void leftAssociativityTest() {
        Expression expression = parse("1 - 2 + 3 - 4");
        Assert.assertEquals(-2, expression.evaluate(123), 0);
    }

    @Test
    public void sinTest() {
        Expression expression = parse("sin(x)");
        double x = Math.PI / 4;
        Assert.assertEquals(Math.sin(x), expression.evaluate(x), DELTA);
        Expression fdr = expression.derivative();
        x = 0;
        Assert.assertEquals(Math.cos(x), fdr.evaluate(x), DELTA);
    }

    @Test
    public void cosTest() {
        Expression expression = parse("cos(x)");
        double x = Math.PI / 4;
        Assert.assertEquals(Math.cos(x), expression.evaluate(x), DELTA);
        Expression fdr = expression.derivative();
        x = 0;
        Assert.assertEquals(-Math.sin(x), fdr.evaluate(x), DELTA);
    }

    @Test
    public void sqrOfXTest() {
        Expression expression = parse("x^2");
        double x = 2;
        Assert.assertEquals(Math.pow(x, 2), expression.evaluate(x), DELTA);
        expression = expression.derivative();
        Assert.assertEquals(2 * x, expression.evaluate(x), DELTA);
        expression = expression.derivative();
        Assert.assertEquals(2, expression.evaluate(x), DELTA);
        expression = expression.derivative();
        Assert.assertEquals(0, expression.evaluate(x), DELTA);
    }

    @Test
    public void powerOf2Test() {
        Expression expression = parse("2^x");
        double x = 5;
        Assert.assertEquals(Math.pow(2, x), expression.evaluate(x), DELTA);
        expression = expression.derivative();
        Assert.assertEquals(Math.log(2) * Math.pow(2, x), expression.evaluate(x), DELTA);
    }

    @Test
    public void lnSinXTest() {
        Expression expression = parse("ln(sin(x))");
        double x = Math.PI / 4;
        Assert.assertEquals(Math.log(Math.sin(x)), expression.evaluate(x), DELTA);
        expression = expression.derivative();
        Assert.assertEquals(1 / Math.tan(x), expression.evaluate(x), DELTA);
    }

    @Test
    public void fractionTest() {
        Expression expression = parse("x / sin(x)");
        double x = Math.PI / 2;
        Assert.assertEquals(x / Math.sin(x), expression.evaluate(x), DELTA);
        expression = expression.derivative();
        Assert.assertEquals((Math.sin(x) - x * Math.cos(x)) / Math.pow(Math.sin(x), 2),
                expression.evaluate(x), DELTA);
    }

    @Test
    public void unaryMinusTest() {
        Expression expression = parse("-1");
        Assert.assertEquals(-1, expression.evaluate(12332), DELTA);
    }

    @Test
    public void minusXTest() {
        Expression expression = parse("-x");
        Assert.assertEquals(-1, expression.evaluate(1), DELTA);
        expression = expression.derivative();
        Assert.assertEquals(-1, expression.evaluate(1234), DELTA);

    }

    @Test
    public void priorityTest() {
        Expression expression = parse("2 ^ 2 * 2 ^ 2 + 2 * 2 ^ 2");
        Assert.assertEquals(Math.pow(2, 2) * Math.pow(2, 2) + 2 * Math.pow(2, 2), expression.evaluate(123),
                DELTA);
    }

    @Test
    public void powerAssociativityTest() {
        Expression expression = parse("2 ^ 2 ^ 3");
        Assert.assertEquals(Math.pow(2, Math.pow(2, 3)), expression.evaluate(123), DELTA);
    }

    @Test
    public void doubleMinusTest() {
        Expression expression = parse("2 ^ 2 - -1");
        Assert.assertEquals(5, expression.evaluate(1), DELTA);
    }

    @Test
    public void multiParensTest() {
        Expression expression = parse("2 + ((2 + 2) + 2) + 2");
        Assert.assertEquals(10, expression.evaluate(123), DELTA);
        expression = parse("1 + (1 + (1 + 1))");
        Assert.assertEquals(4, expression.evaluate(123), DELTA);
    }

    private Expression parse(String input) {
        List<Lexeme> lexemes = lexer.parse(input);
        List<String> errors = grammarChecker.check(lexemes);
        if (!errors.isEmpty()) {
            Assert.fail();
        }
        AbstractSyntaxTree ast = parser.parse(lexemes);
        return ast.convert(visitor);
    }
}
