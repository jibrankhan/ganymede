package nlr.ganymede.command;

public strictfp abstract class CommandEntity extends Command {
	
	private static final long serialVersionUID = -7131235897477247685L;

	private long entityId;
	
	public strictfp long getEntityId() {
		
		return entityId;
	}

	public CommandEntity(long entityId) {
		
		super();
		
		this.entityId = entityId;
	}
}
