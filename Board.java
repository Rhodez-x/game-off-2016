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
    
    void addCardToFuction(Card card, int functionIndex, int placeIndex) {
        
    }

    public void OnTurnStart(Player player, Player other) {
        FunctionCard functionCard = eventFunctions.get(EventType.OnTurnStart);

        if (functionCard != null) {
            functionCard.execute(player, player);
        }
    }

    public void OnTurnEnd(Player player, Player other) {
        FunctionCard functionCard = eventFunctions.get(EventType.OnTurnEnd);

        if (functionCard != null) {
            functionCard.execute(player, player);
        }
    }

    public void OnCardPlayed(Player player, Player other) {
        FunctionCard functionCard = eventFunctions.get(EventType.OnCardPlayed);

        if (functionCard != null) {
            functionCard.execute(player, player);
        }
    }

    public void OnCardDraw(Player player, Player other) {
        FunctionCard functionCard = eventFunctions.get(EventType.OnCardDraw);

        if (functionCard != null) {
            functionCard.execute(player, player);
        }
    }
}
