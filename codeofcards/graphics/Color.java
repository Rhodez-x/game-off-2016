package codeofcards.graphics;

public class Color {
	public float a, r, g, b;
	
	public static Color black = new Color(1, 0, 0, 0);
	public static Color white = new Color(1, 1, 1, 1);
	
	public Color(float r, float g, float b) { this(1f, r, g, b); }
	public Color(float a, float r, float g, float b) {
		this.a = Math.min(1f, Math.max(0f, a));
		this.r = Math.min(1f, Math.max(0f, r));
		this.g = Math.min(1f, Math.max(0f, g));
		this.b = Math.min(1f, Math.max(0f, b));
	}
	
	final static float inv255 = 1f/255f;
	
	public int toInt() {
		return ((int)(a * 255) << 24) | ((int)(r * 255) << 16) | ((int)(g * 255) << 8) | (int)(b * 255);
 	}
	
	public static Color fromInts(int ia, int ir, int ig, int ib) {
		return new Color(inv255*ia, inv255*ir, inv255*ig, inv255*ib);
	}
	
	public static Color fromInt(int i) {
		float a, r, g, b;
		b = inv255*(float)(i & 255); i >>= 8;
		g = inv255*(float)(i & 255); i >>= 8;
		r = inv255*(float)(i & 255); i >>= 8;
		a = inv255*(float)(i & 255);
		return new Color(a, r, g, b);
	}
	
	public Color add(Color b) { return add(this, b); }
	public static Color add(Color a, Color b) {
		return new Color(a.a + b.a, a.r + b.r, a.g + b.g, a.b + b.b);
	}
	
	public static Color gray(float val) {
		return new Color(1, val, val, val);
	}
	
	public Color inv() {
		return new Color(a, 1 - r, 1 - g, 1 - b);
	}
	
	public Color multiply(Color b) { return multiply(this, b); }
	public static Color multiply(Color a, Color b) {
		return new Color(a.a * b.a, a.r * b.r, a.g * b.g, a.b * b.b);
	}
	
	
	public Color weird(Color b) { return weird(this, b); }
	public static Color weird(Color a, Color b) {
		float na, nr, ng, nb;
		na = a.a + 2f*(b.a - 0.5f);
		nr = a.r + 2f*(b.r - 0.5f);
		ng = a.g + 2f*(b.g - 0.5f);
		nb = a.b + 2f*(b.b - 0.5f);
		return new Color(na, nr, ng, nb);
	}
	
	public static float overlay(float a, float b) {
		if (a < 0.5f) return 2*a*b;
		return 1 - 2*(1 - a)*(1 - b);
	}
	
	public Color overlay(Color b) { return overlay(this, b); }
	public static Color overlay(Color a, Color b) {
		return new Color(a.a, overlay(a.r, b.r), overlay(a.g, b.g), overlay(a.b, b.b));
	}
	
	public Color lerp(Color b, float t) { return colorLerp(this, b, t);}
	public static Color colorLerp(Color a, Color b, float t) {
		return new Color(a.a + t*(a.a - b.a), a.r + t*(a.r - b.r), a.g + t*(a.g - b.g), a.b + t*(a.b - b.b));
	}

}
