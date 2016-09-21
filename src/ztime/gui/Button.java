package ztime.gui;

import org.newdawn.slick.Color;

public class Button extends Pane {
	
	private boolean mouseDownIn = false;
	private Runnable onClick = null;
	
	public Button(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void setOnClick(Runnable onClick) {
		this.onClick = onClick;
	}

	@Override
	public void draw() {
		super.draw();
		if (mouseDownIn) {
			graphics.setColor(new Color(255, 255, 255, 100));
			graphics.fillRect(x, y, width+1, height+1);
		}
	}

	@Override
	public boolean onMouseLeftDown(int x, int y) {
		if (isIn(x, y)) {
			mouseDownIn = true;
			return true;
		} else
			return false;
	}

	@Override
	public boolean onMouseLeftUp(int x, int y) {
		if (isIn(x, y)) {
			if (mouseDownIn) {
				onClick.run();
				mouseDownIn = false;
			}
			return true;
		} else {
			mouseDownIn = false;
			return false;
		}
	}
	
	protected boolean isIn(int x, int y) {
		return x>=this.x && y>=this.y && x<=this.x+width && y<=this.y+height;
	}

	@Override
	public boolean onMouseRightDown(int x, int y) {
		return isIn(x, y);
	}

	@Override
	public boolean onMouseRightUp(int x, int y) {
		return isIn(x, y);
	}
}
