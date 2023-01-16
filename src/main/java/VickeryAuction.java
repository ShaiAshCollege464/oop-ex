public class VickeryAuction {
    private String product;
    private int realPrice;
    private Participant[] participants = new Participant[100];
    private int current;

    public VickeryAuction (String product, int realPrice) {
        this.product = product;
        this.realPrice = realPrice;
    }

    //TODO:
    public boolean addParticipant (Participant participant) {
    }

    //TODO:
    public Winner getWinner () {
    }
}
