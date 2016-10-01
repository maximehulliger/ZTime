package ztime;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import ztime.terrain.Case;
import ztime.terrain.Pathfinder;
import ztime.terrain.Terrain;
import ztime.Selector.Selectable;
import ztime.gui.GUIManager;
import ztime.object.House;
import ztime.object.Object;
import ztime.object.Unit;
import ztime.object.Villager;

public class ZTime extends BasicGame {

	public static final int width = 720, height = 540;
	public static Camera cam;
	public static Terrain terrain;
	public static GameContainer gc;
	public static List<Object> objects = new ArrayList<>();
	public static ResourceManager resources = new ResourceManager();
	public static Selector selector;
	public static UnactiveManager unactivityManager = new UnactiveManager();
	public static GUIManager gui;
	
	public ZTime() {
		super("ZTime");
	}

	public void init(GameContainer gc) 
			throws SlickException {
		ZTime.gc = gc;
		gui = new GUIManager();
		ImageManager.init();
		Case.init();
		ResourceManager.init();
		terrain = new Terrain(100);
		Pathfinder.init(terrain);
		cam = new Camera(gc, terrain);
		selector = new Selector(gc);
		
		Unit exampleUnit1 = new Villager();
		exampleUnit1.setPos(cam.pos);
		objects.add(exampleUnit1);
		Unit exampleUnit2 = new Villager();
		exampleUnit2.setPos(new Vector2f(cam.pos.x, cam.pos.y+2));
		
		objects.add(exampleUnit2);
	}
	
	public void update(GameContainer gc, int i) 
			throws SlickException {
		Time.update();
		for (Object o : objects)
			o.update();
		cam.update();
		selector.update();
		gui.update();
		
		if (gc.getInput().isKeyPressed(Input.KEY_E))
			selector.setToPlace(new House());
	}

	public void render(GameContainer gc, Graphics g) 
			throws SlickException {
		cam.render();
		for (Object o : objects)
			o.draw();
		selector.draw();
		gui.draw();
		resources.draw();
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
			if (!gui.mouseLeftPressed(x, y)) {
				cam.onMouseLeftPressed(x, y);
				selector.onMouseLeftPressed(x, y);
			}
		} 
	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		gui.mouseMoved(newx, newy);
	}

	public void mouseReleased(int button, int x, int y) {
		if (button == 0) {
			cam.onMouseLeftReleased(x, y);
			if (!gui.mouseLeftReleased(x, y)) {
				
			}
		} else if (button == 1) {
			if (!gui.mouseRightReleased(x, y)) {
				selector.onMouseRightReleased(x, y);
			}
		}
	}

	public static Selectable selectableUnder(Vector2f terrainPoint) {
		for (Object o : objects)
			if (o.isIn(terrainPoint))
				return o;
		final int x = (int)terrainPoint.x, y = (int)terrainPoint.y;
		if (terrain.isIn(x, y))
			return terrain.get(x, y);
		return null;
	}
}
