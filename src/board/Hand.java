package board;

import java.util.ArrayList;

import cards.Card;

public class Hand implements Displayable{

    private ArrayList<Card> handList;

    public Hand(){
        handList = new ArrayList<Card>();
    }

    @Override
    public void add(Card c) {
        this.handList.add(c);
        
    }

    @Override
    public int size() {
        return this.handList.size();
    }

    @Override
    public Card getElementAt(int index) {
        return this.handList.get(index);
    }

    @Override
    public Card removeElement(int index) {
        return this.handList.remove(index);
    }
    
}
