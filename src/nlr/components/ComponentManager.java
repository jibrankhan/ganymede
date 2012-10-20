package nlr.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public strictfp final class ComponentManager {

	private List<Component> componentsToUpdate;
	private List<ComponentRenderable> componentsToRender;
	
	private Map<Long, Component> componentsLookup;
	
	private Queue<Component> componentsToAdd;
	private Queue<Component> componentsToRemove;
	
	private SortedSet<Long> ids;
	
	private List<ComponentAddedEventListener> componentAddedEventListeners;
	private List<ComponentDestroyedEventListener> componentDestroyedEventListeners;
	
	public ComponentManager() {
		
		super();
		
		this.componentsToUpdate = new ArrayList<Component>();
		this.componentsToRender = new ArrayList<ComponentRenderable>();
		
		this.componentsLookup = new ConcurrentHashMap<Long, Component>();
		
		this.componentsToAdd = new ConcurrentLinkedQueue<Component>();
		this.componentsToRemove = new ConcurrentLinkedQueue<Component>();
		
		this.ids = new TreeSet<Long>();
		
		this.componentAddedEventListeners = new ArrayList<ComponentAddedEventListener>();
		this.componentDestroyedEventListeners = new ArrayList<ComponentDestroyedEventListener>();
	}
	
	public strictfp void init(GameContainer gameContainer) {
		
		this.componentsToUpdate.clear();
		this.componentsToRender.clear();
		
		this.componentsLookup.clear();
		
		this.componentsToRemove.clear();
	}
	
	public strictfp void update(GameContainer gameContainer, int delta) throws SlickException {
		
		this.addComponents();
		
		for (Component i : this.componentsToUpdate) {
			
			if (i.isAlive()) {
				
				i.update(gameContainer, delta);
			}
			else {
				
				this.removeComponent(i);
			}
		}
		
		this.removeComponents();
		
		this.depthSort();
	}
	
	public strictfp void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		for (ComponentRenderable i : this.componentsToRender) {
			
			if (i.isAlive()) {
				
				i.render(gameContainer, graphics);
			}
		}
	}
	
	public strictfp void destroy() {
		
		for (Component i : this.componentsToUpdate) {
			
			this.removeComponent(i);
		}
		
		this.removeComponents();
	}
	
	public strictfp Component getComponent(long id) {
		
		return this.componentsLookup.get(id);
	}
	
	@SuppressWarnings("unchecked")
	public strictfp <T> List<T> getComponents(Class<T> type) {
		
		ArrayList<T> result = new ArrayList<T>();
		
		for (Component i : this.componentsToUpdate) { 
			
			if ((i.isInitialized()) && (i.isAlive())) {
				
				if (type.isInstance(i)) { 
					
					result.add((T)i);
				}
			}
		}
		
		return result;
	}
	
	public strictfp long takeId() {
		
		long j = 0;
		
		for (long i : this.ids) {
			
			if (i > j + 1) {
				
				break;
			}
			
			j = i;
		}
		
		long k = j + 1;
		
		this.ids.add(k);
		
		return k;
	}
	
	public strictfp void addComponent(Component component) {
		
		if (this.componentsLookup.containsKey(component.getId())) {
			
			System.out.println("Warning! Id " + Long.toString(component.getId()) + " already in use. ");
		}
		
		this.componentsToAdd.add(component);
	}
	
	public strictfp void removeComponent(Component component) {
		
		if (!this.componentsLookup.containsKey(component.getId())) {
			
			System.out.println("Warning! Id " + Long.toString(component.getId()) + " not in components list. ");
		}
		
		this.componentsToRemove.add(component);
	}
	
	public strictfp void addComponentAddedEventListener(ComponentAddedEventListener listener) {
		
		if (!this.componentAddedEventListeners.contains(listener)) {
			
			this.componentAddedEventListeners.add(listener);
		}
	}
	
	public strictfp void removeComponentAddedEventListener(ComponentAddedEventListener listener) {
		
		this.componentAddedEventListeners.remove(listener);
	}
	
	public strictfp void addComponentDestroyedEventListener(ComponentDestroyedEventListener listener) {
		
		if (!this.componentDestroyedEventListeners.contains(listener)) {
			
			this.componentDestroyedEventListeners.add(listener);
		}
	}
	
	public strictfp void removeComponentDestroyedEventListener(ComponentDestroyedEventListener listener) {
		
		this.componentDestroyedEventListeners.remove(listener);
	}
	
	private strictfp void triggerComponentAddedEvent(Component component) {
		
		for (ComponentAddedEventListener i : new ArrayList<ComponentAddedEventListener>(this.componentAddedEventListeners)) {
			
			i.onComponentAdded(component);
		}
	}
	
	private strictfp void triggerComponentDestroyedEvent(Component component) {
		
		for (ComponentDestroyedEventListener i : new ArrayList<ComponentDestroyedEventListener>(this.componentDestroyedEventListeners)) {
			
			i.onComponentDestroyed(component);
		}
	}
	
	private strictfp void addComponents() throws SlickException {
		
		while (!this.componentsToAdd.isEmpty()) {
			
			Component component = componentsToAdd.remove();
			
			this.componentsToUpdate.add(component);
			
			this.componentsLookup.put(component.getId(), component);
			
			if (component instanceof ComponentRenderable) {
				
				this.componentsToRender.add((ComponentRenderable) component);
			}
			
			component.init(null);
			
			this.triggerComponentAddedEvent(component);
		}
	}
	
	private strictfp void removeComponents() {
		
		while (!this.componentsToRemove.isEmpty()) {
			
			Component component = componentsToRemove.remove();
			
			this.componentsToUpdate.remove(component);
			
			this.componentsLookup.remove(component.getId());
			
			if (component instanceof ComponentRenderable) {
				
				this.componentsToRender.remove((ComponentRenderable)component);
			}
			
			this.ids.remove(component.getId());
			
			this.triggerComponentDestroyedEvent(component);
		}
	}
	
	private strictfp void depthSort() {
		
		Collections.sort(this.componentsToRender, ComponentManager.depthComparator);
	}
	
	private static final Comparator<ComponentRenderable> depthComparator = new Comparator<ComponentRenderable>() {
		
		@Override
	    public int compare(ComponentRenderable a, ComponentRenderable b) {
			
			if (a.getDepth() > b.getDepth()) {
				
				return -1;
			}
			else {
				
				if (a.getDepth() == b.getDepth()) {
					
					return 0;
				}
				else {
					
					return 1;
				}
			}
		}
	};
}
