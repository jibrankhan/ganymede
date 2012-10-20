package nlr.ganymede.data;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public final class SpriteData {

	private Image image;
	
	private int tw;
	private int th;
	
	private int x1;
	private int y1;
	
	private int x2;
	private int y2;
	
	private int duration;
	
	private int originX;
	private int originY;
	
	public Image getImage() {
		return image;
	}

	public int getTw() {
		return tw;
	}

	public int getTh() {
		return th;
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
	}

	public int getDuration() {
		return duration;
	}

	public int getOriginX() {
		return originX;
	}

	public int getOriginY() {
		return originY;
	}

	public SpriteData(Image image, int tw, int th, int x1, int y1, int x2,
			int y2, int duration, int originX, int originY) {
		
		super();
		
		this.image = image;
		
		this.tw = tw;
		this.th = th;
		
		this.x1 = x1;
		this.y1 = y1;
		
		this.x2 = x2;
		this.y2 = y2;
		
		this.duration = duration;
		
		this.originX = originX;
		this.originY = originY;
	}
	
	/**
	 * Constructs an Animation based on local variables.
	 * 
	 * Creates an entirely new Animation each time, to avoid 
	 * interference. 
	 *  
	 * @return A new Animation instance
	 */
	public Animation constructAnimation() {
		return new Animation(
				new SpriteSheet(this.image.copy(), this.tw, this.th), 
				this.x1, this.y1, this.x2, this.y2, true, this.duration, true);
	}
}
