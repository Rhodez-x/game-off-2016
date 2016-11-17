package codeofcards.graphics;

import codeofcards.cards.*;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

public class FunctionCardGraphic extends GraphicsObject {

	public static int fillColor = 0xffaaccdd;
	public static int strokeColor = 0xff2b4d5e;
	public static float padding = 16;

	public FunctionCard card;

	public FunctionCardGraphic(FunctionCard card, PVector pos) {
		this.card = card;
		this.pos = pos;
		this.offset = new PVector(0, 0);
		this.dim = new PVector(300, CardViewNode.cardHeight * card.cards.size() + 2*padding);
	}

	@Override
	public void draw(PGraphics g) {
		g.pushMatrix();
		g.fill(fillColor);
		g.stroke(strokeColor);
		g.translate(left(), top());
		g.rect(0, 0, dim.x, dim.y);
		float textX = dim.x*0.5f;
		float textY = dim.y*0.5f;
		g.textAlign(PConstants.CENTER, PConstants.CENTER);
		g.text(card.text, textX, textY);
		g.popMatrix();
	}
}