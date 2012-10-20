package nlr.ganymede.simulation.rendering;

import java.awt.Point;

import nlr.ganymede.data.SpriteData;
import nlr.ganymede.simulation.View;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public strictfp final class Sprite {

	private View view;
	
	private SpriteData spriteData;
	
	private Animation animation;
	
	private Vector2f position;
	private Color filter;
	
	private Vector2f renderPosition;
	
	private boolean isLooping;
	
	private Rectangle boundingBox;
	
	public SpriteData getSpriteData() {
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

	public void setSpriteData(SpriteData spriteData) {
		
		if (this.spriteData != spriteData) {
			
			this.spriteData = spriteData;
			
			this.animation = this.spriteData.constructAnimation();
			
			this.renderPosition.set(
					this.position.getX() - this.spriteData.getOriginX(), 
					this.position.getY() - this.spriteData.getOriginY());
			
			this.animation.setLooping(this.isLooping);
			
			this.boundingBox.setSize(this.animation.getWidth(), this.animation.getHeight());
		}
	}

	public Vector2f getPosition() {
		return position.copy();
	}

	public void setPosition(Vector2f position) {
		
		this.position.set(position);
		this.boundingBox.setLocation(this.position);
		
		if (this.view == null) {
			if (animation != null) {
				this.renderPosition.set(
						this.position.getX() - this.spriteData.getOriginX(), 
						this.position.getY() - this.spriteData.getOriginY());
			}
		}
	}

	public Color getFilter() {
		return filter;
	}

	public void setFilter(Color filter) {
		this.filter = filter;
	}
	
	public Sprite(View view) {
		
		super();
		
		this.view = view;
		
		this.position = new Vector2f(0, 0);
		this.filter = Color.white;
		
		this.renderPosition = new Vector2f(0, 0);
		
		this.isLooping = true;
		
		this.boundingBox = new Rectangle(0, 0, 0, 0);
	}
	
	public Sprite(View view, SpriteData spriteData) {
		
		this(view);
		
		this.setSpriteData(spriteData);
	}
	
	public void render() {
		
		if (animation != null) {
			
			Point v = this.view.worldCoordinatesToViewCoordinates(this.position.getX(), this.position.getY());
			
			this.renderPosition.set(v.x - this.spriteData.getOriginX(), v.y - this.spriteData.getOriginY());
			
			this.boundingBox.setLocation(this.renderPosition);
			
			this.animation.draw(this.renderPosition.getX(), this.renderPosition.getY(), this.filter);
		}
	}
}
