package hoxo.math.parser.rule;

import com.google.common.collect.Sets;
import hoxo.math.parser.Lexeme;
import hoxo.math.parser.StringIterator;

import java.util.Optional;
import java.util.Set;

public abstract class Rule {
    protected static final Set<Character> SEPARATORS = Sets.newHashSet('+', '-', '*', '/', ')', '^', ' ');

    public abstract Optional<Lexeme> process(StringBuilder sb, StringIterator iterator);

    protected static boolean isSeparator(char c) {
        return SEPARATORS.contains(c);
    }

    public static Rule is(Character c) {
        return new Rule() {
            @Override
            public Optional<Lexeme> process(StringBuilder sb, StringIterator iterator) {
                return iterator.current().equals(c) ? Lexeme.find(c) : Optional.empty();
            }
        };
    }
}
