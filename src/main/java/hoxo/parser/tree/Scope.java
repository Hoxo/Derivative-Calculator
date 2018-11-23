package hoxo.parser.tree;

import static hoxo.parser.tree.IntermediaryType.SCOPE;

public class Scope extends WrapperNode {

    public Scope() {
        super("()");
    }

    @Override
    public IntermediaryType getType() {
        return SCOPE;
    }

    @Override
    public String toString() {
        return "(" + getChild() + ")";
    }
}