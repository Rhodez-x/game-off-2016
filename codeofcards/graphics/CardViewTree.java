package codeofcards.graphics;

import processing.core.PGraphics;
import processing.core.PVector;

public class CardViewTree {
	public CardViewNode root;
	public float x, y;
	
	public CardViewTree(CardViewNode root, float x, float y) {
		this.root = root;
		this.x = x;
		this.y = y;

		this.root.calculateBounds();
	}

	public void draw(PGraphics g, CardViewNode clicked) {		
		root.draw(g, x, y, clicked);
	}
	
	public CardViewNode getClickedNode(float mouseX, float mouseY) {
		return root.getClickedNode(mouseX - x, mouseY - y);
	}
}
