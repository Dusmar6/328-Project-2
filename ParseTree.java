public class ParseTree {

    private Operator root;// the base value
    private ParseTree left;// points to left subtree
    private ParseTree right;// points to right subtree

    /**
     * construct a tree with a specified root value
     * @param root an operator or integer
     */
    public ParseTree(Operator root) {

        this.root = root;
        if (root instanceof IntNode) {
            left = null;
            right = null;
        }

    }

    /**
     * construct a tree with a specified root value. Also include left or right subtrees for the root to point to
     * @param root an operator or integer
     * @param left a subtree
     * @param right a subtree
     */
    public ParseTree(Operator root, ParseTree left, ParseTree right) {
        this.root = root;
        this.left = left;
        this.right = right;
    }

    /**
     * print out every node attached to the root with the In-Order sequence (left, root, right)
     */
    public void printInOrder() {

        if (left != null) {
            left.printInOrder();
        }

        System.out.print(root.getSymbol() + " ");

        if (right != null) {
            right.printInOrder();
        }

    }

    /**
     * recursive equation solver. Navigate to the left most operator, then navigate to the right most operator. If the
     * operator only points to integers then perform the calculation and return to the previous call.
     * @return the calculation for the parse tree expression
     */
    public int evaluate() {

        //if the root is an integer return it
        if (root instanceof IntNode) {
            return ((IntNode) root).getNumber();
        //if the root is an operator recursively call evaluate til an integer is reached
        } else {
            //if the left subtree holds any operator perform those operation next
            //perform the calculation with the resulting integers
            //if the right subtree holds any operator perform that operation next
            switch(root.getSymbol()) {
                case "+":
                    return left.evaluate() + right.evaluate();
                case "-":
                    return left.evaluate() - right.evaluate();
                case "*":
                    return left.evaluate() * right.evaluate();
                case "/":
                    return left.evaluate() / right.evaluate();
                case "^":
                    return (int) Math.pow(left.evaluate(), right.evaluate());
                default:
                    return left.evaluate() + right.evaluate();
            }

        }

    }

    /**
     * return the left subtree
     * @return left parse tree
     */
    public ParseTree getLeft() {
        if (root instanceof IntNode)
            return null;
        return left;
    }

    /**
     * return the right subtree
     * @return right parse tree
     */
    public ParseTree getRight() {
        if (root instanceof IntNode)
            return null;
        return right;
    }

    /**
     * return root operator
     * @return the root object
     */
    public Operator getRoot() {
        return root;
    }

}
