package hoxo.parser.tree;

public class Divide extends OperationNode {
    public Divide() {
        super("/");
    }

    @Override
    public <T> T visit(ASTVisitor<T> visitor) {
        return visitor.visitDivide(this);
    }

    @Override
    public IntermediaryType getType() {
        return IntermediaryType.DIVIDE;
    }
}
