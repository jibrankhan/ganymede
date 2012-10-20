package nlr.ganymede.command;

public final class CommandPlaceStructure extends Command {
	
	private static final long serialVersionUID = 1952167041525105011L;
	
	private int faction;
	
	private int x;
	private int y;
	
	private int structureId;
	
	public int getFaction() {
		return faction;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getStructureId() {
		return structureId;
	}

	public CommandPlaceStructure(int faction, int x, int y, int structureId) {
		
		super();
		
		this.faction = faction;
		
		this.x = x;
		this.y = y;
		
		this.structureId = structureId;
	}
}
