package ztime.object;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import ztime.ZTime;
import ztime.object.composant.Navigator;
import ztime.object.composant.Renderer;

public class Unit extends Object {
	
	public final static float radius = 0.4f;

	public Unit(String name) {
		super(name);
		super.addComposant(new Renderer("unit.bmp", new Vector2f(radius*2, radius*2)));
		super.addComposant(new Navigator());
	}

	public boolean isIn(Vector2f point) {
		return point.distanceSquared(pos) <= radius*radius;
	}

	public void drawSelection() {
		Graphics g = ZTime.gc.getGraphics();
		g.setColor(Color.black);
		Vector2f screenPos = ZTime.cam.toScreen(pos);
		g.draw(new Circle(screenPos.x, screenPos.y, radius*ZTime.cam.tileSize));
	}
}
