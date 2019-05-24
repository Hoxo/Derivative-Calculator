package hoxo.math.parser.rule;

import hoxo.math.parser.Lexeme;
import hoxo.math.parser.StringIterator;

import java.util.Optional;

public class LParenRule extends Rule {

    private static final LParenRule INSTANCE = new LParenRule();

    @Override
    public Optional<Lexeme> process(StringBuilder sb, StringIterator iterator) {
        if (iterator.current().equals('(')) {
            if (!iterator.hasPrev() || isSeparator(iterator.peekPrev()) || iterator.peekPrev().equals('(')) {
                return Optional.of(Lexeme.lparen());
            }
        }
        return Optional.empty();
    }

    public static LParenRule instance() {
        return INSTANCE;
    }
}
