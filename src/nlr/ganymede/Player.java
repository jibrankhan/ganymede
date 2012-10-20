package nlr.ganymede;

public final class Player {

	private int id;
	private int faction;
	
	private String handle;

	public int getId() {
		return id;
	}
	
	public int getFaction() {
		return faction;
	}

	public String getHandle() {
		return handle;
	}

	public Player(int id, int faction, String handle) {
		
		super();
		
		this.id = id;
		this.faction = faction;
		
		this.handle = handle;
	}
}
