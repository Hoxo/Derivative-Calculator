package hoxo.parser.tree;

public class Value extends Leaf {
    public Value(String value) {
        super(value);
    }

    @Override
    public <T> T visit(ASTVisitor<T> visitor) {
        return visitor.visitValue(this);
    }

    @Override
    public LeafType getType() {
        return LeafType.VALUE;
    }
}
