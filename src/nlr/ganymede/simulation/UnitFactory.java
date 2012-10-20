package nlr.ganymede.simulation;

import java.awt.Point;

import nlr.components.BasicEntity;
import nlr.components.ComponentManager;
import nlr.components.Entity;
import nlr.ganymede.PlayerService;
import nlr.ganymede.data.ArmourData;
import nlr.ganymede.data.BoidData;
import nlr.ganymede.data.DataService;
import nlr.ganymede.data.FoundationsData;
import nlr.ganymede.data.HitPointsData;
import nlr.ganymede.data.InfoData;
import nlr.ganymede.data.LosData;
import nlr.ganymede.data.MilitaryUnitData;
import nlr.ganymede.data.MinerData;
import nlr.ganymede.data.RadarProviderData;
import nlr.ganymede.data.RadarVisibleData;
import nlr.ganymede.data.StructureRendererData;
import nlr.ganymede.data.SupplyData;
import nlr.ganymede.data.TrainsUnitsData;
import nlr.ganymede.data.TurretData;
import nlr.ganymede.data.UnitRendererData;
import nlr.ganymede.data.UtilitiesData;
import nlr.ganymede.simulation.control.BasicController;
import nlr.ganymede.simulation.control.BasicSelectable;
import nlr.ganymede.simulation.fogOfWar.FogOfWar;
import nlr.ganymede.simulation.fogOfWar.StructureLos;
import nlr.ganymede.simulation.fogOfWar.TiledVisibility;
import nlr.ganymede.simulation.fogOfWar.UnitLos;
import nlr.ganymede.simulation.fogOfWar.Visibility;
import nlr.ganymede.simulation.health.BasicArmour;
import nlr.ganymede.simulation.health.BasicHitPoints;
import nlr.ganymede.simulation.health.HitPoints;
import nlr.ganymede.simulation.physics.BasicBoid;
import nlr.ganymede.simulation.physics.Boid;
import nlr.ganymede.simulation.projectiles.ProjectileManager;
import nlr.ganymede.simulation.radar.BasicRadarVisible;
import nlr.ganymede.simulation.radar.RadarProvider;
import nlr.ganymede.simulation.rendering.Renderer;
import nlr.ganymede.simulation.rendering.StructureRenderer;
import nlr.ganymede.simulation.rendering.UnitRenderer;
import nlr.ganymede.simulation.structures.BasicFoundations;
import nlr.ganymede.simulation.structures.BasicMiner;
import nlr.ganymede.simulation.structures.BasicUtilities;
import nlr.ganymede.simulation.structures.Foundations;
import nlr.ganymede.simulation.structures.StructureTrainsUnits;
import nlr.ganymede.simulation.structures.Utilities;
import nlr.ganymede.simulation.supply.BasicSupply;
import nlr.ganymede.simulation.targeting.FireController;
import nlr.ganymede.simulation.targeting.TargetManager;
import nlr.ganymede.simulation.targeting.Weapon;

import org.lwjgl.Sys;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public strictfp final class UnitFactory {

	private DataService dataService;
	private PlayerService playerService;
	private ComponentManager componentManager;
	private FactionManager factionManager;
	private ResourceManager resourceManager;
	private GanymedeMap map;
	private View view;
	private FogOfWar fogOfWar;
	private PowerManager powerManager;
	private TargetManager targetManager;
	private ProjectileManager projectileManager;
	private UnlocksManager unlocksManager;
	
	public UnitFactory(
			DataService dataService, 
			PlayerService playerService, 
			ComponentManager componentManager, 
			FactionManager factionManager, 
			ResourceManager resourceManager, 
			GanymedeMap ganymedeMap,
			View view, 
			FogOfWar fogOfWar, 
			PowerManager powerManager, 
			TargetManager targetManager, 
			ProjectileManager projectileManager, 
			UnlocksManager unlocksManager) {
		
		super();
		
		this.dataService = dataService;
		this.playerService = playerService;
		this.componentManager = componentManager;
		this.factionManager = factionManager;
		this.resourceManager = resourceManager;
		this.map = ganymedeMap;
		this.view = view;
		this.fogOfWar = fogOfWar;
		this.powerManager = powerManager;
		this.targetManager = targetManager;
		this.projectileManager = projectileManager;
		this.unlocksManager = unlocksManager;
	}
	
	public void createEntity(int id, int faction, int x, int y, boolean isBuilt) {
		
		Entity entity = new BasicEntity(this.componentManager.takeId());
		
		this.componentManager.addComponent(entity);
		
		// Info
		try {
			
			InfoData infoData = this.dataService.getInfoData(id);
			
			Info info = new Info(
					this.componentManager.takeId(), 
					infoData, 
					faction, 
					this.unlocksManager);
			
			entity.add(info);
			
			this.componentManager.addComponent(info);
		}
		catch (SlickException e) {
			
		}
		
		// Hit Points
		try {
			
			HitPointsData hitPointsData = this.dataService.getHitPointsData(id);
			
			BasicHitPoints basicHitPoints = new BasicHitPoints(
					this.componentManager.takeId(), 
					entity, 
					hitPointsData);
			
			entity.add(basicHitPoints);
			
			this.componentManager.addComponent(basicHitPoints);
		}
		catch (SlickException e) {
			
		}
		
		// Supply
		try {
			
			SupplyData supplyData = this.dataService.getSupplyData(id);
			
			Affiliated affiliated = entity.get(Affiliated.class);
			
			BasicSupply basicSupply = new BasicSupply(
					this.componentManager.takeId(), 
					this.factionManager, 
					supplyData, 
					affiliated);
			
			entity.add(basicSupply);
			
			this.componentManager.addComponent(basicSupply);
		}
		catch (SlickException e) {
			
		}
		
		// Armour
		try {
			
			SupplyData supplyData = this.dataService.getSupplyData(id);
			ArmourData armourData = this.dataService.getArmourData(supplyData.getArmourId());
			
			HitPoints hitPoints = entity.get(HitPoints.class);
			
			BasicArmour basicArmour = new BasicArmour(
					this.componentManager.takeId(), 
					this.componentManager, 
					armourData, 
					hitPoints);
			
			entity.add(basicArmour);
			
			this.componentManager.addComponent(basicArmour);
		}
		catch (SlickException e) {
			
		}
		
		// Foundations
		try {
			
			FoundationsData foundationsData = this.dataService.getFoundationsData(id);
			
			BasicFoundations basicFoundations = new BasicFoundations(
					this.componentManager.takeId(), 
					foundationsData, 
					this.map, 
					x, 
					y);
			
			entity.add(basicFoundations);
			
			this.componentManager.addComponent(basicFoundations);
		}
		catch (SlickException e) {
			
		}
		
		// Utilities
		try {
			
			UtilitiesData utilitiesData = this.dataService.getUtilitiesData(id);
			
			Affiliated affiliated = entity.get(Affiliated.class);
			Foundations foundations = entity.get(Foundations.class);
			HitPoints hitPoints = entity.get(HitPoints.class);
			
			BasicUtilities basicUtilities = new BasicUtilities(
					this.componentManager.takeId(), 
					this.map, 
					utilitiesData, 
					affiliated, 
					foundations, 
					hitPoints, 
					isBuilt);
			
			entity.add(basicUtilities);
			
			this.componentManager.addComponent(basicUtilities);
		}
		catch (SlickException e) {
			
		}
		
		// Boid
		try {
			
			BoidData boidData = this.dataService.getBoidData(id);
			
			BasicBoid basicBoid = new BasicBoid(
					this.componentManager.takeId(), 
					this.map.getTileCenter(x, y), 
					boidData, 
					this.map);
			
			entity.add(basicBoid);
			
			this.componentManager.addComponent(basicBoid);
		}
		catch (SlickException e) {
			
		}
		
		// Unit Los
		try {
			
			LosData losData = this.dataService.getLosData(id);
			
			Affiliated affiliated = entity.get(Affiliated.class);
			Boid boid = entity.get(Boid.class);
			
			UnitLos unitLos = new UnitLos(
					this.componentManager.takeId(), 
					losData, 
					affiliated, 
					boid);
			
			entity.add(unitLos);
			
			this.componentManager.addComponent(unitLos);
			
		} catch(SlickException e) {
			
		}
		
		// Structure Los
		try {
			
			LosData losData = this.dataService.getLosData(id);
			
			Affiliated affiliated = entity.get(Affiliated.class);
			Foundations foundations = entity.get(Foundations.class);
			Utilities utilities = entity.get(Utilities.class);
			
			StructureLos structureLos = new StructureLos(
					this.componentManager.takeId(), 
					losData, 
					affiliated, 
					foundations, 
					utilities);
			
			entity.add(structureLos);
			
			this.componentManager.addComponent(structureLos);
			
		} catch (SlickException e) {
			
		}
		
		// Visibility
		try {
			
			Tiled tiled = entity.get(Tiled.class);
			
			TiledVisibility tiledVisibility = new TiledVisibility(
					this.componentManager.takeId(), 
					this.fogOfWar, 
					tiled);
			
			entity.add(tiledVisibility);
			
			this.componentManager.addComponent(tiledVisibility);
		}
		catch (SlickException e) {
			
		}
		
		// Unit Renderer
		try {
			
			UnitRendererData unitRendererData = this.dataService.getUnitRendererData(id);
			
			Affiliated affiliated = entity.get(Affiliated.class);
			Boid boid = entity.get(Boid.class);
			Visibility visibility = entity.get(Visibility.class);
			
			UnitRenderer unitRenderer = new UnitRenderer(
					this.componentManager.takeId(), 
					this.playerService, 
					this.view, 
					unitRendererData, 
					affiliated, 
					boid, 
					visibility);
			
			entity.add(unitRenderer);
			
			this.componentManager.addComponent(unitRenderer);
		}
		catch (SlickException e) {
			
		}
		
		// Structure Renderer
		try {
			
			StructureRendererData structureRendererData = this.dataService.getStructureRendererData(id);
			
			Affiliated affiliated = entity.get(Affiliated.class);
			Foundations foundations = entity.get(Foundations.class);
			Utilities utilities = entity.get(Utilities.class);
			Visibility visibility = entity.get(Visibility.class);
			
			StructureRenderer structureRenderer = new StructureRenderer(
					this.componentManager.takeId(), 
					this.playerService, 
					structureRendererData, 
					affiliated, 
					foundations, 
					utilities, 
					visibility, 
					this.view, 
					this.map);
			
			entity.add(structureRenderer);
			
			this.componentManager.addComponent(structureRenderer);
		}
		catch (SlickException e) {
			
		}
		
		// Radar Provider
		try {
			
			RadarProviderData radarProviderData = this.dataService.getRadarProviderData(id);
			
			Affiliated affiliated = entity.get(Affiliated.class);
			Utilities utilities = entity.get(Utilities.class);
			
			RadarProvider radarProvider = new RadarProvider(
					this.componentManager.takeId(), 
					radarProviderData, 
					affiliated, 
					utilities);
			
			entity.add(radarProvider);
			
			this.componentManager.addComponent(radarProvider);
		}
		catch(SlickException e) {
			
		}
		
		// Radar Visibility
		try { 
			
			RadarVisibleData radarVisibleData = this.dataService.getRadarVisibleData(id);
			
			Affiliated affiliated = entity.get(Affiliated.class);
			Renderer renderer = entity.get(Renderer.class);
			
			BasicRadarVisible basicRadarVisible = new BasicRadarVisible(
					this.componentManager.takeId(), 
					radarVisibleData, 
					affiliated, 
					renderer);
			
			entity.add(basicRadarVisible);
			
			this.componentManager.addComponent(basicRadarVisible);
			
		} catch(SlickException e) {
			
		}
		
		// Military unit
		try {
			
			MilitaryUnitData militaryUnitData = this.dataService.getMilitaryUnitData(id);
			
			Affiliated affiliated = entity.get(Affiliated.class);
			UnitRenderer unitRenderer = entity.get(UnitRenderer.class);
			Boid boid = entity.get(Boid.class);
			
			Weapon weapon = new Weapon(
					this.componentManager.takeId(), 
					militaryUnitData.getWeaponData(), 
					this.map, 
					this.projectileManager, 
					affiliated);
			
			entity.add(weapon);
			
			this.componentManager.addComponent(weapon);
			
			MilitaryUnit militaryUnit = new MilitaryUnit(
					this.componentManager.takeId(), 
					militaryUnitData, 
					weapon, 
					unitRenderer);
			
			entity.add(militaryUnit);
			
			this.componentManager.addComponent(militaryUnit);
			
			FireController fireController = new FireController(
					this.componentManager.takeId(), 
					this.componentManager, 
					this.map, 
					boid, 
					weapon);
			
			entity.add(fireController);
			
			this.componentManager.addComponent(fireController);
		}
		catch (SlickException e) {
			
		}
		
		// Turret
		try {
			
			TurretData turretData = this.dataService.getTurretData(id);
			
			Affiliated affiliated = entity.get(Affiliated.class);
			Boid boid = entity.get(Boid.class);
			Visibility visibility = entity.get(Visibility.class);
			
			Weapon weapon = new Weapon(
					this.componentManager.takeId(), 
					turretData.getWeaponData(), 
					this.map, 
					this.projectileManager, 
					affiliated);
			
			entity.add(weapon);
			
			this.componentManager.addComponent(weapon);
			
			Turret turret = new Turret(
					this.componentManager.takeId(), 
					turretData, 
					this.playerService, 
					this.view, 
					affiliated, 
					boid, 
					visibility);
			
			entity.add(turret);
			
			this.componentManager.addComponent(turret);
			
			FireController fireController = new FireController(
					this.componentManager.takeId(), 
					this.componentManager, 
					this.map, 
					turret, 
					weapon);
			
			entity.add(fireController);
			
			this.componentManager.addComponent(fireController);
			
		} catch(SlickException e) {
			
		}
		
		// Trains Units
		try {
			
			TrainsUnitsData trainsUnitsData = this.dataService.getTrainsUnitsData(id);
			
			Affiliated affiliated = entity.get(Affiliated.class);
			Foundations foundations = entity.get(Foundations.class);
			
			StructureTrainsUnits structureTrainsUnits = new StructureTrainsUnits(
					this.componentManager.takeId(), 
					trainsUnitsData, 
					this.dataService, 
					this.resourceManager, 
					this.map, 
					affiliated, 
					foundations, 
					this);
			
			entity.add(structureTrainsUnits);
			
			this.componentManager.addComponent(structureTrainsUnits);
		}
		catch (SlickException e) {
			
		}
		
		// Miner
		try {
			
			MinerData minerData = this.dataService.getMinerData(id);
			
			Affiliated affiliated = entity.get(Affiliated.class);
			Foundations foundations = entity.get(Foundations.class);
			Utilities utilities = entity.get(Utilities.class);
			StructureRenderer structureRenderer = entity.get(StructureRenderer.class);
			
			BasicMiner basicMiner = new BasicMiner(
					this.componentManager.takeId(), 
					minerData, 
					this.resourceManager, 
					this.map, 
					affiliated, 
					foundations, 
					utilities);
			
			this.componentManager.addComponent(basicMiner);
			
			entity.add(basicMiner);
			
			MinerRenderer minerRenderer = new MinerRenderer(
					this.componentManager.takeId(), 
					minerData, 
					basicMiner, 
					structureRenderer);
			
			entity.add(minerRenderer);
			
			this.componentManager.addComponent(minerRenderer);
		}
		catch (SlickException e) {
			
		}
		
		// Controller
		BasicController basicController = new BasicController(this.componentManager.takeId());
		
		entity.add(basicController);
		
		this.componentManager.addComponent(basicController);
		
		// Selectable
		try {
			
			Affiliated affiliated = entity.get(Affiliated.class);
			Visibility visibility = entity.get(Visibility.class);
			Labelled labelled = entity.get(Labelled.class);
			Renderer renderer = entity.get(Renderer.class);
			
			BasicSelectable basicSelectable = new BasicSelectable(
					this.componentManager.takeId(), 
					entity, 
					affiliated, 
					visibility, 
					labelled, 
					renderer);
			
			entity.add(basicSelectable);
			
			this.componentManager.addComponent(basicSelectable);
		}
		catch (SlickException e) {
			
		}
	}
	
	public boolean canCreateStructure(int id, int faction, int tx, int ty) {
		
		try {
			
			FoundationsData foundationsData = this.dataService.getFoundationsData(id);
			UtilitiesData utilitiesData = this.dataService.getUtilitiesData(id);
			
			this.dataService.getStructureRendererData(id);
			
			int xx;
			int yy;
			
			boolean powered = !utilitiesData.isPowerRequired();
			
			boolean needsIce = false;
			boolean needsMinerals = false;
			boolean needsMetal = false;
			
			try {
				
				MinerData minerData = this.dataService.getMinerData(id);
				
				if (minerData.getIceRate() > 0) {
					
					needsIce = true;
				}
				
				if (minerData.getMineralRate() > 0) {
					
					needsMinerals = true;
				}
				
				if (minerData.getMetalRate() > 0) {
					
					needsMetal = true;
				}
			}
			catch (SlickException e) {
				
			}
			
			for (int x = 0; x < foundationsData.getWidth(); x++) {
				
				for (int y = 0; y < foundationsData.getLength(); y++) {
					
					xx = ty + x; 
					yy = tx + y;
					
					if (this.map.isSolid(xx, yy)) {
						
						return false;
					}
					
					if (this.map.isBuiltOn(xx, yy)) {
						
						return false;
					}
					
					if (!this.fogOfWar.isVisible(xx, yy, faction)) {
						
						return false;
					}
					
					if (!powered) {
						
						if (this.powerManager.isPowered(xx, yy, faction)) {
							
							powered = true;
						}
					}
					
					if (needsIce) {
						
						if (this.map.getIce(xx, yy) > 0) {
							
							needsIce = false;
						}
					}
					
					if (needsMinerals) {
						
						if (this.map.getMinerals(xx, yy) > 0) {
							
							needsMinerals = false;
						}
					}
					
					if (needsMetal) {
						
						if (this.map.getMetal(xx, yy) > 0) {
							
							needsMetal = false;
						}
					}
				}
			}
			
			if ((needsIce) && (needsMinerals) && (needsMetal)) {
				
				return false;
			}
			
			return powered;
			
		} catch (SlickException e) {
			
			return false;
		}
	}
}
