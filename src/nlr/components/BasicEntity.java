package nlr.components;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;

public strictfp class BasicEntity extends BasicComponent implements Entity {

	private List<Component> components;
	
	@Override
	public List<Component> getComponents() {
		
		return new ArrayList<Component>(this.components);
	}
	
	public BasicEntity(long id) {
		
		super(id);
		
		this.components = new ArrayList<Component>();
	}
	
	@Override
	public strictfp void destroy() throws SlickException {
		
		super.destroy();
		
		for (Component i : this.components) {
			
			if (i.isAlive()) {
				
				i.destroy();
			}
		}
	}

	@Override
	public void add(Component component) {
		
		this.components.add(component);
	}

	@Override
	public void remove(Component component) {
		
		this.components.add(component);
	}

	@Override
	public <T> boolean is(Class<T> type) {
		
		for (Component i : this.components) {
			
			if (type.isInstance(i)) {
				
				return true;
			}
		}
		
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Class<T> type) throws SlickException {
		
		for (Component i : this.components) {
			
			if (type.isInstance(i)) {
				
				return (T)i;
			}
		}
		
		throw new SlickException("Entity [" + this.toString() + "] does not have aspect " + type.toString() + ". ");
	}
}
