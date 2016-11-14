package codeofcards;

import codeofcards.cards.Card;
import codeofcards.cards.EventCard;
import codeofcards.cards.FunctionCard;
import codeofcards.cards.StatementCard;

import java.util.ArrayList;

public class Board {
    public enum EventType {OnTurnStart, OnTurnEnd, OnCardPlayed, OnCardDraw}

    CardFactory cardFactory;

    ArrayList<FunctionCard> functionCards = new ArrayList<>();

    ArrayList<Card> cardsInPlay = new ArrayList<>();
    //HashMap<EventType, CodeOfCards.FunctionCard> eventFunctions = new HashMap<>();

    Board() {
        this.cardFactory = new CardFactory(this);
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

    public void addFunctionToBoard(FunctionCard card, int index) {
        functionCards.add(index, card);
    }

    public void addCyclesToFunction(int functionIndex, int amount) {
        functionCards.get(functionIndex).addCycles(amount);
    }

    public boolean addCardToFunction(Card card, int functionIndex, int index) {
        if (card instanceof StatementCard &&
                ((((StatementCard) card).statementType != StatementCard.StatementType.SelfExecuteFunction)
                || (((StatementCard) card).statementType != StatementCard.StatementType.OtherExecuteFunction))) {

            if (index > functionCards.get(functionIndex).cards.size()) {
                index = functionCards.get(functionIndex).cards.size();
            }
            functionCards.get(functionIndex).addCard(card, index);
            return true;
        }

        return false;
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
