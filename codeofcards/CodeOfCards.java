package codeofcards;

import java.util.ArrayList;

import processing.core.*;

/**
 *
 * @author rhodez-x
 */
public class CodeOfCards extends PApplet {
    float cardHeight = 70;
    float cardWidth = 300;
    float padding = 10;
    float columnWidth = cardWidth + 2*padding;

    ArrayList<String> fff = new ArrayList<String>();

	Game game;
	public void settings() {
		size(1000, 600);
	}

	public void setup() {
        fff.add("Self.DrawCard()");
        fff.add("Other.DrawCard()");
        fff.add("Other.Life++");
        fff.add("Self.Life--");
        fff.add("Self.DiscardCard()");
        fff.add("Other.DiscardCard()");
	}

	public void draw() {
		// for (CodeOfCards.FunctionCard funcCard : game.board.functionCards) {

            float functionHeight = fff.size() * cardHeight + 2 * padding;

            pushMatrix();
            fill(0xff0000);
            translate(padding, padding);
            rect(0, 0, columnWidth, functionHeight);
            translate(padding, padding);
            for (int cardIndex = 0; cardIndex < fff.size(); ++cardIndex) {
                String cardText = fff.get(cardIndex);
                fill(0xffccaa);
                rect(0, 0, cardWidth, cardHeight);
                fill(0);
                textAlign(CENTER, CENTER);
                text(cardText, cardWidth/2, cardHeight/2);
                translate(0, cardHeight);
            }
            popMatrix();
        // }
	}

    public static void main(String[] args){
    	PApplet.main("codeofcards.CodeOfCards");
    }
}
