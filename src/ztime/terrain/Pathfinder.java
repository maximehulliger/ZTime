package ztime.terrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Pathfinder {
	protected static Terrain terrain;
	
	private static final float diagCaseDist = (float)Math.sqrt(2);
	
	private final Map<Integer, PathNode> cache = new HashMap<>();
	
	private static float sq(float x) {
		return x*x;
	}
	
	private float getH(float x, float y, float goalX, float goalY) {
		return sq(goalX-x)+sq(goalY-y);
	}
	
	public static PathNode computePath(float fromX, float fromY, float toX, float toY) {
		PathNode endNode = new Pathfinder().computePathReversed(fromX, fromY, toX, toY); 
		//on inverse la parenté jusqu'au commencement
		PathNode lastNode = null;
		for ( PathNode node = endNode ; node != null ; node.parent = lastNode, lastNode = node );
		return lastNode;
	}
	
	private PathNode computePathReversed(float fromX, float fromY, float toX, float toY) {
		Set<PathNode> openList = new HashSet<>();
		PathNode bestNode = new PathNode(fromX, fromY);
		bestNode.D = 0;
		bestNode.H = getH(fromX, fromX, toX, toY);
		openList.add(bestNode);
		
		while (!openList.isEmpty()) {
			//si on a terminé, on ajoute le dernier noeud
			if ((int)bestNode.x == (int)toX && (int)bestNode.y == (int)toY) {
				PathNode endNode = new PathNode(toX, toY);
				endNode.parent = bestNode;
				return endNode;
			}
			
			openList.remove(bestNode);
			
			//on ajoute ses voisins à l'openList
			List<PathNode> voisins = getVoisins(bestNode);
			for (PathNode v : voisins) {
				if (v.c.isWalkable()) {
					// selon si la case est en diagonale, on check les autres cases
					final int bnx = (int)bestNode.x,
							vx = (int)v.x,
							bny = (int)bestNode.y,
							vy = (int)v.y;
					final boolean diagonale = bnx!=vx && bny!=vy;
					if (!diagonale || (terrain.get(vx, bny).isWalkable()
							&& terrain.get(bnx, vy).isWalkable())) {
						final float futurD = bestNode.D + (diagonale ? diagCaseDist : 1);
						if (v.isNew()) {
							openList.add(v);
							v.D = futurD;
							v.H = getH(vx, vy, toX, toY);
							v.parent = bestNode;
						} else if (v.D < futurD) {
							v.D = futurD;
							v.H = getH(vx, vy, toX, toY);
							v.parent = bestNode;
						}	
					} 
				}
			}
			
			//on cherche le nouveau meilleur
			float bestF = 0;
			for (PathNode n : openList) {
				final float F = n.D + n.H;
				if (F > bestF) {
					bestF = F;
					bestNode = n;
				}
			}
		}
		
		return null;
	}
	
	private PathNode getNode(int x, int y) {
		Integer location = x + (y<<16);
		PathNode cached = cache.get(location);
		if (cached == null) {
			PathNode newNode = new PathNode(x,y);
			cache.put(location, newNode);
			return newNode;
		} else
			return cached;
	}
	
	private List<PathNode> getVoisins(PathNode node) {
		int x = (int)node.x, y = (int)node.y;
		List<PathNode> voisins = new ArrayList<>();
		if (terrain.isIn(x+1, y))
			voisins.add(getNode(x+1, y));
		if (terrain.isIn(x+1, y+1))
			voisins.add(getNode(x+1, y+1));
		if (terrain.isIn(x, y+1))
			voisins.add(getNode(x, y+1));
		if (terrain.isIn(x-1, y+1))
			voisins.add(getNode(x-1, y+1));
		if (terrain.isIn(x, y-1))
			voisins.add(getNode(x, y-1));
		if (terrain.isIn(x-1, y-1))
			voisins.add(getNode(x-1, y-1));
		if (terrain.isIn(x, y-1))
			voisins.add(getNode(x, y-1));
		if (terrain.isIn(x+1, y-1))
			voisins.add(getNode(x+1, y-1));
		return voisins;
	}
	
	public static void init(Terrain terrain) {
		Pathfinder.terrain = terrain;
	}
}
