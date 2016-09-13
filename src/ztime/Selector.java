package ztime;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import ztime.object.Object;

public class Selector {
	
	private final Input input;
	private Object selected = null;
	
	public Selector(GameContainer gc) {
		this.input = gc.getInput();
	}

	public void onMouseLeftPressed(int x, int y) {
		Vector2f mousePos = ZTime.cam.toTerrain(new Vector2f(input.getMouseX(), input.getMouseY()));
		selected = ZTime.objectUnder(mousePos);
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
		if (selected != null)
			selected.drawSelection();
	}
}
