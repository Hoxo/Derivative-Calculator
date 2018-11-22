package hoxo.parser.tree;

import java.util.List;

public abstract class Intermediary extends Node {

    protected Intermediary(String value) {
        super(value);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    public abstract List<Node> getChildren();

    public abstract boolean hasChildren();

    @Override
    public abstract IntermediaryType getType();
}
