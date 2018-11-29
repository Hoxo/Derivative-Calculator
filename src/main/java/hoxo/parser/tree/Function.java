package hoxo.parser.tree;

public class Function extends WrapperNode {
    public Function(String value) {
        super(value);
    }

    @Override
    public <T> T visit(ASTVisitor<T> visitor) {
        return visitor.visitFunction(this);
    }

    @Override
    public String toString() {
        return getValue() + "(" + getChild() + ")";
    }
}
