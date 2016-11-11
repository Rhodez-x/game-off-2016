public class RepeatCard extends FlowCard{
    public int times;

    @Override
    public int execute(int cycles) {
        for(int i = 0; i < times; i++) {
            cycles = super.execute(cycles);

            if (cycles < 1) {
                break;
            }
        }

        return cycles;
    }
    
}
