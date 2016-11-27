package codeofcards.graphics;

import codeofcards.Game;
import codeofcards.Board;
import codeofcards.cards.*;
import codeofcards.graphics.CardViewNode.ClickedNodeResult;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;

public class GameView extends PApplet {

	public enum GuiState {
		IDLE, BEGIN_DRAG, DRAGGING
	}

	public Board board;
	public Game game;

	public FunctionCard fc;
	public RepeatCard rc2;

	public CardViewNode root;
	public CardViewTree tree;

	public GuiState guiState = GuiState.IDLE;
	
	public void settings() {
		size(1024, 768);
	}
	
	public void setup() {
		surface.setResizable(true);
		
		fc = new FunctionCard("Function", 5, 10);
		fc.pushCard(new StatementCard("other.discardCard()", 5, StatementCard.StatementType.OtherDiscardCard));
		fc.pushCard(new StatementCard("function.cycles++", 5, StatementCard.StatementType.CyclesIncrement));
		RepeatCard rc = new RepeatCard("Repeat 3", 3, 3);
		rc.pushCard(new StatementCard("self.life++", 5, StatementCard.StatementType.SelfIncrementLife));
		
		rc2 = new RepeatCard("Repeat 5", 5, 3);
		rc2.pushCard(rc);
		fc.pushCard(rc2);
		RepeatCard rc3 = new RepeatCard("Repeat 3", 3, 3);
		rc2.pushCard(rc3);
		rc3.pushCard(new StatementCard("self.life--", 5, StatementCard.StatementType.SelfDecrementLife));
//
//		fc.pushCard(rc2);
//		fc.pushCard(rc2);
		
		root = new CardViewNode(fc);
		tree = new CardViewTree(root, 100, 100);
		
//		PFont labelFont = loadFont("RobotoCondensed-Regular-23.vlw");
//		textFont(labelFont);
		
		strokeWeight(1);
		g = getGraphics();
		g.textSize(13);
		
	}

	
	public float cx = 20, cy = 20, cox, coy;
	public boolean dragging = false;
	
	public PGraphics g;
	
	CardViewNode clickedNode = null;
	boolean lastMousePressed = false;
		
	public CardViewTree dragTree = null;
	
	public String debugStr = "";
	
	public float insertLineX, insertLineY, insertLineX2, insertLineY2;

	public void draw() {

		// Update state
		if (guiState == GuiState.IDLE) {
			if (mousePressed && !lastMousePressed) {
				ClickedNodeResult cnr = tree.getNodeAtPoint(mouseX, mouseY); 
				if (cnr != null) {
					clickedNode = cnr.clickedNode;
					cx = mouseX;
					cy = mouseY;
					cox = cnr.xOffset;
					coy = cnr.yOffset;
					guiState = GuiState.BEGIN_DRAG;
				}
			}
		}
		else {
			boolean mouseReleased = !mousePressed && lastMousePressed;
			
			if(guiState == GuiState.DRAGGING) {
				boolean prepend = false;
				boolean asChild = false;
				
				ClickedNodeResult cnr = tree.getNodeAtPoint(mouseX - cox, mouseY - coy);
				
				if (cnr != null) {
					float yPct = cnr.yOffset / CardViewNode.cardHeight;
					if (yPct <= 0.5f) {
						prepend = true;
						asChild = false;
					}
					else {
						if (cnr.clickedNode.isLeaf) {
							prepend = false;
							asChild = false;
						}
						else {
							prepend = (yPct <= 1);
							asChild = true;
						}
					}
					
					debugStr = "prepend = " + prepend + "\nasChild = " + asChild;
					
					if (mouseReleased) {
						CardViewNode node = cnr.clickedNode;
						if (asChild) {
							if (prepend) {
								node.insertChildTop(dragTree.root);
							}
							else {
								node.insertChildBottom(dragTree.root);
							}
						}
						else {
							if (prepend) {
								node.insertBefore(dragTree.root);
							}
							else {
								node.insertAfter(dragTree.root);
							}
						}
					}
				}
				//tree.checkForPotentialDropSpot(mouseX - cox, mouseY - coy);
			}
			
			if (mouseReleased) {
				clickedNode = null;
				dragTree = null;
				guiState = GuiState.IDLE;
			}
			
		}
		
		if (guiState == GuiState.BEGIN_DRAG) {
			float dx = mouseX - cx;
			float dy = mouseY - cy;
			
			// inline of something like magnitude(vec(dx, dy)) > 24
			if (dx*dx + dy*dy > 2) {
				guiState = GuiState.DRAGGING;
				if (clickedNode.parent != null) {
					clickedNode.removeFromTree();
					dragTree = new CardViewTree(clickedNode, mouseX, mouseY);
				}
				else {
					dragTree = tree;
				}
				clickedNode = null;
			}
		}
		lastMousePressed = mousePressed;
		
		tree.root.updateBounds();
		
		// Draw
		background(0xff40b020);
		
		tree.draw(g, clickedNode);

		if (guiState == GuiState.DRAGGING) {
			dragTree.root.updateBounds();
			dragTree.x = mouseX - cox;
			dragTree.y = mouseY - coy;
			dragTree.draw(g, dragTree.root);
		}
		
		text(debugStr, 5, 20);
	}

	public GameView() {
		//this.game = new Game();
		//this.board = game.board;

//		game.hostGame();
	}

}