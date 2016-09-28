package ztime.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import ztime.Time;

public class Pane extends GUIElement {

	protected final int x, y, width, height;
	public Color borderColor = Color.black, insideColor = new Color(100, 100, 100);
	public String text = null;
	public Image image = null;
	public boolean isIn = false;
	public static final float overTextWait = 1;
	public String overText = null;
	public float etat = 0;
	public boolean overTextOpen = false;
	public int overX, overY;
	
	
	public Pane(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void update() {
		if (overText != null && isIn) {
			etat += Time.deltaTime;
			if (etat > overTextWait) {
				overTextOpen = true;
			}
		}
	}

	public void draw() {
		graphics.setColor(borderColor);
		graphics.drawRect(x, y, width, height);
		if (image == null) {
			graphics.setColor(insideColor);
			graphics.fillRect(x+1, y+1, width-1, height-1);
		} else {
			image.draw(x+1, y+1, width-1, height-1);
		}
		if (text != null) {
			graphics.setColor(borderColor);
			int textWidth = graphics.getFont().getWidth(text);
			int textHeight = graphics.getFont().getHeight(text);
			graphics.drawString(text, x+width/2-textWidth/2, y+height/2-textHeight/2);
		}
		if (overTextOpen) {
			int textWidth = graphics.getFont().getWidth(overText);
			int textHeight = graphics.getFont().getHeight(overText);
			graphics.setColor(borderColor);
			graphics.drawRect(overX, overY-textHeight-5, textWidth+10, textHeight+10);
			graphics.setColor(insideColor);
			graphics.fillRect(overX+1, overY-textHeight-4, textWidth+9, textHeight+9);
			graphics.setColor(borderColor);
			graphics.drawString(overText, overX+5, overY-textHeight-5);
		}
	}

	public boolean onMouseLeftDown(int x, int y) {
		return isIn;
	}

	public boolean onMouseLeftUp(int x, int y) {
		return isIn;
	}

	public boolean onMouseRightDown(int x, int y) {
		return isIn;
	}

	public boolean onMouseRightUp(int x, int y) {
		return isIn;
	}
	
	public void mouseMoved(int x, int y) {
		isIn = isIn(x,y);
		if (!isIn) {
			overTextOpen = false;
			etat = 0;
		}
		overX = x;
		overY = y;
	}

	protected boolean isIn(int x, int y) {
		return x>=this.x && y>=this.y && x<=this.x+width && y<=this.y+height;
	}
}
