import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    public enum EventType {OnTurnStart, OnTurnEnd, OnCardPlayed, OnCardDraw}

    ArrayList<FunctionCard> functionCards;
    HashMap<EventType, FunctionCard> eventFunctions = new HashMap<>();
    
    Board() {
        for (EventType eventType : EventType.values()) {
            eventFunctions.put(eventType, null);
        }
    }
    
    public void addCardToFuction(Card card, int functionIndex, int placeIndex) {
        
    }

    public void executeFunction(int functionIndex, Player target, Player other) {
        int cycles = functionCards.get(functionIndex).execute(target, other);

        if (cycles < 1) {
            functionCards.remove(functionIndex);
        }
    }

    private void executeEvent(EventType eventType, Player target, Player other) {
        FunctionCard functionCard = eventFunctions.get(eventType);

        if (functionCard != null) {
            int cycles = functionCard.execute(target, other);

            if (cycles < 1) {
                eventFunctions.remove(eventType);
            }
        }
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
