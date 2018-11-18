package hoxo.parser;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AbstractSyntaxTree {

    private Node root;

    public AbstractSyntaxTree() {}

    public AbstractSyntaxTree(Node root) {
        this.root = root;
    }

    public Iterator iterator() {
        return new Iterator(root);
    }

    public class Iterator {
        private Node current;

        private Iterator(Node current) {
            this.current = current;
        }

        public boolean hasParent() {
            return current != null && current.hasParent();
        }

        public Optional<Intermediary> getParent() {
            if (!hasParent()) {
                return Optional.empty();
            }
            return Optional.of(current.getParent());
        }

        public Intermediary addParent(Intermediary par) {
            if (root == null) {
                setRoot(par);
                return par;
            }
            setParentFor(current, par);
            return par;
        }

        public Intermediary addParentWithPriority(Intermediary par) {
            Node tmp = current;
            while (tmp.hasParent() &&
                    tmp.getParent().getIntermediaryType().getPriority() >= par.getIntermediaryType().getPriority() &&
                    tmp.getParent().getIntermediaryType().getPriority() != 2) {
                tmp = tmp.getParent();
            }
            setParentFor(tmp, par);
            return par;
        }

        private void setParentFor(Node child, Intermediary parent) {
            if (child.hasParent()) {
                child.getParent().clearChildren();
                child.getParent().addChild(parent);
                parent.setParent(child.getParent());
            } else {
                root = parent;
            }
            child.setParent(parent);
            parent.addChild(child);
        }

        public Optional<Intermediary> parent() {
            if (!hasParent()) {
                return Optional.empty();
            }
            current = current.getParent();
            return Optional.of((Intermediary) current);
        }

        public Optional<Node> root() {
            if (root == null) {
                return Optional.empty();
            } else {
                current = root;
                return Optional.of(current);
            }
        }

        public Optional<Node> current() {
            return Optional.ofNullable(current);
        }

        public <T extends Node> Optional<T> addChild(T node) {
            if (current == null) {
                setRoot(node);
                return Optional.of(node);
            }
            if (current.isLeaf()) {
                return Optional.empty();
            }
            ((Intermediary) current).addChild(node);
            return Optional.of(node);
        }

        private void setRoot(Node node) {
            current = root = node;
        }

        public boolean hasChildren() {
            return current instanceof Intermediary && ((Intermediary) current).hasChildren();
        }

        public List<Node> getChildren() {
            if (!(current instanceof Intermediary)) {
                return Collections.emptyList();
            } else {
                return ((Intermediary) current).getChildren();
            }
        }

        public <T extends Node> Optional<T> addSibling(T node) {
            if (!hasParent()) {
                return Optional.empty();
            }
            current.getParent().addChild(node);
            return Optional.of(node);
        }

        public Optional<Node> child(int i) {
            if (current.isLeaf() || current.asIntermediary().getChildren().size() <= i || i < 0) {
                return Optional.empty();
            } else {
                current = current.asIntermediary().getChildren().get(i);
                return Optional.of(current);
            }
        }
    }

    private interface Type {}

    public enum LeafType implements Type {
        VALUE,
        VARIABLE,
        ;
    }

    public enum IntermediaryType implements Type {
        MULTIPLY(1),
        SUM(0),
        MINUS(0),
        DIVIDE(1),
        POWER(1),
        FUNCTION(2),
        SCOPE(2),
        ;

        private int priority;

        IntermediaryType(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }
    }

    public static abstract class Node {
        private Intermediary parent;

        public Node(Intermediary parent) {
            this.parent = parent;
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

        public abstract boolean isLeaf();
    }

    public static class Leaf extends Node {
        private LeafType leafType;
        private String value;

        private Leaf(String value, LeafType leafType) {
            this(value, leafType, null);
        }

        private Leaf(String value, LeafType leafType, Intermediary parent) {
            super(parent);
            this.leafType = leafType;
            this.value = value;
        }

        @Override
        public boolean isLeaf() {
            return true;
        }

        public static Leaf variable(String variable) {
            return new Leaf(variable, LeafType.VARIABLE);
        }

        public static Leaf value(String value) {
            return new Leaf(value, LeafType.VALUE);
        }

        public LeafType getLeafType() {
            return leafType;
        }

        public String getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Leaf leaf = (Leaf) o;
            return leafType == leaf.leafType &&
                    Objects.equals(value, leaf.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(leafType, value);
        }
    }

    public static class Intermediary extends Node {
        private IntermediaryType intermediaryType;
        private String value;
        private List<Node> children;

        private Intermediary(IntermediaryType type) {
            super( null);
            intermediaryType = type;
            children = Lists.newArrayList();
        }

        private Intermediary(IntermediaryType intermediaryType, String value, Node... children) {
            super(null);
            this.intermediaryType = intermediaryType;
            this.value = value;
            this.children = Lists.newArrayList(children);
        }

        public static Intermediary sum(Node... children) {
            return new Intermediary(IntermediaryType.SUM, "+", children);
        }

        public static Intermediary multiply(Node... children) {
            return new Intermediary(IntermediaryType.MULTIPLY, "*", children);
        }

        public static Intermediary divide(Node... children) {
            return new Intermediary(IntermediaryType.DIVIDE, "/", children);
        }

        public static Intermediary power(Node... children) {
            return new Intermediary(IntermediaryType.POWER, "^", children);
        }

        public static Intermediary function(String value, Node... children) {
            return new Intermediary(IntermediaryType.FUNCTION, value, children);
        }

        public static Intermediary scope(Node... children) {
            return new Intermediary(IntermediaryType.SCOPE, "()", children);
        }

        public IntermediaryType getIntermediaryType() {
            return intermediaryType;
        }

        public void addChild(Node node) {
            children.add(node);
            node.setParent(this);
        }

        @Override
        public boolean isLeaf() {
            return false;
        }

        public String getValue() {
            return value;
        }

        public List<Node> getChildren() {
            return children;
        }

        public void clearChildren() {
            children.clear();
        }

        public boolean hasChildren() {
            return children != null && !children.isEmpty();
        }
    }
}
