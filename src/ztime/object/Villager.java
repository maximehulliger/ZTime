package ztime.object;

import ztime.object.composant.Renderer;

public class Villager extends Unit {

	public Villager() {
		super("Villager");
		super.addComposant(new Renderer("villager.bmp", size));
		
	}
	
}
