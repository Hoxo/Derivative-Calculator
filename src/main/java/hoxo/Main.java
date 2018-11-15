package hoxo;

import hoxo.parser.Lexeme;
import hoxo.parser.Lexer;
import hoxo.parser.Parser;

import java.util.List;

import static hoxo.parser.rule.Rules.BASIC_RULES;

public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer(BASIC_RULES);
        List<Lexeme> lexemeList = lexer.parse("a(a(a(a(a()))))");
        Parser parser = new Parser();
        parser.parse(lexemeList);
    }
}
