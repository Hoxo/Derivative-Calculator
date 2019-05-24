package hoxo.parser;

public class ExpressionException extends RuntimeException {
    public ExpressionException(String s) {
        super(s);
    }

    public ExpressionException(String message, Throwable cause) {
        super(message, cause);
    }
}
