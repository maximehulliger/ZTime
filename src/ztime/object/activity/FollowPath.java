package ztime.object.activity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import ztime.Time;
import ztime.ZTime;
import ztime.object.Unit;
import ztime.terrain.PathNode;
import ztime.terrain.Pathfinder;

public class FollowPath extends Activity {

	Unit unit;
	PathNode next;

	public FollowPath(Unit unit, Vector2f to) {
		this.unit = unit;
		this.next = Pathfinder.computePath(unit.pos.x, unit.pos.y, to.x, to.y);
	}

	public FollowPath(Unit unit, PathNode path) {
		this.unit = unit;
		this.next = path;
	}
	
	public void update() {
		Vector2f goal = new Vector2f(next.x, next.y);
		final float speed = 2;
		final float depl = speed*Time.deltaTime;
		if (goal.distanceSquared(unit.pos) < depl*depl) {
			unit.pos.set(goal);
			next = next.parent;
		} else {
			Vector2f toGoal = goal.sub(unit.pos).normalise().scale(depl);
			unit.pos.add(toGoal);
		}
	}
	
	public void draw() {
		Graphics g = ZTime.gc.getGraphics();
		g.setColor(Color.red);
		for (PathNode node = next ; node != null ; node = node.parent) {
			Vector2f screenPos = ZTime.cam.toScreen(new Vector2f(node.x, node.y));
			g.draw(new Circle(screenPos.x, screenPos.y, 0.1f*ZTime.cam.tileSize));
		}
	}

	public boolean isOver() {
		return next == null;
	}
	
}
