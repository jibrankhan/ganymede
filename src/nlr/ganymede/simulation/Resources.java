package nlr.ganymede.simulation;

public strictfp class Resources {
	
	private int ice;
	private int minerals;
	private int metal;
	
	public int getIce() {
		return ice;
	}
	
	public int getMinerals() {
		return minerals;
	}
	
	public int getMetal() {
		return metal;
	}

	public Resources(int ice, int minerals, int metal) {
		
		super();
		
		this.ice = ice;
		this.minerals = minerals;
		this.metal = metal;
	}
	
	public Resources() {
		
		this(0, 0, 0);
	}
	
	public void zero() {
		
		this.ice = 0;
		this.minerals = 0;
		this.metal = 0;
	}
	
	public Resources add(int ice, int minerals, int metal) {
		
		this.ice += ice;
		this.minerals += minerals;
		this.metal += metal;
		
		return this;
	}
	
	public Resources add(Resources resources) {
		
		return this.add(resources.getIce(), resources.getMinerals(), resources.getMetal());
	}
	
	public Resources sub(int ice, int minerals, int metal) {
		
		this.ice -= ice;
		this.minerals -= minerals;
		this.metal -= metal;
		
		return this;
	}
	
	public Resources sub(Resources resources) {
		
		return this.sub(resources.getIce(), resources.getMinerals(), resources.getMetal());
	}
	
	public Resources copy() {
		
		return new Resources(this.ice, this.minerals, this.metal);
	}
}
