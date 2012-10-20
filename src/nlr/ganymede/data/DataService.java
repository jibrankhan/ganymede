package nlr.ganymede.data;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public interface DataService {

	ArmourData getArmourData(int id) throws SlickException;
	
	BoidData getBoidData(int id) throws SlickException;
	
	InfoData getInfoData(int id) throws SlickException;
	
	ArrayList<Integer> getEntityIdsForHotkey(int key);
	
	FoundationsData getFoundationsData(int id) throws SlickException;
	
	HitPointsData getHitPointsData(int id) throws SlickException;
	
	Image getImage(int id) throws SlickException;
	
	LosData getLosData(int id) throws SlickException;
	
	MilitaryUnitData getMilitaryUnitData(int id) throws SlickException;
	
	MinerData getMinerData(int id) throws SlickException;
	
	UnitRendererData getUnitRendererData(int id);
	
	RaceData getRaceData(int id);
	
	RadarProviderData getRadarProviderData(int id) throws SlickException;
	
	RadarVisibleData getRadarVisibleData(int id) throws SlickException;
	
	RequirementsData getRequirementsData(int id) throws SlickException;
	
	RotatingSpriteData getRotatingSpriteData(int id) throws SlickException;
	
	SpriteData getSpriteData(int id) throws SlickException;
	
	StructureRendererData getStructureRendererData(int id) throws SlickException;
	
	TextData getTextData(int id);
	
	TrainsUnitsData getTrainsUnitsData(int id) throws SlickException;
	
	TurretData getTurretData(int id) throws SlickException;
	
	SupplyData getSupplyData(int id) throws SlickException;
	
	UtilitiesData getUtilitiesData(int id) throws SlickException;
	
	WeaponData getWeaponData(int id) throws SlickException;
}
