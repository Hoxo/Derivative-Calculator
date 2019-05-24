package hoxo.math.parser.tree;

import java.util.List;

public abstract class Intermediary extends Node {

    protected Intermediary(String value) {
        super(value);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    public abstract int getPriority();

    public abstract List<Node> getChildren();

    public abstract boolean hasChildren();
}
