package hoxo.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public class GrammarChecker {

    private final Set<String> allowedFunctions;

    public GrammarChecker(Set<String> allowedFunctions) {
        this.allowedFunctions = Sets.newHashSet(allowedFunctions);
    }

    public List<String> check(List<Lexeme> lexemes) {
        List<String> result = Lists.newArrayList();
        for (Lexeme lexeme : lexemes) {
            if (!lexeme.getType().getPattern().matcher(lexeme.getValue()).matches()) {
                result.add("Unknown lexeme: " + lexeme.getValue());
                continue;
            }
            if (lexeme.getType().equals(Lexeme.Type.FUNCTION_START)) {
                if (!allowedFunctions.contains(lexeme.getValue())) {
                    result.add("Unknown function: " + lexeme.getValue());
                    continue;
                }
            }

        }
        return result;
    }

}
