public class RepeatCard extends FlowCard{
    public int times;

    public RepeatCard(String text, int frequency, int times) {
        this.text = text;
        this.frequency = frequency;
        this.times = times;
    }

    @Override
    public int execute(int cycles, Player player, Player other) {
        for(int i = 0; i < times; i++) {
            cycles = super.execute(cycles, player, other);

            if (cycles < 1) {
                break;
            }
        }

        return cycles;
    }
    
}
