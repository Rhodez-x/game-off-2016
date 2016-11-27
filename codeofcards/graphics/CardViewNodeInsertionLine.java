package codeofcards.graphics;

import codeofcards.cards.Card;
import processing.core.PGraphics;

public class CardViewNodeInsertionLine extends CardViewNode {

	public CardViewNodeInsertionLine() {
		super(null);
		this.isLeaf = true;
	}
	
	@Override
	public float updateBounds(float xAt, float yAt) {
		relativeBounds = new BoundsRect(xAt, yAt - 2, cardWidth, 4);
		return 0;
	}
	
	@Override
	public void draw(PGraphics g) { draw(g, null); }
	@Override
	public void draw(PGraphics g, CardViewNode clickedNode) {
		float drawX = tree.x + relativeBounds.x;
		float drawY = tree.y + relativeBounds.y;
		float drawWidth = relativeBounds.w;
		float drawHeight = relativeBounds.h;
		
		g.noStroke();
		g.fill(0xffffff00);
		g.rect(drawX, drawY, drawWidth, drawHeight);
	}

}
