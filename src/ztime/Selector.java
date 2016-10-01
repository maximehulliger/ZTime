package ztime;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import ztime.Selector.Selectable.Action;
import ztime.gui.Button;
import ztime.object.Building;

public class Selector {
	
	enum Mode { None, Selection, Placement }
	
	private Mode mode = Mode.None;
	private final Input input;
	private Selectable selected = null;
	private Building toPlace = null;
	private List<Button> actionButtons = new ArrayList<>();
	
	final int infoWidth = 200, infoHeight = 100;
	final int leftInfo = ZTime.width - infoWidth -1;
	final int topInfo = ZTime.height - infoHeight -1;
	final int actionWidth = 400, actionHeight = 65;
	final int leftAction = leftInfo - actionWidth;
	final int topAction = ZTime.height - actionHeight -1;
	
	
	public Selector(GameContainer gc) {
		this.input = gc.getInput();
	}
	
	public void select(Selectable s) {
		mode = Mode.Selection;
		selected = s;
		List<Action> actions = s.getActions();
		if (actions != null) {
			int margin = 5, imgSize = 30;
			for (int i = 0; i<actions.size(); i++) {
				int x = margin + i*(margin+imgSize);
				Button b = new Button(leftAction+x, topAction+margin, imgSize, imgSize);
				Action a = actions.get(i);
				b.image = a.img;
				b.overText = a.overText;
				b.setOnClick(a.action);
				actionButtons.add(b);
			}
			for (Button b : actionButtons)
				ZTime.gui.add(b);
		}
	}
	
	public void unselect() {
		for (Button b : actionButtons)
			ZTime.gui.remove(b);
		actionButtons.clear();
	}

	public void onMouseLeftPressed(int x, int y) {
		switch (mode) {
		case None:
		case Selection:
			unselect();
			Vector2f mousePos = ZTime.cam.toTerrain(new Vector2f(x, y));
			Selectable selected = ZTime.selectableUnder(mousePos);;
			if (selected != null)
				select(selected);
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
		unselect();
		this.toPlace = toPlace;
		this.mode = Mode.Placement;
	}

	public void onMouseRightReleased(int x, int y) {
		if (mode == Mode.Selection) {
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
		g.setColor(Color.black);
		g.drawRect(leftInfo, topInfo, infoWidth, infoHeight);
		g.setColor(new Color(100, 100, 100));
		g.fillRect(leftInfo+1, topInfo+1, infoWidth-1, infoHeight-1);
		if (mode == Mode.Selection) {
			if (selected != null)
				selected.drawSelection(g, leftInfo+5, topInfo+5, ZTime.cam);
		}
		
		// selected actions
		g.setColor(Color.black);
		g.drawRect(leftAction, topAction, actionWidth, actionHeight);
		g.setColor(new Color(100, 100, 100));
		g.fillRect(leftAction+1, topAction+1, actionWidth-1, actionHeight-1);
		
	}
	
	public interface Selectable {
		public void drawSelection(Graphics g, int left, int top, Camera cam);
		public void onRightClickSelected(Vector2f mousePos);
		public List<Action> getActions();
		
		public class Action {
			Image img; 
			String overText;
			Runnable action;
			
			public Action(Image img, String overText, Runnable action) {
				this.img = img;
				this.overText = overText;
				this.action = action;
			}
		}
	}
}
