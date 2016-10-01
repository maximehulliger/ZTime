package ztime.object;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import ztime.Selector.Selectable;
import ztime.object.composant.Composant;

public abstract class Object implements Selectable {
	
	public final Vector2f pos = new Vector2f();
	private Map<Class<? extends Composant>, Composant> composants = new HashMap<>();
	private String name;
	
	public Object(String name) {
		this.name = name;
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends Composant> T get(Class<T> compClass) {
		return (T)composants.get(compClass);
	}
	
	protected void addComposant(Composant composant) {
		composant.setObject(this);
		composants.put(composant.getClass(), composant);
	}
	
	public String name() {
		return name;
	}
	
	public void update() {
		for (Composant c : composants.values())
			c.update();
	}

	public void draw() {
		for (Composant c : composants.values())
			c.draw();
	}

	public abstract boolean isIn(Vector2f point);

	public void onRightClickSelected(Vector2f point) {}
	
	public class ToBuild {
		public Image image;
		public Building building;
		
		public ToBuild(Image image, Building building) {
			this.image = image;
			this.building = building;
		}
		
	}
}
