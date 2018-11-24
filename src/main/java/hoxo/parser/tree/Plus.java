package hoxo.parser.tree;

public class Plus extends OperationNode {

    public Plus() {
        super("+");
    }

    @Override
    public <T> T visit(ASTVisitor<T> visitor) {
        return visitor.visitPlus(this);
    }

    @Override
    public IntermediaryType getType() {
        return IntermediaryType.SUM;
    }

}
