package swmasters.woj.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import swmasters.woj.WoJGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Wheel of Jeopardy";
		config.useGL30 = false;
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new WoJGame(), config);
	}
}
