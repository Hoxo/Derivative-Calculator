package hoxo.parser;

import java.util.*;
import java.util.stream.Collectors;

public class Parser {

    private AbstractSyntaxTree ast;
    private ListIterator<Lexeme> iterator;
    private Deque<Lexeme> stack;

    public AbstractSyntaxTree parse(List<Lexeme> lexemes) {
        lexemes = lexemes.stream().filter(lexeme -> !lexeme.getType().equals(Lexeme.Type.WHITESPACE)).collect(Collectors.toList());
        iterator = lexemes.listIterator();
        ast = new AbstractSyntaxTree();
        stack = new ArrayDeque<>();
        hasNextOrThrow(iterator);
        Lexeme next = iterator.next();
        switch (next.getType()) {
            case FUNCTION_START:
                stack.push(next);
                processFunction(next);
                break;
            case VALUE:
                processValue(next);
                break;
            case VARIABLE:
                processVariable(next);
                break;
            case LPAREN:
                stack.push(next);
                processLParen(next);
                break;
            default:
                unexpected(next);
        }
        if (iterator.hasNext()) {
            throw new ParseException("Parser bug");
        }
        return ast;
    }

    private void processFunction(Lexeme lexeme) {
        hasNextOrThrow(iterator);
        Lexeme next = iterator.next();
        switch (next.getType()) {
            case FUNCTION_START:
                stack.push(next);
                processFunction(next);
                break;
            case VALUE:
                processValue(next);
                break;
            case VARIABLE:
                processVariable(next);
                break;
            case LPAREN:
                stack.push(next);
                processLParen(next);
            case RPAREN:
                throw new ParseException("Missed argument in function: " + lexeme.getValue());
            default:
                unexpected(next);
        }
    }

    private void processLParen(Lexeme lexeme) {

        Lexeme next = iterator.next();
        switch (next.getType()) {
            case VALUE:
                processValue(next);
                break;
            case VARIABLE:
                processVariable(next);
                break;
            case FUNCTION_START:
                processFunction(next);
                break;
            default:
                unexpected(lexeme);
        }
    }

    private void processVariable(Lexeme lexeme) {
        if (!iterator.hasNext()) {
            if (stack.isEmpty()) {
                return;
            } else {
                unexpectedEOL();
            }
        }
        Lexeme next = iterator.next();
        switch (next.getType()) {
            case MINUS:
            case PLUS:
            case MULTIPLY:
            case DIVIDE:
            case POWER:
                processOperator(next);
                break;
            case RPAREN:
                if (!stack.isEmpty()) {
                    processRParen(next);
                } else {
                    unexpected(next);
                }
                break;
            default:
                unexpected(next);
        }
    }

    private void processOperator(Lexeme lexeme) {

    }

    private void processValue(Lexeme lexeme) {

    }

    private void processRParen(Lexeme lexeme) {

    }

    private void processWithConstraints(Lexeme lexeme, Set<Lexeme.Type> constraints) {
        if (!constraints.contains(lexeme.getType())) {
            unexpected(lexeme);
        }
        switch (lexeme.getType()) {
            case PLUS:
            case MINUS:
            case MULTIPLY:
            case DIVIDE:
            case POWER:
                processOperator(lexeme);
                break;
            case FUNCTION_START:
                processFunction(lexeme);
                break;
            case VALUE:
                processValue(lexeme);
                break;
            case VARIABLE:
                processVariable(lexeme);
                break;
            case LPAREN:
                processLParen(lexeme);
                break;
            case RPAREN:
                processRParen(lexeme);
        }
    }

    private static void unexpected(Lexeme lexeme) {
        throw new ParseException(lexeme);
    }

    private static void unexpectedEOL() {
        throw new ParseException("Unexpected end of line");
    }

    private static void hasNextOrThrow(Iterator<Lexeme> iterator) {
        if (!iterator.hasNext()) {
            unexpectedEOL();
        }
    }
}
