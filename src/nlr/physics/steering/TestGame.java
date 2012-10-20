package nlr.physics.steering;

import java.io.File;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public final class TestGame extends BasicGame {

	private BasicVehicle basicVehicle;
	
	public TestGame() {
		
		super("Physics Test");
		
		this.basicVehicle = new BasicVehicleTwoAxes(0, new Vector2f(32, 32), 100f, 1.5f, 4f, 2f, 0.5f);
	}

	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		float x = this.basicVehicle.getPosition().getX();
		float y = this.basicVehicle.getPosition().getY();
		
		graphics.drawOval(x - 2f, y - 2f, 4f, 4f);
	}

	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		this.basicVehicle.init(gameContainer);
	}

	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		this.basicVehicle.update(gameContainer, delta);
		
		if (gameContainer.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			
			float x = gameContainer.getInput().getMouseX();
			float y = gameContainer.getInput().getMouseY();
			
			float d = (float) this.basicVehicle.getPosition().copy().sub(new Vector2f(x, y)).getTheta();
			
			this.basicVehicle.turn(SteeringHelper.turningFace(this.basicVehicle.getRotation(), d));
			
			this.basicVehicle.addSteering(
					SteeringHelper.steeringSeek(
							this.basicVehicle.getPosition(), 
							this.basicVehicle.getVelocity(), 
							new Vector2f(x, y)), 
							1f);
		}
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
