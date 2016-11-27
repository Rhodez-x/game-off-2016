package codeofcards.graphics;

import java.util.ArrayList;
import java.util.Iterator;

import codeofcards.cards.*;
import processing.core.*;

public class CardViewNode {
	public static float cardWidth = 200;
	public static float cardHeight = 24;
	public static float halfCardHeight = cardHeight*0.5f;
	public static float paddingX = 12;
	public static float paddingY = 12;
	
	public static Color functionCardFill = Color.fromInts(255, 156, 196, 246);
	public static Color statementCardFill = Color.fromInts(255, 240, 240, 240);
	public static Color controlFlowCardFill = Color.fromInts(255, 250, 200, 180);
	public static Color functionCardFillInv = functionCardFill.inv();
	public static Color statementCardFillInv = statementCardFill.inv();
	public static Color controlFlowCardFillInv = controlFlowCardFill.inv();
	
	public static Color functionCardStroke = functionCardFill.lerp(Color.black, 0.6f);
	public static Color statementCardStroke = statementCardFill.lerp(Color.black, 0.6f);
	public static Color controlFlowCardStroke = controlFlowCardFill.lerp(Color.black, 0.6f);
	
	public Card card;
//	public ArrayList<CardViewNode> children;
	public int fillColor;
	public int activeFillColor;
	public int strokeColor;
	public boolean toBeRemoved;
	public boolean isLeaf;
	public boolean dirty;
	public BoundsRect relativeBounds;
	public CardViewNode nextSibling;
	public CardViewNode prevSibling;
	public CardViewNode firstChild;
	public CardViewNode parent;
	public CardViewTree tree;

	public CardViewNode(Card card) { this(card, 0, 0, null); }
	public CardViewNode(Card card, float xAt, float yAt, CardViewNode parent) {
		this.card = card;
		this.tree = null;
		this.parent = parent;
		this.toBeRemoved = false;
		this.dirty = true;
		this.isLeaf = !(card instanceof FlowCard);

		if (!isLeaf) {

			if (card instanceof RepeatCard) {
				this.fillColor = controlFlowCardFill.toInt();
				this.activeFillColor = controlFlowCardFillInv.toInt();
				this.strokeColor = controlFlowCardStroke.toInt();

			}
			else {
				this.fillColor = functionCardFill.toInt();
				this.activeFillColor = functionCardFillInv.toInt();
				this.strokeColor = functionCardStroke.toInt();
			}
			
			Iterator<Card> i = ((FlowCard)card).cards.iterator();
			if (i.hasNext()) {
				Card c = i.next();
				CardViewNode childNode = new CardViewNode(c, xAt, yAt, this);
				this.firstChild = childNode;
				
				while (i.hasNext()) {
					c = i.next();
					childNode.nextSibling = new CardViewNode(c, xAt, yAt, this);
					childNode.nextSibling.prevSibling = childNode;
					childNode = childNode.nextSibling;
				}
			}
		}
		else {
			this.fillColor = statementCardFill.toInt();
			this.activeFillColor = statementCardFillInv.toInt();
			this.strokeColor = statementCardStroke.toInt();
		}
	}
	
	public void insertBefore(CardViewNode node) {
		if (prevSibling != null) {
			prevSibling.nextSibling = node;
		}
		
		if (parent != null && parent.firstChild == this) {
			parent.firstChild = node;
		}
		
		node.prevSibling = prevSibling;
		prevSibling = node;
		node.nextSibling = this;
		node.parent = parent;
		node.setTree(tree);
		tree.root.updateBounds();
	}
	
	public void insertAfter(CardViewNode node) {
		if (nextSibling != null) {
			nextSibling.prevSibling = node;
		}
		node.nextSibling = nextSibling;
		nextSibling = node;
		node.prevSibling = this;
		node.parent = parent;
		node.setTree(tree);
		tree.root.updateBounds();
	}
	
	public void insertChildTop(CardViewNode node) {
		assert(!this.isLeaf);
		assert(firstChild.prevSibling == null);
		
		if (firstChild != null) {
			firstChild.insertBefore(node);
		}
		else {
			firstChild = node;
			node.parent = this;
			node.setTree(tree);
			tree.root.updateBounds();
		}
	}
	
	public void insertChildBottom(CardViewNode node) {
		assert(!this.isLeaf);

		CardViewNode c = firstChild;
		if(c != null) {
			while(c.nextSibling != null) c = c.nextSibling;
			c.nextSibling = node;
			node.prevSibling = c;
		}
		else {
			firstChild = node;
		}
		
		node.parent = this;
		node.setTree(tree);
		tree.root.updateBounds();
	}
	
	public void addToTree(CardViewTree tree) {
		this.tree = tree;
		
		CardViewNode c = firstChild;
		while(c != null) {
			c.addToTree(tree);
			c = c.nextSibling;
		}
	}
	
	public void setTree(CardViewTree newTree) {
		CardViewNode c = firstChild;
		while(c != null) {
			c.setTree(newTree);
			c = c.nextSibling;
		}
		tree = newTree;
	}
	
	public class RemovedFromSpot {
		public CardViewNode parent, nextSibling, prevSibling;
		
		public void restore(CardViewNode node) {
			if (prevSibling != null) {
				prevSibling.insertAfter(node);
			}
			else if (nextSibling != null) {
				nextSibling.insertBefore(node);
			}
			else if (parent != null) {
				parent.insertChildTop(node);
			}
			else {
				System.out.println("Hello");
			}
		}
	}

	public RemovedFromSpot removeFromTree() {
		RemovedFromSpot result = new RemovedFromSpot();
		result.parent = parent;
		result.nextSibling = nextSibling;
		result.prevSibling = prevSibling;
		
		if(parent != null && parent.firstChild == this) {
			parent.firstChild = nextSibling;
		}
		
		if (nextSibling != null) {
			nextSibling.prevSibling = prevSibling;
		}
		
		if (prevSibling != null) {
			prevSibling.nextSibling = nextSibling;
		}
		
		prevSibling = null;
		nextSibling = null;
		parent = null;
		tree.root.updateBounds();
		
		setTree(null);
		
		return result;
	}
	
	public float updateBounds() { return updateBounds(0, 0); }
	public float updateBounds(float xAt, float yAt) {
		
		float dynHeight = cardHeight;

		if (isLeaf) {
			this.relativeBounds = new BoundsRect(xAt, yAt, cardWidth, dynHeight);
		}
		else {
			CardViewNode c = firstChild;
			while(c != null) {
				dynHeight += c.updateBounds(xAt + paddingX, yAt + dynHeight);
				c = c.nextSibling;
			}

			dynHeight += paddingY;
			
			dynHeight = Math.max(dynHeight, cardHeight + 2*paddingY);

			this.relativeBounds = new BoundsRect(xAt, yAt, cardWidth, dynHeight);
		}
		
		return dynHeight;
	}
	
	public class ClickedNodeResult {
		float xOffset;
		float yOffset;
		CardViewNode clickedNode;
		public ClickedNodeResult(float xOffset, float yOffset, CardViewNode clickedNode) {
			this.xOffset = xOffset;
			this.yOffset = yOffset;
			this.clickedNode = clickedNode;
		}
	}

	public ClickedNodeResult getClickedNode(float clickX, float clickY) {
		
		ClickedNodeResult result = null;

		if (!isLeaf) {
			CardViewNode c = firstChild;
			while(c != null) {
				result = c.getClickedNode(clickX, clickY);
				if (result != null) {
					return result;
				}
				
				c = c.nextSibling;
			}
		}
		
		if (relativeBounds.pointInside(clickX, clickY)) {
			float[] clickOffset = relativeBounds.getPointOffset(clickX, clickY);
			result = new ClickedNodeResult(clickOffset[0], clickOffset[1], this);
		}
	
		return result;
	}
	
//	public class DropSpotResult {
//		public CardViewNode sibling;
//		public boolean after;
//		
//		public DropSpotResult(CardViewNode sibling, boolean after) {
//			this.sibling = sibling;
//			this.after = after;
//		}
//	}

//	public DropSpotResult checkForPotentialDropSpot(float checkX, float checkY) {
//		DropSpotResult result = null;
//
//		if (!isLeaf) {
//			CardViewNode c = firstChild;
//			while(c != null) {
//				if (c.toBeRemoved) {
//					continue;
//				}
//				result = c.checkForPotentialDropSpot(checkX, checkY);
//				if (result != null) {
//					return result;
//				}
//				
//				c = c.nextSibling;
//			}
//		}
//		
//		if (relativeBounds.pointInside(checkX, checkY)) {
//			float yPct = (checkY - relativeBounds.y) / relativeBounds.h;
//			
//			if (yPct < 0.15f) {
//				result = new DropSpotResult(this, false);
//			}
//			else if(yPct >= 0.85f) {
//				result = new DropSpotResult(this, true);
//			}
//		}
//		
//		return result;
//	}

	public void draw(PGraphics g) { draw(g, null); }
	public void draw(PGraphics g, CardViewNode clicked) {
		boolean selected = clicked != null && this.equals(clicked);
		
		g.stroke(strokeColor);
		
		g.textAlign(PConstants.LEFT, PConstants.CENTER);
		g.stroke(0x60000000);
		g.strokeWeight(1);
		
		int fillColor = (selected) ? this.activeFillColor : this.fillColor;
		int textColor = (selected) ? 255 : 0;

		float drawX = tree.x + relativeBounds.x;
		float drawY = tree.y + relativeBounds.y;
		float drawWidth = relativeBounds.w;
		float drawHeight = relativeBounds.h;

		g.fill(fillColor);
		g.rect(drawX, drawY, drawWidth, drawHeight);
		
		if (!isLeaf) {

			g.fill(fillColor & 0xff808080);
			g.rect(drawX + paddingX, drawY + cardHeight, cardWidth - paddingX, paddingY);
			
			CardViewNode c = firstChild;
			while(c != null) {
				c.draw(g, selected ? c : clicked);
				c = c.nextSibling;
			}
		}		
		
		g.fill(textColor);
		g.text(card.text, drawX + 4, drawY + halfCardHeight);
		
		
	}
}