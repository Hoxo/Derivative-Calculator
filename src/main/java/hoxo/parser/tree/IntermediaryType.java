package hoxo.parser.tree;

public enum IntermediaryType implements NodeType {
    MULTIPLY(1, "*"),
    SUM(0, "+"),
    MINUS(0, "-"),
    DIVIDE(1, "/"),
    POWER(1, "^"),
    FUNCTION(2, "function"),
    SCOPE(2, "()"),
    ;

    private int priority;
    private String value;

    IntermediaryType(int priority, String value) {
        this.priority = priority;
        this.value = value;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return value;
    }
}