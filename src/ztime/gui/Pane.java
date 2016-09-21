package ztime.gui;

import org.newdawn.slick.Color;

public class Pane extends GUIElement {

	protected final int x, y, width, height;
	
	public String text = null;
	public Color borderColor = Color.black, insideColor = new Color(100, 100, 100);
	
	public Pane(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void draw() {
		graphics.setColor(borderColor);
		graphics.drawRect(x, y, width, height);
		graphics.setColor(insideColor);
		graphics.fillRect(x+1, y+1, width, height);

		if (text != null) {
			graphics.setColor(borderColor);
			int textWidth = graphics.getFont().getWidth(text);
			int textHeight = graphics.getFont().getHeight(text);
			graphics.drawString(text, x+width/2-textWidth/2, y+height/2-textHeight/2);
		}
	}

	public boolean onMouseLeftDown(int x, int y) {
		return isIn(x, y);
	}

	public boolean onMouseLeftUp(int x, int y) {
		return isIn(x, y);
	}

	public boolean onMouseRightDown(int x, int y) {
		return isIn(x, y);
	}

	public boolean onMouseRightUp(int x, int y) {
		return isIn(x, y);
	}

	protected boolean isIn(int x, int y) {
		return x>=this.x && y>=this.y && x<=this.x+width && y<=this.y+height;
	}
}
