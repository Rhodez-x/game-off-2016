package codeofcards.cards;

import codeofcards.Game;
import codeofcards.Player;
import codeofcards.commands.*;

public class StatementCard extends Card {
    public enum StatementType {
        SelfDrawCard, OtherDrawCard, SelfIncrementLife, SelfDecrementLife, OtherIncrementLife, OtherDecrementLife,
        SelfDiscardCard, OtherDiscardCard, SelfExecuteFunction, OtherExecuteFunction, CyclesIncrement, CyclesDecrement}

    public StatementType statementType;

    public StatementCard(String text, int frequency, StatementType statementType) {
        this.statementType = statementType;
        this.frequency = frequency;
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    @Override
    public int execute(int cyclesLeft, Player player, Player other) {
        Command command = null;

        switch (statementType) {
            case SelfDrawCard:
                command = new DrawCommand(player.id);
                break;
            case OtherDrawCard:
                command = new DrawCommand(other.id);
                break;
            case SelfIncrementLife:
                command = new LifeAddCommand(player.id, 1);
                break;
            case SelfDecrementLife:
                command = new LifeAddCommand(player.id, -1);
                break;
            case OtherIncrementLife:
                command = new LifeAddCommand(other.id, 1);
                break;
            case OtherDecrementLife:
                command = new LifeAddCommand(other.id, -1);
                break;
            case SelfDiscardCard:
                command = new ToDiscardCommand(player.id, 1);
                break;
            case OtherDiscardCard:
                command = new ToDiscardCommand(other.id, 1);
                break;
            case SelfExecuteFunction:
                //player.executeFunction(other);
                break;
            case CyclesIncrement:
                cyclesLeft += 2;
                break;
            case CyclesDecrement:
                cyclesLeft -= 1;
                break;
            default:
                break;
        }

        if (command != null) {
            Game.instance.execute(command);
        }

        return cyclesLeft - 1;
    }

    @Override
    public Card clone() {
        return new StatementCard(text, frequency, statementType);
    }
}
