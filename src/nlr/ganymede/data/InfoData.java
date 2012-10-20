package nlr.ganymede.data;

public final class InfoData {

	private int id;
	
	private TextData name;
	private TextData description;
	private TextData acronym;
	
	public int getId() {
		return id;
	}
	
	public TextData getName() {
		return name;
	}
	
	public TextData getDescription() {
		return description;
	}
	
	public TextData getAcronym() {
		return acronym;
	}

	public InfoData(int id, TextData name, TextData description,
			TextData acronym) {
		
		super();
		
		this.id = id;
		this.name = name;
		this.description = description;
		this.acronym = acronym;
	}
}
