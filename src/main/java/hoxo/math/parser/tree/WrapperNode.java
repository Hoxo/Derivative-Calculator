package hoxo.math.parser.tree;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class WrapperNode extends Intermediary {
    protected Optional<Node> child;

    public WrapperNode(String value) {
        super(value);
        child = Optional.empty();
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public List<Node> getChildren() {
        return child.<List<Node>>map(Lists::newArrayList).orElseGet(Lists::newArrayList);
    }

    @Override
    public boolean hasChildren() {
        return child.isPresent();
    }

    public Node getChild() {
        return child.orElse(null);
    }

    public void setChild(Node child) {
        this.child = Optional.ofNullable(child);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WrapperNode that = (WrapperNode) o;
        return Objects.equals(child, that.child);
    }
}