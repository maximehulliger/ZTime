package ztime;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import ztime.terrain.Case;
import ztime.terrain.Terrain;

public class Camera {
	public static final float tileSize = 32;
	public static final float speed = 20;
	
	private final Terrain terrain;
	private final Input input;
	private final GameContainer gc;
	
	private final Vector2f pos;
	private final Vector2f dragLastPos = new Vector2f();
	
	public Camera(GameContainer gc, Terrain terrain) {
		this.terrain = terrain;
		this.input = gc.getInput();
		this.gc = gc;
		pos = new Vector2f(terrain.size/2f, terrain.size/2f);
	}
	
	public void update() {
		// drag movement
		if (input.isMousePressed(0)) {
			dragLastPos.set(input.getMouseX(), input.getMouseY());
		} else if (input.isMouseButtonDown(0)) {
			Vector2f dragDiff = new Vector2f(input.getMouseX(), input.getMouseY()).sub(dragLastPos);
			dragDiff.scale(1/tileSize);
			pos.sub(dragDiff);
			dragLastPos.set(input.getMouseX(), input.getMouseY());
		}
		
		// wasd movement
		float xdiff = 0, ydiff = 0;
		if (input.isKeyDown(Input.KEY_W))
			ydiff --;
		if (input.isKeyDown(Input.KEY_S))
			ydiff ++;
		if (input.isKeyDown(Input.KEY_A))
			xdiff --;
		if (input.isKeyDown(Input.KEY_D))
			xdiff ++;
		
		int fps = gc.getFPS();
		if (fps > 0) {
			pos.x += speed*xdiff/fps;
			pos.y += speed*ydiff/fps;
		}
		
		// remain in the terrain
		pos.x = Math.min(terrain.size, Math.max(0, pos.x));
		pos.y = Math.min(terrain.size, Math.max(0, pos.y));
	}
	
	public void render() {
		float halfWidthInTile = ZTime.width/(2f*tileSize);
		float halfHeightInTile = ZTime.height/(2f*tileSize);
		
		float corner1X = Math.max(0, pos.x - halfWidthInTile);
		float corner1Y = Math.max(0, pos.y - halfHeightInTile);
		float corner2X = Math.min(terrain.size-1, pos.x + halfWidthInTile);
		float corner2Y = Math.min(terrain.size-1, pos.y + halfHeightInTile);
		float posPremPixelX = ZTime.width/2f - pos.x*tileSize;
		float posPremPixelY = ZTime.height/2f - pos.y*tileSize;
		
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
