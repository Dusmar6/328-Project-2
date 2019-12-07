// Greyson Cabrera 014121118
// Dustin Martin 015180085

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String args[]) {

        //quick examples 1: "3+5*16/2^2-6" 2: "3*2+6-5/5" 3: "4+5-3*2*1" 4: "1+2*3+4"
        Scanner in = new Scanner(System.in);
        System.out.print("Enter an expression to solve: ");
        String input = sanitizeExpression(in.nextLine());

        do {

			//verifies an actual expression was entered
            while (input.equals("")) {
                System.out.print("Enter a real expression \"2+3*4^3/2\": ");
                input = sanitizeExpression(in.nextLine());
            }
			
            ArrayList<Operator> express = new ArrayList<Operator>();

            //extracts the given equation into an arraylist
            for (int i = 0; i < input.length(); i++) {

                //creates an operator object and adds it to the arraylist
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
                //creates an integer object and adds it to the arraylist
                } else if (isNumber(input.charAt(i))) {

                    int value = 0;//holds the entire number
                    int j = i;
                    //grabs all the digits connected to one number
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
            System.out.print("Print postfix expression: ");
            for (Operator x : postfixExpress) {
                System.out.print(x.getSymbol() + " ");
            }

            //convert postfix Arraylist into a parse tree
            Stack<ParseTree> tree = new Stack<ParseTree>();
            ParseTree next = null;

            //iterate through every operator in the expression
            for (Operator x : postfixExpress) {
                //push integers onto the stack
                if (x instanceof IntNode) {
                    next = new ParseTree(x);
                    tree.push(next);
                //pop off previous 2 parsetrees then add them to the newly pushed operator
                } else {
                    ParseTree prev1 = tree.pop();
                    ParseTree prev2 = tree.pop();
                    next = new ParseTree(x, prev2, prev1);
                    tree.push(next);
                }
            }//resulting stack has one parse tree object, this is the root of the parse tree

            System.out.print("\nPrint finished Parse Tree: ");
            tree.peek().printInOrder();// prints in left, root, right order (in-order)

            System.out.print("\nCalculating: ");
            System.out.println(tree.peek().evaluate());//

            System.out.println("\nEnter another expression to try again.\n/exit to finish.\n");
            input = in.nextLine();
            if (input.equals("/exit"))
                System.out.println("Goodbye");

        } while (!input.equals("/exit"));

    }

    /**
     * clears any characters other than (0-9), +, -, *, /, and ^ from the users entered expression
     * @param input user specified expression
     * @return clean expression with only valid characters
     */
    public static String sanitizeExpression(String input) {

        Pattern pattern = Pattern.compile("[^0-9\\+\\-\\*\\/\\^]");
        Matcher matcher = pattern.matcher(input);

        return matcher.replaceAll("");

    }

    /**
     * converts an in-fix expression to a post-fix expression
     * @param express an array that is in in-fix order
     * @return an array of the expression in post-fix order
     */
    public static ArrayList<Operator> infixToPostfix(ArrayList<Operator> express) {

        ArrayList<Operator> postfix = new ArrayList<Operator>();
        Stack<Operator> stack = new Stack<Operator>();

        for (int i = 0; i < express.size(); i++) {

            Operator c = express.get(i);

            //if its an integer add it to the arraylist
            if (c instanceof IntNode) {
                postfix.add(c);
            //else its an operator so add any previous operators, then add the current operator to the stack
            } else {

                while (!stack.isEmpty() && c.getValue() <= stack.peek().getValue()) {
                    postfix.add(stack.pop());
                }

                stack.push(c);

            }

        }

        //add the stack remnants to the array list
        while (!stack.isEmpty()) {
            postfix.add(stack.pop());
        }

        return postfix;

    }

    /**
     * determines if the character is an operator or not
     * @param sym the character to inspect
     * @return whether the given character is an operator or not
     */
    public static boolean isSymbol(char sym) {

        if (sym == '+' || sym == '-' || sym == '*' || sym == '/' || sym == '^') {
            return true;
        } else {
            return false;
        }

    }

    /**
     * determines if the character is an operator or not
     * @param num the character to inspect
     * @return whether the given character is an integer or not
     */
    public static boolean isNumber(char num) {

        String sNum = Character.toString(num);

        if (sNum.matches("[0-9]")) {
            return true;
        } else {
            return false;
        }
    }

}
