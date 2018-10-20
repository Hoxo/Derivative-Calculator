package hoxo.parser.rule;

import hoxo.parser.Lexeme;
import hoxo.parser.StringIterator;
import hoxo.util.StringUtils;

import java.util.Optional;

public class VariableRule extends Rule {

    private static final VariableRule INSTANCE = new VariableRule();

    @Override
    public Optional<Lexeme> process(StringBuilder sb, StringIterator iterator) {

        if (!iterator.hasNext() || isSeparator(iterator.peekNext())) {
            if (StringUtils.isWord(sb)) {
                return Optional.of(Lexeme.variable(sb.toString()));
            }
        }
        return Optional.empty();
    }

    public static VariableRule instance() {
        return INSTANCE;
    }
}
