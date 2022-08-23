package board;

import cards.Card;

public interface Displayable {
    public void add(Card c);

    public int size();

    public Card getElementAt(int index);

    public Card removeElement(int index);
}
