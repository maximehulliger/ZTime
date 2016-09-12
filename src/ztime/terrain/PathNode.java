package ztime.terrain;

public class PathNode {
	
	final Case c;
	
	public final float x, y;
    float D = -1;
    float H = -1;
    PathNode parent = null;
    
    public PathNode(float x, float y) {
    	this.c = Pathfinder.terrain.get((int)x, (int)y);
    	this.x = x;
    	this.y = y;
    }
    
    boolean isNew() {
    	return D == -1;
    }
}
