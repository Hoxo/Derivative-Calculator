package hoxo.parser;

import hoxo.parser.rule.Rule;
import hoxo.parser.rule.UnknownRule;

import java.util.*;

public class Lexer {

    private final List<Rule> rules;
    private final UnknownRule unknownRule;

    public Lexer(List<Rule> rules) {
        this.rules = rules;
        unknownRule = new UnknownRule();
    }

    public List<Lexeme> parse(String text) {
        List<Lexeme> lexemes = new ArrayList<>();
        StringBuilder accumulator = new StringBuilder();
        StringIterator iterator = new StringIterator(text);
        while (iterator.hasNext()) {
            iterator.next();
            accumulator.append(iterator.current());
            boolean created = false;
            for (Rule rule : rules) {
                Optional<Lexeme> lexemeO = rule.process(accumulator, iterator);
                if (lexemeO.isPresent()) {
                    created = true;
                    lexemes.add(lexemeO.get());
                    accumulator = new StringBuilder();
                    break;
                }
            }
            if (!created) {
                Optional<Lexeme> unknown = unknownRule.process(accumulator, iterator);
                unknown.ifPresent(lexemes::add);
            }
        }
        return lexemes;
    }
}
