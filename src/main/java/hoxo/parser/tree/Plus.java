package hoxo.parser.tree;

public class Plus extends OperationNode {

    public Plus() {
        super("+");
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public <T> T visit(ASTVisitor<T> visitor) {
        return visitor.visitPlus(this);
    }
}
