package nlr.ganymede;

import java.util.Collection;
import java.util.HashSet;

public final class DummyPlayerManager implements PlayerService {

	private Player localPlayer;
	private HashSet<Player> players;
	
	public DummyPlayerManager() {
		
		super();
		
		this.localPlayer = new Player(0, 0, "Rico");
		
		this.players = new HashSet<Player>(GanymedeConstants.MAX_FACTION_COUNT);
		
		this.players.add(this.localPlayer);
		this.players.add(new Player(1, 1, "Mike"));
	}

	@Override
	public Player getLocalPlayer() {
		
		return this.localPlayer;
	}

	@Override
	public Collection<Player> getPlayers() {
		
		return this.players;
	}
}
