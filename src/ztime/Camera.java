package ztime;

import ztime.terrain.Case;
import ztime.terrain.Terrain;

public class Camera {
	public static final int tileSize = 16;
	
	private final Terrain terrain;
	private float x, y;
	
	public Camera(Terrain terrain) {
		this.terrain = terrain;
		x = y = terrain.size/2f;
	}
	
	public void render() {
		float halfWidthInTile = ZTime.width/(2f*tileSize);
		float halfHeightInTile = ZTime.height/(2f*tileSize);
		
		float corner1X = Math.max(0, x - halfWidthInTile);
		float corner1Y = Math.max(0, y - halfHeightInTile);
		float corner2X = Math.min(terrain.size-1, x + halfWidthInTile);
		float corner2Y = Math.min(terrain.size-1, y + halfHeightInTile);
		float posPremPixelX = ZTime.width/2f - x*tileSize;
		float posPremPixelY = ZTime.height/2f - y*tileSize;
		
		for(int i=(int)corner1X; i<=corner2X; i++) {
	        for(int j=(int)corner1Y; j<=corner2Y; j++) {
	            Case c = terrain.get(i,j);
	            if (c != null) {

	            	//on calcule où la case doit être peinte
	            	float xp = posPremPixelX+i*tileSize;
	            	float yp = posPremPixelY+j*tileSize;

	            	//on la peint
	            	c.getImage().draw(xp, yp);
	            }
	        }
	    }
	}
}
