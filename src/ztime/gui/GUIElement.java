package ztime.gui;

import org.newdawn.slick.Graphics;

public abstract class GUIElement {
	
	protected static Graphics graphics;
	
	public GUIElement() {
	}
	
	public abstract void draw();
	public abstract boolean onMouseLeftDown(int x, int y);
	public abstract boolean onMouseLeftUp(int x, int y);
	public abstract boolean onMouseRightDown(int x, int y);
	public abstract boolean onMouseRightUp(int x, int y);
}
