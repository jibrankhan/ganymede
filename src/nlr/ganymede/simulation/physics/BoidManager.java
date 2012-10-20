package nlr.ganymede.simulation.physics;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import nlr.components.BasicComponent;
import nlr.components.ComponentManager;
import nlr.ganymede.simulation.GanymedeMap;
import nlr.physics.collisions.CollisionManager;

public strictfp final class BoidManager extends BasicComponent {
	
	private ComponentManager componentManager;
	private GanymedeMap map;
	
	private CollisionManager collisionManager;

	public BoidManager(long id, ComponentManager componentManager, GanymedeMap map) {
		
		super(id);
		
		this.componentManager = componentManager;
		this.map = map;
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.collisionManager = new CollisionManager(
				this.componentManager.takeId(), 
				this.componentManager, 
				this.map.getWidthInTiles(), 
				this.map.getHeightInTiles(), 
				this.map.getTileWidth(), 
				this.map.getTileHeight());
		
		this.componentManager.addComponent(this.collisionManager);
	}
	
	@Override
	public void destroy() throws SlickException {
		
		super.destroy();
		
		this.collisionManager.destroy();
	}
}
