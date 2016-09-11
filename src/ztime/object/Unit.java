package ztime.object;

import org.newdawn.slick.geom.Vector2f;

import ztime.object.composant.Navigator;
import ztime.object.composant.Renderer;

public class Unit extends Object {

	public Unit(String name) {
		super(name);
		float diameter = 0.75f;
		super.addComposant(new Renderer("unit.bmp", new Vector2f(diameter, diameter)));
		super.addComposant(new Navigator());
	}

}
