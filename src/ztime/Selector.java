package ztime;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import ztime.object.Building;
import ztime.object.Object;

public class Selector {
	
	enum Mode { None, Selection, Placement }
	
	private Mode mode = Mode.None;
	private final Input input;
	private final List<Object> objects;
	
	private Object selected = null;
	private Building toPlace = null;
	
	
	public Selector(GameContainer gc, List<Object> objects) {
		this.input = gc.getInput();
		this.objects = objects;
	}

	public void onMouseLeftPressed(int x, int y) {
		switch (mode) {
		case None:
		case Selection:
			Vector2f mousePos = ZTime.cam.toTerrain(new Vector2f(x, y));
			selected = null;
			for (Object o : objects)
				if (o.isIn(mousePos)) {
					selected = o;
					mode = Mode.Selection;
				}
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
	
	public void update() {
		
	}
	
	public void draw() {
		switch (mode) {
		case Selection:
			if (selected != null)
				selected.drawSelection();
			break;
		case Placement:
			toPlace.drawToPlace(ZTime.cam.toTerrain(new Vector2f(input.getMouseX(), input.getMouseY())));
			break;
		case None:
			break;
		}
	}
}
