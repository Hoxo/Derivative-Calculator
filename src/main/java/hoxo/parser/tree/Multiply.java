package hoxo.parser.tree;

public class Multiply extends OperationNode {
    public Multiply() {
        super("*");
    }

    @Override
    public <T> T visit(ASTVisitor<T> visitor) {
        return visitor.visitMultiply(this);
    }

    @Override
    public IntermediaryType getType() {
        return IntermediaryType.MULTIPLY;
    }
}
