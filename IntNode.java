package Project2;

public class IntNode extends Operator {

    private int number;

    public IntNode(int number) {
        super("" + number);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

}
