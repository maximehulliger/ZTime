package ztime.object;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import ztime.Camera;
import ztime.ZTime;
import ztime.object.activity.FollowPath;
import ztime.object.composant.Activator;
import ztime.terrain.Case;

public abstract class Unit extends Object {
	
	public final static float radius = 0.4f;
	public final static Vector2f size = new Vector2f(radius*2, radius*2);
	public final List<Case> caseOn = new ArrayList<>();
	
	public Unit(String name) {
		super(name);
		super.addComposant(new Activator());
	}

	public boolean isIn(Vector2f point) {
		return point.distanceSquared(pos) <= radius*radius;
	}

	public void drawSelection(Graphics g, int left, int top, Camera cam) {
		g.setColor(Color.black);
		Vector2f screenPos = ZTime.cam.toScreen(pos);
		g.draw(new Circle(screenPos.x, screenPos.y, radius*cam.tileSize));
		
		g.setColor(Color.black);
		g.drawString(name(), left, top);
	}
	
	public void onRightClickSelected(Vector2f point) {
		get(Activator.class).set(new FollowPath(this, point));
	}
	
	public void setPos(Vector2f pos) {
		for (Case c : caseOn)
			c.occupator = null;
		caseOn.clear();
		Vector2f halfSize = size.copy().scale(0.5f);
		Vector2f ul = pos.copy().sub(halfSize);
		Vector2f dr = pos.copy().add(halfSize);
		final int ulx = (int)ul.x, uly = (int)ul.y;
		for (int x = ulx; x < dr.x; x++)
			for (int y = uly; y < dr.y; y++) {
				Case c = ZTime.terrain.get(x, y);
				caseOn.add(c);
				c.occupator = this;
			}
		super.pos.set(pos);
	}
}
