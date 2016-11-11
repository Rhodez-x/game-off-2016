import java.util.ArrayList;

import processing.core.*;

/**
 *
 * @author rhodez-x
 */
public class CodeOfCards extends PApplet {
    float cardHeight = 70;
    float padding = 5;

    ArrayList<String> fff = new ArrayList<String>();

	Game game;
	public void settings() {
		size(800, 600);
	}

	public void setup() {
        fff.add("Self.DrawCard()");
        fff.add("Other.DrawCard()");
        fff.add("Self.Life++");
        fff.add("Other.Life++");
        fff.add("Self.Life--");
        fff.add("Other.Life--");
        fff.add("Self.DiscardCard()");
        fff.add("Other.DiscardCard()");
	}

	public void draw() {
		// for (FunctionCard funcCard : game.board.functionCards) {

            float functionHeight = fff.size() * cardHeight + 2 * padding;

            pushMatrix();
            fill(0xaaccff);
            translate(padding, padding);
            rect(0, 0, 300, functionHeight);
            translate(padding, padding);
            fill(0xffccaa);
            for (int cardIndex = 0; cardIndex < fff.size(); ++cardIndex) {
                String cardText = fff.get(cardIndex);
                translate(0, cardHeight);
                rect(0, 0, 300-padding, cardHeight);
            }
            popMatrix();
        // }
	}

    public static void main(String[] args){
    	PApplet.main("CodeOfCards");
    }
}
