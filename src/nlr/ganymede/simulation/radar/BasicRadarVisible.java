package nlr.ganymede.simulation.radar;

import org.newdawn.slick.geom.Shape;

import nlr.components.BasicComponent;
import nlr.ganymede.data.RadarVisibleData;
import nlr.ganymede.simulation.Affiliated;
import nlr.ganymede.simulation.rendering.Renderer;

public strictfp final class BasicRadarVisible extends BasicComponent implements RadarVisible {

	private RadarVisibleData radarVisibleData;
	
	private Affiliated affiliated;
	private Renderer renderer;
	
	@Override
	public strictfp RadarBlipSize getRadarBlipSize() {
		
		return this.radarVisibleData.getLevel();
	}
	
	@Override
	public strictfp int getFaction() {
		
		return this.affiliated.getFaction();
	}
	
	@Override
	public Shape getRendererBoundingShape() {
		
		return this.renderer.getRendererBoundingShape();
	}
	
	public BasicRadarVisible(
			long id, 
			RadarVisibleData radarVisibleData,
			Affiliated affiliated, 
			Renderer renderer) {
		
		super(id);
		
		this.radarVisibleData = radarVisibleData;
		
		this.affiliated = affiliated;
		this.renderer = renderer;
	}
}
