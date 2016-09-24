package ztime.object.composant;

import ztime.ZTime;
import ztime.object.Object;
import ztime.object.Unit;
import ztime.object.activity.Activity;

public class Activator extends Composant {

	private Activity activity = null;
	
	public void setObject(Object object) {
		super.setObject(object);
		ZTime.unactivityManager.add((Unit)object);
	}
	
	public void update() {
		if (activity != null) {
			if (activity.isOver()) {
				activity = null;
				ZTime.unactivityManager.add((Unit)object);
			} else
				activity.update();
		}
	}

	public void draw() {
		if (activity != null)
				activity.draw();
	}
	
	public void set(Activity activity) {
		if (this.activity != null)
			this.activity.terminate();
		this.activity = activity;
		ZTime.unactivityManager.remove((Unit)object);
		activity.setActivator(this);
	}

}
