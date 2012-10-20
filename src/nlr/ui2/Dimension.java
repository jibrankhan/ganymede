package nlr.ui2;

public strictfp class Dimension {
	
	private float absolute;
	private float relative;
	
	public strictfp float getAbsolute() {
		
		return absolute;
	}
	
	public strictfp float getRelative() {
		
		return relative;
	}
	
	public Dimension(float absolute, float relative) {
		
		super();
		
		this.absolute = absolute;
		this.relative = relative;
	}
	
	public Dimension(float absolute) {
		
		this(absolute, 0f);
	}
	
	public Dimension(Dimension dimension) {
		
		this(dimension.getAbsolute(), dimension.getRelative());
	}
	
	public strictfp float get(float max) {
		
		return this.absolute + max * this.relative;
	}
}
