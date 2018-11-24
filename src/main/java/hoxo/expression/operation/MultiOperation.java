package hoxo.expression.operation;

import hoxo.expression.Expression;

import java.util.List;
import java.util.Objects;

public abstract class MultiOperation implements Expression {
    private List<Expression> args;

    public MultiOperation(List<Expression> args) {
        this.args = args;
    }

    public List<Expression> getArgs() {
        return args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiOperation that = (MultiOperation) o;
        return Objects.equals(args, that.args);
    }
}
