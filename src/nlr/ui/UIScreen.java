package nlr.ui;

import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public strictfp class UIScreen implements UIContainer {

	private UniScale padding;
	
	private Set<UIComponent> children;
	
	private float w;
	private float h;
	
	@Override
	public boolean isEnabled() {
		
		return true;
	}
	
	public UIScreen(UniScale padding) {
		
		super();
		
		this.padding = padding;
		
		this.children = new HashSet<UIComponent>();
	}
	
	public void init(GameContainer gameContainer) {
		
		this.w = gameContainer.getWidth();
		this.h = gameContainer.getHeight();
		
		for (UIComponent i : this.children) {
			
			i.init(gameContainer);
		}
	}

	public void update(GameContainer gameContainer, int delta) {
		
		for (UIComponent i : this.children) {
			
			i.update(gameContainer, delta);
		}
	}
	
	public void render(GameContainer gameContainer, Graphics graphics) {
		
		for (UIComponent i : this.children) {
			
			i.render(gameContainer, graphics);
		}
	}

	@Override
	public void add(UIComponent component) {
		
		this.children.add(component);
		
		component.setContainer(this);
	}

	@Override
	public void remove(UIComponent component) {
		
		this.children.remove(component);
	}

	@Override
	public void removeAll() {
		
		this.children.clear();
	}

	@Override
	public float getInnerX() {
		
		return this.padding.getX();
	}

	@Override
	public float getInnerY() {
		
		return this.padding.getY();
	}

	@Override
	public float getInnerWidth() {
		
		return this.w - 2f * this.padding.getX();
	}

	@Override
	public float getInnerHeight() {
		
		return this.h - 2f * this.padding.getY();
	}
}
