package codeofcards.graphics;

public class BoundsRect {
	public float x, y, w, h;

	public BoundsRect(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public boolean pointInside(float px, float py) {
		return px >= x && px <= x + w && py >= y && py <= y + h;
	}

	public float[] getPointOffset(float px, float py) {
		return new float[]{px - x, py - y};
	}
}
