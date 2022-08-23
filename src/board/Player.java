package board;

import java.lang.ProcessBuilder.Redirect.Type;
import java.util.ArrayList;

import cards.Butter;
import cards.Card;
import cards.CardType;
import cards.Cider;
import cards.EdibleItem;
import cards.Mushroom;
import cards.Pan;
import cards.Stick;

public class Player {
    private Hand h;
    private Display d;
    private int score;
    private int handlimit;
    private int sticks;

    public Player(){
        this.h = new Hand();
        this.d = new Display();
        addCardtoDisplay(new Pan());
        this.score  = 0;
        this.handlimit = 8;
        this.sticks = 0;
    }

    public int getScore(){
        return this.score;
    }

    public int getHandLimit(){
        
        return this.handlimit;
    }

    public int getStickNumber(){
        return this.sticks;
    }

    public void addSticks(int amount){
        this.sticks = this.sticks + amount;

        for (int i = 0; i < amount; i++){
            addCardtoDisplay(new Stick());
        }
    }

    public void removeSticks(int amount){
        this.sticks = this.sticks - amount;
        for (int i = 0; i < d.size() && amount > 0; i++){
            if (this.d.getElementAt(i).getType() == CardType.STICK){
                this.d.removeElement(i);
                amount = amount - 1;
                i--;
            }
        }
    }

    public Hand getHand(){
        return this.h;
    }

    public Display getDisplay(){
        return this.d;
    }

    public void addCardtoHand(Card c){
        if (c.getType() == CardType.BASKET){
            this.handlimit += 2;
            addCardtoDisplay(c);
            return;
        }
        this.h.add(c);
    }

    public void addCardtoDisplay(Card c){
        this.d.add(c);
    }

    public boolean takeCardFromTheForest(int visualIndex){
        int index = Board.getForest().size() - visualIndex;
        int cost = Math.max(0, visualIndex - 2);

        //check for valid input
        if (index < 0 || index > Board.getForest().size()){
            return false;
        }

        //check for handlimit if not basket
        if (h.size() >= this.handlimit && Board.getForest().getElementAt(index).getType() != CardType.BASKET){
            return false;
        }

        //check if you have sticks
        if (cost > this.sticks){
            return false;
        }

        addCardtoHand(Board.getForest().getElementAt(index));
        Board.getForest().removeCardAt(index);
        removeSticks(cost);
        return true;
    }

    public boolean takeFromDecay(){
        int noBaskets = 0;

        if(Board.getDecayPile().size() == 0){
            return false;
        }

        //count the number of baskets
        for(int i = 0; i < Board.getDecayPile().size(); i++){
            if (Board.getDecayPile().get(i).getType() == CardType.BASKET ){
                noBaskets += 1;
            }
        }

        //check if you have enough space
        if(this.handlimit + noBaskets * 2 < this.h.size() + Board.getDecayPile().size() - noBaskets){
            return false;
        }

        //add cards to hand
        for(int i = 0; i < Board.getDecayPile().size(); i++){
            addCardtoHand(Board.getDecayPile().get(i));
        }

        //clear decay pile
        Board.getDecayPile().clear();
        return true;
    }

    public boolean cookMushrooms(ArrayList<Card> cards){
        boolean panInH  = false;
        int panInD  = -1;
        int fValue = 0;
        int noCards = 0;

        int noMushroomsN = 0;
        int noMushroomsD = 0;
        int noCider = 0;
        int noButter = 0;

        String mushroomName = "";

        // check display for pan
        for (int i = 0; i < this.d.size(); i++){
            if(this.d.getElementAt(i).getType() == CardType.PAN){
                panInD = i;
            }
        }

        //check the card array for pan
        for (int i = 0; i < cards.size(); i++){
            switch(cards.get(i).getType()){
            case DAYMUSHROOM:
                mushroomName = cards.get(i).getName();
                fValue += ((EdibleItem) cards.get(i)).getFlavourPoints();
                noCards += 1;
                noMushroomsD += 1;
                break;
            case NIGHTMUSHROOM:
                mushroomName = cards.get(i).getName();
                fValue += ((EdibleItem) cards.get(i)).getFlavourPoints() * 2;
                noCards += 2;
                noMushroomsN += 1;
                break;
            case CIDER:
                fValue += ((EdibleItem) cards.get(i)).getFlavourPoints();
                noCards -= 5;
                noCider += 1;
                break;
            case BUTTER:
                fValue += ((EdibleItem) cards.get(i)).getFlavourPoints();
                noCards -= 4;
                noButter += 1;
                break;
            case PAN:
                panInH = true;
                break;
            default:
                return false;
            }
        }

        for (int i = 0; i < cards.size(); i++){
            if (cards.get(i).getType() != CardType.DAYMUSHROOM &&
                cards.get(i).getType() != CardType.NIGHTMUSHROOM){
                continue;
            }
            
            if (!cards.get(i).getName().equals(mushroomName)){
                return false;
            }
        }

        if(panInD < 0 && panInH == false){
            return false;
        }

        if (noCards < 0 || noMushroomsD + noMushroomsN * 2 < 3){
            return false;
        }

        if(panInH == false){
            d.removeElement(panInD);
        }

        for(int i = 0; i < h.size(); i++){
            if (h.getElementAt(i).getType() == CardType.NIGHTMUSHROOM &&
                h.getElementAt(i).getName().equals(mushroomName) && noMushroomsD > 0){
                h.removeElement(i);
                noMushroomsD--;
            } else if (h.getElementAt(i).getType() == CardType.DAYMUSHROOM &&
                h.getElementAt(i).getName().equals(mushroomName) && noMushroomsN > 0){
                h.removeElement(i);
                noMushroomsN--;
            } else if (h.getElementAt(i).getType() == CardType.CIDER && noCider > 0){
                h.removeElement(i);
                noCider--;
            } else if (h.getElementAt(i).getType() == CardType.BUTTER && noButter > 0){
                h.removeElement(i);
                noButter--;
            } else if (h.getElementAt(i).getType() == CardType.PAN && panInH == true){
                panInH = false;
                h.removeElement(i);
            }
        }

        this.score = this.score + fValue;
        return true;
    }

    public boolean sellMushrooms(String s, int amount){
        s = s.replaceAll("[^a-zA-Z]", "");
        s = s.toLowerCase();

        if (amount < 2){
            return false;
        }

        int noMushroomsN = 0;
        int noMushroomsD = 0;
        int toRmvN = 0;
        int toRmvD = 0;

        for (int i = 0; i < h.size(); i++){
            if(h.getElementAt(i).getName().equals(s) && h.getElementAt(i).getType() == CardType.DAYMUSHROOM){
                noMushroomsD += 1;
            }
            if(h.getElementAt(i).getName().equals(s) && h.getElementAt(i).getType() == CardType.NIGHTMUSHROOM){
                noMushroomsN += 1;
            }
        }

        if (noMushroomsD + noMushroomsN * 2 < amount ||
            noMushroomsD == 0 && amount % 2 == 1){
                return false;
        }
        
        toRmvN = Math.min(amount / 2, noMushroomsN);
        toRmvD = amount - toRmvN * 2;

        for (int i = 0; i < h.size(); i++){
            if(h.getElementAt(i).getType() == CardType.DAYMUSHROOM &&
                h.getElementAt(i).getName().equals(s) && toRmvD > 0){
                addSticks(((Mushroom) h.getElementAt(i)).getSticksPerMushroom());

                h.removeElement(i);
                toRmvD--;
                i--;
            } else if(h.getElementAt(i).getType() == CardType.NIGHTMUSHROOM &&
                h.getElementAt(i).getName().equals(s) && toRmvN > 0){
                addSticks(((Mushroom) h.getElementAt(i)).getSticksPerMushroom() * 2);
                
                h.removeElement(i);
                toRmvN--;
                i--;
            }
        }
        return true;
    }

    public boolean putPanDown(){
        for (int i = 0; i < h.size(); i++){
            if (h.getElementAt(i).getType() == CardType.PAN){
                d.add(h.getElementAt(i));
                h.removeElement(i);
                return true;
            }
        }
        return false;
    }
}