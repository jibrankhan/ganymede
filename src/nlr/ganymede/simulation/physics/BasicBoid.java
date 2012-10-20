package nlr.ganymede.simulation.physics;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import nlr.ganymede.data.BoidData;
import nlr.ganymede.simulation.GanymedeMap;
import nlr.physics.steering.BasicVehicleTwoAxes;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public strictfp final class BasicBoid extends BasicVehicleTwoAxes implements Boid {

	private BoidData boidData;
	
	private GanymedeMap map;
	
	private float z;
	
	private List<Point> tiles;
	
	@Override
	public boolean isGround() {
		
		return this.boidData.isGround();
	}

	@Override
	public boolean isAir() {
		
		return this.boidData.isAir();
	}

	@Override
	public float getZ() {
		
		return this.z;
	}

	@Override
	public List<Point> getTiles() {
		
		return this.tiles;
	}

	@Override
	public Shape getBoundingShape() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSteady() {
		
		return !this.isMoving();
	}
	
	public BasicBoid(long id, Vector2f initialPosition, BoidData boidData, GanymedeMap map) {
		
		super(id, initialPosition, boidData.getMass(), 1.5f, boidData.getForce(), boidData.getTurnSpeed(), boidData.getLateralForce());
		
		this.boidData = boidData;
		
		this.map = map;
		
		this.tiles = new ArrayList<Point>();
		
		this.tiles.add(new Point());
	}
	
	@Override
	public strictfp void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.updateTiles();
	}
	
	@Override
	public strictfp void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (this.hasMoved()) {
			
			this.updateTiles();
		}
	}
	
	private void updateTiles() {
		
		this.tiles.set(0, this.map.getNearestTile(this.getPosition()));
	}
}
