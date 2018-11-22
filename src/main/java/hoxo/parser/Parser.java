package hoxo.parser;

import com.google.common.collect.Sets;
import hoxo.parser.tree.*;

import java.util.*;
import java.util.stream.Collectors;

import static hoxo.parser.Lexeme.Type.*;

public class Parser {

    private static Set<Lexeme.Type> AFTER_OPERATOR = Sets.newHashSet(VALUE, VARIABLE, LPAREN, FUNCTION_START);
    private static Set<Lexeme.Type> AFTER_OPERAND = Sets.newHashSet(PLUS, MINUS, MULTIPLY, DIVIDE, POWER, RPAREN);

    private AbstractSyntaxTree ast;
    private AbstractSyntaxTree.Iterator astIterator;
    private ListIterator<Lexeme> iterator;
    private Deque<Lexeme> stack;

    public AbstractSyntaxTree parse(List<Lexeme> lexemes) {
        lexemes = lexemes.stream().filter(lexeme -> !lexeme.getType().equals(WHITESPACE)).collect(Collectors.toList());
        iterator = lexemes.listIterator();
        ast = new AbstractSyntaxTree();
        astIterator = ast.iterator();
        stack = new ArrayDeque<>();
        hasNextOrThrow(iterator);
        Lexeme next = iterator.next();
        switch (next.getType()) {
            case FUNCTION_START:
                processFunction(next);
                break;
            case VALUE:
                processValue(next);
                break;
            case VARIABLE:
                processVariable(next);
                break;
            case LPAREN:
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
        stack.push(lexeme);
        astIterator.setChild(Nodes.function(lexeme.getValue()));
        hasNextOrThrow(iterator);
        Lexeme next = iterator.next();
        switch (next.getType()) {
            case FUNCTION_START:
                processFunction(next);
                break;
            case VALUE:
                processValue(next);
                break;
            case VARIABLE:
                processVariable(next);
                break;
            case LPAREN:
                processLParen(next);
            case RPAREN:
                throw new ParseException("Missed argument in function: " + lexeme.getValue());
            default:
                unexpected(next);
        }
    }

    private void processLParen(Lexeme lexeme) {
        stack.push(lexeme);
        astIterator.setChild(Nodes.scope());
        hasNextOrThrow(iterator);
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
        astIterator.setChild(Nodes.variable(lexeme.getValue()));
        if (!iterator.hasNext()) {
            throwIfUnfinished();
            return;
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
        hasNextOrThrow(iterator);
        astIterator.addParentWithPriority(getNodeForOperator(lexeme.getType()));
        processWithConstraints(iterator.next(), AFTER_OPERATOR);
    }

    private void processValue(Lexeme lexeme) {
        astIterator.setChild(Nodes.value(lexeme.getValue()));
        if (!iterator.hasNext()) {
            throwIfUnfinished();
            return;
        }
        processWithConstraints(iterator.next(), AFTER_OPERAND);

    }

    private void processRParen(Lexeme lexeme) {
        if (stack.isEmpty()) {
            unexpected(lexeme);
        } else {

            Lexeme head = stack.pop();
            astIterator.parent();
            switch (head.getType()) {
                case LPAREN:
                    moveUpUntilTypeNotEquals(IntermediaryType.SCOPE);
                    break;
                case FUNCTION_START:
                    moveUpUntilTypeNotEquals(IntermediaryType.FUNCTION);
                    break;
                default:
                    throw new ParseException("Unknown lexeme in stack");
            }
        }
        if (!iterator.hasNext()) {
            throwIfUnfinished();
            return;
        }
        processWithConstraints(iterator.next(), AFTER_OPERAND);
    }

    private void moveUpUntilTypeNotEquals(IntermediaryType type) {
        while(astIterator.hasParent() && (astIterator.isCurrentLeaf() || astIterator.isCurrentIntermediary() &&
                !astIterator
                        .current()
                        .get()
                        .getType()
                        .equals(type))) {
            astIterator.parent();
        }
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

    private static Intermediary getNodeForOperator(Lexeme.Type lexemeType) {
        switch (lexemeType) {
            case MULTIPLY:
                return Nodes.multiply();
            case PLUS:
                return Nodes.plus();
            case MINUS:
                return Nodes.minus();
            case DIVIDE:
                return Nodes.divide();
            case POWER:
                return Nodes.power();
            default:
                throw new ParseException("Unable to find node with same operator " + lexemeType);
        }
    }

    private static void unexpected(Lexeme lexeme) {
        throw new ParseException(lexeme);
    }

    private static void unexpectedEOL() {
        throw new ParseException("Unexpected end of the line");
    }

    private static void hasNextOrThrow(Iterator<Lexeme> iterator) {
        if (!iterator.hasNext()) {
            unexpectedEOL();
        }
    }

    private void throwIfUnfinished() {
        if (!iterator.hasNext()) {
            if (!stack.isEmpty()) {
                unexpectedEOL();
            }
        }
    }
}
