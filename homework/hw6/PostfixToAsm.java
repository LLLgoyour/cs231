import java.util.*;

public class PostfixToAsm {
    private static int tmpId = 0;

    private static String newTmp() {
        tmpId++;
        return "TMP" + tmpId;
    }

    private static void emit(String op, String operand) {
        System.out.printf("   %-6s %s%n", op, operand);
    }

    // evaluate(left, op, right): prints assembly and returns temp name holding
    // result
    private static String evaluate(String left, String op, String right) {
        // Load left
        emit("LD", left);
        // Apply operator with right
        switch (op) {
            case "+":
                emit("AD", right);
                break;
            case "-":
                emit("SB", right);
                break;
            case "*":
                emit("MU", right);
                break;
            case "/":
                emit("DV", right);
                break;
            default:
                throw new IllegalArgumentException("Unknown op: " + op);
        }
        // Store to temp and return temp name
        String t = newTmp();
        emit("ST", t);
        return t;
    }

    // main conversion: postfix (space-separated) to assembly
    public static void generate(String postfix) {
        tmpId = 0;
        // use Stack (LIFO) to evaluate postfix expressions
        Stack<String> st = new Stack<>();
        String[] toks = postfix.trim().split("\\s+");
        for (String t : toks) {
            if (t.equals("+") || t.equals("-") || t.equals("*") || t.equals("/")) {
                String right = st.pop();
                String left = st.pop();
                String res = evaluate(left, t, right);
                st.push(res);
            } else {
                st.push(t);
            }
        }
        // Top of stack holds final temp; nothing more to print per spec.
    }

    // Example usage
    public static void main(String[] args) {
        String infix = "( ( AX + ( BY * C ) ) / ( D4 - E ) )";
        String postfix = "AX BY C * + D4 E - /";

        System.out.println("Infix Expression: " + infix);
        System.out.println("Postfix Expression: " + postfix);
        System.out.println();
        generate(postfix);
    }
}
