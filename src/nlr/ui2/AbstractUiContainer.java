package nlr.ui2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import nlr.ui2.layout.Layout;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public strictfp abstract class AbstractUiContainer implements UiContainer {

	private Layout layout;
	
	private List<UiComponent> components;
	
	private boolean isValidated;
	
	@Override
	public List<UiComponent> getComponents() {
		
		return this.components; 
	}
	
	public AbstractUiContainer(Layout layout) {
		
		super();
		
		this.layout = layout;
		
		this.components = new ArrayList<UiComponent>();
		
		this.isValidated = false;
	}
	
	@Override
	public void init(GameContainer gameContainer) {
		
		for (UiComponent i : this.components) {
			
			i.init(gameContainer);
		}
	}

	@Override
	public void update(GameContainer gameContainer, int delta) {
		
		for (UiComponent i : this.components) {
			
			i.update(gameContainer, delta);
		}
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) {
		
		if (!this.isValidated) {
			
			this.validate();
		}
		
		for (UiComponent i : this.components) {
			
			i.render(gameContainer, graphics);
		}
	}
	
	@Override
	public void validate() {
		
		for (UiComponent i : this.components) {
			
			i.validate();
		}
		
		this.layout.doLayout(this);
		
		this.isValidated = true;
	}

	@Override
	public void invalidate() {
		
		this.isValidated = false;
	}

	@Override
	public void add(UiComponent component) {
		
		this.components.add(component);
	}

	@Override
	public void remove(UiComponent component) {
		
		this.components.remove(component);
	}

	@Override
	public void removeAll() {
		
		this.components.clear();
	}
}
