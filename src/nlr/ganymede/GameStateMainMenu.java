package nlr.ganymede;

import mdes.slick.sui.Display;
import mdes.slick.sui.Label;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public strictfp final class GameStateMainMenu extends BasicGameState {

	private Display display;
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		
		this.display = new Display(gameContainer);
		
		Label label = new Label("Ganymede Alpha");
		
		label.setSize(160f, 32f);
		label.setVisible(true);
		
		this.display.add(label);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta)
			throws SlickException {
		
		this.display.update(gameContainer, delta);
	}
	
	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
			throws SlickException {
		
		this.display.render(gameContainer, graphics);
	}

	@Override
	public int getID() {
		
		return GameStateIds.GAME_STATE_MAIN_MENU_ID;
	}
}
