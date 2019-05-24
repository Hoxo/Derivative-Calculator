package hoxo.math.parser.rule;

import hoxo.math.parser.Lexeme;
import hoxo.math.parser.StringIterator;

import java.util.Optional;

public class WhitespaceRule extends Rule {

    private static final WhitespaceRule INSTANCE = new WhitespaceRule();

    @Override
    public Optional<Lexeme> process(StringBuilder sb, StringIterator iterator) {
        if (Character.isWhitespace(iterator.current())) {
            for (;iterator.hasNext() && Character.isWhitespace(iterator.peekNext()); iterator.next());
            return Optional.of(Lexeme.space());
        }
        return Optional.empty();
    }

    public static WhitespaceRule instance() {
        return INSTANCE;
    }
}
