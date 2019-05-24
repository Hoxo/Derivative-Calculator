package hoxo.parser.tree;

public interface ASTVisitor<T> {
    T visitPlus(Plus plus);
    T visitMinus(Minus minus);
    T visitPower(Power power);
    T visitMultiply(Multiply multiply);
    T visitDivide(Divide divide);
    T visitValue(Value value);
    T visitVariable(Variable variable);
    T visitScope(Scope scope);
    T visitFunction(Function function);
    T visitUnaryMinus(UnaryMinus minus);
}
