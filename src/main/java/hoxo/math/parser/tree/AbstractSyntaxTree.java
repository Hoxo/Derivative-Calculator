package hoxo.math.parser.tree;

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

    public Node getRoot() {
        return root;
    }

    public <T> T convert(ASTVisitor<T> visitor) {
        return root.visit(visitor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractSyntaxTree that = (AbstractSyntaxTree) o;
        return Objects.equals(root, that.root);
    }

    @Override
    public String toString() {
        return "AbstractSyntaxTree{" +
                "root=" + root +
                '}';
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
            if (current == null) {
                setRoot(par);
                return par;
            }
            Node tmp = current;
            while (tmp.hasParent() &&
                    tmp.getParent().getPriority() > par.getPriority() &&
                    tmp.getParent() instanceof OperationNode) {
                tmp = tmp.getParent();
            }
            while(tmp.hasParent() && tmp.getParent().getPriority() == par.getPriority() &&
                    par instanceof OperationNode && ((OperationNode) par).hasLeftAssociativity()) {
                tmp = tmp.getParent();
            }
            if (tmp.hasParent()) {
                Intermediary tmpParent = tmp.getParent();
                if (tmpParent instanceof OperationNode) {
                    if (tmp == ((OperationNode) tmpParent).getLeftChild()) {
                        setFirstChildFor(tmpParent, par);
                    } else {
                        setLastChildFor(tmpParent, par);
                    }
                } else if (tmpParent instanceof WrapperNode) {
                    setFirstChildFor(tmpParent, par);
                }
            } else {
                root = par;
            }
            setFirstChildFor(par, tmp);
            current = par;
            return par;
        }

        private void setParentFor(Node child, Intermediary parent) {
            if (child.hasParent()) {
                setLastChildFor(child.getParent(), parent);
            } else {
                root = parent;
            }
            current = parent;
            setLastChildFor(parent, child);
        }

        private void setLastChildFor(Intermediary parent, Node child) {
            if (parent instanceof OperationNode) {
                ((OperationNode) parent).setRightChild(child);
                child.setParent(parent);
            }
            if (parent instanceof WrapperNode) {
                ((WrapperNode) parent).setChild(child);
                child.setParent(parent);
            }
        }

        private void setFirstChildFor(Intermediary parent, Node child) {
            if (parent instanceof OperationNode) {
                ((OperationNode) parent).setLeftChild(child);
                child.setParent(parent);
            }
            if (parent instanceof WrapperNode) {
                ((WrapperNode) parent).setChild(child);
                child.setParent(parent);
            }
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

        public boolean hasCurrent() {
            return current != null;
        }

        public boolean isCurrentLeaf() {
            return current instanceof Leaf;
        }

        public boolean isCurrentIntermediary() {
            return current instanceof Intermediary;
        }

        public boolean isCurrentOperation() {
            return current instanceof OperationNode;
        }

        public boolean isCurrentWrapper() {
            return current instanceof WrapperNode;
        }

        public <T extends Node> Optional<T> setChild(T node) {
            if (current == null) {
                setRoot(node);
                return Optional.of(node);
            }
            if (isCurrentWrapper()) {
                ((WrapperNode) current).setChild(node);
                node.setParent(((WrapperNode) current));
                current = node;
                return Optional.of(node);
            } else if (isCurrentOperation()) {
                setLastChildFor(((OperationNode) current), node);
                current = node;
                return Optional.of(node);
            } else {
                return Optional.empty();
            }
        }

        public <T extends Node> Optional<T> setLeftChild(T node) {
            if (isCurrentOperation()) {
                ((OperationNode) current).setLeftChild(node);
                node.setParent(((OperationNode) current));
                current = node;
                return Optional.of(node);
            } else {
                return Optional.empty();
            }
        }

        public <T extends Node> Optional<T> setRightChild(T node) {
            if (isCurrentOperation()) {
                ((OperationNode) current).setRightChild(node);
                node.setParent(((OperationNode) current));
                current = node;
                return Optional.of(node);
            } else {
                return Optional.empty();
            }
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

        public Optional<Node> lastChild() {
            if (isCurrentIntermediary()) {
                if (isCurrentOperation()) {
                    OperationNode op = ((OperationNode) current);
                    if (op.hasRightChild()) {
                        current = op.getRightChild();
                    } else if (op.hasLeftChild()) {
                        current = op.getLeftChild();
                    } else {
                        return Optional.empty();
                    }
                    return Optional.of(current);
                }
                if (isCurrentWrapper()) {
                    current = ((WrapperNode) current).getChild();
                    return Optional.of(current);
                }
            }
            return Optional.empty();
        }
    }

}
