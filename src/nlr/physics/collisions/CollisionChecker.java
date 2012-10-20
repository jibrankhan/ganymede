package nlr.physics.collisions;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public strictfp final class CollisionChecker {
	
	private CollisionChecker() {
		
		super();
	}
	
	public strictfp static boolean collision(float aX, float aY, float aRadius, float bX, float bY, float bRadius) {
		
		float seperationX = aX - bX;
		float seperationY = aY - bY;
		
		float requiredDistance = aRadius + bRadius;
		
		return !(seperationX * seperationX + seperationY * seperationY > requiredDistance * requiredDistance);
	}

	public strictfp static Vector2f collisionDepth(float aX, float aY, float aRadius, float bX, float bY, float bRadius) {
		
		float seperationX = aX - bX;
		float seperationY = aY - bY;
		
		float requiredDistance = aRadius + bRadius;
		
		float seperationLengthSquared = seperationX * seperationX + seperationY * seperationY;
		
		if (seperationLengthSquared > requiredDistance * requiredDistance) {
			
			return new Vector2f(0, 0);
		}
		else {
			
			float l;
			
			if (seperationLengthSquared == 0) {
				
				l = 1f;
			}
			else {
				
				l = (float) Math.sqrt(seperationLengthSquared);
			}
			
			float d = requiredDistance - l;
			
			return new Vector2f(seperationX * d / l, seperationY * d / l);
		}
	}
	
	public static Vector2f collisionDepth(Rectangle a, Rectangle b) {
		
		float seperationX = a.getCenterX() - b.getCenterX();
		float distanceX = Math.abs(seperationX);
		float requiredDistanceX = (a.getWidth() + b.getWidth()) / 2;
		
		if (distanceX < requiredDistanceX) {
			
			float seperationY = a.getCenterY() - b.getCenterY();
			float distanceY = Math.abs(seperationY);
			float requiredDistanceY = (a.getHeight() + b.getHeight()) / 2;
			
			if (distanceY < requiredDistanceY) {
				
				if (distanceX > distanceY) {
					
					return new Vector2f(seperationX, 0).normalise().scale(requiredDistanceX - distanceX);
				}
				else {
					
					return new Vector2f(0, seperationY).normalise().scale(requiredDistanceY - distanceY);
				}
			}
		}
		
		return new Vector2f(0, 0);
	}
	
	public static Vector2f collisionDepth(Circle a, Rectangle b) {
		
		if (a.getCenterX() < b.getX()) {
			
			if (a.getCenterY() < b.getY()) {
				
				return CollisionChecker.collisionDepth(a, new Vector2f(b.getX(), b.getY()));
			}
			else if (a.getCenterY() > b.getY() + b.getHeight()) {
				
				return CollisionChecker.collisionDepth(a, new Vector2f(b.getX(), b.getY() + b.getHeight()));
			}
			else {
				
				float seperation = b.getX() - a.getCenterX();
				
				if (seperation > a.getRadius()) {
					
					return new Vector2f(0, 0);
				}
				else {
					
					return new Vector2f(seperation - a.getRadius(), 0);
				}
			}
		}
		else if (a.getCenterX() > b.getX() + b.getWidth()) {
			
			if (a.getCenterY() < b.getY()) {
				
				return CollisionChecker.collisionDepth(a, new Vector2f(b.getX() + b.getWidth(), b.getY()));
			}
			else if (a.getCenterY() > b.getY() + b.getHeight()) {
				
				return CollisionChecker.collisionDepth(a, new Vector2f(b.getX() + b.getWidth(), b.getY() + b.getHeight()));
			}
			else {
				
				float seperation = b.getX() + b.getWidth() - a.getCenterX();
				float distance = Math.abs(seperation);
				
				if (distance > a.radius) {
					
					return new Vector2f(0, 0);
				}
				else {
					
					return new Vector2f(seperation, 0).normalise().scale(distance - a.getRadius());
				}
			}
		}
		else {
			
			if (a.getCenterY() < b.getY()) {
				
				float seperation = b.getY() - a.getCenterY();
				
				if (seperation > a.getRadius()) {
					
					return new Vector2f(0, 0);
				}
				else {
					
					return new Vector2f(0, seperation - a.getRadius());
				}
			}
			else if (a.getCenterY() > b.getY() + b.getHeight()) {
				
				float seperation = b.getY() + b.getHeight() - a.getCenterY();
				float distance = Math.abs(seperation);
				
				if (distance > a.radius) {
					
					return new Vector2f(0, 0);
				}
				else {
					
					return new Vector2f(0, seperation).normalise().scale(distance - a.getRadius());
				}
			}
			else {
				
				return CollisionChecker.collisionDepth(
						new Rectangle(a.getX(),a.getY(), a.getRadius() * 2, a.getRadius() * 2), 
						b);
			}
		}
	}
	
	public static Vector2f collisionDepth(Circle a, Vector2f b) {
		
		Vector2f seperation = new Vector2f(a.getCenterX(), a.getCenterY()).sub(new Vector2f(b.getX(), b.getY()));
		
		if (seperation.lengthSquared() > a.getRadius() * a.getRadius()) {
			
			return new Vector2f(0, 0);
		}
		else {
			
			return seperation.copy().normalise().scale(a.getRadius() - seperation.length());
		}
	}
}
