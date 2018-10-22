package hoxo.function.operation;

import hoxo.function.Function;

import java.util.List;
import java.util.Objects;

public abstract class MultiOperation implements Function {
    private List<Function> args;

    public MultiOperation(List<Function> args) {
        this.args = args;
    }

    public List<Function> getArgs() {
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
