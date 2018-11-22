package hoxo.parser.tree;

public class Multiply extends OperationNode {
    public Multiply() {
        super("*");
    }

    @Override
    public IntermediaryType getType() {
        return IntermediaryType.MULTIPLY;
    }
}
