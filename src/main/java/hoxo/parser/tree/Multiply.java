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
    public <T> T visit(ASTVisitor<T> visitor) {
        return visitor.visitMultiply(this);
    }
}
