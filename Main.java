package Project2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);
        String input = "";

        while (!input.equals("/exit")) {

            input = "3+5*16/2^2-6";//1: "3+5*16/2^2-6" 2: "3*2+6-5/5" 3: "4+5-3*2*1"

            ArrayList<Operator> express = new ArrayList<Operator>();

            //extracts the given equation into an arraylist
            for (int i = 0; i < input.length(); i++) {

                if (isSymbol(input.charAt(i))) {
                    switch (input.charAt(i)) {
                        case '+':
                            express.add(new Plus());
                            break;
                        case '-':
                            express.add(new Minus());
                            break;
                        case '*':
                            express.add(new Times());
                            break;
                        case '/':
                            express.add(new Divide());
                            break;
                        case '^':
                            express.add(new Power());
                            break;
                        default:
                            System.out.println("Not a valid operation.");
                            break;
                    }
                } else if (isNumber(input.charAt(i))) {

                    int value = 0;
                    int j = i;

                    while (j != input.length() && isNumber(input.charAt(j))) {

                        value = (value * 10) + Character.getNumericValue(input.charAt(j));
                        j++;

                    }

                    i = j - 1;
                    express.add(new IntNode(value));

                }

            }

            //converts the arraylist into postfix equation format
            ArrayList<Operator> postfixExpress = infixToPostfix(express);
            Operator root = postfixExpress.get(postfixExpress.size() - 1);

            //places operations into the binary search tree CORRECT
            for (int i = postfixExpress.size() - 2; i >= 0; i--) {

                if (!(postfixExpress.get(i) instanceof IntNode)) {
                    root.insertOperator(postfixExpress.get(i));
                }

            }

            // places the numbers into the binary search tree FAILS
//            for (int i = postfixExpress.size() - 2; i >= 0; i--) {
//
//                if (postfixExpress.get(i) instanceof IntNode) {
//                    root.insertNumber(postfixExpress.get(i));
//                }
//
//            }

            root.printInOrder();

            input = "/exit";

        }

    }

    public static ArrayList<Operator> infixToPostfix(ArrayList<Operator> express) {

        ArrayList<Operator> postfix = new ArrayList<Operator>();
        Stack<Operator> stack = new Stack<Operator>();

        for (int i = 0; i < express.size(); i++) {

            Operator c = express.get(i);

            if (c instanceof IntNode) {
                postfix.add(c);
            } else {

                while (!stack.isEmpty() && c.getValue() <= stack.peek().getValue()) {
                    postfix.add(stack.pop());
                }

                stack.push(c);

            }

        }

        while (!stack.isEmpty()) {
            postfix.add(stack.pop());
        }

        return postfix;

    }

    public static boolean isSymbol(char sym) {

        if (sym == '+' || sym == '-' || sym == '*' || sym == '/' || sym == '^') {
            return true;
        } else {
            return false;
        }

    }

    public static boolean isNumber(char num) {

        String sNum = Character.toString(num);

        if (sNum.matches("[0-9]")) {
            return true;
        } else {
            return false;
        }
    }

}
