package hoxo.math.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class Lexeme {

    public enum Type {
        FUNCTION_START("[a-zA-Z]+\\d*[a-zA-Z]*"),
        LPAREN("\\("),
        RPAREN("\\)"),
        VARIABLE("[a-zA-Z]+"),
        PLUS("\\+"),
        MINUS("\\-"),
        MULTIPLY("\\*"),
        DIVIDE("\\/"),
        POWER("\\^"),
        VALUE("\\d*\\.?\\d+"),
        WHITESPACE("\\s"),
        UNKNOWN("")
        ;

        private Pattern pattern;

        Type(String regex) {
            this.pattern = Pattern.compile(regex);
        }

        public Pattern getPattern() {
            return pattern;
        }
    }

    private static final Map<String, Lexeme> DICT = new HashMap<>();

    static {
        DICT.put("(", new Lexeme(Type.LPAREN, "("));
        DICT.put(")", new Lexeme(Type.RPAREN, ")"));
        DICT.put("+", new Lexeme(Type.PLUS, "+"));
        DICT.put("-", new Lexeme(Type.MINUS, "-"));
        DICT.put("/", new Lexeme(Type.DIVIDE, "/"));
        DICT.put("*", new Lexeme(Type.MULTIPLY, "*"));
        DICT.put("^", new Lexeme(Type.POWER, "^"));
        DICT.put(" ", new Lexeme(Type.WHITESPACE, " "));
    }

    private final Type type;
    private final String value;

    private Lexeme(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public static Lexeme plus() {
        return DICT.get("+");
    }

    public static Lexeme minus() {
        return DICT.get("-");
    }

    public static Lexeme multiply() {
        return DICT.get("*");
    }

    public static Lexeme divide() {
        return DICT.get("/");
    }

    public static Lexeme power() {
        return DICT.get("^");
    }

    public static Lexeme functionStart(String value) {
        return new Lexeme(Type.FUNCTION_START, value);
    }

    public static Lexeme rparen() {
        return DICT.get(")");
    }

    public static Lexeme variable(String variable) {
        return new Lexeme(Type.VARIABLE, variable);
    }

    public static Lexeme value(String value) {
        return new Lexeme(Type.VALUE, value);
    }

    public static Lexeme lparen() {
        return DICT.get("(");
    }

    public static Lexeme unknown(String str) {
        return new Lexeme(Type.UNKNOWN, str);
    }

    public static Lexeme space() {
        return DICT.get(" ");
    }

    public static Optional<Lexeme> find(String value) {
        return Optional.ofNullable(DICT.get(value));
    }

    public static Optional<Lexeme> find(Character value) {
        return Optional.ofNullable(DICT.get(Character.toString(value)));
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lexeme lexeme = (Lexeme) o;

        if (type != lexeme.type) return false;
        return value.equals(lexeme.value);
    }

    @Override
    public String toString() {
        return "Lexeme{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
