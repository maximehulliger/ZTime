package ztime.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.geom.Vector2f;

import ztime.ZTime;
import ztime.ImageManager;
import ztime.ResourceManager.Resource;
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

	@Override
	public List<Action> getActions() {
		ArrayList<Action> actions = new ArrayList<>();
		actions.add(new Action(
				ImageManager.houseIco, 
				"House: "+House.price().toString(), 
				() -> ZTime.selector.setToPlace(new House())));
		return actions;
	}

	public static Map<Resource, Integer> price() {
		Map<Resource, Integer> price = new HashMap<>();
		price.put(Resource.Food, 100);
		return price;
	}
}
