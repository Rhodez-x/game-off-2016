package codeofcards.cards;

import codeofcards.Game;
import codeofcards.Player;
import codeofcards.commands.Command;
import codeofcards.commands.CyclesAddCommand;
import codeofcards.commands.DrawCommand;
import codeofcards.commands.LifeAddCommand;

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
                //player.discardCard();
                break;
            case OtherDiscardCard:
                //other.discardCard();
                break;
            case SelfExecuteFunction:
                //player.executeFunction(other);
                break;
            case CyclesIncrement:
                //command = new CyclesAddCommand(this.id, 1);
                break;
            case CyclesDecrement:
                //command = new CyclesAddCommand(this.id, -1);
                break;
            default:
                break;
        }

        Game.instance.execute(command);
        //game.execute(new CyclesAddCommand(this.id, 1));

        return cyclesLeft;
    }
}
