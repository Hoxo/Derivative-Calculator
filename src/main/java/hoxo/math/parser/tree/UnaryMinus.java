package hoxo.math.parser.tree;

public class UnaryMinus extends WrapperNode {

    public UnaryMinus() {
        super("-");
    }

    @Override
    public <T> T visit(ASTVisitor<T> visitor) {
        return visitor.visitUnaryMinus(this);
    }

    @Override
    public String toString() {
        return "-" + getChild();
    }
}
