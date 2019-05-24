package hoxo.parser.tree;

public class Divide extends OperationNode {
    public Divide() {
        super("/");
    }

    @Override
    public int getPriority() {
        return 1;
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
        return visitor.visitDivide(this);
    }
}
