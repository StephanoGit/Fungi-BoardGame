package cards;

public abstract class Mushroom extends EdibleItem {
    
    protected int sticksPerMushroom;

    public Mushroom(CardType type, String cardName) {
        super(type, cardName);
    }

    public int getSticksPerMushroom(){
        return this.sticksPerMushroom;
    }
}
