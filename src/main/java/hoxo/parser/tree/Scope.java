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
    public <T> T visit(ASTVisitor<T> visitor) {
        return visitor.visitScope(this);
    }

    @Override
    public String toString() {
        return "(" + getChild() + ")";
    }
}
