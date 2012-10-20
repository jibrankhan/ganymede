package nlr.ganymede;


import nlr.ganymede.data.DataManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public strictfp final class GameStateLoading extends BasicGameState {
	
	private DataManager dataManager;
	
	public GameStateLoading(
			PlayerService playerService, 
			SimulationStepService commandService, 
			DataManager dataManager) {
		
		super();
		
		this.dataManager = dataManager;
	}
	
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		
	}
	
	@Override
	public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		
		// Load
		this.dataManager.load();
		
		// Leave
		this.leave(gameContainer, stateBasedGame);
		
		// Play
		stateBasedGame.enterState(GameStateIds.GAME_STATE_PLAYING_ID);
	}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		
	}

	@Override
	public int getID() {
		
		return GameStateIds.GAME_STATE_LOADING_ID;
	}
}
