package ztime.object;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import ztime.Camera;
import ztime.ZTime;
import ztime.object.composant.Renderer;

public class Building extends Object {

	final Vector2f size;
	
	public Building(String name, Vector2f size) {
		super(name);
		this.size = size;
		addComposant(new Renderer("petiteMaison.bmp", size));
	}

	public boolean isIn(Vector2f point) {
		return point.x < pos.x+size.x/2 && point.x > pos.x-size.x/2 
				&& point.y < pos.y+size.y/2 && point.y > pos.y-size.y/2;
	}

	public void drawSelection(Graphics g, int left, int top, Camera cam) {
		g.setColor(Color.black);
		Vector2f screenPos = cam.toScreen(pos.copy().sub(size.copy().scale(0.5f)));
		g.draw(new Rectangle(screenPos.x, screenPos.y, size.x*cam.tileSize, size.y*cam.tileSize));

		g.setColor(Color.black);
		g.drawString(name(), left, top);
	}
	
	public boolean canPlace(Vector2f point) {
		Vector2f halfSize = size.copy().scale(0.5f);
		Vector2f ul = pos.copy().sub(halfSize);
		Vector2f dr = pos.copy().add(halfSize);
		for (int x = (int)ul.x; x < dr.x; x++)
			for (int y = (int)ul.y; y < dr.y; y++) {
				if (!ZTime.terrain.isIn(x,y) || !ZTime.terrain.get(x, y).isWalkable())
					return false;
			}
		return true;
	}
	
	public void place(Vector2f point) {
		Vector2f halfSize = size.copy().scale(0.5f);
		Vector2f ul = pos.copy().sub(halfSize);
		Vector2f dr = pos.copy().add(halfSize);
		final int ulx = (int)ul.x, uly = (int)ul.y;
		for (int x = ulx; x < dr.x; x++)
			for (int y = uly; y < dr.y; y++) {
				ZTime.terrain.get(x, y).occupator = this;
			}
		pos.set(ulx+halfSize.x, uly+halfSize.y);
		ZTime.objects.add(this);
		
	}
	
	public void drawToPlace(Vector2f point) {
		Vector2f halfSize = size.copy().scale(0.5f);
		Vector2f ul = point.copy().sub(halfSize).add(new Vector2f(0.5f, 0.5f));
		final int ulx = (int)ul.x, uly = (int)ul.y;
		pos.set(ulx+halfSize.x, uly+halfSize.y);
		Color color = canPlace(point) ? new Color(0, 255, 0, 100) : new Color(255, 0, 0, 100);
		get(Renderer.class).drawFaded(color);
		
	}
}
