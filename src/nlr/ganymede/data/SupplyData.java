package nlr.ganymede.data;

public final class SupplyData {

	private int id;
	
	private int armourId;
	
	private int value;
	private int supply;
	
	private int iceCost;
	private int mineralCost;
	private int metalCost;
	
	public int getId() {
		return id;
	}
	
	public int getArmourId() {
		return armourId;
	}

	public int getValue() {
		return value;
	}

	public int getSupply() {
		return supply;
	}

	public int getIceCost() {
		return iceCost;
	}

	public int getMineralCost() {
		return mineralCost;
	}

	public int getMetalCost() {
		return metalCost;
	}

	public SupplyData(int id, int armourId, int value, int supply, int iceCost, int mineralCost, int metalCost) {
		
		super();
		
		this.id = id;
		
		this.armourId = armourId;
		
		this.value = value;
		this.supply = supply;
		
		this.iceCost = iceCost;
		this.mineralCost = mineralCost;
		this.metalCost = metalCost;
	}
}
