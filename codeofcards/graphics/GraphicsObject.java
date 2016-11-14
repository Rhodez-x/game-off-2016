package codeofcards.graphics;
import processing.core.*;

public abstract class GraphicsObject {
	public PVector pos;
	public PVector offset;
	public PVector dim;
	
	public float left() {return pos.x - offset.x;}
	public float right() {return left() + dim.x;}
	public float top() {return pos.y - offset.y;}
	public float bottom() {return top() + dim.y;}
	
	public abstract void draw(PGraphics g);
}
