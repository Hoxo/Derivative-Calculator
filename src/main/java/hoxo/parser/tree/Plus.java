package hoxo.parser.tree;

public class Plus extends OperationNode {

    public Plus() {
        super("+");
    }

    @Override
    public IntermediaryType getType() {
        return IntermediaryType.SUM;
    }

}
