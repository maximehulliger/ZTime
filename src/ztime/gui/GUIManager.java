package ztime.gui;

import java.util.ArrayList;
import java.util.List;

import ztime.ZTime;

public class GUIManager {
	
	List<GUIElement> elements = new ArrayList<>();
	List<GUIElement> elementsToAdd = new ArrayList<>();
	List<GUIElement> elementsToRemove = new ArrayList<>();
	
	public GUIManager() {
		GUIElement.graphics = ZTime.gc.getGraphics();
	}

	public void add(GUIElement e) {
		elementsToAdd.add(e);
	}

	public void remove(GUIElement e) {
		elementsToRemove.add(e);
	}

	public void draw() {
		for (GUIElement e : elements)
			e.draw();
	}

	public void update() {
		for (GUIElement e : elements)
			e.update();
		elements.addAll(elementsToAdd);
		elements.removeAll(elementsToRemove);
		elementsToAdd.clear();
		elementsToRemove.clear();
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
	
	public void mouseMoved(int x, int y) {
		for (GUIElement e : elements)
			e.mouseMoved(x, y);
	}

	public boolean mouseRightReleased(int x, int y) {
		boolean on = false;
		for (GUIElement e : elements)
			if (e.onMouseRightUp(x, y))
				on = true;
		return on;
	}
	
}
