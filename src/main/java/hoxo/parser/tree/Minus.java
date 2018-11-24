package hoxo.parser.tree;

import static hoxo.parser.tree.IntermediaryType.MINUS;

public class Minus extends OperationNode {

    public Minus() {
        super("-");
    }

    @Override
    public <T> T visit(ASTVisitor<T> visitor) {
        return visitor.visitMinus(this);
    }

    @Override
    public IntermediaryType getType() {
        return MINUS;
    }

}
