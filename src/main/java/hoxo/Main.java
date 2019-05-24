package hoxo;

import hoxo.expression.Expression;
import hoxo.expression.Functions;
import hoxo.parser.*;
import hoxo.parser.tree.AbstractSyntaxTree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import static hoxo.parser.rule.Rules.BASIC_RULES;

public class Main {
    private Lexer lexer;
    private GrammarChecker grammarChecker;
    private Parser parser;
    private DefaultVisitor visitor;

    public Main() {
        lexer = new Lexer(BASIC_RULES);
        grammarChecker = new GrammarChecker(Functions.functions().keySet());
        parser = new Parser();
        visitor = new DefaultVisitor(Functions.functions());
    }

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    public void run() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String a;
        while (!(a = reader.readLine()).equals("q")) {
            try {
                Expression expression;
                if (a.startsWith("'")) {
                    String[] input = a.split(" ", 2);
                    String cmd = input[0];
                    String exp = input[1];
                    if (!cmd.matches("'*")) {
                        throw new Exception("Incorrect command");
                    }
                    Expression der = parse(exp);
                    for (int i = 0; i < cmd.length(); i++) {
                        der = der.derivative();
                    }
                    System.out.println(der);
                    continue;
                }
                if (a.startsWith("dfr ")) {
                    expression = parse(a.substring(3));
                    expression = expression.derivative();
                    System.out.println(expression);
                } else {
                    expression = parse(a);
                    System.out.println(expression.evaluate(1));
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    private Expression parse(String input) {
        List<Lexeme> lexemes = lexer.parse(input);
        List<String> errors = grammarChecker.check(lexemes);
        if (!errors.isEmpty()) {
            for (String error : errors) {
                System.err.println(error);
            }
        }
        AbstractSyntaxTree ast = parser.parse(lexemes);
        return ast.convert(visitor);
    }
}
