import java.math.BigInteger;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * The basic reverse polish calculator based on stack and red black tree.
 * It reads and then evaluates postfix expressions involving BigIntegers and variables.
 * It reads a line from the user, evaluates the expression and displays the result. It
 * continues to do this until the user hits the return key with no input or it encounters
 * an error. We will use the standard binary operators (+, -, *, / , %, =) with their
 * usual meanings. The assignment operator “=” requires that the left hand side of the
 * expression be a variable. We will also use a unary minus. The unary minus will be
 * represented with the tilde character “~”. Finally, we will use a ternary operation
 * - powerMod. powerMod computes x to the y modulo z. It will be represented by the
 * circumflex character “^”. All results will be integers.
 *
 * @author: Yan Xing
 * @create: 2020-02-13 16:30
 **/
public class ReversePolishNotation {
    // stack
    private DynamicStack stack;
    // the set of operations
    private static Set<String> ops;
    // red black tree
    private RedBlackTree redBlackTree;

    /**
     * Constructor
     */
    public ReversePolishNotation() {
        this.stack = new DynamicStack();
        this.redBlackTree = new RedBlackTree();
        ops = new HashSet<>();
        ops.add("+");
        ops.add("-");
        ops.add("*");
        ops.add("/");
        ops.add("%");
        ops.add("=");
        ops.add("^");
        ops.add("~");
    }

    /**
     * Check whether input string is word.
     *
     * @param string input string
     * @return whether is word
     */
    private boolean isWord(String string) {
        return string.matches("[a-zA-Z]+");
    }

    /**
     * Get the number of the string. If input is variable, search it in tree, if not exists, throw exception.
     *
     * @param string input string
     * @return value
     */
    public BigInteger getNum(String string) {
        if (isWord(string)) {
            BigInteger res = redBlackTree.get(string);
            if (res == null) {
                System.out.println("Exception in thread \"main\" java.lang.Exception: error: no variable " + string);
                System.exit(0);
            }
            return res;
        }

        return new BigInteger(string);
    }

    /**
     * Main routine to test the ReversePolishNotation.
     *
     * @param args input args
     */
    public static void main(String[] args) {
        ReversePolishNotation instance = new ReversePolishNotation();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String inputString = scanner.nextLine();

            // terminating
            if (inputString.length() < 1) {
                System.out.println("terminating");
                System.exit(0);
            }

            String[] strings = inputString.split(" ");
            for (int i = 0; i < strings.length; i++) {
                String cur = strings[i];
                if (ops.contains(cur)) {
                    // check + - * / %
                    if (cur.equals("+") || cur.equals("-") || cur.equals("*") || cur.equals("/") ||
                            cur.equals("%")) {

                        if (instance.stack.getSize() < 2) {
                            System.out.println("Exception in thread \"main\" java.lang.Exception: error: stack underflow exception");
                            System.exit(0);
                        }

                        String aString = (String) instance.stack.pop();
                        String bString = (String) instance.stack.pop();

                        BigInteger a = instance.getNum(aString);
                        BigInteger b = instance.getNum(bString);

                        BigInteger result = BigInteger.ZERO;

                        if (cur.equals("+")) {
                            result = b.add(a);
                        } else if (cur.equals("-")) {
                            result = b.subtract(a);
                        } else if (cur.equals("*")) {
                            result = b.multiply(a);
                        } else if (cur.equals("/")) {
                            result = b.divide(a);
                        } else {
                            result = b.mod(a);
                        }

                        instance.stack.push(result.toString());
                    } else if (cur.equals("~")) {
                        // check ~
                        if (instance.stack.getSize() < 1) {
                            System.out.println("Exception in thread \"main\" java.lang.Exception: error: stack underflow exception");
                            System.exit(0);
                        }

                        BigInteger a = new BigInteger((String) instance.stack.pop());
                        BigInteger result = a.negate();
                        instance.stack.push(result.toString());
                    } else if (cur.equals("^")) {
                        // check ^
                        if (instance.stack.getSize() < 3) {
                            System.out.println("Exception in thread \"main\" java.lang.Exception: error: stack underflow exception");
                            System.exit(0);
                        }

                        BigInteger a = new BigInteger((String) instance.stack.pop());
                        BigInteger b = new BigInteger((String) instance.stack.pop());
                        BigInteger c = new BigInteger((String) instance.stack.pop());

                        BigInteger result = c.modPow(b, a);
                        instance.stack.push(result.toString());
                    } else if (cur.equals("=")) {
                        // check =
                        if (instance.stack.getSize() < 2) {
                            System.out.println("Exception in thread \"main\" java.lang.Exception: error: stack underflow exception");
                            System.exit(0);
                        }

                        BigInteger a = new BigInteger((String) instance.stack.pop());
                        String var = (String) instance.stack.pop();

                        if (instance.isWord(var)) {
                            instance.redBlackTree.insert(var, a);
                            instance.stack.push(a.toString());
                        } else {
                            System.out.println("Exception in thread \"main\" java.lang.Exception: error: " + var + " not an lvalue");
                            System.exit(0);
                        }
                    }
                } else {
                    // poping the string into stack
                    instance.stack.push(strings[i]);
                }
            }
            System.out.println(instance.getNum((String) instance.stack.pop()));
        }
    }

}
