package nlr.ganymede;

import nlr.ganymede.command.Command;

import org.newdawn.slick.SlickException;

public strictfp interface CommandSender {

	void sendCommand(Command command) throws SlickException;
}
