package Project2;

public class Operator {

    private String symbol;
    private int value;
    private boolean finished;
    private Operator parent;
    private Operator left;
    private Operator right;

    public Operator(String symbol) {

        this.symbol = symbol;
        this.finished = false;

        if (symbol.equals("^")) {
            this.value = 3;
        } else if (symbol.equals("*") || symbol.equals("/")) {
            this.value = 2;
        } else if (symbol.equals("+") || symbol.equals("-")) {
            this.value = 1;
        } else {
            this.value = 0;
        }

    }

    public void insertOperator(Operator next) {

        if (left != null && this.left.getValue() == this.value) {
            left.insertOperator(next);
        } else if (next.value <= this.value) {
            if (left == null) {
                left = next;
                left.parent = this;
            } else {
                left.insertOperator(next);
            }
        } else {
            if (right == null) {
                right = next;
                right.parent = this;
            } else {
                right.insertOperator(next);
            }
        }

    }

    public void insertNumber(Operator num) {

        if (this.left != null && this.right == null) {
            if (this.left.finished == true) {
                right = num;
            }
        } else if (this.right != null && this.left == null) {
            if (this.right.finished == true) {
                left = num;
            }
        } else if (this.left != null && this.right != null) {
            if (this.left.finished == true && this.right.finished == true) {
                this.parent.insertNumber(num);
            }
        }

        if (!(right instanceof IntNode) && right != null && right.finished == false) {
            right.insertNumber(num);
        } else if (!(left instanceof IntNode) && left != null && left.finished == false){
            left.insertNumber(num);
        } else if (right == null) {
            right = num;
            right.finished = true;
        } else if (left == null) {
            left = num;
            left.finished = true;
        } else {
            this.finished = true;
            this.parent.insertNumber(num);
        }

    }

    public void printInOrder() {

        if (left != null) {
            left.printInOrder();
        }

        System.out.println(symbol);

        if (right != null) {
            right.printInOrder();
        }

    }

    public void setParent(Operator parent) {
        this.parent = parent;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getValue() {
        return value;
    }

    public Operator getParent() {
        return parent;
    }

    public void setLeft(Operator left) {
        this.left = left;
    }

    public void setRight(Operator right) {
        this.right = right;
    }

    public Operator getLeft() {
        return left;
    }

    public Operator getRight() {
        return right;
    }

}
