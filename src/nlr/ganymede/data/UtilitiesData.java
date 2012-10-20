package nlr.ganymede.data;

public final class UtilitiesData {

	private int id;
	
	private int buildTime;
	
	private boolean powerRequired;
	private boolean powerGenerating;
	
	private int powerTransferRadius;

	public int getId() {
		return id;
	}

	public int getBuildTime() {
		return buildTime;
	}

	public boolean isPowerRequired() {
		return powerRequired;
	}

	public boolean isPowerGenerating() {
		return powerGenerating;
	}

	public int getPowerTransferRadius() {
		return powerTransferRadius;
	}

	public UtilitiesData(int id, int buildTime, boolean powerRequired,
			boolean powerGenerating, int powerTransferRadius) {
		
		super();
		
		this.id = id;
		this.buildTime = buildTime;
		this.powerRequired = powerRequired;
		this.powerGenerating = powerGenerating;
		this.powerTransferRadius = powerTransferRadius;
	}
}
