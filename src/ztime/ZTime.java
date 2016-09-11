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
	public static GameContainer gc;
	public List<Object> objects = new ArrayList<>();
	private Selector selector;
	
	public ZTime() {
		super("ZTime");
	}

	public void init(GameContainer gc) 
			throws SlickException {
		Case.init();
		Terrain terrain = new Terrain(100);
		Pathfinder.init(terrain);
		ZTime.gc = gc;
		cam = new Camera(gc, terrain);
		selector = new Selector(gc, objects);
		Object exampleUnit1 = new Unit("d_d");
		exampleUnit1.pos.set(cam.pos);
		objects.add(exampleUnit1);
	}
	
	public void update(GameContainer gc, int i) 
			throws SlickException {
		for (Object o : objects)
			o.update();
		cam.update();
		selector.update();
	}

	public void render(GameContainer gc, Graphics g) 
			throws SlickException {
		cam.render();
		for (Object o : objects)
			o.draw();
		selector.draw();
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
	
	public void mousePressed(int button, int x, int y) {
		if (button == 0) {
			cam.onMouseLeftPressed(x, y);
			selector.onMouseLeftPressed(x, y);
		}
	}
}
