package ztime.object.activity;

import org.newdawn.slick.geom.Vector2f;

import ztime.ResourceManager.Resource;
import ztime.Time;
import ztime.ZTime;
import ztime.object.Unit;
import ztime.terrain.Case;
import ztime.terrain.Case.Type;
import ztime.terrain.Pathfinder;
import ztime.terrain.ResourceCase;

public class GatherResource extends Activity {
	private static final float gatheringTime = 2;
	private static final int gatheringAmount = 5000;
	
	private enum State {WalkToResource, GatherResource}
	
	private State state = State.WalkToResource;
	private final Unit unit;
	private FollowPath followPath;
	private Type gatheringType;
	private int gatheringPosX, gatheringPosY;
	private boolean over = false;
	private float etat = 0;
	
	public GatherResource(Unit unit, Vector2f to) {
		this.unit = unit;
		this.gatheringType = ZTime.terrain.get((int)to.x, (int)to.y).type;
		followPath = new FollowPath(unit, Pathfinder.computePathToRessource(unit.pos.x, unit.pos.y, to.x, to.y, gatheringType));
	}
	
	@Override
	public void update() {
		switch (state) {
		case WalkToResource:
			if (followPath.isOver()) {
				//on cherche une resource à côté
				Vector2f gatheringPos = getGatheringPosAround();
				if (gatheringPos == null) {
					// on reste en walkToResource
					followPath = new FollowPath(unit, Pathfinder.computePathToRessource(unit.pos.x, unit.pos.y, unit.pos.x, unit.pos.y, gatheringType));
				} else {
					state = State.GatherResource;
					gatheringPosX = (int)gatheringPos.x;
					gatheringPosY = (int)gatheringPos.y;
					etat = 0;
				}
			} else
				followPath.update();
			break;
		case GatherResource:
			//on check que la case soit encore là
			Case c = ZTime.terrain.get(gatheringPosX, gatheringPosY);
			if (c.type != gatheringType)
				state = State.WalkToResource;
			else {
				etat += Time.deltaTime;
				if (etat >= gatheringTime) {
					ResourceCase rc = (ResourceCase)c;
					rc.resourceLeft -= gatheringAmount;
					if (rc.resourceLeft <= 0)
						ZTime.terrain.setToPlaine(gatheringPosX, gatheringPosY);
					ZTime.resources.add(Resource.fromCaseType(gatheringType), gatheringAmount);
					etat = 0;
				}
			}
			break;
		}
		
	}

	public void draw() {
		if (state == State.WalkToResource)
			followPath.draw();
	}
	
	private Vector2f getGatheringPosAround() {
		int x = (int)unit.pos.x, y = (int)unit.pos.y;
		int[] xs = new int[] {x+1, x+1, x,   x-1, x-1, x-1, x,   x+1};
		int[] ys = new int[] {y,   y+1, y+1, y+1, y,   y-1, y-1, y-1};
		for (int i=0; i<8; i++) {
			if (ZTime.terrain.isIn(xs[i], ys[i]) && ZTime.terrain.get(xs[i], ys[i]).type == gatheringType) {
				return new Vector2f(xs[i]+0.5f, ys[i]+0.5f);
			}
		}
		return null;
	}

	@Override
	public boolean isOver() {
		return over;
	}

}
