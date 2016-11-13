package codeofcards.cards;

import codeofcards.Game;
import codeofcards.Player;
import codeofcards.commands.*;

public class LineCard extends Card {
    public enum LineType {
        SelfDrawCard, OtherDrawCard, SelfIncrementLife, SelfDecrementLife, OtherIncrementLife, OtherDecrementLife,
        SelfDiscardCard, OtherDiscardCard, SelfExecuteFunction, OtherExecuteFunction, CyclesIncrement, CyclesDecrement}
    
    public LineType lineType;
    
    public LineCard(String text, int frequency, LineType lineType) {
        this.lineType = lineType;
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

        switch (lineType) {
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

        Game.instance.execute(command);

        return cyclesLeft;
    }

    @Override
    public Card clone() {
        return new LineCard(text, frequency, lineType);
    }
}
