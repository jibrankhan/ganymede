package nlr.utils;

import org.newdawn.slick.geom.Vector2f;

public strictfp final class Utils {
	
	private Utils() {
		
		super();
	}
	
	public strictfp static Vector2f closestPointOnLineSegment(Vector2f p, Vector2f a, Vector2f b) {
		
		Vector2f ap = p.copy().sub(a);
		Vector2f ab = b.copy().sub(a);
		
		Vector2f v = ab.copy().normalise();
		
		float d = ab.length();
		float t = v.dot(ap);
		
		if (t < 0) {
			
			return a;
		}
		
		if (t > d) { 
			
			return b;
		}
		
		return a.copy().add(v.scale(t));
	}
	
	public strictfp static float wrapAngle(float a) {
		
		while (a < 0) {
			
			a += 360f;
		}
		
		a %= 360f;
		
		return a;
	}
	
	public strictfp static float angleDifference(float a, float b) {
		
		return Math.abs((a + 180f - b) % 360f - 180f);
	}
	
	public strictfp static int directionShortest(float a, float b) {
		
		if ((a - b + 360f) % 360f > 180f) {
			
			return 1;
		}
		else {
			
			return -1;
		}
	}
	
	public strictfp static float length(float x, float y) {
		
		float xxyy = x * x + y * y;
		
		return (float) Math.sqrt(xxyy * xxyy);
	}
}
