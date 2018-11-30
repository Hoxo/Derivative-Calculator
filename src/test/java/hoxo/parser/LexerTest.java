package hoxo.parser;

import java.util.List;

import com.google.common.collect.Lists;

import hoxo.parser.rule.Rules;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static hoxo.parser.Lexeme.*;

public class LexerTest {
    private Lexer lexer;

    @Before
    public void init() {
        lexer = new Lexer(Rules.BASIC_RULES);
    }

    @Test
    public void rulesTest() {
        List<Lexeme> exp = Lists.newArrayList(
                Lexeme.variable("x"),
                Lexeme.space(),
                Lexeme.functionStart("f"),
                Lexeme.variable("x"),
                Lexeme.rparen(),
                Lexeme.divide(),
                Lexeme.multiply(),
                Lexeme.space(),
                Lexeme.minus(),
                Lexeme.space(),
                Lexeme.plus(),
                Lexeme.power(),
                Lexeme.variable("x")
        );
        Assert.assertEquals(exp, lexer.parse("x f(x)/*    - +^x"));
    }

    @Test
    public void variableTest() {
        String vr = "x";
        Assert.assertEquals(Lists.newArrayList(Lexeme.variable(vr), Lexeme.plus()), lexer.parse(vr + "+"));
        Assert.assertEquals(Lists.newArrayList(Lexeme.variable(vr), Lexeme.minus()), lexer.parse(vr + "-"));
        Assert.assertEquals(Lists.newArrayList(Lexeme.variable(vr), Lexeme.multiply()), lexer.parse(vr + "*"));
        Assert.assertEquals(Lists.newArrayList(Lexeme.variable(vr), Lexeme.divide()), lexer.parse(vr + "/"));
        Assert.assertEquals(Lists.newArrayList(Lexeme.variable(vr), Lexeme.power()), lexer.parse(vr + "^"));
        Assert.assertEquals(Lists.newArrayList(Lexeme.variable(vr), Lexeme.space()), lexer.parse(vr + "     "));
        Assert.assertEquals(Lists.newArrayList(Lexeme.functionStart(vr)), lexer.parse(vr + "("));
    }

    @Test
    public void incorrectLexemeTest() {
        for (String name : Lists.newArrayList("$xxx.x,x..xx1", "x.11x")) {
            Assert.assertEquals(Lists.newArrayList(Lexeme.unknown(name)), lexer.parse(name));
            Assert.assertEquals(Lists.newArrayList(Lexeme.unknown(name + "(")), lexer.parse(name + "("));
        }

    }

    @Test
    public void numberTest() {
        Assert.assertEquals(Lists.newArrayList(Lexeme.value("42")), lexer.parse("42"));
        Assert.assertEquals(Lists.newArrayList(Lexeme.unknown("4x2")), lexer.parse("4x2"));
        Assert.assertEquals(Lists.newArrayList(Lexeme.unknown("42(")), lexer.parse("42("));
        Assert.assertEquals(Lists.newArrayList(Lexeme.unknown("42x")), lexer.parse("42x"));
        Assert.assertEquals(Lists.newArrayList(Lexeme.unknown("x42")), lexer.parse("x42"));
    }

    @Test
    public void parenthesesTest() {
        Assert.assertEquals(Lists.newArrayList(lparen(), lparen(), rparen(), rparen()), lexer.parse("(())"));
    }

    @Test
    public void valueInParenthesesTest() {
        Assert.assertEquals(Lists.newArrayList(lparen(), variable("a"), rparen()), lexer.parse("(a)"));
    }
}
