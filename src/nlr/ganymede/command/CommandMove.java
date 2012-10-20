package nlr.ganymede.command;

public strictfp final class CommandMove extends CommandQueueable {

	private static final long serialVersionUID = -2325527379269990474L;
	
	private float x;
	private float y;

	public strictfp float getX() {
		return x;
	}

	public strictfp float getY() {
		return y;
	}

	public CommandMove(long entityId, boolean shouldClear, float x, float y) {
		
		super(entityId, shouldClear);
		
		this.x = x;
		this.y = y;
	}
}
