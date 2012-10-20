package nlr.ganymede.data;

public final class HitPointsData {

	private int id;
	
	private int start;
	private int max;
	
	private int recharge;
	
	public int getId() {
		return id;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getMax() {
		return max;
	}
	
	public int getRecharge() {
		return recharge;
	}

	public HitPointsData(int id, int start, int max, int recharge) {
		
		super();
		
		this.id = id;
		
		this.start = start;
		this.max = max;
		
		this.recharge = recharge;
	}
}
