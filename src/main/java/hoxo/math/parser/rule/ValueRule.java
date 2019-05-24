package hoxo.math.parser.rule;

import hoxo.math.parser.Lexeme;
import hoxo.math.parser.StringIterator;

import java.util.Optional;

import static hoxo.util.StringUtils.isNumber;

public class ValueRule extends Rule {

    private static final ValueRule INSTANCE = new ValueRule();

    @Override
    public Optional<Lexeme> process(StringBuilder sb, StringIterator iterator) {

        if (Character.isDigit(iterator.current())) {
            if (!iterator.hasNext() || isSeparator(iterator.peekNext()) ) {
                if (isNumber(sb)) {
                    return Optional.of(Lexeme.value(sb.toString()));
                }
            }
        }
        return Optional.empty();
    }

    public static ValueRule instance() {
        return INSTANCE;
    }

}
