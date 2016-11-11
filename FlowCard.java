import java.util.ArrayList;

public class FlowCard extends Card{
    int cycles; // How many times the fuction can be called.
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

    public int execute(Player player, Player other) {
        return execute(this.cycles, player, other);
    }
    
}
