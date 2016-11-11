public class FunctionCard extends FlowCard{
    int cycles; // How many times the function can be called.

    public int execute(Player player, Player other) {
        return super.execute(this.cycles, player, other);
    }
}
