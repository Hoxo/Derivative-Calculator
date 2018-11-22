package hoxo.parser.tree;

public class Divide extends OperationNode {
    public Divide() {
        super("/");
    }

    @Override
    public IntermediaryType getType() {
        return IntermediaryType.DIVIDE;
    }
}
