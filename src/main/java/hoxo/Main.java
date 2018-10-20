package hoxo;

import java.util.List;

import hoxo.parser.GrammarChecker;
import hoxo.parser.Lexeme;
import hoxo.parser.Lexer;

import static hoxo.parser.rule.Rules.BASIC_RULES;

public class Main {
    public static void main(String[] args) {
        List<Lexeme> list = new Lexer(BASIC_RULES).parse("hjds123jf 123 a");
        System.out.println(list);
        System.out.println(new GrammarChecker().check(list));
    }
}
