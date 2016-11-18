package codeofcards.graphics;

import java.util.ArrayList;
import codeofcards.cards.*;
import processing.core.*;

public class CardViewNode {
	
	public static int color(int a, int r, int g, int b) {
		a = Math.min(255, Math.max(0, a));
		r = Math.min(255, Math.max(0, r));
		g = Math.min(255, Math.max(0, g));
		b = Math.min(255, Math.max(0, b));
		return (a << 24) | (r << 16) | (g << 8) | b;
	}
	
	public static int color(int r, int g, int b) {
		return color(255, r, g, b);
	}
	
	public static int colorBrighten(int col, int val) {
		int a, r, g, b;
		b = (col & 0xff) + val; col >>= 8;
		g = (col & 0xff) + val; col >>= 8;
		r = (col & 0xff) + val; col >>= 8;
		a = (col & 0xff);
		return color(a, r, g, b);
	}
	
	final static float inv255 = 0.00392156862745098f;
	
	public static int colorMult(int col1, int col2) {
		float a1, r1, g1, b1;
		float a2, r2, g2, b2;
		
		b1 = (col1 & 255)*inv255; col1 >>= 8;
		g1 = (col1 & 255)*inv255; col1 >>= 8;
		r1 = (col1 & 255)*inv255; col1 >>= 8;
		a1 = (col1 & 255)*inv255;
		
		b2 = (col2 & 255)*inv255; col2 >>= 8;
		g2 = (col2 & 255)*inv255; col2 >>= 8;
		r2 = (col2 & 255)*inv255; col2 >>= 8;
		a2 = (col2 & 255)*inv255;
		
		return color((int)(a1 * a2 * 255), (int)(r1 * r2 * 255), (int)(g1 * g2 * 255), (int)(b1 * b2 * 255));
	}
	
	public static int colorLerp(int col1, int col2, float t) {
		int a1, r1, g1, b1;
		int a2, r2, g2, b2;
		int ar, rr, gr, br;
		
		b1 = (col1 & 255); col1 >>= 8;
		g1 = (col1 & 255); col1 >>= 8;
		r1 = (col1 & 255); col1 >>= 8;
		a1 = (col1 & 255);
		
		b2 = (col2 & 255); col2 >>= 8;
		g2 = (col2 & 255); col2 >>= 8;
		r2 = (col2 & 255); col2 >>= 8;
		a2 = (col2 & 255);
		
		ar = (int)(a1 + t*(a2 - a1));
		rr = (int)(r1 + t*(r2 - r1));
		gr = (int)(g1 + t*(g2 - g1));
		br = (int)(b1 + t*(b2 - b1));
		
		return color(ar, rr, gr, br);
	}
	
	public static int colorDarken(int col, int val) {
		return colorBrighten(col, -val);
	}

	public static float cardWidth = 300;
	public static float cardHeight = 50;
	public static float halfCardHeight = cardHeight*0.5f;
	public static float paddingX = 10;
	public static float paddingY = 16;
	
	public static int functionCardFill = color(156, 196, 246);
	public static int statementCardFill = color(240, 240, 240);
	public static int controlFlowCardFill = color(250, 200, 180);
	public static int functionCardStroke = colorDarken(functionCardFill, 120);
	public static int statementCardStroke = colorDarken(statementCardFill, 120);
	public static int controlFlowCardStroke = colorDarken(controlFlowCardFill, 120);
	
	public float offsetX, offsetY;
//	public int childIndex;
	public Card card;
	public CardViewNode parent;
	public CardViewNode rootParent;
	public ArrayList<CardViewNode> children;
	public float drawHeight;
	public int fillColor;
	public int nestLevel;
	public int strokeColor;
	public boolean gettingDragged;
	public float x, y;
	
	public CardViewNode(Card card) { this(card, 0, 0, null); }
	public CardViewNode(Card card, float x, float y, CardViewNode parent) {
		this.card = card;
		this.children = new ArrayList<CardViewNode>();
		this.parent = parent;
//		this.rootParent = (parent != null) ? ((parent.rootParent != null) ? parent.rootParent : parent) : null;
		this.gettingDragged = false;
//		this.nestLevel = nestLevel;
		this.x = x;
		this.y = y;
		
		if (card instanceof FlowCard) {
			if (card instanceof RepeatCard) {
				this.fillColor = controlFlowCardFill;
				this.strokeColor = controlFlowCardStroke;
			}
			else {
				this.fillColor = functionCardFill;
				this.strokeColor = functionCardStroke;
			}
			
			this.drawHeight = cardHeight;
//			int childIndex = 0;
			for (Card c : ((FlowCard)card).cards) {
				CardViewNode childNode = new CardViewNode(c, x + paddingX, y + drawHeight, this);
				this.children.add(childNode);
//				childNode.childIndex = childIndex++;
				this.drawHeight += childNode.drawHeight;
			}
			this.drawHeight += paddingY;
		}
		else {
			this.fillColor = statementCardFill;
			this.strokeColor = statementCardStroke;
			
			this.drawHeight = cardHeight;
		}
	}
	
	public float left() { return x; }
	public float right() { return x + cardWidth; }
	public float top() { return y; }
	public float bottom() { return y + drawHeight; }
	public boolean pointInside(float px, float py) {
		return (px >= left() &&
				px <= right() &&
				py >= top() &&
				py <= bottom());
	}
	
	public float getDrawHeight() {
		if (children.isEmpty()) {
			return cardHeight;
		}
		
		float result = cardHeight + paddingY;
		for (CardViewNode c : children) {
			if (c.gettingDragged) continue;
			
			result += c.getDrawHeight();
		}
		
		return Math.max(result, cardHeight + 2*paddingY);
	}
	
	public CardViewNode getClickedNode(float clickX, float clickY) {
		
		CardViewNode result = null;
		
		for (CardViewNode c : children) {
			result = c.getClickedNode(clickX, clickY);
			if (result != null) {
				break;
			}
		}
		
		if (result == null && pointInside(clickX, clickY)) {
			result = this;
		}
	
		return result;
	}
	
	public void draw(PGraphics g) {
		draw(g, x, y, false);
	}
	
	public void draw(PGraphics g, boolean drag) {
		draw(g, x, y, drag);
	}

	public void draw(PGraphics g, float x, float y, boolean drag) {
		g.stroke(strokeColor);
		
		g.textAlign(PConstants.LEFT, PConstants.CENTER);
		g.stroke(0);
		g.strokeWeight(1);
		
		int fillColor = (drag) ? colorDarken(this.fillColor, 0x80) : this.fillColor;
		int textColor = (drag) ? 255 : 0;
		float drawHeight = getDrawHeight();
				
		g.fill(fillColor);
		
		if (!children.isEmpty()) {
			g.rect(x, y, cardWidth, Math.max(drawHeight, cardHeight + 2*paddingY));
			
			float childrenHeight = cardHeight;
			for (CardViewNode c : children) {
				if (c.gettingDragged) continue;
				c.draw(g, x + paddingX, y + childrenHeight, drag);
				childrenHeight += c.getDrawHeight();
			}
			
			if (drawHeight <= cardHeight + 2*paddingY) {
				g.fill(color(127, 0, 0, 0));
				g.rect(x + paddingX, y + cardHeight, cardWidth - paddingX, paddingY);
			}
		}
		else {
			g.rect(x, y, cardWidth, drawHeight);
		}
			
		
		
		g.fill(textColor);
		g.text(card.text, x + paddingX, y + halfCardHeight);
		
		
	}
}