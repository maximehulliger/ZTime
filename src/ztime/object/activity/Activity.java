package ztime.object.activity;

import ztime.object.composant.Activator;

public abstract class Activity {
	
	protected Activator activator;
	
	public void setActivator(Activator activator) {
		this.activator = activator;
	}
	
	public abstract void update();
	
	public abstract boolean isOver();
}
