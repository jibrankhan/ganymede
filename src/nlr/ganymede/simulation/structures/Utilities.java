package nlr.ganymede.simulation.structures;

import nlr.ganymede.simulation.Affiliated;
import nlr.ganymede.simulation.PowerReceiver;
import nlr.ganymede.simulation.PowerTransfer;
import nlr.ganymede.simulation.Powered;

public strictfp interface Utilities extends Affiliated, Builds, Powered, PowerTransfer, PowerReceiver {

}
