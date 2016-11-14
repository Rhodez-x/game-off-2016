package codeofcards.graphics;

import codeofcards.Game;
import codeofcards.Board;
import codeofcards.cards.*;
import processing.core.PApplet;
import processing.core.PGraphics;

public class GameView extends PApplet {

	public final float cardHeight = 70;
	public final float cardWidth = 300;
	public final float padding = 16;
	public final float doublePadding = 2*padding;

	public final int functionCardFill = color(156, 196, 226);
	public final int statementCardFill = color(247, 202, 96);
	public final int controlFlowCardFill = color(150, 219, 125);
	public final int functionCardStroke = color(106, 146, 176);
	public final int statementCardStroke = color(197, 152, 46);
	public final int controlFlowCardStroke = color(100, 169, 75);

	public Board board;
	public Game game;

	public float getDrawHeight(Card card) {
		if (card instanceof StatementCard) {
			return cardHeight;
		}
		
		float sum = cardHeight + doublePadding;
		for (Card c : ((FlowCard)card).cards) {
			sum += getDrawHeight(c);
		}
		return sum;
	}

	public void settings() {
		size(400, 400);
	}

	public void setup() {
		background(color(20, 192, 40));
	}

//	public void draw() {
//
//	}

	public GameView() {
		this.game = new Game();
		this.board = game.board;

		game.hostGame();
	}

	public void drawGameBoard() {

	}
	
	public static void main(String[] args) {
		PApplet.main("codeofcards.graphics.GameView");
	}
}