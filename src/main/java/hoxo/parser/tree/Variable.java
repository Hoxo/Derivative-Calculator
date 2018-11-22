package hoxo.parser.tree;

public class Variable extends Leaf {
    public Variable(String value) {
        super(value);
    }

    @Override
    public LeafType getType() {
        return LeafType.VARIABLE;
    }
}
