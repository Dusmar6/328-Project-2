// Greyson Cabrera 014121118
// Dustin Martin 015180085

/**
 * Base object for any mathematical expression or number
 */
public class Operator {

    private String symbol;//string representation
    private int value;//precedence value

    public Operator(String symbol) {

        this.symbol = symbol;

        //highest precedence
        if (symbol.equals("^")) {
            this.value = 3;
        } else if (symbol.equals("*") || symbol.equals("/")) {
            this.value = 2;
        } else if (symbol.equals("+") || symbol.equals("-")) {
            this.value = 1;
        //lowest precedence
        } else {
            this.value = 0;
        }

    }

    public String getSymbol() {
        return symbol;
    }

    public int getValue() {
        return value;
    }

}
