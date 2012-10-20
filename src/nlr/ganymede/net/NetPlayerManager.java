package nlr.ganymede.net;

import java.util.ArrayList;
import java.util.Collection;

import nlr.ganymede.Player;
import nlr.ganymede.PlayerService;


public final class NetPlayerManager implements PlayerService {

	private Player localPlayer;
	private Player remotePlayer;
	
	private ArrayList<Player> players;
	
	public NetPlayerManager(Player hostPlayer, Player clientPlayer, boolean isHost) {
		
		super();
		
		if (isHost) {
			this.localPlayer = hostPlayer;
			this.remotePlayer = clientPlayer;
		}
		else {
			this.localPlayer = clientPlayer;
			this.remotePlayer = hostPlayer;
		}
		
		this.players = new ArrayList<Player>();
		
		this.players.add(this.localPlayer);
		this.players.add(this.remotePlayer);
	}

	@Override
	public Player getLocalPlayer() {
		return localPlayer;
	}
	
	public Player getRemotePlayer() {
		return remotePlayer;
	}

	@Override
	public Collection<Player> getPlayers() {
		return new ArrayList<Player>(players);
	}
}
