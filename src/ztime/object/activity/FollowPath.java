package ztime.object.activity;

import org.newdawn.slick.geom.Vector2f;

import ztime.object.Unit;
import ztime.terrain.PathNode;
import ztime.terrain.Pathfinder;

public class FollowPath extends Activity {

	Unit unit;
	PathNode next;
	
	public FollowPath(Unit unit, Vector2f to) {
		this.unit = unit;
		this.next = new Pathfinder().computePath(unit.pos.x, unit.pos.y, to.x, to.y);
	}
	
	public void update() {
		Vector2f goal = new Vector2f(next.x, next.y);
		final float speed = 2;
		if (goal.distanceSquared(unit.pos) < speed*speed) {
			unit.pos.set(goal);
			next = next.parent;
		} else {
			Vector2f toGoal = goal.sub(unit.pos).normalise().scale(speed);
			unit.pos.add(toGoal);
		}
	}

	public boolean isOver() {
		return next == null;
	}
	
}
