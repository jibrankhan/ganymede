package nlr.ganymede.simulation;

import nlr.components.Component;
import nlr.ganymede.data.TextData;

public strictfp interface Labelled extends Component {

	int getEntityDataId();
	
	TextData getName();
	
	TextData getDescription();
	
	TextData getAcronym();
}
