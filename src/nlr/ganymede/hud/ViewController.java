package nlr.ganymede.hud;


import java.awt.Point;

import nlr.ganymede.GanymedeConstants;
import nlr.ganymede.simulation.GanymedeMap;
import nlr.ganymede.simulation.View;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

public strictfp final class ViewController {

	private View view;
	private GanymedeMap ganymedeMap;
	private Input input;
	
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	public ViewController(
			View view, 
			GanymedeMap ganymedeMap, 
			Input input) {
		
		super();
		
		this.view = view;
		this.ganymedeMap = ganymedeMap;
		this.input = input;
		
		this.up = false;
		this.down = false;
		this.left = false;
		this.right = false;
	}
	
	public void update() {
		
		this.updateInput();
		
		if ((this.up) || (this.down) || (this.left) || (this.right)) {
			
			Point adjustment = new Point(0, 0);
			
			if (this.up) {
				adjustment.y--;
			}
			
			if (this.down) {
				adjustment.y++;
			}
			
			if (this.left) {
				adjustment.x--;
			}
			
			if (this.right) {
				adjustment.x++;
			}
			
			adjustment.x *= GanymedeConstants.VIEW_SPEED;
			adjustment.y *= GanymedeConstants.VIEW_SPEED;
			
			this.adjust(adjustment);
			this.clamp();
		}
	}
	
	public void center(float x, float y) {
		
		this.center(new Vector2f(x, y));
	}
	
	public void center(Vector2f worldCoordinates) {
		
		Point p = this.view.worldCoordinatesToViewCoordinates(worldCoordinates.getX(), worldCoordinates.getY());
		
		p.x -= this.view.getWidth() / 2;
		p.y -= this.view.getHeight() / 2;
		
		this.view.setPosition(p);
		
		this.clamp();
	}
	
	private void updateInput() {
		
		this.up = ((this.input.isKeyDown(Input.KEY_UP)) || 
				(this.input.getMouseY() <= GanymedeConstants.SCREEN_EDGE_HOT_HEIGHT));
		
		this.down = ((this.input.isKeyDown(Input.KEY_DOWN)) || 
				(this.input.getMouseY() >= this.view.getHeight() - GanymedeConstants.SCREEN_EDGE_HOT_HEIGHT));
		
		this.left = ((this.input.isKeyDown(Input.KEY_LEFT)) || 
				(this.input.getMouseX() <= GanymedeConstants.SCREEN_EDGE_HOT_WIDTH));
		
		this.right = ((this.input.isKeyDown(Input.KEY_RIGHT)) || 
				(this.input.getMouseX() >= this.view.getWidth() - GanymedeConstants.SCREEN_EDGE_HOT_WIDTH));
		
		/*this.up = this.input.isKeyDown(Input.KEY_UP);
		
		this.down = this.input.isKeyDown(Input.KEY_DOWN);
		
		this.left = this.input.isKeyDown(Input.KEY_LEFT);
		
		this.right = this.input.isKeyDown(Input.KEY_RIGHT);*/
	}
	
	private void adjust(Point adjustment) {
		
		adjustment = new Point(adjustment);
		
		adjustment.x += this.view.getPosition().x;
		adjustment.y += this.view.getPosition().y;
		
		this.view.setPosition(adjustment);
	}
	
	private void clamp() {
		
		Point topLeft = new Point(0, 0);
		Point bottomRight = new Point(
				this.ganymedeMap.getWidthInTiles() * this.ganymedeMap.getTileWidth(), 
				this.ganymedeMap.getHeightInTiles() * this.ganymedeMap.getTileHeight());
		
		Point clamp = this.view.getPosition();
		
		if (clamp.x < topLeft.x) {
			clamp.x = topLeft.x;
		}
		
		if (clamp.y < topLeft.y) {
			clamp.y = topLeft.y;
		}
		
		if (clamp.x + this.view.getWidth() > bottomRight.x) {
			clamp.x = bottomRight.x - this.view.getWidth();
		}
		
		if (clamp.y + this.view.getHeight() > bottomRight.y) {
			clamp.y = bottomRight.y - this.view.getHeight();
		}
		
		this.view.setPosition(clamp);
	}
}
