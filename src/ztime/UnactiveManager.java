package ztime;

import java.util.ArrayList;
import java.util.List;

import ztime.gui.Button;
import ztime.object.Unit;

public class UnactiveManager {

	private List<Unit> unactiveUnits = new ArrayList<>();
	private final Button unactiveUnitButton;
	private Unit unactiveUnitSelected = null;
	
	public UnactiveManager() {
		unactiveUnitButton = new Button(0, 50, 100, 50);
		unactiveUnitButton.text = "Unactive\nUnits";
		unactiveUnitButton.setOnClick(() -> {
			int idx = unactiveUnits.indexOf(unactiveUnitSelected);
			unactiveUnitSelected = unactiveUnits.get(idx == -1 ? 0 : (idx+1)%unactiveUnits.size());
			ZTime.selector.select(unactiveUnitSelected);
		});
	}

	public void add(Unit u) {
		if (unactiveUnits.size() == 0) {
			ZTime.gui.add(unactiveUnitButton);
		}
		unactiveUnits.add(u);
	}

	public void remove(Unit u) {
		unactiveUnits.remove(u);
		if (unactiveUnits.size() == 0) {
			ZTime.gui.remove(unactiveUnitButton);
		}
	}
}
