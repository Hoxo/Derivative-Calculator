package hoxo;

import hoxo.parser.tree.AbstractSyntaxTree;
import hoxo.parser.Lexeme;
import hoxo.parser.Lexer;
import hoxo.parser.Parser;

import java.util.List;

import static hoxo.parser.rule.Rules.BASIC_RULES;

public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer(BASIC_RULES);
        String input = "a + f(b * f(c / f(d - f(e ^ f)))) + g";
        List<Lexeme> lexemeList = lexer.parse(input);
        Parser parser = new Parser();
        AbstractSyntaxTree ast = parser.parse(lexemeList);
        System.out.println(input);
        System.out.println(ast.getRoot());
    }
}
