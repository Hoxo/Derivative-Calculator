package hoxo.parser.tree;

public class Multiply extends OperationNode {
    public Multiply() {
        super("*");
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
        return true;
    }

    @Override
    public <T> T visit(ASTVisitor<T> visitor) {
        return visitor.visitMultiply(this);
    }
}
