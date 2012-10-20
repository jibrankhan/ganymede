package nlr.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;

public strictfp class AbstractUIComponent implements UIComponent, KeyListener, MouseListener {
	
	private UniScale position;
	private UniScale area;
	
	private int z;
	
	private boolean selfEnabled;
	
	private UIContainer parent;
	
	private boolean isMouseOver;
	private boolean isMousePressed;
	
	@Override
	public void setContainer(UIContainer parent) {
		
		this.parent = parent;
	}

	public AbstractUIComponent(UniScale position, UniScale area, int z) {
		
		super();
		
		this.position = position;
		this.area = area;
		
		this.z = z;
		
		this.selfEnabled = true;
		
		this.parent = null;
		
		this.isMouseOver = false;
		this.isMousePressed = false;
	}
	
	public AbstractUIComponent(UniScale position, UniScale area) {
		
		this(position, area, 0);
	}
	
	@Override
	public boolean isEnabled() {
		
		if (this.parent == null) {
			
			return this.selfEnabled;
		}
		else {
			
			if (this.parent.isEnabled()) {
				
				return this.selfEnabled;
			}
			else {
				
				return false;
			}
		}
	}
	
	@Override
	public void setEnabled(boolean isEnabled) {
		
		this.selfEnabled = isEnabled;
	}
	
	@Override
	public float getX() {
		
		if (this.parent == null) {
			
			return this.position.getX();
		}
		else {
			
			return this.parent.getInnerX() + this.position.getActualX(this.parent);
		}
	}
	
	@Override
	public float getY() {
		
		if (this.parent == null) {
			
			return this.position.getY();
		}
		else {
			
			return this.parent.getInnerY() + this.position.getActualY(this.parent);
		}
	}
	
	@Override
	public float getWidth() {
		
		if (this.parent == null) {
			
			return this.area.getX();
		}
		else {
			
			return this.area.getActualX(this.parent);
		}
	}
	
	@Override
	public float getHeight() {
		
		if (this.parent == null) {
			
			return this.area.getY();
		}
		else {
			
			return this.area.getActualY(this.parent);
		}
	}
	
	@Override
	public int getZ() {
		
		return this.z;
	}
	
	protected boolean isMouseOver() {
		
		return this.isMouseOver;
	}
	
	protected boolean isMousePressed() {
		
		if (this.isMouseOver) {
			
			return this.isMousePressed;
		}
		else {
			
			return false;
		}
	}
	
	@Override
	public void init(GameContainer gameContainer) {
		
		gameContainer.getInput().addKeyListener(this);
		gameContainer.getInput().addMouseListener(this);
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) {
		
	}

	@Override
	public void render(GameContainer gameContainer, Graphics graphics) {
		
	}
	
	protected void clicked() {
		
	}

	@Override
	public void inputEnded() {
		
	}

	@Override
	public void inputStarted() {
		
	}

	@Override
	public boolean isAcceptingInput() {
		
		return this.isEnabled();
	}

	@Override
	public void setInput(Input input) {
		
		input.addKeyListener(this);
		input.addMouseListener(this);
	}

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void mouseMoved(int oldX, int oldY, int newX, int newY) {
		
		boolean contained = 
				(newX >= this.getX()) && 
				(newY >= this.getY()) && 
				(newX <= this.getX() + this.getWidth()) && 
				(newY <= this.getY() + this.getHeight());
		
		this.isMouseOver = contained;
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		
		if (button == Input.MOUSE_LEFT_BUTTON) {
			
			this.isMousePressed = true;
		}
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		
		if (this.isMousePressed) {
			
			this.isMousePressed = false;
			
			if (this.isMouseOver) {
				
				this.clicked();
			}
		}
	}

	@Override
	public void mouseWheelMoved(int arg0) {
		
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		
	}
}
