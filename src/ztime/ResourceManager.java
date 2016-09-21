package ztime;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ResourceManager {
	public enum Resource {Wood, Rock, Food}
	
	public static Map<Resource, Image> imgs = new HashMap<>();
	public Map<Resource, Integer> resources = new HashMap<>();
	
	public ResourceManager() {
		resources.put(Resource.Wood, 0);
		resources.put(Resource.Rock, 0);
		resources.put(Resource.Food, 0);
	}
	
	public static void init() 
			throws SlickException {
		imgs.put(Resource.Wood, new Image("media/resources/wood.bmp", Color.white));
		imgs.put(Resource.Rock, new Image("media/resources/rock.bmp", Color.white));
		imgs.put(Resource.Food, new Image("media/resources/food.bmp", Color.white));
	}
	
	public boolean hasEnough(Map<Resource, Integer> resources) {
		for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
			if (this.resources.get(entry.getKey()) < entry.getValue())
				return false;
		}
		return true;
	}

	public void add(Map<Resource, Integer> resources) {
		for (Map.Entry<Resource, Integer> entry : resources.entrySet())
			this.resources.put(entry.getKey(), this.resources.get(entry.getKey()) + entry.getValue());
	}
	
	public void add(Resource r, int i) {
		this.resources.put(r, this.resources.get(r) + i);
	}

	public void remove(Map<Resource, Integer> resources) {
		for (Map.Entry<Resource, Integer> entry : resources.entrySet())
			this.resources.put(entry.getKey(), this.resources.get(entry.getKey()) - entry.getValue());
	}
	
	public void draw() {
		int margin = 10, imgSize = 25, marginY = 5;
		int width = 0;
		Font f = ZTime.gc.getGraphics().getFont();
		for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
			width += f.getWidth(""+entry.getValue()) + margin*2 + imgSize;
		}
		int left = ZTime.width/2 - width/2;
		for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
			imgs.get(entry.getKey()).draw(left, marginY, imgSize, imgSize);
			left += imgSize+margin;
			f.drawString(left, marginY, ""+entry.getValue());
			left += f.getWidth(""+entry.getValue()) + margin;
		}
	}
}
