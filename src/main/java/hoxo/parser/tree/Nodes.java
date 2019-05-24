package hoxo.parser.tree;

public class Nodes {

    public static Plus plus() {
        return new Plus();
    }

    public static Minus minus() {
        return new Minus();
    }

    public static Function function(String value) {
        return new Function(value);
    }

    public static Scope scope() {
        return new Scope();
    }

    public static Multiply multiply() {
        return new Multiply();
    }

    public static Divide divide() {
        return new Divide();
    }

    public static Power power() {
        return new Power();
    }

    public static Variable variable(String value) {
        return new Variable(value);
    }

    public static Value value(String value) {
        return new Value(value);
    }

    public static UnaryMinus unaryMinus() {
        return new UnaryMinus();
    }

}
