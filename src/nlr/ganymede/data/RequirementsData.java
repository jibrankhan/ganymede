package nlr.ganymede.data;

import java.util.List;

public final class RequirementsData {

	private int id;
	
	private List<Integer> requirements;
	
	public int getId() {
		return id;
	}
	
	public List<Integer> getRequirements() {
		return requirements;
	}

	public RequirementsData(int id, List<Integer> requirements) {
		
		super();
		
		this.id = id;
		
		this.requirements = requirements;
	}
}
