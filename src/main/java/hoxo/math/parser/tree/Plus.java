package hoxo.math.parser.tree;

public class Plus extends OperationNode {

    public Plus() {
        super("+");
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
        return true;
    }

    @Override
    public <T> T visit(ASTVisitor<T> visitor) {
        return visitor.visitPlus(this);
    }
}
