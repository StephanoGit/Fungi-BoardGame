package board;

import java.util.ArrayList;

import cards.Card;

public class CardList {
    
    private ArrayList<Card> cList = new ArrayList<>();

    public CardList() {}

    public void add(Card card){
        this.cList.add(0, card);
    }

    public int size(){
        return this.cList.size();
    }
    
    public Card getElementAt(int index){
        return this.cList.get(index);
    }

    public Card removeCardAt(int index){
        return this.cList.remove(index);
    }
}
