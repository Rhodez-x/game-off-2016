package codeofcards;

import codeofcards.cards.Card;
import codeofcards.cards.EventCard;
import codeofcards.cards.FunctionCard;

import java.util.ArrayList;

public class Board {
    public enum EventType {OnTurnStart, OnTurnEnd, OnCardPlayed, OnCardDraw}

    CardFactory cardFactory;

    ArrayList<FunctionCard> functionCards = new ArrayList<>();

    ArrayList<Card> cardsInPlay = new ArrayList<>();
    //HashMap<EventType, CodeOfCards.FunctionCard> eventFunctions = new HashMap<>();

    Board() {
        this.cardFactory = new CardFactory();
    }
    
    Board(CardFactory cardFactory) {
        this.cardFactory = cardFactory;
        /*for (EventType eventType : EventType.values()) {
            eventFunctions.put(eventType, null);
        }*/
    }
    
    public void addFunctionToBoard(FunctionCard card) {
        functionCards.add(card);
    }
    
    public void addCardToFuction(Card card, int functionIndex, int index) {
        functionCards.get(functionIndex).addCard(card, index);
    }

    public void executeFunction(int functionIndex, Player target, Player other) {
        int cycles = functionCards.get(functionIndex).execute(target, other);

        if (cycles < 1) {
            functionCards.remove(functionIndex);
        }
    }

    private void executeEvent(EventType eventType, Player target, Player other) {
        int eventIndex = -1;

        for (FunctionCard card : functionCards) {
            if (card instanceof EventCard && ((EventCard) card).eventType == eventType) {
                eventIndex = functionCards.indexOf(card);
                break;
            }
        }

        if (eventIndex > 0) {
            executeFunction(eventIndex, target, other);
        }
    }

    public void addCard(Card card) {
        cardsInPlay.add(card);
    }

    public void removeCard(Card card) {
        cardsInPlay.remove(card);
    }

    public void OnTurnStart(Player player, Player other) {
        executeEvent(EventType.OnTurnStart, player, other);
    }

    public void OnTurnEnd(Player player, Player other) {
        executeEvent(EventType.OnTurnEnd, player, other);
    }

    public void OnCardPlayed(Player player, Player other) {
        executeEvent(EventType.OnCardPlayed, player, other);
    }

    public void OnCardDraw(Player player, Player other) {
        executeEvent(EventType.OnCardDraw, player, other);
    }
}
