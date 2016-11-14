package codeofcards.cards;

import codeofcards.Player;

public abstract class Card {
    public String text;
    public int frequency;
    public abstract int execute(int cyclesLeft, Player self, Player player); // Takes cycelsLeft and return new cycle left.
    public abstract Card clone();
}
