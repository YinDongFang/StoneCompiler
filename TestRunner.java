import stone.*;
import stone.ast.*;

public class TestRunner {
    public static void main(String[] args) throws ParseException {
        Lexer l = new Lexer(new CodeDialog());
        BasicParser bp = new BasicParser();
        while (l.peek(0) != Token.EOF) {
            ASTree ast = bp.parse(l);
            System.out.println("program: \n" + ast.toString());
        }
    }
}
/*
even = 0
odd = 0
i = 1
while i < 10 {
if i % 2 == 0 { // even number?
even = even + i
} else {
odd = odd + i
}
i = i + 1
}
even + odd
*/