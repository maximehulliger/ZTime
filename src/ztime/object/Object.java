package ztime.object;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.geom.Vector2f;

import ztime.object.composant.Composant;

public abstract class Object {
	
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
	
	public abstract void drawSelection();
}
