package ztime;

import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import ztime.object.Building;
import ztime.object.Unit;

public class Selector {
	
	enum Mode { None, Selection, Placement }
	
	private Mode mode = Mode.None;
	private final Input input;
	private Selectable selected = null;
	private Building toPlace = null;
	
	
	public Selector(GameContainer gc) {
		this.input = gc.getInput();
	}
	
	public void select(Unit u) {
		mode = Mode.Selection;
		selected = u;
		ZTime.cam.pos.set(u.pos);
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

		if (mode == Mode.Placement) {
			toPlace.drawToPlace(ZTime.cam.toTerrain(new Vector2f(input.getMouseX(), input.getMouseY())));
		}

		// selected info
		final int infoWidth = 200, infoHeight = 100;
		int leftInfo = ZTime.width - infoWidth -1;
		int topInfo = ZTime.height - infoHeight -1;
		g.setColor(Color.black);
		g.drawRect(leftInfo, topInfo, infoWidth, infoHeight);
		g.setColor(new Color(100, 100, 100));
		g.fillRect(leftInfo+1, topInfo+1, infoWidth-1, infoHeight-1);
		if (mode == Mode.Selection) {
			if (selected != null)
				selected.drawSelection(g, leftInfo+5, topInfo+5, ZTime.cam);
		}
		
		// selected actions
		final int actionWidth = 400, actionHeight = 65;
		int leftAction = leftInfo - actionWidth;
		int topAction = ZTime.height - actionHeight -1;
		g.setColor(Color.black);
		g.drawRect(leftAction, topAction, actionWidth, actionHeight);
		g.setColor(new Color(100, 100, 100));
		g.fillRect(leftAction+1, topAction+1, actionWidth-1, actionHeight-1);
		
	}
	
	public interface Selectable {
		public void drawSelection(Graphics g, int left, int top, Camera cam);
		public void onRightClickSelected(Vector2f mousePos);
	}
}
