package nlr.ganymede.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import nlr.ganymede.simulation.projectiles.ProjectileType;
import nlr.ganymede.simulation.radar.RadarBlipSize;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public final class DataManager implements DataService {

	private HashMap<Integer, ArmourData> armourData;
	private HashMap<Integer, BoidData> boidData;
	private HashMap<Integer, InfoData> infoData;
	private HashMap<Integer, FoundationsData> foundationData;
	private HashMap<Integer, HitPointsData> hitPointsData;
	private HashMap<Integer, HotkeyData> hotkeyData;
	private HashMap<Integer, Image> images;
	private HashMap<Integer, LosData> losData;
	private HashMap<Integer, MilitaryUnitData> militaryUnitData;
	private HashMap<Integer, MinerData> minerData;
	private HashMap<Integer, RaceData> raceData;
	private HashMap<Integer, RadarProviderData> radarProviderData;
	private HashMap<Integer, RadarVisibleData> radarVisiblesData;
	private HashMap<Integer, RequirementsData> requirementsData;
	private HashMap<Integer, RotatingSpriteData> rotatingSpriteData;
	private HashMap<Integer, SpriteData> spriteData;
	private HashMap<Integer, StructureRendererData> structureRendererData;
	private HashMap<Integer, TextData> textData;
	private HashMap<Integer, TrainsUnitsData> trainsUnitsData;
	private HashMap<Integer, TurretData> turretData;
	private HashMap<Integer, SupplyData> supplyData;
	private HashMap<Integer, UnitRendererData> unitRendererData;
	private HashMap<Integer, UtilitiesData> utilitiesData;
	private HashMap<Integer, WeaponData> weaponData;
	
	public DataManager() {
		
		super();
		
		this.armourData = new HashMap<Integer, ArmourData>();
		this.infoData = new HashMap<Integer, InfoData>();
		this.foundationData = new HashMap<Integer, FoundationsData>();
		this.hitPointsData = new HashMap<Integer, HitPointsData>();
		this.hotkeyData = new HashMap<Integer, HotkeyData>();
		this.images = new HashMap<Integer, Image>();
		this.losData = new HashMap<Integer, LosData>();
		this.militaryUnitData = new HashMap<Integer, MilitaryUnitData>();
		this.minerData = new HashMap<Integer, MinerData>();
		this.unitRendererData = new HashMap<Integer, UnitRendererData>();
		this.boidData = new HashMap<Integer, BoidData>();
		this.raceData = new HashMap<Integer, RaceData>();
		this.radarProviderData = new HashMap<Integer, RadarProviderData>();
		this.radarVisiblesData = new HashMap<Integer, RadarVisibleData>();
		this.requirementsData = new HashMap<Integer, RequirementsData>();
		this.rotatingSpriteData = new HashMap<Integer, RotatingSpriteData>();
		this.spriteData = new HashMap<Integer, SpriteData>();
		this.structureRendererData = new HashMap<Integer, StructureRendererData>();
		this.textData = new HashMap<Integer, TextData>();
		this.trainsUnitsData = new HashMap<Integer, TrainsUnitsData>();
		this.turretData = new HashMap<Integer, TurretData>();
		this.supplyData = new HashMap<Integer, SupplyData>();
		this.utilitiesData = new HashMap<Integer, UtilitiesData>();
		this.weaponData = new HashMap<Integer, WeaponData>();
	}
	
	public void load() throws SlickException {
		
        try {
        	// Driver code
        	Class.forName("org.sqlite.JDBC");
        	
        	// Connect
			Connection connection = DriverManager.getConnection("jdbc:sqlite:assets/Ganymede.db");
			
			// Load
			this.loadImages(connection);
			this.loadSpriteData(connection);
			this.loadRotatingSpriteData(connection);
			this.loadTextData(connection);
			this.loadArmourData(connection);
			this.loadEntityData(connection);
			this.loadFoundationData(connection);
			this.loadBoidData(connection);
			this.loadHitPointsData(connection);
			this.loadHotkeys(connection);
			this.loadRaceData(connection);
			this.loadRadarProviderData(connection);
			this.loadRadarVisibleData(connection);
			this.loadRequirementsData(connection);
			this.loadTrainsUnitsData(connection);
			this.loadLosData(connection);
			this.loadMinerData(connection);
			this.loadStructureRenderData(connection);
			this.loadUnitRendererData(connection);
			this.loadUnitData(connection);
			this.loadUtilitiesData(connection);
			this.loadWeaponData(connection);
			this.loadMilitaryUnitData(connection);
			this.loadTurretData(connection);
			
		} catch (SQLException e) {
			
			throw new SlickException(e.getMessage(), e);
		} 
        catch (ClassNotFoundException e) {
        	
			throw new SlickException(e.getMessage(), e);
		}
	}
	
	@Override
	public ArmourData getArmourData(int id) throws SlickException {
		
		ArmourData armourData = this.armourData.get(id);
		
		if (armourData == null) {
			
			throw new SlickException("Could not find ArmourData with id " + Integer.toString(id) + ". ");
		}
		else {
			
			return armourData;
		}
	}

	@Override
	public BoidData getBoidData(int id) throws SlickException {
		
		BoidData boidData = this.boidData.get(id);
		
		if (boidData == null) {
			
			throw new SlickException("Could not find BoidData with id " + Integer.toString(id) + ". ");
		}
		else {
			
			return boidData;
		}
	}
	
	@Override
	public InfoData getInfoData(int id) throws SlickException {
		
		InfoData infoData = this.infoData.get(id);
		
		if (infoData == null) {
			
			throw new SlickException("Could not find InfoData with id " + Integer.toString(id) + ". ");
		}
		else {
			
			return infoData;
		}
	}
	
	@Override
	public FoundationsData getFoundationsData(int id) throws SlickException {
		
		FoundationsData foundationsData = this.foundationData.get(id);
		
		if (foundationsData == null) {
			
			throw new SlickException("Could not find FoundationsData with id " + Integer.toString(id) + ". ");
		}
		else {
			
			return foundationsData;
		}
	}
	
	@Override
	public HitPointsData getHitPointsData(int id) throws SlickException {
		
		HitPointsData hitPointsData = this.hitPointsData.get(id);
		
		if (hitPointsData == null) {
			
			throw new SlickException("Could not find HitPointsData with id " + Integer.toString(id) + ". ");
		}
		else {
			
			return hitPointsData;
		}
	}
	
	@Override
	public Image getImage(int id) throws SlickException {
		
		Image image = this.images.get(id);
		
		if (image == null) {
			
			throw new SlickException("Could not find Image with id " + Integer.toString(id) + ". ");
		}
		else {
			
			return image;
		}
	}
	
	@Override
	public LosData getLosData(int id) throws SlickException {
		
		LosData losData = this.losData.get(id);
		
		if (losData == null) {
			throw new SlickException("Could not find LosData with id " + Integer.toString(id) + ". ");
		}
		else {
			return losData;
		}
	}

	@Override
	public MinerData getMinerData(int id) throws SlickException {
		
		MinerData minerData = this.minerData.get(id);
		
		if (minerData == null) {
			
			throw new SlickException("Could not find MinerData with id " + Integer.toString(id) + ". ");
		}
		else {
			
			return minerData;
		}
	}
	
	@Override
	public UnitRendererData getUnitRendererData(int id) {
		
		return this.unitRendererData.get(id);
	}
	
	@Override
	public RaceData getRaceData(int id) {
		
		return this.raceData.get(id);
	}
	
	@Override
	public RadarProviderData getRadarProviderData(int id) throws SlickException {
		
		RadarProviderData radarProviderData = this.radarProviderData.get(id);
		
		if (radarProviderData == null) {
			
			throw new SlickException("Could not find RadarProviderData with id " + Integer.toString(id) + ". ");
		}
		else {
			
			return radarProviderData;
		}
	}
	
	@Override
	public RadarVisibleData getRadarVisibleData(int id) throws SlickException {
		
		RadarVisibleData radarVisibleData = this.radarVisiblesData.get(id);
		
		if (radarVisibleData == null) {
			
			throw new SlickException("Could not find RadarVisibilityData with id " + Integer.toString(id) + ". ");
		}
		else {
			
			return radarVisibleData;
		}
	}

	@Override
	public RequirementsData getRequirementsData(int id) throws SlickException {
		
		RequirementsData requirementsData = this.requirementsData.get(id);
		
		if (requirementsData == null) {
			
			throw new SlickException("Could not find RequirementsData with id " + Integer.toString(id) + ". ");
		}
		else {
			
			return requirementsData;
		}
	}
	
	@Override
	public RotatingSpriteData getRotatingSpriteData(int id) throws SlickException {
		
		RotatingSpriteData rotatingSpriteData = this.rotatingSpriteData.get(id);
		
		if (rotatingSpriteData == null) {
			
			throw new SlickException("Could not find RotatingSpriteData with id " + Integer.toString(id) + ". ");
		}
		else {
			
			return rotatingSpriteData;
		}
	}
	
	@Override
	public StructureRendererData getStructureRendererData(int id) throws SlickException {
		
		StructureRendererData structureRendererData = this.structureRendererData.get(id);
		
		if (structureRendererData == null) {
			
			throw new SlickException("Could not find StructureRendererData with id " + Integer.toString(id) + ". ");
		}
		else {
			
			return structureRendererData;
		}
	}
	
	@Override
	public TextData getTextData(int id) {
		
		return this.textData.get(id);
	}

	@Override
	public TrainsUnitsData getTrainsUnitsData(int id) throws SlickException {
		
		TrainsUnitsData trainsUnitsData = this.trainsUnitsData.get(id);
		
		if (trainsUnitsData == null) {
			
			throw new SlickException("Could not find TrainsUnitsData with id " + Integer.toString(id) + ". ");
		}
		else {
			
			return trainsUnitsData;
		}
	}
	
	@Override
	public TurretData getTurretData(int id) throws SlickException {
		
		TurretData turretData = this.turretData.get(id);
		
		if (turretData == null) {
			
			throw new SlickException("Could not find TurretData with id " + Integer.toString(id) + ". ");
		}
		else {
			
			return turretData;
		}
	}
	
	@Override
	public SupplyData getSupplyData(int id) throws SlickException {
		
		SupplyData supplyData = this.supplyData.get(id);
		
		if (supplyData == null) {
			
			throw new SlickException("Could not find SupplyData with id " + Integer.toString(id) + ". ");
		}
		else {
			
			return supplyData;
		}
	}
	
	@Override
	public UtilitiesData getUtilitiesData(int id) throws SlickException {
		
		UtilitiesData utilitiesData = this.utilitiesData.get(id);
		
		if (utilitiesData == null) { 
			throw new SlickException("Could not find UtilitiesData with id " + Integer.toString(id) + ". ");
		}
		else {
			return utilitiesData;
		}
	}
	
	@Override
	public WeaponData getWeaponData(int id) throws SlickException {
		
		WeaponData weaponData = this.weaponData.get(id);
		
		if (weaponData == null) {
			
			throw new SlickException("Could not find WeaponData with id " + Integer.toString(id) + ". ");
		}
		else {
			
			return weaponData;
		}
	}
	
	@Override
	public ArrayList<Integer> getEntityIdsForHotkey(int key) {
		
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		for (HotkeyData i : this.hotkeyData.values()) {
			if (i.getKey() == key) {
				result.add(i.getEntityId());
			}
		}
		
		return result;
	}

	private void loadArmourData(Connection connection) throws SQLException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM Armours");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.armourData.put(
					resultSet.getInt("Id"), 
					new ArmourData(
							resultSet.getInt("Id"), 
							this.getTextData(resultSet.getInt("TextIdName")), 
							resultSet.getInt("ResistancePierce"), 
							resultSet.getInt("ResistanceHeat"), 
							resultSet.getInt("ResistanceImpact")));
		}
	}
	
	private void loadBoidData(Connection connection) throws SQLException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM Boids");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.boidData.put(
					resultSet.getInt("Id"), 
					new BoidData(
							resultSet.getInt("Id"), 
							resultSet.getInt("HardRadius"), 
							resultSet.getInt("SoftRadius"), 
							(resultSet.getInt("IsGround") == 1), 
							(resultSet.getInt("IsAir") == 1), 
							resultSet.getInt("Force") / 100f, 
							resultSet.getInt("LateralForce") / 100f, 
							resultSet.getInt("MaxSpeed") / 100f, 
							resultSet.getInt("Mass"), 
							resultSet.getInt("TurnSpeed") / 100f));
		}
	}

	private void loadEntityData(Connection connection) throws SQLException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM Entities");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.infoData.put(
					resultSet.getInt("Id"), 
					new InfoData(
							resultSet.getInt("Id"), 
							this.textData.get(resultSet.getInt("TextIdName")), 
							this.textData.get(resultSet.getInt("TextIdDescription")), 
							this.textData.get(resultSet.getInt("TextIdAcronym"))
							)
					);
		}
	}
	
	private void loadFoundationData(Connection connection) throws SQLException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM Foundations");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.foundationData.put(
					resultSet.getInt("Id"), 
					new FoundationsData(
							resultSet.getInt("Id"), 
							resultSet.getInt("Width"), 
							resultSet.getInt("Length")
							)
					);
		}
	}

	private void loadHitPointsData(Connection connection) throws SQLException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM HitPoints");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.hitPointsData.put(
					resultSet.getInt("Id"), 
					new HitPointsData(
							resultSet.getInt("Id"), 
							resultSet.getInt("Start"), 
							resultSet.getInt("Max"), 
							resultSet.getInt("Recharge")));
		}
	}
	
	private void loadHotkeys(Connection connection) throws SQLException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM Hotkeys");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.hotkeyData.put(
					resultSet.getInt("Id"), 
					new HotkeyData(
							resultSet.getInt("Id"), 
							resultSet.getInt("Key"), 
							resultSet.getInt("EntityId")
							)
					);
		}
	}

	private void loadImages(Connection connection) throws SQLException, SlickException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM Images");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.images.put(
					resultSet.getInt("Id"), 
					new Image(resultSet.getString("Reference")));
		}
	}

	private void loadLosData(Connection connection) throws SQLException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM LoS");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.losData.put(
					resultSet.getInt("Id"), 
					new LosData(
							resultSet.getInt("Id"), 
							resultSet.getInt("LoS")
							)
					);
		}
	}
	
	private void loadMilitaryUnitData(Connection connection) throws SQLException, SlickException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM MilitaryUnits");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.militaryUnitData.put(
					resultSet.getInt("Id"), 
					new MilitaryUnitData(
							resultSet.getInt("Id"), 
							this.getWeaponData(resultSet.getInt("WeaponId")), 
							this.getRotatingSpriteData(resultSet.getInt("RotatingSpriteIdCharging")), 
							this.getRotatingSpriteData(resultSet.getInt("RotatingSpriteIdRecovering"))));
		}
	}

	private void loadMinerData(Connection connection) throws SQLException, SlickException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM Miners");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.minerData.put(
					resultSet.getInt("Id"), 
					new MinerData(
							resultSet.getInt("Id"), 
							resultSet.getInt("GatherInterval"), 
							resultSet.getInt("IceRate"), 
							resultSet.getInt("MineralRate"), 
							resultSet.getInt("MetalRate"), 
							this.getSpriteData(resultSet.getInt("SpriteIdMining"))));
		}
	}

	private void loadRaceData(Connection connection) throws SQLException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM Races");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.raceData.put(
					resultSet.getInt("Id"), 
					new RaceData(
							resultSet.getInt("Id"), 
							this.textData.get(resultSet.getInt("TextIdWin")), 
							this.textData.get(resultSet.getInt("TextIdLose")), 
							resultSet.getInt("IdHq")
							)
					);
		}
	}
	
	private void loadRadarProviderData(Connection connection) throws SQLException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM RadarProviders");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			
			this.radarProviderData.put(
					resultSet.getInt("Id"), 
					new RadarProviderData(
							resultSet.getInt("Id")));
		}
	}

	private void loadRadarVisibleData(Connection connection) throws SQLException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM RadarVisibles");
		
		ResultSet resultSet = statement.getResultSet();
		
		int level;
		RadarBlipSize levelEnum;
		
		while (resultSet.next()) {
			
			level = resultSet.getInt("Level");
			
			if (level > 0) {
				if (level == 1) {
					levelEnum = RadarBlipSize.Small;
				}
				else if (level == 2) {
					levelEnum = RadarBlipSize.Medium;
				}
				else {
					levelEnum = RadarBlipSize.Large;
				}
				
				this.radarVisiblesData.put(
						resultSet.getInt("Id"), 
						new RadarVisibleData(
								resultSet.getInt("Id"), 
								levelEnum
								)
						);
			}
		}
	}

	private void loadRequirementsData(Connection connection) throws SQLException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM Requirements");
		
		ResultSet resultSet = statement.getResultSet();
		
		ArrayList<Integer> requirements;
		
		while (resultSet.next()) {
			
			requirements = new ArrayList<Integer>();
			
			for (String i : resultSet.getString("Requirements").split(" ")) {
				requirements.add(Integer.parseInt(i));
			}
			
			this.requirementsData.put(
					resultSet.getInt("Id"), 
					new RequirementsData(
							resultSet.getInt("Id"), 
							requirements
							)
					);
		}
	}

	private void loadRotatingSpriteData(Connection connection) throws SQLException, SlickException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM RotatingSprites");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.rotatingSpriteData.put(
					resultSet.getInt("Id"), 
					new RotatingSpriteData(
							this.getImage(resultSet.getInt("ImageId")), 
							resultSet.getInt("TW"), 
							resultSet.getInt("TH"), 
							resultSet.getInt("Duration"), 
							resultSet.getInt("OriginX"), 
							resultSet.getInt("OriginY"), 
							(resultSet.getInt("Looping")) == 1));
		}
	}
	
	private void loadSpriteData(Connection connection) throws SQLException, SlickException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM Sprites");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.spriteData.put(
					resultSet.getInt("Id"), 
					new SpriteData(
							this.getImage(resultSet.getInt("ImageId")), 
							resultSet.getInt("TW"), 
							resultSet.getInt("TH"), 
							resultSet.getInt("X1"), 
							resultSet.getInt("Y1"), 
							resultSet.getInt("X2"), 
							resultSet.getInt("Y2"), 
							resultSet.getInt("Duration"), 
							resultSet.getInt("OriginX"), 
							resultSet.getInt("OriginY")));
		}
	}

	private void loadStructureRenderData(Connection connection) throws SQLException, SlickException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM StructureRenderers");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.structureRendererData.put(
					resultSet.getInt("Id"), 
					new StructureRendererData(
							resultSet.getInt("Id"), 
							this.getSpriteData(resultSet.getInt("SpriteId")), 
							this.getSpriteData(resultSet.getInt("SpriteIdBuilding"))));
		}
	}

	private void loadTextData(Connection connection) throws SQLException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM Text");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.textData.put(
					resultSet.getInt("Id"), 
					new TextData(
							resultSet.getInt("Id"), 
							resultSet.getString("English") 
							)
					);
		}
	}

	private void loadTrainsUnitsData(Connection connection) throws SQLException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM TrainsUnits");
		
		ResultSet resultSet = statement.getResultSet();
		
		ArrayList<Integer> idsTrain;
		
		while (resultSet.next()) {
			
			idsTrain = new ArrayList<Integer>();
			
			for (String i : resultSet.getString("IdsTrain").split(" ")) {
				
				idsTrain.add(Integer.parseInt(i));
			}
			
			this.trainsUnitsData.put(
					resultSet.getInt("Id"), 
					new TrainsUnitsData(
							resultSet.getInt("Id"), 
							idsTrain
							)
					);
		}
	}
	
	private void loadTurretData(Connection connection) throws SQLException, SlickException { 
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM Turrets");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.turretData.put(
					resultSet.getInt("Id"), 
					new TurretData(
							resultSet.getInt("Id"), 
							this.getRotatingSpriteData(resultSet.getInt("RotatingSpriteId")), 
							this.getWeaponData(resultSet.getInt("WeaponId")), 
							resultSet.getInt("TurnSpeed")));
		}
	}

	private void loadUnitData(Connection connection) throws SQLException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM Units");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.supplyData.put(
					resultSet.getInt("Id"), 
					new SupplyData(
							resultSet.getInt("Id"), 
							resultSet.getInt("ArmourId"), 
							resultSet.getInt("Value"),
							resultSet.getInt("Supply"), 
							resultSet.getInt("IceCost"), 
							resultSet.getInt("MineralCost"), 
							resultSet.getInt("MetalCost")));
		}
	}
	
	private void loadUnitRendererData(Connection connection) throws SQLException, SlickException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM UnitRenderers");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.unitRendererData.put(
					resultSet.getInt("Id"), 
					new UnitRendererData(
							resultSet.getInt("Id"), 
							this.getRotatingSpriteData(resultSet.getInt("RotatingAnimationIdIdle")), 
							this.getRotatingSpriteData(resultSet.getInt("RotatingAnimationIdMoving"))));
		}
	}

	private void loadUtilitiesData(Connection connection) throws SQLException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM Utilities");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			this.utilitiesData.put(
					resultSet.getInt("Id"), 
					new UtilitiesData(
							resultSet.getInt("Id"), 
							resultSet.getInt("BuildTime"), 
							resultSet.getBoolean("IsPowerRequired"),
							resultSet.getBoolean("IsPowerGenerating"), 
							resultSet.getInt("PowerTransferRadius")
							)
					);
		}
	}

	private void loadWeaponData(Connection connection) throws SQLException, SlickException {
		
		Statement statement = connection.createStatement();
		
		statement.execute("SELECT * FROM Weapons");
		
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			
			int id = resultSet.getInt("Id");	
			int projectileType = resultSet.getInt("Type");
			
			ProjectileType projectileTypeEnum;
			
			switch (projectileType) {
			
				case 0:
					
					projectileTypeEnum = ProjectileType.Bullet;
					
					break;
				
				case 1:
					
					projectileTypeEnum = ProjectileType.OneTwenty;
					
					break;
					
				case 2:
					
					projectileTypeEnum = ProjectileType.Rocket;
					
					break;
					
				case 3:
					
					projectileTypeEnum = ProjectileType.BigRocket;
					
					break;
	
				default:
					
					throw new SlickException("Invalid projectile type " + Integer.toString(projectileType) + " for id " + Integer.toString(id) + ". ");
			}
			
			this.weaponData.put(
					id, 
					new WeaponData(
							id, 
							this.textData.get(resultSet.getInt("TextIdName")), 
							resultSet.getInt("DamagePierce"), 
							resultSet.getInt("DamageHeat"), 
							resultSet.getInt("DamageImpact"), 
							resultSet.getBoolean("IsEmp"), 
							resultSet.getInt("RangeMin"), 
							resultSet.getInt("RangeMax"), 
							resultSet.getInt("ChargeTime"), 
							resultSet.getInt("RecoveryTime"), 
							projectileTypeEnum, 
							(resultSet.getInt("TargetsGround") == 1), 
							(resultSet.getInt("TargetsAir") == 1)));
		}
	}

	@Override
	public SpriteData getSpriteData(int id) throws SlickException {
		
		SpriteData spriteData = this.spriteData.get(id);
		
		if (spriteData == null) {
			throw new SlickException("Could not find SpriteData with id " + Integer.toString(id) + ". ");
		}
		else {
			return spriteData;
		}
	}
	
	@Override
	public MilitaryUnitData getMilitaryUnitData(int id) throws SlickException {
		
		MilitaryUnitData militaryUnitData = this.militaryUnitData.get(id);
		
		if (militaryUnitData == null) {
			throw new SlickException("Could not find MilitaryUnitData with id " + Integer.toString(id) + ". ");
		}
		else {
			return militaryUnitData;
		}
	}
}
