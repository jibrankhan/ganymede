package nlr.ganymede.data;

public final class HotkeyData {

	private int id;
	
	private int key;
	private int entityId;
	
	public int getId() {
		return id;
	}
	
	public int getKey() {
		return key;
	}
	
	public int getEntityId() {
		return entityId;
	}

	public HotkeyData(int id, int key, int entityId) {
		
		super();
		
		this.id = id;
		
		this.key = key;
		this.entityId = entityId;
	}
}
