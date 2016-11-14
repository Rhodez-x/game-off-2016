package codeofcards.cards;

import codeofcards.Board;

/**
 * Created by mads on 11/11/2016.
 */
public class EventCard extends FunctionCard {
    public Board.EventType eventType;

    public EventCard(String text, int frequency, int cycles, Board.EventType eventType) {
        super(text, frequency, cycles);
        this.eventType = eventType;
    }

    @Override
    public Card clone() {
        return new EventCard(text, frequency, cycles, eventType);
    }
}
