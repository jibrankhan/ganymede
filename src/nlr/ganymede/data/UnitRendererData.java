package nlr.ganymede.data;

public final class UnitRendererData {

	private int id;
	
	private RotatingSpriteData rotatingSpriteDataIdle;
	private RotatingSpriteData rotatingSpriteDataMoving;
	
	public int getId() {
		return id;
	}
	
	public RotatingSpriteData getRotatingSpriteDataIdle() {
		return rotatingSpriteDataIdle;
	}
	
	public RotatingSpriteData getRotatingSpriteDataMoving() {
		return rotatingSpriteDataMoving;
	}

	public UnitRendererData(
			int id,
			RotatingSpriteData rotatingSpriteDataIdle,
			RotatingSpriteData rotatingSpriteDataMoving) {
		
		super();
		
		this.id = id;
		
		this.rotatingSpriteDataIdle = rotatingSpriteDataIdle;
		this.rotatingSpriteDataMoving = rotatingSpriteDataMoving;
	}
}
