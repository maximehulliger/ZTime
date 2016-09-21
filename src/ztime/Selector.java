package ztime;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import ztime.object.Building;

public class Selector {
	
	enum Mode { None, Selection, Placement }
	
	private Mode mode = Mode.None;
	private final Input input;
	private Selectable selected = null;
	private Building toPlace = null;
	
	
	public Selector(GameContainer gc) {
		this.input = gc.getInput();
	}

	public void onMouseLeftPressed(int x, int y) {
		switch (mode) {
		case None:
		case Selection:
			Vector2f mousePos = ZTime.cam.toTerrain(new Vector2f(x, y));
			selected = ZTime.selectableUnder(mousePos);;
			if (selected != null)
				mode = Mode.Selection;
			break;
		case Placement:
			Vector2f point = ZTime.cam.toTerrain(new Vector2f(x,y));
			if (toPlace.canPlace(point))
				toPlace.place(point);
			toPlace = null;
			mode = Mode.None;
		}
	}

	public void setToPlace(Building toPlace) {
		this.toPlace = toPlace;
		this.mode = Mode.Placement;
	}

	public void onMouseRightReleased(int x, int y) {
		if (selected != null) {
			Vector2f mousePos = ZTime.cam.toTerrain(new Vector2f(input.getMouseX(), input.getMouseY()));
			selected.onRightClickSelected(mousePos);
		}
	}
	
	public void update() {
		
	}
	
	public void draw() {
		Graphics g = ZTime.gc.getGraphics();
		
		// selected info
		int leftInfo = ZTime.width - 200;
		int topInfo = ZTime.height - 100;
		
		g.setColor(Color.black);
		g.drawRect(leftInfo, topInfo, ZTime.width, ZTime.height);
		g.setColor(new Color(100, 100, 100));
		g.fillRect(leftInfo+1, topInfo+1, ZTime.width, ZTime.height);
		
		switch (mode) {
		case Selection:
			if (selected != null)
				selected.drawSelection(g, leftInfo+5, topInfo+5, ZTime.cam);
			break;
		case Placement:
			toPlace.drawToPlace(ZTime.cam.toTerrain(new Vector2f(input.getMouseX(), input.getMouseY())));
			break;
		case None:
			break;
		}
	}
	
	public interface Selectable {
		public void drawSelection(Graphics g, int left, int top, Camera cam);
		public void onRightClickSelected(Vector2f mousePos);
	}
}
