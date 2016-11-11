public abstract class Card {
    public String text;
    public float frequency;
    public abstract int execute(int cyclesLeft); // Takes cycelsLeft and return new cycle left. 
    
}
