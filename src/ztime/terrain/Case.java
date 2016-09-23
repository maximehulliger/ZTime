package ztime.terrain;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import ztime.Camera;
import ztime.Selector.Selectable;

public class Case implements Selectable {
	public enum Type {
        Plaine,
        Terre,
        Sable,
        Vegetation,
        Pierre,
        Eau
    };
    
    private static Image imgPlaine, imgTerre, imgSable, imgVegetation, imgPierre, imgEau;
    
    
    public Type type;
    public boolean occupied = false;
    
    public Case(Type type) {
    	this.type = type;
    }
    
    public static void init() throws SlickException {
    	imgPlaine = new Image("media/plaine.bmp");
    	imgTerre = new Image("media/terre.bmp");
    	imgSable = new Image("media/sable.bmp");
    	imgVegetation = new Image("media/vegetation.bmp");
    	imgPierre = new Image("media/pierre.bmp");
    	imgEau = new Image("media/eau.bmp");
    }
    
    public Image getImage() {
    	switch (type) {
    	case Plaine:
    		return imgPlaine;
    	case Terre:
    		return imgTerre;
    	case Sable:
    		return imgSable;
    	case Vegetation:
    		return imgVegetation;
    	case Pierre:
    		return imgPierre;
    	case Eau:
    		return imgEau;
    	default:
    		return null;
    	}
    }
    
    public boolean isWalkable() {
    	if (occupied)
    		return false;
    	switch (type) {
    	case Plaine:
    	case Terre:
    	case Sable:
    		return true;
    	default:
    		return false;
    	}
    }

	public void drawSelection(Graphics g, int left, int top, Camera cam) {
		g.setColor(Color.black);
		g.drawString("Case type "+type, left, top);
	}

	public void onRightClickSelected(Vector2f point) {}
}
