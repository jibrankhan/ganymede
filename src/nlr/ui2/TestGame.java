package nlr.ui2;

import java.io.File;

import nlr.ui2.layout.FlowLayout;
import nlr.ui2.layout.StaticLayout;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public strictfp final class TestGame extends BasicGame {

	private UiScreen screen;
	
	public TestGame() {
		
		super("UI Test");
		
		this.screen = new UiScreen(new FlowLayout(4f, 4f), 8f, 8f);
		
		for (int i = 0; i < 8; i++) {
			
			this.screen.add(
					new UiPane(
							new FlowLayout(4f, 4f), 
							new Dimension(0f), 
							new Dimension(0f), 
							new Dimension(128f), 
							new Dimension(128f)));
			
			if (i == 4) {
				
				UiPane pane = new UiPane(
						new FlowLayout(4f, 4f), 
						new Dimension(0f), 
						new Dimension(0f), 
						new Dimension(260f), 
						new Dimension(260f));
				
				pane.add(
						new UiPane(
							new FlowLayout(4f, 4f), 
							new Dimension(0f), 
							new Dimension(0f), 
							new Dimension(16f), 
							new Dimension(16f)));
				
				this.screen.add(pane);
			}
		}
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
