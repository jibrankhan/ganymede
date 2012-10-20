package nlr.ganymede.simulation.control;

import org.newdawn.slick.geom.Shape;

import nlr.components.BasicComponent;
import nlr.components.Entity;
import nlr.ganymede.data.TextData;
import nlr.ganymede.simulation.Affiliated;
import nlr.ganymede.simulation.Labelled;
import nlr.ganymede.simulation.fogOfWar.Visibility;
import nlr.ganymede.simulation.rendering.Renderer;

public class BasicSelectable extends BasicComponent implements Selectable {

	private Entity entity;
	
	private Affiliated affiliated;
	private Visibility visibility;
	private Labelled labelled;
	private Renderer renderer;
	
	@Override
	public Entity getEntity() {
		
		return this.entity;
	}
	
	@Override
	public int getFaction() {
		
		return this.affiliated.getFaction();
	}
	
	@Override
	public int getEntityDataId() {
		
		return this.labelled.getEntityDataId();
	}

	@Override
	public TextData getName() {
		
		return this.labelled.getName();
	}

	@Override
	public TextData getDescription() {
		
		return this.labelled.getDescription();
	}

	@Override
	public TextData getAcronym() {
		
		return this.labelled.getAcronym();
	}
	
	@Override
	public Shape getSelectionArea() {
		
		return this.renderer.getRendererBoundingShape();
	}
	
	public BasicSelectable(
			long id, 
			Entity entity, 
			Affiliated affiliated, 
			Visibility visibility, 
			Labelled labelled, 
			Renderer renderer) {
		
		super(id);
		
		this.entity = entity;
		
		this.affiliated = affiliated;
		this.visibility = visibility;
		this.labelled = labelled;
		this.renderer = renderer;
	}
	
	@Override
	public boolean isVisible(int faction) {
		
		return this.visibility.isVisible(faction);
	}
}
