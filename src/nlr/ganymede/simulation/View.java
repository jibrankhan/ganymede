package nlr.ganymede.simulation;

import java.awt.Point;

import org.newdawn.slick.geom.Vector2f;

public strictfp final class View {

	private int width;
	private int height;
	
	private Point position;
	
	public int getWidth() {
		
		return this.width;
	}
	
	public int getHeight() {
		
		return this.height;
	}
	
	public Point getPosition() {
		
		return new Point(this.position);
	}
	
	public void setPosition(Point position) {
		
		this.position = new Point(position);
	}
	
	public View(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		this.position = new Point(0, 0);
	}
	
	public Vector2f viewCoordinatesToWorldCoordinates(int vx, int vy) {
		
		return new Vector2f(
				this.position.x + vx, 
				this.position.y + vy);
	}
	
	public Vector2f viewCoordinatesToWorldCoordinates(Point viewCoordinates) {
		
		return new Vector2f(
				this.position.x + viewCoordinates.x, 
				this.position.y + viewCoordinates.y);
	}
	
	public Point worldCoordinatesToViewCoordinates(float worldX, float worldY) {
		
		return new Point(
				(int) worldX - this.position.x, 
				(int) worldY - this.position.y);
	}
}
