package nlr.ganymede;

import java.io.File;
import java.io.IOException;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public final class LauncherTest {
	
	public static void main(String[] args) {
		
		// Linking
		System.setProperty("org.lwjgl.librarypath", new File(new File(System.getProperty("user.dir"), "native"), LWJGLUtil.getPlatformName()).getAbsolutePath());
		System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
		
		// Check args
		if (args.length != 5) {
			
			// About
			System.out.println("Ganymede Alpha 1.0");
			System.out.println("Test mode");
			System.out.println();
			
			// Instructions
			System.out.println("0 - screen width");
			System.out.println("1 - screen height");
			System.out.println("2 - full screen mode (1 = yes, 0 = no)");
			System.out.println("3 - v sync (1 = yes, 0 = no)");
			System.out.println("4 - map");
			System.out.println();
		}
		else {			
			try {
				// Get parameters
				int width = Integer.parseInt(args[0]);
				
				if (width < 640) { 
					throw new IOException("Width must be no less than 640");
				}
				
				int height = Integer.parseInt(args[1]);
				
				if (height < 480) { 
					throw new IOException("Height must be no less than 480");
				}
				
				int i = (Integer.parseInt(args[2]));
				
				boolean fullScreenMode;
				
				if (i == 0) {
					fullScreenMode = false;
				}
				else if (i == 1) {
					fullScreenMode = true;
				}
				else {
					throw new IOException("Full screen mode must be set to 1 or 0");
				}
				
				int j = (Integer.parseInt(args[3]));
				
				boolean vSync;
				
				if (j == 0) {
					vSync = false;
				}
				else if (j == 1) {
					vSync = true;
				}
				else {
					throw new IOException("V sync must be set to 1 or 0");
				}
				
				String map = args[4];
				
				// Launch
				DummyPlayerManager playerManager = new DummyPlayerManager();
				DummySimulationStepManager commandManager = new DummySimulationStepManager();
				GanymedeRules ganymedeRules = new GanymedeRules(map);
				
				GanymedeGame ganymedeGame = new GanymedeGame(playerManager, commandManager, ganymedeRules);
				
				AppGameContainer appGameContainer = new AppGameContainer(ganymedeGame);
				
				appGameContainer.setDisplayMode(width, height, fullScreenMode);
				appGameContainer.setTargetFrameRate(60);
				appGameContainer.setVSync(vSync);
				appGameContainer.setUpdateOnlyWhenVisible(false);
				appGameContainer.setAlwaysRender(true);
				
				appGameContainer.start();
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
}