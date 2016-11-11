import processing.core.PGraphics;
import processing.core.PVector;

public class RenderObject {
	public PGraphics g;
	public PVector position;
	public PVector anchor;
	public PVector dim;
	
	public float left() {return position.x - anchor.x;}
	public float right() {return position.x - anchor.x;}
	public float top() {return position.y - anchor.y;}
	public float bottom() {return position.y - anchor.y;}
	
	public RenderObject(PVector position, PVector anchor, PVector dim) {
		this.position = position;
		this.anchor = anchor;
		this.dim = dim;
	}
	
//	public render()
}
