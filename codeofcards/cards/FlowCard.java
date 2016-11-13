package codeofcards.cards;

import codeofcards.Player;

import java.util.ArrayList;

public class FlowCard extends Card{

    public ArrayList<Card> cards = new ArrayList<>();

    @Override
    public int execute(int cycles, Player player, Player other) {
        for (Card card : this.cards) {
            if (cycles < 1) {
                break;
            }

            cycles = card.execute(cycles, player, other);
        }

        return cycles;
    }

    public void addCard(Card card, int index) {
        cards.add(index, card);
    }
    
    @Override
    public String toString() {
        return text + " -> " + cards.toString();
    } 
}
