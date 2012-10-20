package nlr.ganymede.net.netMessages;

import nlr.net.NetMessage;

public final class NetMessageHostInfo extends NetMessage {

	private static final long serialVersionUID = 3793672742505620603L;
	
	private String hostHandle;
	private String map;

	public String getHostHandle() {
		return hostHandle;
	}

	public String getMap() {
		return map;
	}

	public NetMessageHostInfo(String hostHandle, String map) {
		
		super();
		
		this.hostHandle = hostHandle;
		this.map = map;
	}
}
