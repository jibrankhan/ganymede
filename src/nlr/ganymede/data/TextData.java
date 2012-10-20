package nlr.ganymede.data;

public final class TextData {

	private Integer id;
	private String english;
	
	public Integer getId() {
		return id;
	}

	public String getEnglish() {
		return english;
	}

	public TextData(Integer id, String english) {
		
		super();
		
		this.id = id;
		this.english = english;
	}
}
