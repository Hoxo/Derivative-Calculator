package hoxo.parser;

import com.google.common.collect.Lists;
import hoxo.parser.tree.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ParserTest {

    private Parser parser;

    @Before
    public void init() {
        parser = new Parser();
    }

    @Test
    public void oneNodeTest() {
        String input = "a";
        Leaf variable = Nodes.variable(input);
        List<Lexeme> lexemes = Lists.newArrayList(Lexeme.variable(input));
        AbstractSyntaxTree ast = new AbstractSyntaxTree(variable);
        Assert.assertEquals(ast, parser.parse(lexemes));
    }

    @Test
    public void simpleSumTest() {
        String a = "a";
        String b = "b";
        List<Lexeme> lexemes = Lists.newArrayList(Lexeme.variable(a), Lexeme.plus(), Lexeme.variable(b));
        Plus plus = Nodes.plus();
        plus.setLeftChild(Nodes.variable(a));
        plus.setRightChild(Nodes.variable(b));
        AbstractSyntaxTree expected = new AbstractSyntaxTree(plus);
        Assert.assertEquals(expected, parser.parse(lexemes));
    }

    @Test
    public void leftAssociativityTest() {
        String a = "a";
        Variable va = Nodes.variable(a);
        List<Lexeme> lexemes = Lists.newArrayList(
                Lexeme.variable(a),
                Lexeme.plus(),
                Lexeme.variable(a),
                Lexeme.minus(),
                Lexeme.variable(a)
        );
        Minus minus = Nodes.minus();
        Plus plus = Nodes.plus();
        minus.setLeftChild(plus);
        minus.setRightChild(va);
        plus.setLeftChild(va);
        plus.setRightChild(va);
        AbstractSyntaxTree expected = new AbstractSyntaxTree(minus);
        Assert.assertEquals(expected, parser.parse(lexemes));
    }

    @Test
    public void expressionWithParenthesesTest() {
        String a = "a";
        List<Lexeme> lexemes = Lists.newArrayList(
                Lexeme.variable(a),
                Lexeme.multiply(),
                Lexeme.lparen(),
                Lexeme.variable(a),
                Lexeme.plus(),
                Lexeme.variable(a),
                Lexeme.rparen()
        );
        Variable va1 = Nodes.variable(a);
        Variable va2 = Nodes.variable(a);
        Variable va3 = Nodes.variable(a);
        Multiply multiply = Nodes.multiply();
        Plus plus = Nodes.plus();
        plus.setRightChild(va2);
        plus.setLeftChild(va3);
        Scope scope = Nodes.scope();
        scope.setChild(plus);
        multiply.setLeftChild(va1);
        multiply.setRightChild(scope);
        Assert.assertEquals(new AbstractSyntaxTree(multiply), parser.parse(lexemes));
    }

    @Test
    public void expressionWithFunctionTest() {
        String a = "a";
        List<Lexeme> lexemes = Lists.newArrayList(
                Lexeme.functionStart(a),
                Lexeme.variable(a),
                Lexeme.plus(),
                Lexeme.functionStart(a),
                Lexeme.variable(a),
                Lexeme.plus(),
                Lexeme.variable(a),
                Lexeme.rparen(),
                Lexeme.rparen()
        );
        Function f1a = Nodes.function(a);
        Function f2a = Nodes.function(a);
        Variable va = Nodes.variable(a);
        Plus plus1 = Nodes.plus();
        Plus plus2 = Nodes.plus();
        f1a.setChild(plus1);
        plus1.setLeftChild(va);
        plus1.setRightChild(f2a);
        plus2.setRightChild(va);
        plus2.setLeftChild(va);
        f2a.setChild(plus2);
        Assert.assertEquals(new AbstractSyntaxTree(f1a), parser.parse(lexemes));
    }

    @Test
    public void diffirentPriorityOperationsTest() {
        String a = "a";
        String b = "b";
        Leaf a1 = Nodes.variable(a);
        Leaf a2 = Nodes.variable(a);
        Leaf b1 = Nodes.variable(b);
        Leaf b2 = Nodes.variable(b);
        Multiply m1 = Nodes.multiply();
        m1.setLeftChild(a1);
        m1.setRightChild(a2);
        Multiply m2 = Nodes.multiply();
        m2.setLeftChild(b1);
        m2.setRightChild(b2);
        Plus p = Nodes.plus();
        p.setLeftChild(m1);
        p.setRightChild(m2);
        List<Lexeme> lexemes = Lists.newArrayList(
                Lexeme.variable(a),
                Lexeme.multiply(),
                Lexeme.variable(a),
                Lexeme.plus(),
                Lexeme.variable(b),
                Lexeme.multiply(),
                Lexeme.variable(b)
        );
        Assert.assertEquals(new AbstractSyntaxTree(p), parser.parse(lexemes));
    }
}
