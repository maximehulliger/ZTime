package ztime;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import ztime.terrain.Case;
import ztime.terrain.Terrain;

public class ZTime extends BasicGame {

	public static final int width = 640, height = 480;
	Camera cam;
	
	public ZTime(String gamename) {
		super(gamename);
	}

	public void init(GameContainer gc) 
			throws SlickException {
		Case.init();
		Terrain terrain = new Terrain(100);
		cam = new Camera(terrain);
	}
	
	public void update(GameContainer gc, int i) 
			throws SlickException {}

	public void render(GameContainer gc, Graphics g) 
			throws SlickException {
		cam.render();
		g.drawString("Howdy!", 10, 50);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new ZTime("Simple Slick Game"));
			appgc.setDisplayMode(640, 480, false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(ZTime.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
