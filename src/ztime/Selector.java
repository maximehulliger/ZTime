package ztime;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import ztime.object.Object;

public class Selector {
	
	private final Input input;
	private final List<Object> objects;
	private Object selected = null;
	
	public Selector(GameContainer gc, List<Object> objects) {
		this.input = gc.getInput();
		this.objects = objects;
	}

	public void onMouseLeftPressed(int x, int y) {
		Vector2f mousePos = ZTime.cam.toTerrain(new Vector2f(input.getMouseX(), input.getMouseY()));
		selected = null;
		for (Object o : objects)
			if (o.isIn(mousePos))
				selected = o;
	}
	
	public void update() {
		
	}
	
	public void draw() {
		if (selected != null)
			selected.drawSelection();
	}
}
