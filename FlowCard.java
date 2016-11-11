import java.util.ArrayList;

public class FlowCard extends Card{

    ArrayList<Card> cards;

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
    
    @Override
    public String toString() {
        return this.text;
    } 
}
