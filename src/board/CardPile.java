package board;
import cards.Card;
import java.util.Stack;
import java.util.Collections;

public class CardPile {
    
    private Stack<Card> cPile;

    public CardPile() {
        cPile = new Stack<Card>();
    }

    public void addCard(Card card){
        this.cPile.add(card);
    }

    public Card drawCard(){
        return this.cPile.pop();
    }

    public void shufflePile(){
        Collections.shuffle(this.cPile);
    }

    public int pileSize(){
        return this.cPile.size();
    }

    public boolean isEmpty(){
        return this.cPile.empty();
    }
    
}
