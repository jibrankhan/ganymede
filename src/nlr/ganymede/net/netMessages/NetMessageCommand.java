package nlr.ganymede.net.netMessages;

import nlr.ganymede.command.Command;
import nlr.net.NetMessage;


public final class NetMessageCommand extends NetMessage {

	private static final long serialVersionUID = 6571695806264440854L;
	
	private Command command;
	
	public Command getCommand() {
		return command;
	}

	public NetMessageCommand(Command command) {
		
		super();
		
		this.command = command;
	}
}
