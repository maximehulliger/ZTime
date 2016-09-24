package ztime.terrain;

public class PathNode {
	
	public final Case c;
	
	public final float x, y;
    float D = -1;
    float H = -1;
    public PathNode parent = null;
    
    public PathNode(float x, float y) {
    	this.c = Pathfinder.terrain.get((int)x, (int)y);
    	this.x = x;
    	this.y = y;
    }
    
    boolean isNew() {
    	return H == -1;
    }
}
