package ztime;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import ztime.terrain.Case;
import ztime.terrain.Pathfinder;
import ztime.terrain.Terrain;
import ztime.object.Object;
import ztime.object.Unit;

public class ZTime extends BasicGame {

	public static final int width = 720, height = 540;
	public static Camera cam;
	public List<Object> objects = new ArrayList<>();
	
	public ZTime() {
		super("ZTime");
	}

	public void init(GameContainer gc) 
			throws SlickException {
		Case.init();
		Terrain terrain = new Terrain(100);
		Pathfinder.init(terrain);
		cam = new Camera(gc, terrain);
		Object exampleUnit1 = new Unit("d_d");
		exampleUnit1.pos.set(cam.pos);
		objects.add(exampleUnit1);
	}
	
	public void update(GameContainer gc, int i) 
			throws SlickException {
		for (Object o : objects)
			o.update();
		cam.update();
	}

	public void render(GameContainer gc, Graphics g) 
			throws SlickException {
		cam.render();
		for (Object o : objects)
			o.draw();
		g.drawString("Continue!", 10, 50);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new ZTime());
			appgc.setDisplayMode(width, height, false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(ZTime.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
