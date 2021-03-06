package hoxo.math.parser.tree;

public class Variable extends Leaf {
    public Variable(String value) {
        super(value);
    }

    @Override
    public <T> T visit(ASTVisitor<T> visitor) {
        return visitor.visitVariable(this);
    }
}
