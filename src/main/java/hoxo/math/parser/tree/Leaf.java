package hoxo.math.parser.tree;

public abstract class Leaf extends Node {

    public Leaf(String value) {
        super(value);
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public String toString() {
        return getValue();
    }
}