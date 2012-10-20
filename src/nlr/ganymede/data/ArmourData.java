package nlr.ganymede.data;

public final class ArmourData {

	private int id;
	
	private TextData name;
	
	private int resistancePierce;
	private int resistanceHeat;
	private int resistanceImpact;
	
	public int getId() {
		return id;
	}
	
	public TextData getName() {
		return name;
	}
	
	public int getResistancePierce() {
		return resistancePierce;
	}
	
	public int getResistanceHeat() {
		return resistanceHeat;
	}
	
	public int getResistanceImpact() {
		return resistanceImpact;
	}

	public ArmourData(int id, TextData name, int resistancePierce,
			int resistanceHeat, int resistanceImpact) {
		
		super();
		
		this.id = id;
		this.name = name;
		this.resistancePierce = resistancePierce;
		this.resistanceHeat = resistanceHeat;
		this.resistanceImpact = resistanceImpact;
	}
}
