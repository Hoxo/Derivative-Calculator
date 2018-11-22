package hoxo.parser.tree;

import static hoxo.parser.tree.IntermediaryType.FUNCTION;

public class Function extends WrapperNode {
    public Function(String value) {
        super(value);
    }

    @Override
    public IntermediaryType getType() {
        return FUNCTION;
    }

    @Override
    public String toString() {
        return getValue() + "(" + getChild() + ")";
    }
}
