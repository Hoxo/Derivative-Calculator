package hoxo.parser.tree;

import static hoxo.parser.tree.IntermediaryType.MINUS;

public class Minus extends OperationNode {

    public Minus() {
        super("-");
    }

    @Override
    public IntermediaryType getType() {
        return MINUS;
    }

}
