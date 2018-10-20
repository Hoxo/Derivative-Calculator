package hoxo.parser.rule;

import java.util.List;

import com.google.common.collect.Lists;

public class Rules {

    public static final List<Rule> BASIC_RULES = Lists.newArrayList(
            Rule.is(')'),
            Rule.is('+'),
            Rule.is('-'),
            Rule.is('/'),
            Rule.is('*'),
            Rule.is('^'),
            FunctionRule.instance(),
            WhitespaceRule.instance(),
            ValueRule.instance(),
            ValueRule.instance(),
            LParenRule.instance()
    );
}
