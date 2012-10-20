package nlr.ganymede.data;

public strictfp final class MinerData {

	private int id;
	
	private int gatherInteral;
	
	private int iceRate;
	private int mineralRate;
	private int metalRate;
	
	private SpriteData spriteDataMining;
	
	public int getId() {
		return id;
	}
	
	public int getGatherInterval() {
		return gatherInteral;
	}
	
	public int getIceRate() {
		return iceRate;
	}
	
	public int getMineralRate() {
		return mineralRate;
	}
	
	public int getMetalRate() {
		return metalRate;
	}

	public SpriteData getSpriteDataMining() {
		return spriteDataMining;
	}

	public MinerData(
			int id, 
			int gatherInterval, 
			int iceRate, 
			int mineralRate,
			int metalRate, 
			SpriteData spriteDataMining) {
		
		super();
		
		this.id = id;
		
		this.gatherInteral = gatherInterval;
		
		this.iceRate = iceRate;
		this.mineralRate = mineralRate;
		this.metalRate = metalRate;
		
		this.spriteDataMining = spriteDataMining;
	}
}
