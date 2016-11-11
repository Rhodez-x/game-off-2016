import processing.core.*;

/**
 *
 * @author rhodez-x
 */
public class CodeOfCards extends PApplet {
	Game game;
	public void settings() {
		size(800, 600);
	}
	
	public void setup() {
            game = new Game();
            game.runGame(); // Starts the program.
	}
	
	public void draw() {
		Rendering.drawCard(getGraphics(), "self.DrawCard", new PVector(200, 200));
		Rendering.drawCard(getGraphics(), "other.DrawCard", new PVector(500, 200));
	}
	
    public static void main(String[] args){
    	PApplet.main("CodeOfCards");
    }
}
