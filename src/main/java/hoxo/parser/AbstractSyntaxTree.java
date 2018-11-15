package hoxo.parser;

import com.google.common.collect.Lists;

import java.util.List;

public class AbstractSyntaxTree {

    private Node root;

    public static class Iterator {
        private Node current;

        public boolean hasParent() {
            return current.hasParent();
        }

        public Intermediary parent() {
            if (!hasParent()) {
                return null;
            }
            current = current.getParent();
            return ((Intermediary) current);
        }

        public boolean hasChildren() {
            return current instanceof Intermediary && ((Intermediary) current).hasChildren();
        }

        public List<Node> getChildren() {
            if (!(current instanceof Intermediary)) {
                return null;
            } else {
                return ((Intermediary) current).getChildren();
            }
        }
    }

    private interface Type {}

    private enum LeafType implements Type {
        VALUE,
        VARIABLE,
        ;
    }

    private enum NodeType implements Type {
        MULTIPLY,
        SUM,
        MINUS,
        DIVIDE,
        POWER,
        FUNCTION,
        SCOPE,
        ;
    }

    private static abstract class Node {
        private boolean isLeaf;
        private Intermediary parent;

        public Node(boolean isLeaf, Intermediary parent) {
            this.isLeaf = isLeaf;
            this.parent = parent;
        }

        public Intermediary getParent() {
            return parent;
        }

        public void setParent(Intermediary parent) {
            this.parent = parent;
        }

        public boolean hasParent() {
            return parent != null;
        }

        public boolean isLeaf() {
            return isLeaf;
        }
    }

    public static class Leaf extends Node {
        private LeafType leafType;
        private String value;

        public Leaf(String value, LeafType leafType) {
            this(value, leafType, null);
        }

        public Leaf(String value, LeafType leafType, Intermediary parent) {
            super(true, parent);
            this.leafType = leafType;
            this.value = value;
        }

        public LeafType getLeafType() {
            return leafType;
        }

        public String getValue() {
            return value;
        }
    }

    public static class Intermediary extends Node {
        private NodeType nodeType;
        private List<Node> children;

        public Intermediary(NodeType nodeType, Intermediary parent, Node... children) {
            super(false, parent);
            this.nodeType = nodeType;
            this.children = Lists.newArrayList(children);
        }

        public NodeType getNodeType() {
            return nodeType;
        }

        public void add(Node node) {
            children.add(node);
        }

        public List<Node> getChildren() {
            return children;
        }

        public boolean hasChildren() {
            return children != null && !children.isEmpty();
        }
    }
}
