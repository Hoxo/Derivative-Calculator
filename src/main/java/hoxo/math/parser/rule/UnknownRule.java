package hoxo.math.parser.rule;

import hoxo.math.parser.Lexeme;
import hoxo.math.parser.StringIterator;

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
