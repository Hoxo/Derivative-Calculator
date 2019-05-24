package hoxo.math.parser.tree;

public class Scope extends WrapperNode {

    public Scope() {
        super("()");
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
