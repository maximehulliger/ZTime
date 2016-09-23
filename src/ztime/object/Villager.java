package ztime.object;

import org.newdawn.slick.geom.Vector2f;

import ztime.ZTime;
import ztime.object.activity.FollowPath;
import ztime.object.activity.GatherResource;
import ztime.object.composant.Activator;
import ztime.object.composant.Renderer;
import ztime.terrain.Case.Type;

public class Villager extends Unit {

	public Villager() {
		super("Villager");
		super.addComposant(new Renderer("villager.bmp", size));
		
	}

	public void onRightClickSelected(Vector2f point) {
		final int pointXi = (int)point.x, pointYi = (int)point.y;
		if (ZTime.terrain.isIn(pointXi, pointYi)) {
			Type type = ZTime.terrain.get(pointXi, pointYi).type;
			if (type == Type.Pierre || type == Type.Vegetation)
				get(Activator.class).set(new GatherResource(this, point));
			else
				get(Activator.class).set(new FollowPath(this, point));
		}
		
	}
}
