package codeofcards;

import processing.core.*;

public final class Rendering {
	
	public static PVector cardDim = new PVector(300, 70);
	public static PVector cardAnchor = cardDim.copy().mult(0.5f);

	public static void drawCard(PGraphics g, String text, PVector pos) {
		g.pushMatrix();
		g.stroke(100);
		g.fill(240);
		g.translate(pos.x - cardAnchor.x, pos.y - cardAnchor.x);
		g.rect(0, 0, cardDim.x, cardDim.y);
		g.fill(0);
		g.textAlign(PConstants.CENTER, PConstants.CENTER);
		g.translate(cardAnchor.x, cardAnchor.y);
		g.text(text, 0, 0);
		g.popMatrix();
	}

}
