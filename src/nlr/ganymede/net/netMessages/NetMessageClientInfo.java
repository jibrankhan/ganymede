package nlr.ganymede.net.netMessages;

import nlr.net.NetMessage;

public final class NetMessageClientInfo extends NetMessage {

	private static final long serialVersionUID = 5620887639097118113L;
	
	private String clientHandle;

	public String getClientHandle() {
		return clientHandle;
	}

	public NetMessageClientInfo(String clientHandle) {
		
		super();
		
		this.clientHandle = clientHandle;
	}
}
