package nlr.ui;

import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;

public strictfp class AbstractUIContainerComponent extends AbstractUIComponent implements UIContainer, KeyListener, MouseListener {
	
	private UniScale padding;
	
	private Set<UIComponent> children;

	public AbstractUIContainerComponent(UniScale position, UniScale area, int z, UniScale padding) {
		
		super(position, area, z);
		
		this.padding = padding;
		
		this.children = new HashSet<UIComponent>();
	}
	
	public AbstractUIContainerComponent(UniScale position, UniScale area, UniScale padding) {
		
		this(position, area, 0, padding);
	}
	
	public AbstractUIContainerComponent(UniScale position, UniScale area, int z) {
		
		this(position, area, z, new UniScale());
	}
	
	public AbstractUIContainerComponent(UniScale position, UniScale area) {
		
		this(position, area, 0, new UniScale());
	}
	
	@Override
	public float getInnerX() {
		
		return this.getX() + this.padding.getX();
	}

	@Override
	public float getInnerY() {
		
		return this.getY() + this.padding.getY();
	}

	@Override
	public float getInnerWidth() {
		
		return this.getWidth() - 2f * this.padding.getX();
	}

	@Override
	public float getInnerHeight() {
		
		return this.getHeight() - 2f * this.padding.getY();
	}

	@Override
	public void update(GameContainer gameContainer, int delta) {
		
		super.update(gameContainer, delta);
		
		for (UIComponent i : this.children) {
			
			i.update(gameContainer, delta);
		}
	}

	@Override
	public void render(GameContainer gameContainer, Graphics graphics) {
		
		super.render(gameContainer, graphics);
		
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
		
		component.setContainer(null);
		
		this.children.remove(component);	
	}

	@Override
	public void removeAll() {
		
		for (UIComponent component : this.children) {
			
			component.setContainer(null);
		}
		
		this.children.clear();
	}
}
