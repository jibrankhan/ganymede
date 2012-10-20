package nlr.rendering;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.Sys;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;

public strictfp final class RotatingAnimation implements Renderable {

	private List<Frame> frames; 
	
	private int currentFrame = -1;
	private long nextChange = 0;
	private boolean stopped = false;
	private long timeLeft;
	private float speed = 1.0f;
	private int stopAt = -2;
	private long lastUpdate;
	private boolean firstUpdate = true;
	private boolean autoUpdate = true;
	private int direction = 1;
	private boolean pingPong;
	private boolean loop = true;
	
	private SpriteSheet spriteSheet = null;
	
	public RotatingAnimation() {
		
		this(true);
	}
	
	public RotatingAnimation(boolean autoUpdate) {
		
		super();
		
		this.autoUpdate = autoUpdate;
	}
	
	public RotatingAnimation(SpriteSheet spriteSheet, int duration) {
		
		super();
		
		this.spriteSheet = spriteSheet;
		
		this.frames = new ArrayList<Frame>();
		
		for (int y = 0; y < spriteSheet.getVerticalCount(); y++) {
			
			Animation animation = new Animation(spriteSheet, 0, y, spriteSheet.getHorizontalCount() - 1, y, true, 1, false);
			
			this.frames.add(new Frame(animation, duration));
		}
		
		this.setCurrentFrame(0);
	}
	
	public void setAutoUpdate(boolean auto) {
		this.autoUpdate = auto;
	}
	
	public void setPingPong(boolean pingPong) {
		this.pingPong = pingPong;
	}
	
	public boolean isStopped() {
		return stopped;
	}
	
	public void setSpeed(float spd) {
		if (spd > 0) {
			// Adjust nextChange
			nextChange = (long) (nextChange * speed / spd);

			speed = spd;
		} 
	}
	
	public float getSpeed() {
	   return speed;
	}
	
	public void stop() {
		if (frames.size() == 0) {
			return;
		}
		timeLeft = nextChange;
		stopped = true;
	}
	
	public void start() {
		if (!stopped) {
			return;
		}
		if (frames.size() == 0) {
			return;
		}
		stopped = false;
		nextChange = timeLeft;
	}
	
	public void restart() {
		
		if (frames.size() == 0) {
			return;
		}
		
		stopped = false;
		currentFrame = 0;
		nextChange = (int) (((Frame) frames.get(0)).duration / speed);
		firstUpdate = true;
		lastUpdate = 0;
	}
	
	public void draw(float angle) {
		draw(0, 0, angle);
	}
	
	@Override
	public void draw(float x, float y) {
		this.draw(x, x, 0f);
	}
	
	public void draw(float x, float y, float angle) {
		draw(x, y, getWidth(), getHeight(), angle);
	}
	
	public void draw(float x,float y, Color filter, float angle) {
		draw(x, y, getWidth(), getHeight(), filter, angle);
	}
	
	public void draw(float x, float y, float width, float height, float angle) {
		draw(x, y, width, height, Color.white, angle);
	}
	
	public void draw(float x, float y, float width, float height, Color col, float angle) {
		
		if (frames.size() == 0) {
			return;
		}
		
		if (autoUpdate) {
			long now = getTime();
			long delta = now - lastUpdate;
			if (firstUpdate) {
				delta = 0;
				firstUpdate = false;
			}
			lastUpdate = now;
			nextFrame(delta);
		}
		
		Frame frame = (Frame) frames.get(currentFrame);
		
		frame.animation.setCurrentFrame(this.getFrame(angle));
		frame.animation.draw(x, y, width, height, col);
	}
	
	public int getWidth() {
		if (currentFrame == -1) { 
			return 0;
		}
		else {
			return ((Frame) frames.get(currentFrame)).animation.getWidth();
		}
	}
	
	public int getHeight() {
		if (currentFrame == -1) { 
			return 0;
		}
		else {
			return ((Frame) frames.get(currentFrame)).animation.getHeight();
		}
	}
	
	public void drawFlash(float x, float y, float width, float height, float angle) {
		drawFlash(x, y, width, height, Color.white, angle);
	}
	
	public void drawFlash(float x, float y, float width, float height, Color col, float angle) {
		if (frames.size() == 0) {
			return;
		}
		
		if (autoUpdate) {
			long now = getTime();
			long delta = now - lastUpdate;
			if (firstUpdate) {
				delta = 0;
				firstUpdate = false;
			}
			lastUpdate = now;
			nextFrame(delta);
		}
		
		Frame frame = (Frame) frames.get(currentFrame);
		
		frame.animation.setCurrentFrame(this.getFrame(angle));
		frame.animation.drawFlash(x, y, width, height, col);
	}
	
	public void updateNoDraw() {
		
		long now = getTime();
		long delta = now - lastUpdate;
		
		if (firstUpdate) {
			delta = 0;
			firstUpdate = false;
		}
		
		lastUpdate = now;
		nextFrame(delta);
	}
	
	/*public void update(long delta) {
		nextFrame(delta);
	}*/
	
	public int getFrame() {
		return currentFrame;
	}
	
	public void setCurrentFrame(int index) {
		currentFrame = index;
	}
	
	public int getFrameCount() {
		return frames.size();
	}
	
	public Animation getFrame(int index) {
		return this.frames.get(index).animation;
	}
	
	public Animation getCurrentFrame() {
		return this.frames.get(this.currentFrame).animation; 
	}
	
	/**
	 * Check if we need to move to the next frame
	 * 
	 * @param delta The amount of time thats passed since last update
	 */
	private void nextFrame(long delta) {
		if (stopped) {
			return;
		}
		if (frames.size() == 0) {
			return;
		}
		
		nextChange -= delta;
		
		while (nextChange < 0 && (!stopped)) {
			if (currentFrame == stopAt) {
				stopped = true;
				break;
			}
			if ((currentFrame == frames.size() - 1) && (!loop)) {
	            stopped = true; 
				break;
			}
			currentFrame = (currentFrame + direction) % frames.size();
			
			if (pingPong) {
				if (currentFrame <= 0) {
					currentFrame = 0;
					direction = 1;
				}
				if (currentFrame >= frames.size()-1) {
					currentFrame = frames.size()-1;
					direction = -1;
				}
			}
			int realDuration = (int) (((Frame) frames.get(currentFrame)).duration / speed);
			nextChange = nextChange + realDuration;
		}
	}
	
	/**
	 * Indicate if this animation should loop or stop at the last frame
	 * 
	 * @param loop True if this animation should loop (true = default)
	 */
	public void setLooping(boolean loop) {
		this.loop = loop;
	}
	
	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	private long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	/**
	 * Indicate the animation should stop when it reaches the specified
	 * frame index (note, not frame number but index in the animation
	 * 
	 * @param frameIndex The index of the frame to stop at
	 */
	public void stopAt(int frameIndex) {
		stopAt = frameIndex; 
	}
	
	/**
	 * Get the duration of a particular frame
	 * 
	 * @param index The index of the given frame
	 * @return The duration in (ms) of the given frame
	 */
	public int getDuration(int index) {
		return ((Frame) frames.get(index)).duration;
	}
	
	/**
	 * Set the duration of the given frame
	 * 
	 * @param index The index of the given frame
	 * @param duration The duration in (ms) for the given frame
	 */
	public void setDuration(int index, int duration) {
		((Frame) frames.get(index)).duration = duration;
	}
	
	/**
	 * Get the durations of all the frames in this animation
	 * 
	 * @return The durations of all the frames in this animation
	 */
	public int[] getDurations() {
		
		int[] durations = new int[frames.size()];
		
		for (int i = 0; i < frames.size(); i++) {
			durations[i] = getDuration(i);
		}
		
		return durations;
	}
	
	public String toString() {
		
		String res = "[Animation (" + frames.size() + ") ";
		
		for (int i = 0; i < frames.size(); i++) {
			Frame frame = (Frame) frames.get(i);
			res += frame.duration + ",";
		}
		
		res += "]";
		
		return res;
	}
	
	public RotatingAnimation copy() {
		
		RotatingAnimation copy = new RotatingAnimation();
		
		copy.spriteSheet = spriteSheet;
		copy.frames = frames;
		copy.autoUpdate = autoUpdate;
		copy.direction = direction;
		copy.loop = loop;
		copy.pingPong = pingPong;
		copy.speed = speed;
		
		return copy;
	}
	
	private int getFrame(float angle) {
		return (int) Math.round(angle * (this.getCurrentFrame().getFrameCount() - 1) / 360f);
	}
	
	private class Frame {
		
		public Animation animation;
		public int duration;
		
		public Frame(Animation animation, int duration) {
			
			super();
			
			this.animation = animation;
			this.duration = duration;
		}
	}
}
