package ztime;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageManager {
	
	public static Image houseIco;
	
	public static void init() throws SlickException {
		houseIco = new Image("media/petiteMaisonIco.bmp");
	}
	
}
