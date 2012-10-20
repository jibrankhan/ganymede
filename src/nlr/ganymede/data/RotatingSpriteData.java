package nlr.ganymede.data;

import nlr.rendering.RotatingAnimation;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;


public final class RotatingSpriteData {

	private Image image;
	
	private int tw;
	private int th;
	
	private int duration;
	
	private int originX;
	private int originY;
	
	private boolean looping;
	
	public Image getImage() {
		return image;
	}

	public int getTw() {
		return tw;
	}

	public int getTh() {
		return th;
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

	public boolean isLooping() {
		return looping;
	}

	public RotatingSpriteData(Image image, int tw, int th, int duration, int originX, int originY, boolean looping) {
		
		super();
		
		this.image = image;
		
		this.tw = tw;
		this.th = th;
		
		this.duration = duration;
		
		this.originX = originX;
		this.originY = originY;
		
		this.looping = looping;
	}
	
	/**
	 * Constructs a RotatingAnimation based on local variables.
	 * 
	 * Creates an entirely new RotatingAnimation each time, to avoid 
	 * interference. 
	 *  
	 * @return A new Animation instance
	 */
	public RotatingAnimation constructAnimation() {
		
		RotatingAnimation animation = new RotatingAnimation(new SpriteSheet(this.image.copy(), this.tw, this.th), this.duration);
		
		animation.setLooping(this.looping);
		
		return animation;
	}
}
