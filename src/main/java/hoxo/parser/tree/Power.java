package hoxo.parser.tree;

public class Power extends OperationNode {
    public Power() {
        super("^");
    }

    @Override
    public IntermediaryType getType() {
        return IntermediaryType.POWER;
    }
}
