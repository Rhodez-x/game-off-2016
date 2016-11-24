package codeofcards.graphics;

import java.util.ArrayList;
import codeofcards.cards.*;
import processing.core.*;

public class CardViewNode {
	public static float cardWidth = 300;
	public static float cardHeight = 32;
	public static float halfCardHeight = cardHeight*0.5f;
	public static float paddingX = 16;
	public static float paddingY = 16;
	
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
	public ArrayList<CardViewNode> children;
	public int fillColor;
	public int activeFillColor;
	public int strokeColor;
	public boolean hidden;
	public boolean isLeaf;
	public boolean dirty;
	public BoundsRect relativeBounds;

	public CardViewNode(Card card) { this(card, 0, 0); }
	public CardViewNode(Card card, float xAt, float yAt) {
		this.card = card;
		this.children = new ArrayList<CardViewNode>();
		this.hidden = false;
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
			
			for (Card c : ((FlowCard)card).cards) {
				CardViewNode childNode = new CardViewNode(c, xAt, yAt);
				this.children.add(childNode);
			}
		}
		else {
			this.fillColor = statementCardFill.toInt();
			this.activeFillColor = statementCardFillInv.toInt();
			this.strokeColor = statementCardStroke.toInt();
		}
	}

	public float calculateBounds() { return calculateBounds(0, 0); }
	public float calculateBounds(float xAt, float yAt) {
		
		float dynHeight = cardHeight;

		if (isLeaf) {
			this.relativeBounds = new BoundsRect(xAt, yAt, cardWidth, dynHeight);
		}
		else {	
			for (CardViewNode c : children) {
				if (c.hidden) continue;
				
				dynHeight += c.calculateBounds(xAt + paddingX, yAt + dynHeight);
			}

			dynHeight += paddingY;
			
			dynHeight = Math.max(dynHeight, cardHeight + 2*paddingY);

			this.relativeBounds = new BoundsRect(xAt, yAt, cardWidth, dynHeight);
		}
		
		this.dirty = false;
		return dynHeight;
	}
	
	public CardViewNode getClickedNode(float clickX, float clickY) {
		
		CardViewNode result = null;

		if (!isLeaf) {
			for (CardViewNode c : children) {
				if (c.hidden) {
					continue;
				}
				result = c.getClickedNode(clickX, clickY);
				if (result != null) {
					return result;
				}
			}
		}
		
		if (relativeBounds.pointInside(clickX, clickY)) {
			result = this;
		}
	
		return result;
	}

	public void draw(PGraphics g, float x, float y) { draw(g, x, y, null); }
	public void draw(PGraphics g, float x, float y, CardViewNode clicked) {
		boolean selected = clicked != null && this.equals(clicked);
		
		g.stroke(strokeColor);
		
		g.textAlign(PConstants.LEFT, PConstants.CENTER);
		g.stroke(0);
		g.strokeWeight(1);
		
		int fillColor = (selected) ? this.activeFillColor : this.fillColor;
		int textColor = (selected) ? 255 : 0;

		float drawX = x + relativeBounds.x;
		float drawY = y + relativeBounds.y;
		float drawWidth = relativeBounds.w;
		float drawHeight = relativeBounds.h;

		g.fill(fillColor);
		g.rect(drawX, drawY, drawWidth, drawHeight);
		
		if (!isLeaf) {

			g.fill(fillColor & 0xff808080);
			g.rect(drawX + paddingX, drawY + cardHeight, cardWidth - paddingX, paddingY);

			for (CardViewNode c : children) {
				if (!c.hidden) {
					c.draw(g, x, y, selected ? c : clicked);
				}
			}
		}		
		
		g.fill(textColor);
		g.text(card.text, drawX + paddingX, drawY + halfCardHeight);
		
		
	}
}