package nlr.ganymede.command;

public strictfp final class CommandAttack extends CommandQueueable {
	
	private static final long serialVersionUID = -3298943609064422079L;
	
	private long entityIdTarget;
	
	public strictfp long getEntityIdTarget() {
		
		return entityIdTarget;
	}

	public CommandAttack(long entityId, boolean shouldClear, long entityIdTarget) {
		
		super(entityId, shouldClear);
		
		this.entityIdTarget = entityIdTarget;
	}
}
