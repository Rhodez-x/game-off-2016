package codeofcards.graphics;

import codeofcards.graphics.CardViewNode.ClickedNodeResult;
import processing.core.PGraphics;

public class CardViewTree {
	public CardViewNode root;
	public float x, y;
	
	public CardViewTree(CardViewNode root, float x, float y) {
		this.root = root;
		this.x = x;
		this.y = y;
		
		this.root.addToTree(this);
		this.root.updateBounds();
	}

	public void draw(PGraphics g, CardViewNode clicked) {		
		root.draw(g, clicked);
	}
	
	public ClickedNodeResult getNodeAtPoint(float mouseX, float mouseY) {
		return root.getClickedNode(mouseX - x, mouseY - y);
	}
	
	
//	public xxxx checkForPotentialDropSpot(float cardCornerX, float cardCornerY) {
//		DropSpotResult dropSpot = root.checkForPotentialDropSpot(cardCornerX - x, cardCornerY - y);
//		float nx = dropSpot.sibling.relativeBounds.x + x;
//		float ny = dropSpot.sibling.relativeBounds.y + y;
//		
//	}
//	
//	public boolean insertNodeAt(DropSpotResult dropSpot) {
//		boolean success = false;
//		if (dropSpot != null) {
//			if (dropSpot.parent != null) {
//				if (dropSpot.parent.children.size() >= dropSpot.index) {
//					success = true;
//				}
//			}
//		}
//		return success;
//	}
}
