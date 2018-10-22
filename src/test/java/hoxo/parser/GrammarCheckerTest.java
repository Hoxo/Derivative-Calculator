package hoxo.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GrammarCheckerTest {

    @Test
    public void incorrectLexemesTest() {
        String func = "sin";
        List<Lexeme> lexemes = Lists.newArrayList(
                Lexeme.divide(),
                Lexeme.functionStart(func),
                Lexeme.lparen(),
                Lexeme.minus(),
                Lexeme.multiply(),
                Lexeme.power(),
                Lexeme.rparen(),
                Lexeme.variable("x"),
                Lexeme.value("1235"),
                Lexeme.value("12.35"),
                Lexeme.value(".35")
        );
        Assert.assertEquals(Lists.newArrayList(), new GrammarChecker(Sets.newHashSet(func)).check(lexemes));
    }
}
