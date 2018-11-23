package hoxo.parser.tree;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class OperationNode extends Intermediary {
    protected Optional<Node> left, right;

    public OperationNode(String value) {
        super(value);
        left = Optional.empty();
        right = Optional.empty();
    }

    public Node getLeftChild() {
        return left.orElse(null);
    }

    public boolean hasLeftChild() {
        return left.isPresent();
    }

    @Override
    public List<Node> getChildren() {
        List<Node> list = Lists.newArrayList();
        left.ifPresent(list::add);
        right.ifPresent(list::add);
        return list;
    }

    @Override
    public boolean hasChildren() {
        return hasLeftChild() && hasRightChild();
    }

    public void setLeftChild(Node left) {
        this.left = Optional.ofNullable(left);
        left.setParent(this);
    }

    public boolean hasRightChild() {
        return right.isPresent();
    }

    public Node getRightChild() {
        return right.orElse(null);
    }

    public void setRightChild(Node right) {
        this.right = Optional.ofNullable(right);
        right.setParent(this);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", getLeftChild(), getValue(), getRightChild());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OperationNode that = (OperationNode) o;
        return Objects.equals(left, that.left) &&
                Objects.equals(right, that.right);
    }

}
