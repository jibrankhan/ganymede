package nlr.ganymede.data;

public final class StructureRendererData {

	private int id;
	
	private SpriteData spriteData;
	private SpriteData spriteDataBuilding;
	
	public int getId() {
		return id;
	}
	
	public SpriteData getSpriteData() {
		return spriteData;
	}
	
	public SpriteData getSpriteDataBuilding() {
		return spriteDataBuilding;
	}

	public StructureRendererData(int id, SpriteData spriteData, SpriteData spriteDataBuilding) {
		
		super();
		
		this.id = id;
		
		this.spriteData = spriteData;
		this.spriteDataBuilding = spriteDataBuilding;
	}
}
