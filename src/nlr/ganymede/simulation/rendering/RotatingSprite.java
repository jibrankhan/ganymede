package nlr.ganymede.simulation.rendering;

import java.awt.Point;

import nlr.ganymede.data.RotatingSpriteData;
import nlr.ganymede.simulation.View;
import nlr.rendering.RotatingAnimation;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;



public strictfp final class RotatingSprite {

	private View view;
	
	private RotatingSpriteData spriteData;
	
	private RotatingAnimation animation;
	
	private Vector2f position;
	private Color filter;
	private float angle;
	
	private Vector2f renderPosition;
	
	private boolean isLooping;
	
	private Rectangle boundingBox;
	
	public RotatingSpriteData getSpriteData() {
		
		return spriteData;
	}
	
	public boolean isLooping() {
		
		return isLooping;
	}

	public void setLooping(boolean isLooping) {
		
		this.isLooping = isLooping;
		
		if (this.animation != null) {
			
			this.animation.setLooping(this.isLooping);
		}
	}

	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public void setSpriteData(RotatingSpriteData spriteData) {
		
		if (this.spriteData != spriteData) {
			
			this.spriteData = spriteData;
			
			this.animation = this.spriteData.constructAnimation();
			
			this.animation.setLooping(this.isLooping);
			
			this.boundingBox.setSize(this.animation.getWidth(), this.animation.getHeight());
		}
	}

	public Vector2f getPosition() {
		
		return position.copy();
	}

	public void setPosition(float x, float y) {
		
		this.position.set(x, y);
		
		Point v = this.view.worldCoordinatesToViewCoordinates(this.position.getX(), this.position.getY());
		
		this.renderPosition.set(v.x - this.spriteData.getOriginX(), v.y - this.spriteData.getOriginY());
		
		this.boundingBox.setLocation(this.renderPosition);
	}

	public Color getFilter() {
		
		return filter;
	}

	public void setFilter(Color filter) {
		
		this.filter = filter;
	}
	
	public float getAngle() {
		
		return angle;
	}

	public void setAngle(float angle) {
		
		this.angle = angle;
	}
	
	public RotatingSprite(View view, RotatingSpriteData spriteData) {
		
		this(view);
		
		this.setSpriteData(spriteData);
	}
	
	public RotatingSprite(View view) {
		
		this.view = view;
		
		this.position = new Vector2f(0, 0);
		this.filter = Color.white;
		
		this.renderPosition = new Vector2f(0, 0);
		
		this.isLooping = true;
		
		this.boundingBox = new Rectangle(0, 0, 0, 0);
	}
	
	public void render() {
		
		if (this.animation != null) {
			
			Point v = this.view.worldCoordinatesToViewCoordinates(this.position.getX(), this.position.getY());
			
			this.renderPosition.set(v.x - this.spriteData.getOriginX(), v.y - this.spriteData.getOriginY());
			
			this.boundingBox.setLocation(this.renderPosition);
			
			this.animation.draw(this.renderPosition.getX(), this.renderPosition.getY(), this.filter, this.angle);
		}
	}
}
