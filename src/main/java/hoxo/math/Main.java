package hoxo.math;

import hoxo.math.converter.FromPlainTextConverter;
import hoxo.math.expression.Expression;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    private FromPlainTextConverter converter;

    public Main() {
        converter = new FromPlainTextConverter();
    }

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    public void run() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String a;
        while (!(a = reader.readLine()).equals("q")) {
            try {
                Expression expression;
                if (a.startsWith("'")) {
                    String[] input = a.split(" ", 2);
                    String cmd = input[0];
                    String exp = input[1];
                    if (!cmd.matches("'*")) {
                        throw new Exception("Incorrect command");
                    }
                    Expression der = converter.convert(exp);
                    for (int i = 0; i < cmd.length(); i++) {
                        der = der.derivative();
                    }
                    System.out.println(der);
                    continue;
                }
                if (a.startsWith("dfr ")) {
                    expression = converter.convert(a.substring(3));
                    expression = expression.derivative();
                    System.out.println(expression);
                } else {
                    expression = converter.convert(a);
                    System.out.println(expression.evaluate(1));
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}
