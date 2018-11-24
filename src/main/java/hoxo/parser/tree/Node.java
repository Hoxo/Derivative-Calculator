package hoxo.parser.tree;

import java.util.Objects;

public abstract class Node {
    private Intermediary parent;
    private String value;

    public Node(String value) {
        this.value = value;
    }

    public Intermediary getParent() {
        return parent;
    }

    public Intermediary asIntermediary() {
        return (Intermediary) this;
    }

    public Leaf asLeaf() {
        return (Leaf) this;
    }

    public void setParent(Intermediary parent) {
        this.parent = parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public String getValue() {
        return value;
    }

    public abstract <T> T visit(ASTVisitor<T> visitor);

    public abstract boolean isLeaf();

    public abstract NodeType getType();

    public abstract String toString();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(value, node.value);
    }

}