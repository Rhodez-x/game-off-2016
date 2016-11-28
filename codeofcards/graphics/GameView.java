package codeofcards.graphics;

import codeofcards.Game;
import codeofcards.Board;
import codeofcards.cards.*;
import codeofcards.graphics.CardViewNode.ClickedNodeResult;
import codeofcards.graphics.CardViewNode.RemovedFromSpot;
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
	private PGraphics g2;
	
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
		g2 = createGraphics(g.width, g.height);
		g.textSize(12);
		
	}

	
	public float cx = 20, cy = 20, cox, coy;
	public boolean dragging = false;
	
	public PGraphics g;
	
	CardViewNode clickedNode = null;
	boolean lastMousePressed = false;
		
	public CardViewTree dragTree = null;
	
	public String debugStr = "";
	
	public RemovedFromSpot dragFromSpot = null;
	public float insertX = 0;
	public float insertY = 0;

	public void draw() {

		// Update state
		if (guiState == GuiState.IDLE) {
			if (mousePressed && !lastMousePressed) {
				ClickedNodeResult cnr = tree.getNodeAtPoint(mouseX, mouseY); 
				if (cnr != null) {
					clickedNode = cnr.clickedNode;
					cx = mouseX;
					cy = mouseY;
					cox = (clickedNode.parent != null) ? 0 : cnr.xOffset;
					coy = (clickedNode.parent != null) ? 0 : cnr.yOffset;
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
					
					CardViewNode node = cnr.clickedNode;
					
					insertX = node.relativeBounds.x + node.tree.x;
					insertY = node.relativeBounds.y + node.tree.y - 2;
					if (asChild) {
						insertX += CardViewNode.paddingX;
						if (prepend) {
							insertY += CardViewNode.cardHeight;
						}
						else {
							insertY += node.relativeBounds.h - CardViewNode.paddingY;
						}
					}
					else {
						if (!prepend) {
							insertY += node.relativeBounds.h;
						}
						else if(node.parent == null) {
							insertY = -99;
						}
					}
					debugStr = "prepend = " + prepend + "\nasChild = " + asChild;	
				}
				
				if (mouseReleased && dragTree != tree) {
					if (cnr != null) {
						CardViewNode node = cnr.clickedNode;
						if (asChild) {
							if (prepend) {
								node.insertChildTop(dragTree.root);
							}
							else {
								node.insertChildBottom(dragTree.root);
							}
						}
						else if (node.parent != null) {
							if (prepend) {
								node.insertBefore(dragTree.root);
							}
							else {
								node.insertAfter(dragTree.root);
							}
						}
						else {
							if (dragFromSpot != null) {
								dragFromSpot.restore(dragTree.root);
							}
						}
					}
					else {
						if (dragFromSpot != null) {
							dragFromSpot.restore(dragTree.root);
						}
					}
				}
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
			if (dx*dx + dy*dy > 20) {
				guiState = GuiState.DRAGGING;
				if (clickedNode.parent != null) {
					dragFromSpot  = clickedNode.removeFromTree();
					dragTree = new CardViewTree(clickedNode, mouseX, mouseY);
				}
				else {
					dragTree = tree;
				}
				clickedNode = null;
			}
		}
		lastMousePressed = mousePressed;
				
		// Draw
		background(0xff109920);
		
		if (tree != dragTree)
			tree.draw(g, clickedNode);
		
		if (guiState == GuiState.DRAGGING) {
			dragTree.root.updateBounds();
			dragTree.x = mouseX - cox;
			dragTree.y = mouseY - coy;
			dragTree.draw(g, dragTree.root);
		}
		
		if (dragTree != tree) {
			//insertPoint.draw(g, null);
			g.stroke(0);
			g.fill(0xffffff00);
			g.rect(insertX, insertY, CardViewNode.cardWidth, 4);
			insertY = -99;
		}
		
		text(debugStr, 5, 20);
	}

}