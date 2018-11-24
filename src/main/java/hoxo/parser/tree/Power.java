package hoxo.parser.tree;

public class Power extends OperationNode {
    public Power() {
        super("^");
    }

    @Override
    public <T> T visit(ASTVisitor<T> visitor) {
        return visitor.visitPower(this);
    }

    @Override
    public IntermediaryType getType() {
        return IntermediaryType.POWER;
    }
}
