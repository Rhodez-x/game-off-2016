import java.util.ArrayList;

public class FlowCard extends Card{
    int cycles; // How many times the fuction can be called.
    ArrayList<Card> cards;

    @Override
    public int execute(int cyclesLeft) {
        for (Card card : cards) {
            if (cycles < 1) {
                break;
            }

            cycles = card.execute(cycles);
        }

        return cycles;
    }
    
}
