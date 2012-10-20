package nlr.ganymede.data;

public final class RaceData {

	private int id;
	
	private TextData win;
	private TextData lose;
	
	private int idHq;
	
	public int getId() {
		return id;
	}
	
	public TextData getWin() {
		return win;
	}
	
	public TextData getLose() {
		return lose;
	}
	
	public int getIdHq() {
		return idHq;
	}

	public RaceData(int id, TextData win, TextData lose, int idHq) {
		
		super();
		
		this.id = id;
		
		this.win = win;
		this.lose = lose;
		
		this.idHq = idHq;
	}
}
