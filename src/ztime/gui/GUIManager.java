package ztime.gui;

import java.util.ArrayList;
import java.util.List;

import ztime.ZTime;

public class GUIManager {
	
	List<GUIElement> elements = new ArrayList<>();
	
	public GUIManager() {
		GUIElement.graphics = ZTime.gc.getGraphics();
	}
	
	public void add(GUIElement e) {
		elements.add(e);
	}
	
	public void draw() {
		for (GUIElement e : elements)
			e.draw();
	}

	public boolean mouseLeftPressed(int x, int y) {
		for (GUIElement e : elements)
			if (e.onMouseLeftDown(x, y))
				return true;
		return false;
	}

	public boolean mouseLeftReleased(int x, int y) {
		boolean on = false;
		for (GUIElement e : elements)
			if (e.onMouseLeftUp(x, y))
				on = true;
		return on;
	}

	public boolean mouseRightPressed(int x, int y) {
		boolean on = false;
		for (GUIElement e : elements)
			if (e.onMouseRightDown(x, y))
				on = true;
		return on;
	}

	public boolean mouseRightReleased(int x, int y) {
		boolean on = false;
		for (GUIElement e : elements)
			if (e.onMouseRightUp(x, y))
				on = true;
		return on;
	}
	
}
