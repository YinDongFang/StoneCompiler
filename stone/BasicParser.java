package stone;

import static stone.Parser.rule;
import java.util.HashSet;
import stone.Parser.Operators;
import stone.ast.*;

public class BasicParser {
    HashSet<String> reserved = new HashSet<String>();
    Operators operators = new Operators();
    Parser expr0 = rule();
    public Parser primary = rule(PrimaryExpr.class).or(rule().sep("(").ast(expr0).sep(")"),
            rule().number(NumberLiteral.class), rule().identifier(Name.class, reserved),
            rule().string(StringLiteral.class));
    public Parser factor = rule().or(rule(NegativeExpr.class).sep("-").ast(primary), primary);
    public Parser expr = expr0.expression(BinaryExpr.class, factor, operators);
    Parser statement0 = rule();
    public Parser block = rule(BlockStmnt.class).sep("{").option(statement0)
            .repeat(rule().sep(";", Token.EOL).option(statement0)).sep("}");
    public Parser simple = rule(PrimaryExpr.class).ast(expr);
    public Parser statement = statement0.or(
            rule(IfStmnt.class).sep("if").ast(expr).repeat(rule().sep(Token.EOL)).ast(block).option(rule().repeat(rule().sep(Token.EOL)).sep("else").ast(block)),
            rule(WhileStmnt.class).sep("while").ast(expr).ast(block), simple);
    public Parser program = rule().or(statement, rule(NullStmnt.class)).sep(";", Token.EOL);
    public Parser test = rule().or(rule().number().number().sep(Token.EOL), rule().number().number().sep(Token.EOL));

    public BasicParser() {
        reserved.add(";");
        reserved.add("}");
        reserved.add(Token.EOL);
        operators.add("=", 1, Operators.RIGHT);
        operators.add("==", 2, Operators.LEFT);
        operators.add(">", 2, Operators.LEFT);
        operators.add("<", 2, Operators.LEFT);
        operators.add("+", 3, Operators.LEFT);
        operators.add("-", 3, Operators.LEFT);
        operators.add("*", 4, Operators.LEFT);
        operators.add("/", 4, Operators.LEFT);
        operators.add("%", 4, Operators.LEFT);
    }

    public ASTree parse(Lexer lexer) throws ParseException {
        return program.parse(lexer);
    }
}