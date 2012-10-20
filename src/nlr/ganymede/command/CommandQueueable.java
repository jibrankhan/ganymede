package nlr.ganymede.command;

public strictfp abstract class CommandQueueable extends CommandEntity {

	private static final long serialVersionUID = -5673617110287017472L;
	
	private boolean shouldClear;
	
	public strictfp boolean shouldClear() {
		return shouldClear;
	}
	
	public CommandQueueable(long entityId, boolean shouldClear) {
		
		super(entityId);
		
		this.shouldClear = shouldClear;
	}
}
