package hoxo.math.parser.tree;

public class Minus extends OperationNode {

    public Minus() {
        super("-");
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean hasLeftAssociativity() {
        return true;
    }

    @Override
    public boolean hasRightAssociativity() {
        return false;
    }

    @Override
    public <T> T visit(ASTVisitor<T> visitor) {
        return visitor.visitMinus(this);
    }
}
