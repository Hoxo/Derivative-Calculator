package hoxo.parser.rule;

import hoxo.parser.Lexeme;
import hoxo.parser.StringIterator;

import java.util.Optional;

public class UnknownRule extends Rule {
    @Override
    public Optional<Lexeme> process(StringBuilder sb, StringIterator iterator) {
        if (!iterator.hasNext() || isSeparator(iterator.peekNext())) {
            return Optional.of(Lexeme.unknown(sb.toString()));
        }
        return Optional.empty();
    }
}
