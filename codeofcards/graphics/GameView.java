package codeofcards.graphics;

import codeofcards.Game;
import codeofcards.Board;
import codeofcards.cards.*;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PVector;

public class GameView extends PApplet {

	public PFont labelFont;
	
	public final float cardHeight = 50;
	public final float cardWidth = 300;
	public final float halfCardHeight = 0.5f*cardHeight;
	public final float paddingX = 10;
	public final float paddingY = 16;

	public final int functionCardFill = color(156, 196, 246);
	public final int statementCardFill = color(240, 240, 240);
	public final int controlFlowCardFill = color(250, 200, 180);
	public final int functionCardStroke = lerpColor(functionCardFill, 0xff000000, 0.6f);
	public final int statementCardStroke = lerpColor(statementCardFill, 0xff000000, 0.6f);
	public final int controlFlowCardStroke = lerpColor(controlFlowCardFill, 0xff000000, 0.6f);

	public Board board;
	public Game game;

	public float getDrawHeight(Card card) {
		if (card instanceof StatementCard) {
			return cardHeight;
		}

		float sum = cardHeight + paddingY;
		for (Card c : ((FlowCard)card).cards) {
			sum += getDrawHeight(c);
		}
		return sum;
	}

	public float drawCard(Card card, float x, float y) {
		return drawCard(card, x, y, cardWidth);
	}
	
	public float drawCard(Card card, float x, float y, float drawWidth) {
		boolean hasCycles = false;
		
		if (card instanceof StatementCard) {
			stroke(statementCardStroke);
			fill(statementCardFill);
		}
		else if (card instanceof FunctionCard) {
			stroke(functionCardStroke);
			fill(functionCardFill);
			hasCycles = true;
		}
		else if (card instanceof FlowCard) {
			stroke(controlFlowCardStroke);
			fill(controlFlowCardFill);
		}
		
		float drawHeight = getDrawHeight(card);
		strokeWeight(1);
		rect(x, y, drawWidth, drawHeight);
		textSize(20);
		textAlign(LEFT, CENTER);
		fill(0);
		text(card.text, x+paddingX, y+halfCardHeight);
		
		if (hasCycles) {
			fill(255);
			
			float cycleCountX = x+drawWidth-paddingX-15;
			float cycleCountY = y+halfCardHeight;
			
			rect(cycleCountX - 15, cycleCountY - 15, 30, 30);
			textAlign(CENTER, CENTER);
			fill(0);
			text(((FunctionCard)card).cycles, cycleCountX, cycleCountY);
		}

		if (card instanceof FlowCard) {
			for (Card c : ((FlowCard)card).cards) {
				y += drawCard(c, x + paddingX, y + cardHeight, drawWidth);
			}
		}
		
		return drawHeight;
	}

	public void settings() {
		size(1024, 768);
	}

	public FunctionCard fc;
	public RepeatCard rc2;

	public CardViewNode root;
	
	public void setup() {
		fc = new FunctionCard("Function", 5, 10);
		fc.pushCard(new StatementCard("other.discardCard()", 5, StatementCard.StatementType.OtherDiscardCard));
		fc.pushCard(new StatementCard("function.cycles++", 5, StatementCard.StatementType.CyclesIncrement));
		RepeatCard rc = new RepeatCard("Repeat 3", 3, 3);
		rc.pushCard(new StatementCard("self.life++", 5, StatementCard.StatementType.SelfIncrementLife));
		fc.pushCard(rc);
		rc2 = new RepeatCard("Repeat 5", 5, 3);
		RepeatCard rc3 = new RepeatCard("Repeat 3", 3, 3);
		rc2.pushCard(rc3);
		rc3.pushCard(new StatementCard("self.life--", 5, StatementCard.StatementType.SelfDecrementLife));

		fc.pushCard(rc2);
		fc.pushCard(rc2);
		
		root = new CardViewNode(fc, 100, 60, null);
		
		labelFont = loadFont("RobotoCondensed-Regular-23.vlw");
		textFont(labelFont);
		
		strokeWeight(1);
		g = getGraphics();
		
	}

	
	public float cx = 20, cy = 20, cox, coy;
	public boolean dragging = false;
	
	public PGraphics g;
	
	CardViewNode clickedNode;
	boolean lastMousePressed = false;
	
	public void draw() {
		
		background(100);
		
		if (!root.gettingDragged)
			root.draw(g);
		
		if (clickedNode != null) {
			
			clickedNode.draw(g, mouseX + cox, mouseY + coy);
			if (!mousePressed && lastMousePressed) {	
				clickedNode.gettingDragged = false;
				clickedNode = null;
			}
		}
		
		if (mousePressed && !lastMousePressed) {
			
			clickedNode = root.getClickedNode(mouseX, mouseY);
			if (clickedNode != null) {
				clickedNode.gettingDragged = true;
				cox = clickedNode.left() - mouseX;
				coy = clickedNode.top() - mouseY;
			}
		}
		
		lastMousePressed = mousePressed;
		
		
//		background(100);
//		if (mousePressed) {
//			if (!dragging &&
//					mouseX >= cx && mouseX <= cx + cardWidth &&
//					mouseY >= cy && mouseY <= cy + getDrawHeight(fc)) {
//				dragging = true;
//				cox = mouseX - cx;
//				coy = mouseY - cy;
//			}
//		}
//		
//		if(dragging) {
//			if(!mousePressed) {
//				dragging = false;
//			} else {
//				cx = mouseX - cox;
//				cy = mouseY - coy;
//			}
//		}
//		
//		drawCard(fc, cx, cy);
//		drawCard(rc2, cx, cy);
	}

	public GameView() {
		this.game = new Game();
		this.board = game.board;

//		game.hostGame();
	}

	public void drawGameBoard() {

	}
}