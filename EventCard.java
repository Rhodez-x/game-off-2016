/**
 * Created by mads on 11/11/2016.
 */
public class EventCard extends FunctionCard {
    public Board.EventType eventType;

    EventCard(String text, int frequency, int cycles, Board.EventType eventType) {
        super(text, frequency, cycles);
        this.eventType = eventType;
    }
}
