package hoxo;

import hoxo.expression.Expression;
import hoxo.expression.Functions;
import hoxo.parser.*;
import hoxo.parser.rule.Rules;
import hoxo.parser.tree.AbstractSyntaxTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class IntegrationTest {
    private static final double DELTA = 1e-10;

    private Lexer lexer;
    private GrammarChecker grammarChecker;
    private Parser parser;
    private DefaultVisitor visitor;

    @Before
    public void init() {
        lexer = new Lexer(Rules.BASIC_RULES);
        grammarChecker = new GrammarChecker(Functions.functions().keySet());
        parser = new Parser();
        visitor = new DefaultVisitor(Functions.functions());
    }

    @Test
    public void simpleSumTest() {
        String input = "1 + 1";
        Expression expression = parse(input);
        Assert.assertEquals(2, expression.evaluate(123), 0);
    }

    @Test
    public void associativityTest() {
        String input = "2 + 2 * 2";
        Expression expression = parse(input);
        Assert.assertEquals(6, expression.evaluate(123), 0);
    }

    @Test
    public void distributivityTest() {
        String input = "(2 + 2) * 2";
        Expression expression = parse(input);
        Assert.assertEquals(8, expression.evaluate(123), 0);
    }

    @Test
    public void leftAssociativityTest() {
        String input = "1 - 2 + 3 - 4";
        Expression expression = parse(input);
        Assert.assertEquals(-2, expression.evaluate(123), 0);
    }

    @Test
    public void sinTest() {
        String input = "sin(x)";
        Expression expression = parse(input);
        double x = Math.PI / 4;
        Assert.assertEquals(Math.sin(x), expression.evaluate(x), DELTA);
        Expression fdr = expression.derivative();
        x = 0;
        Assert.assertEquals(Math.cos(x), fdr.evaluate(x), DELTA);
    }

    @Test
    public void cosTest() {
        String input = "cos(x)";
        Expression expression = parse(input);
        double x = Math.PI / 4;
        Assert.assertEquals(Math.cos(x), expression.evaluate(x), DELTA);
        Expression fdr = expression.derivative();
        x = 0;
        Assert.assertEquals(-Math.sin(x), fdr.evaluate(x), DELTA);
    }

    @Test
    public void sqrOfXTest() {
        String input = "x^2";
        Expression expression = parse(input);
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
        String input = "2^x";
        Expression expression = parse(input);
        double x = 5;
        Assert.assertEquals(Math.pow(2, x), expression.evaluate(x), DELTA);
        expression = expression.derivative();
        Assert.assertEquals(Math.log(2) * Math.pow(2, x), expression.evaluate(x), DELTA);
    }

    @Test
    public void lnSinXTest() {
        String input = "ln(sin(x))";
        Expression expression = parse(input);
        double x = Math.PI / 4;
        Assert.assertEquals(Math.log(Math.sin(x)), expression.evaluate(x), DELTA);
        expression = expression.derivative();
        Assert.assertEquals(1 / Math.tan(x), expression.evaluate(x), DELTA);
    }

    @Test
    public void fractionTest() {
        String input = "x / sin(x)";
        Expression expression = parse(input);
        double x = Math.PI / 2;
        Assert.assertEquals(x / Math.sin(x), expression.evaluate(x), DELTA);
        expression = expression.derivative();
        Assert.assertEquals((Math.sin(x) - x * Math.cos(x)) / Math.pow(Math.sin(x), 2),
                expression.evaluate(x), DELTA);
    }

    private Expression parse(String input) {
        List<Lexeme> lexemes = lexer.parse(input);
        List<String> errors = grammarChecker.check(lexemes);
        if (!errors.isEmpty()) {
            Assert.fail();
        }
        AbstractSyntaxTree ast = parser.parse(lexemes);
        return ast.getRoot().visit(visitor);
    }
}
