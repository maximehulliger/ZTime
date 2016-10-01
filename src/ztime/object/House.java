package ztime.object;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.geom.Vector2f;

import ztime.ResourceManager.Resource;
import ztime.object.composant.Renderer;

public class House extends Building {

	public House() {
		super("Maison", new Vector2f(2, 3));
		addComposant(new Renderer("petiteMaison.bmp", size));
	}

	@Override
	public List<Action> getActions() {
		
		return null;
	}

	public static Map<Resource, Integer> price() {
		Map<Resource, Integer> price = new HashMap<>();
		price.put(Resource.Rock, 100);
		price.put(Resource.Wood, 50);
		return price;
	}
}
