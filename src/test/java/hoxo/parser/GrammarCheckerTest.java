package hoxo.parser;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static hoxo.parser.rule.Rules.BASIC_RULES;

public class GrammarCheckerTest {

    @Test
    public void incorrectLexemesTest() {
        List<Lexeme> lexemes = Lists.newArrayList(
                Lexeme.divide(),
                Lexeme.functionStart("tRaTaT"),
                Lexeme.lparen(),
                Lexeme.minus(),
                Lexeme.multiply(),
                Lexeme.power(),
                Lexeme.rparen(),
                Lexeme.space(),
                Lexeme.variable("tatat"),
                Lexeme.value("1235"),
                Lexeme.value("12.35"),
                Lexeme.value(".35")
        );
        Assert.assertEquals(Lists.newArrayList(), new GrammarChecker().check(lexemes));
    }
}
