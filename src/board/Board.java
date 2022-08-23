package board;

import java.util.ArrayList;

import cards.Card;
import cards.CardType;
import cards.Chanterelle;
import cards.Cider;
import cards.HenOfWoods;
import cards.HoneyFungus;
import cards.LawyersWig;
import cards.Morel;
import cards.Pan;
import cards.Porcini;
import cards.Shiitake;
import cards.TreeEar;
import cards.Basket;
import cards.BirchBolete;
import cards.Butter;

public class Board {
    private static CardPile forestCardsPile;
    private static CardList forest;
    private static ArrayList<Card> decayPile;

    public static void initialisePiles(){
        forestCardsPile = new CardPile();
        forest = new CardList();
        decayPile = new ArrayList<>();
    }

    public static void setUpCards(){
        for (int i = 0; i < 11; i++){
            forestCardsPile.addCard(new Pan());
            if (i < 10){
                forestCardsPile.addCard(new HoneyFungus(CardType.DAYMUSHROOM));
            }
            if (i < 8){
                forestCardsPile.addCard(new TreeEar(CardType.DAYMUSHROOM));
            }
            if (i < 6){
                forestCardsPile.addCard(new LawyersWig(CardType.DAYMUSHROOM));
            }
            if (i < 5){
                forestCardsPile.addCard(new Basket());
                forestCardsPile.addCard(new Shiitake(CardType.DAYMUSHROOM));
                forestCardsPile.addCard(new HenOfWoods(CardType.DAYMUSHROOM));
            }
            if (i < 4){
                forestCardsPile.addCard(new BirchBolete(CardType.DAYMUSHROOM));
                forestCardsPile.addCard(new Porcini(CardType.DAYMUSHROOM));
                forestCardsPile.addCard(new Chanterelle(CardType.DAYMUSHROOM));
            }
            if (i < 3){
                forestCardsPile.addCard(new Cider());
                forestCardsPile.addCard(new Butter());
                forestCardsPile.addCard(new Morel(CardType.DAYMUSHROOM));
            }
        }

        forestCardsPile.addCard(new HoneyFungus(CardType.NIGHTMUSHROOM));
        forestCardsPile.addCard(new TreeEar(CardType.NIGHTMUSHROOM));
        forestCardsPile.addCard(new LawyersWig(CardType.NIGHTMUSHROOM));
        forestCardsPile.addCard(new Shiitake(CardType.NIGHTMUSHROOM));
        forestCardsPile.addCard(new HenOfWoods(CardType.NIGHTMUSHROOM));
        forestCardsPile.addCard(new BirchBolete(CardType.NIGHTMUSHROOM));
        forestCardsPile.addCard(new Porcini(CardType.NIGHTMUSHROOM));
        forestCardsPile.addCard(new Chanterelle(CardType.NIGHTMUSHROOM));
    }

    public static CardPile getForestCardsPile(){
        return forestCardsPile;
    }

    public static CardList getForest(){
        return forest;
    }

    public static ArrayList<Card> getDecayPile(){
        return decayPile;
    }

    public static void updateDecayPile(){
        if (decayPile.size() >= 4){
            decayPile.clear();
        }
        decayPile.add(forest.getElementAt(forest.size() - 1));
        forest.removeCardAt(forest.size() - 1);
    }
}