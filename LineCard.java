public class LineCard extends Card{
    public enum LineType {
        SelfDrawCard, SelfIncrementLife, SelfDecrementLife, OtherIncrementLife, OtherDecrementLife,
        SelfDiscardCard, OtherDiscardCard}
    
    public LineType lineType;
    
    @Override
    public int execute(int cyclesLeft, Player player, Player other) {
        switch (lineType) {
            case SelfDrawCard:
                player.drawCard();
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
