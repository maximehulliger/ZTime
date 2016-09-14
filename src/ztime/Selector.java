package ztime;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import ztime.object.Building;
import ztime.object.Object;

public class Selector {
	
	enum Mode { None, Selection, Placement }
	
	private Mode mode = Mode.None;
	private final Input input;
	private Object selected = null;
	private Building toPlace = null;
	
	
	public Selector(GameContainer gc) {
		this.input = gc.getInput();
	}

	public void onMouseLeftPressed(int x, int y) {
		switch (mode) {
		case None:
		case Selection:
			Vector2f mousePos = ZTime.cam.toTerrain(new Vector2f(x, y));
			selected = ZTime.objectUnder(mousePos);;
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

	public void onMouseRightPressed(int x, int y) {
		if (selected != null) {
			Vector2f mousePos = ZTime.cam.toTerrain(new Vector2f(input.getMouseX(), input.getMouseY()));
			selected.onRightClickSelected(mousePos);
		}
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
