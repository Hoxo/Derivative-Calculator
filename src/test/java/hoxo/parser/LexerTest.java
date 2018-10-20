package hoxo.parser;

import java.util.List;

import com.google.common.collect.Lists;

import org.junit.Assert;
import org.junit.Test;

import static hoxo.parser.rule.Rules.BASIC_RULES;

public class LexerTest {
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
        Assert.assertEquals(exp, lexer().parse("x f(x)/*    - +^x"));
    }

    @Test
    public void variableTest() {
        Lexer lexer = lexer();
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
        Lexer lexer = lexer();
        for (String name : Lists.newArrayList("$xxx.x,x..xx1", "x.11x")) {
            Assert.assertEquals(Lists.newArrayList(Lexeme.unknown(name)), lexer.parse(name));
            Assert.assertEquals(Lists.newArrayList(Lexeme.unknown(name + "(")), lexer.parse(name + "("));
        }

    }

    @Test
    public void numberTest() {
        Lexer lexer = lexer();
        Assert.assertEquals(Lists.newArrayList(Lexeme.value("42")), lexer.parse("42"));
        Assert.assertEquals(Lists.newArrayList(Lexeme.unknown("4x2")), lexer.parse("4x2"));
        Assert.assertEquals(Lists.newArrayList(Lexeme.unknown("42(")), lexer.parse("42("));
        Assert.assertEquals(Lists.newArrayList(Lexeme.unknown("42x")), lexer.parse("42x"));
        Assert.assertEquals(Lists.newArrayList(Lexeme.unknown("x42")), lexer.parse("x42"));
    }

    private Lexer lexer() {
        return new Lexer(BASIC_RULES);
    }

}
