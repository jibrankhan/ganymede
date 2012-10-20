package nlr.ui;

import java.io.File;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public strictfp final class TestGame extends BasicGame {

	private UIScreen screen;
	
	public TestGame() {
		
		super("UI Test");
		
		this.screen = new UIScreen(new UniScale(32f, 32f));
		
		UIButton button = new UIButton(new UniScale(128f, 128f), new UniScale(160f, 32f), new UniScale(8f, 8f));
		UILabel label = new UILabel(new UniScale(), new UniScale(), "Hello world. ");
		
		button.add(label);
		
		this.screen.add(button);
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		this.screen.init(gameContainer);
	}

	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		this.screen.update(gameContainer, delta);
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		graphics.setBackground(new Color(100, 149, 237));
		
		this.screen.render(gameContainer, graphics);
	}

	/**
	 * @param args
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws SlickException {
		
		// Linking
		System.setProperty("org.lwjgl.librarypath", new File(new File(System.getProperty("user.dir"), "native"), LWJGLUtil.getPlatformName()).getAbsolutePath());
		System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
		
		// Game
		TestGame game = new TestGame();
		
		// Container
		AppGameContainer container = new AppGameContainer(game);
		
		container.setTargetFrameRate(60);
		
		container.start();
	}
}
