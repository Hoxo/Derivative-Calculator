package hoxo.parser.tree;

public class Minus extends OperationNode {

    public Minus() {
        super("-");
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public <T> T visit(ASTVisitor<T> visitor) {
        return visitor.visitMinus(this);
    }
}
