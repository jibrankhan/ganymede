package nlr.ganymede.data;

import java.util.List;

public final class TrainsUnitsData {

	private int id;
	
	private List<Integer> idsTrain;
	
	public int getId() {
		return id;
	}
	
	public List<Integer> getIdsTrain() {
		return idsTrain;
	}

	public TrainsUnitsData(int id, List<Integer> idsTrain) {
		
		super();
		
		this.id = id;
		
		this.idsTrain = idsTrain;
	}
}
