public class Participant {
    private String username;
    private String id;
    private int price;
    private static String[] BANNED = {
            "Noble Volkman", "Edwin Crist", "Gregorio Kuhlman", "Roderick Wunsch",
            "Wilton Brakus", "Renate Pfeffer", "Joanne Hilpert", "Gregory Barrows",
            "Kristie Runolfsdottir", "Marguerita Pfannerstill", "Tamatha Glover"
    };

    public Participant(String name, String id, int price) {
        this.username = name;
        this.id = id;
        this.price = price;
    }

    //TODO:
    public boolean eligibleToBid(int realPrice) {
    }


    public int getPrice() {
        return price;
    }

    public String getUsername() {
        return username;
    }
}
