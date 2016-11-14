package codeofcards.graphics;

import codeofcards.cards.*;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

public class CardGraphic extends GraphicsObject {

	public static int fillColor = 0xccbb99;
	public static int strokeColor = 0xc6b593;
	public static float cardWidth = 300;
	public static float cardHeight = 70;

	public Card card;

	public CardGraphic(Card card, PVector pos) {
		this.card = card;
		this.pos = pos;
		this.offset = new PVector(0, 0);
		this.dim = new PVector(cardWidth, cardHeight);
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