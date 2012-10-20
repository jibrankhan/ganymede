package nlr.ganymede.simulation.health;

import nlr.components.Component;

public strictfp interface Armour extends Component {

	void hit(int pierce, int heat, int impact);
}
