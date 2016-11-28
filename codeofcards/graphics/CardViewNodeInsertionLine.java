package codeofcards.graphics;

import processing.core.PGraphics;

public class CardViewNodeInsertionLine extends CardViewNode {

	public CardViewNodeInsertionLine() {
		super(null);
		this.isLeaf = true;
	}
	
	public void replaceWith(CardViewNode node) {
		if (parent != null && parent.firstChild == this) {
			parent.firstChild = node;
		}
		if (prevSibling != null) {
			prevSibling.nextSibling = node;
		}
		if (nextSibling != null) {
			nextSibling.prevSibling = node;
		}
		
		node.parent = parent;
		node.nextSibling = nextSibling;
		node.prevSibling = prevSibling;

		node.setTree(tree);
		this.removeFromTree();
		node.tree.root.updateBounds();
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
		
		g.stroke(0);
		g.fill(0xffffff00);
		g.rect(drawX, drawY, drawWidth, drawHeight);
	}

}
