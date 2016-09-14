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
		for ( PathNode node = endNode ; node != null;) {
			PathNode next = node.parent;
			node.parent = lastNode;
			lastNode = node;
			node = next; 
		}
		return lastNode;
	}
	
	private PathNode computePathReversed(float fromX, float fromY, float toX, float toY) {
		Set<PathNode> openList = new HashSet<>();
		PathNode bestNode = new PathNode(fromX, fromY);
		bestNode.D = 0;
		bestNode.H = getH(fromX, fromX, toX, toY);
		openList.add(bestNode);
		cache.put((int)fromX + ((int)fromY)<<16, bestNode);
		
		while (!openList.isEmpty()) {
			//si on a terminé, on ajoute le dernier noeud
			if ((int)bestNode.x == (int)toX && (int)bestNode.y == (int)toY) {
				PathNode endNode = new PathNode(toX, toY);
				endNode.parent = bestNode;
				return endNode;
			}
			
			openList.remove(bestNode);
			
			//on ajoute ses voisins à l'openList
			List<PathNode> voisins = getNewVoisins(bestNode);
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
			float bestF = Integer.MAX_VALUE;
			for (PathNode n : openList) {
				final float F = n.D + n.H;
				if (F <= bestF) {
					bestF = F;
					bestNode = n;
				}
			}
		}
		
		return null;
	}
	
	private List<PathNode> getNewVoisins(PathNode node) {
		int x = (int)node.x, y = (int)node.y;
		List<PathNode> voisins = new ArrayList<>();
		int[] xs = new int[] {x+1, x+1, x, x-1, x, x-1, x, x+1};
		int[] ys = new int[] {y, y+1, y+1, y+1, y-1, y-1, y-1, y-1};
		for (int i=0; i<8; i++) {
			int location = xs[i] + (ys[i]<<16);
			if (terrain.isIn(xs[i], ys[i]) && !cache.containsKey(location)) {
				PathNode newNode = new PathNode(xs[i]+0.5f,ys[i]+0.5f);
				cache.put(location, newNode);
				voisins.add(newNode);
			}
		}
		return voisins;
	}
	
	public static void init(Terrain terrain) {
		Pathfinder.terrain = terrain;
	}
}
