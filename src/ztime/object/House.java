package ztime.object;

import org.newdawn.slick.geom.Vector2f;

import ztime.object.composant.Renderer;

public class House extends Building {

	public House() {
		super("Maison", new Vector2f(2, 3));
		addComposant(new Renderer("petiteMaison.bmp", size));
	}
}
