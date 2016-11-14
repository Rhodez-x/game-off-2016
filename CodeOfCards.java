import java.util.ArrayList;

import processing.core.*;

/**
 *
 * @author rhodez-x
 */
public class CodeOfCards extends PApplet {
    int red = color(255, 0, 0);
	float cardHeight = 70;
    float cardWidth = 300;
    float padding = 20;
    float columnWidth = cardWidth + 2*padding;

    ArrayList<String> fff = new ArrayList<String>();
    ArrayList<String> fff2 = new ArrayList<String>();

	Game game;
	public void settings() {
		size(1024, 600);
	}

	public void setup() {
		textSize(20);
		strokeWeight(3);
        fff.add("Self.DrawCard()");
        fff.add("Other.DrawCard()");
        fff.add("Repeat 3 times:");
        fff.add("Self.Life--");
        fff.add("Self.DiscardCard()");
        fff.add("Other.DiscardCard()");
        fff2.add("printHelloWorld()");
        fff2.add("other.giveUp()");
	}

/*
def c(h, x):
    a = (h & (255 << 24)) >> 24
    r = (h & (255 << 16)) >> 16
    g = (h & (255 << 8)) >> 8
    b = (h & (255 << 0)) >> 0
    r -= x
    g -= x
    b -= x
    return hex(a).zfill(2) + hex(r)[2:].zfill(2) + hex(g)[2:].zfill(2) + hex(b)[2:].zfill(2)
*/

	public void draw() {
		// for (FunctionCard funcCard : game.board.functionCards) {

            float functionHeight = (fff.size()+1) * cardHeight;
            int indent = 0;
            int cardsLeftInScope = 0;

            pushMatrix();
            fill(0xffaaccdd);
            stroke(0xff2b4d5e);
            translate(padding, padding);
            rect(0, 0, cardWidth, functionHeight);
            fill(0);
            text("Function:", padding, cardHeight/2);
            translate(padding, cardHeight);
            for (int cardIndex = 0; cardIndex < fff.size(); ++cardIndex) {
                String cardText = fff.get(cardIndex);
                float t;
                if (cardIndex != 2) {
                	fill(0xffddccaa);
                	stroke(0xff5e4d2b);
                    rect(0, 0, cardWidth, cardHeight);
                    t = 0;
                }
                else {
                	fill(0xffbbddbb);
                    stroke(0xff3c5e3c);
                    float h = fff.size() - cardIndex;
                    rect(0, 0, cardWidth, cardHeight*h);
                    t = padding;
                }
                fill(0);
                textAlign(LEFT, CENTER);
                text(cardText, padding, cardHeight/2);
                translate(t, cardHeight);
            }
            popMatrix();

            functionHeight = (fff2.size()+1) * cardHeight;

            pushMatrix();
            fill(0xffaaccdd);
            stroke(0xff2b4d5e);
            translate(padding*4 + cardWidth, padding);
            rect(0, 0, cardWidth, functionHeight);
            fill(0);
            text("Function:", padding, cardHeight/2);
            translate(padding, cardHeight);
            for (int cardIndex = 0; cardIndex < fff2.size(); ++cardIndex) {
                String cardText = fff2.get(cardIndex);
                fill(0xffddccaa);
                stroke(0xff5e4d2b);
                rect(0, 0, cardWidth, cardHeight);
                fill(0);
                textAlign(LEFT, CENTER);
                text(cardText, padding, cardHeight/2);
                translate(0, cardHeight);
            }
            popMatrix();
        // }
	}

    public static void main(String[] args){
    	PApplet.main("CodeOfCards");
    }
}
