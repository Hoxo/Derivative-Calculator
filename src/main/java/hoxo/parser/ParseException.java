package hoxo.parser;

public class ParseException extends RuntimeException {
    public ParseException(Lexeme lexeme) {
        super("Unexpected lexeme: " + lexeme);
    }

    public ParseException(String s) {
        super(s);
    }
}
