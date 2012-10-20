package nlr.ganymede;

import java.util.Collection;

public interface PlayerService {

	Player getLocalPlayer();
	
	Collection<Player> getPlayers();
}
