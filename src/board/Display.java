package board;
import java.util.ArrayList;
import cards.Card;


public class Display implements Displayable{

    private ArrayList<Card> displayList;

    public Display(){
        displayList = new ArrayList<Card>();
    }

    @Override
    public void add(Card c) {
        this.displayList.add(c);
    }

    @Override
    public int size() {
        return this.displayList.size();
    }

    @Override
    public Card getElementAt(int index) {
        return this.displayList.get(index);
    }

    @Override
    public Card removeElement(int index) {
        return this.displayList.remove(index);
    }
    
}
