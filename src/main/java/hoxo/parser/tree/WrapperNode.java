package hoxo.parser.tree;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;

public abstract class WrapperNode extends Intermediary {
    protected Optional<Node> child;

    public WrapperNode(String value) {
        super(value);
        child = Optional.empty();
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
}