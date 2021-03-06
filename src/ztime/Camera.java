package ztime;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import ztime.terrain.Case;
import ztime.terrain.Terrain;

public class Camera {
	public final float tileSize = 32;
	public static final float speed = 40;
	
	private final Terrain terrain;
	private final Input input;
	private final GameContainer gc;
	/** En tile. */
	public final Vector2f pos;
	/** En pixel */
	private final Vector2f dragLastPos = new Vector2f();
	private boolean dragging = false;
	
	public Camera(GameContainer gc, Terrain terrain) {
		this.terrain = terrain;
		this.input = gc.getInput();
		this.gc = gc;
		pos = new Vector2f(terrain.size/2f, terrain.size/2f);
	}
	
	public Vector2f toTerrain(Vector2f screenPos) {
		return pos.copy().add( 
				screenPos.copy().sub(
						new Vector2f(ZTime.width, ZTime.height).scale(0.5f)
						).scale(1f/tileSize));
	}

	public Vector2f toScreen(Vector2f terrainPos) {
		return terrainPos.copy().sub(pos).scale(tileSize).add(
				new Vector2f(ZTime.width, ZTime.height).scale(0.5f));
	}
	
	public void onMouseLeftPressed(int x, int y) {
		dragLastPos.set(x, y);
		dragging = true;
	}
	
	public void onMouseLeftReleased(int x, int y) {
		dragging = false;
	}
	
	public void update() {
		// drag movement
		if (dragging) {
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

	            	//on calcule o� la case doit �tre peinte
	            	float xp = posPremPixelX+i*tileSize;
	            	float yp = posPremPixelY+j*tileSize;

	            	//on la peint
	            	c.getImage().draw(xp, yp, tileSize, tileSize);
	            }
	        }
	    }
	}
}
