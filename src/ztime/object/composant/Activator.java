package ztime.object.composant;

import ztime.object.activity.Activity;

public class Activator extends Composant {

	private Activity activity = null;
	
	public void update() {
		if (activity != null) {
			if (activity.isOver())
				activity = null;
			else
				activity.update();
		}
	}

	public void set(Activity activity) {
		this.activity = activity;
		activity.setActivator(this);
	}

}
