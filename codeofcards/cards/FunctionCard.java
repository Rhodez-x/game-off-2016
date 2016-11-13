package codeofcards.cards;

import codeofcards.Player;

public class FunctionCard extends FlowCard{
    int cycles; // How many times the function can be called.

    public FunctionCard(String text, int frequency, int cycles) {
        this.text = text;
        this.frequency = frequency;
        this.cycles = cycles;
    }

    public int execute(Player player, Player other) {
        return super.execute(this.cycles, player, other);
    }
    
    @Override
    public String toString() {
        return this.text + " " + this.cycles + " -> " + cards.toString();
    }

    public int addCycles(int amount) {
        cycles += amount;

        return cycles;
    }

    @Override
    public Card clone() {
        return new FunctionCard(text, frequency, cycles);
    }
}
