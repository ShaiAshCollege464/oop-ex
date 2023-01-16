public class Winner {
    private String name;
    private int lastPrice;

    public Winner (String name, int lastPrice) {
        this.name = name;
        this.lastPrice = lastPrice;
    }

    public String toString () {
        return this.name + "_" + this.lastPrice;
    }

}
