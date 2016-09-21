package ztime.terrain;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import ztime.Camera;
import ztime.ResourceManager.Resource;

public class ResourceCase extends Case {

	public int resourceLeft = 10000;
	
	public ResourceCase(Type type) {
		super(type);
	}
	
	public Resource getResourceType() {
		switch (type) {
		case Vegetation:
			return Resource.Wood;
		case Pierre:
			return Resource.Rock;
		default:
			return null;	
		}
	}

	public void drawSelection(Graphics g, int left, int top, Camera cam) {
		g.setColor(Color.black);
		g.drawString("Case type "+type+"\nDisponible: "+resourceLeft, left, top);
	}
}
