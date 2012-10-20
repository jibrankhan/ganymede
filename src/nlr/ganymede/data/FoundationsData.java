package nlr.ganymede.data;

public final class FoundationsData {

	private int id;
	
	private int width;
	private int length;
	
	public int getId() {
		return id;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getLength() {
		return length;
	}

	public FoundationsData(int id, int width, int length) {
		
		super();
		
		this.id = id;
		this.width = width;
		this.length = length;
	}
}
