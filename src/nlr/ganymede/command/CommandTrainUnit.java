package nlr.ganymede.command;

public strictfp final class CommandTrainUnit extends CommandEntity {

	private static final long serialVersionUID = 221696627803738055L;
	
	private int trainId;
	
	public strictfp int getTrainId() {
		
		return trainId;
	}

	public CommandTrainUnit(long trainsUnitsId, int trainId) {
		
		super(trainsUnitsId);
		
		this.trainId = trainId;
	}
}
