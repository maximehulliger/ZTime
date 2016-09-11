package ztime.object.composant;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import ztime.ZTime;

public class Renderer extends Composant {

	private Image image;
	private Vector2f posRel;
	private Vector2f size;
	
	public Renderer(String imgPath, Vector2f size) {
		try {
			this.image = new Image("media/"+imgPath, Color.white);
			this.size = size.copy();
			this.posRel = size.copy().scale(-0.5f);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void draw() {
		// image upper left corner in pixel
		Vector2f ul = ZTime.cam.toScreen(object.pos.copy().add(posRel));
		image.draw(ul.x, ul.y, size.x*ZTime.cam.tileSize, size.y*ZTime.cam.tileSize);
	}
	
}
