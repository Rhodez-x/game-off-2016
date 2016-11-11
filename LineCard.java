public class LineCard extends Card{
    public enum LineType {
        SelfDrawCard, OtherDrawCard, SelfIncrementLife, SelfDecrementLife, OtherIncrementLife, OtherDecrementLife,
        SelfDiscardCard, OtherDiscardCard}
    
    public LineType lineType;
    
    LineCard(String text, int frequency, LineType lineType) {
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
        switch (lineType) {
            case SelfDrawCard:
                player.drawCard();
                break;
            case OtherDrawCard:
                other.drawCard();
                break;
            case SelfIncrementLife:
                player.addLife(1);
                break;
            case SelfDecrementLife:
                player.addLife(-1);
                break;
            case OtherIncrementLife:
                other.addLife(1);
                break;
            case OtherDecrementLife:
                other.addLife(-1);
                break;
            case SelfDiscardCard:
                player.discardCard();
                break;
            case OtherDiscardCard:
                other.discardCard();
                break;
            default:
                break;
        }

        return cyclesLeft - 1;
    }
}
