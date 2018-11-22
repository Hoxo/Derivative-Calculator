package hoxo.parser.tree;

public class Value extends Leaf {
    public Value(String value) {
        super(value);
    }

    @Override
    public LeafType getType() {
        return LeafType.VALUE;
    }
}
