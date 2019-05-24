package hoxo.math.parser.tree;

public class Power extends OperationNode {
    public Power() {
        super("^");
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean hasLeftAssociativity() {
        return false;
    }

    @Override
    public boolean hasRightAssociativity() {
        return true;
    }

    @Override
    public <T> T visit(ASTVisitor<T> visitor) {
        return visitor.visitPower(this);
    }
}
