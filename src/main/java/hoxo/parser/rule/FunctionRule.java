package hoxo.parser.rule;

import hoxo.parser.Lexeme;
import hoxo.parser.StringIterator;

import java.util.Optional;

public class FunctionRule extends Rule {

    private static final FunctionRule INSTANCE = new FunctionRule();

    @Override
    public Optional<Lexeme> process(StringBuilder accumulator, StringIterator iterator) {
        Character c = iterator.current();
        if (c.equals('(') && iterator.hasPrev()) {
            if (isFunction(accumulator.substring(0, accumulator.length() - 1))) {
                return Optional.of(Lexeme.functionStart(accumulator.substring(0, accumulator.length() - 1)));
            }
        }
        return Optional.empty();
    }

    public static FunctionRule instance() {
        return INSTANCE;
    }

    private static boolean isFunction(CharSequence charSequence) {
        return Lexeme.Type.FUNCTION_START.getPattern().matcher(charSequence).matches();
    }

}
