package ztime.terrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ztime.terrain.Case.Type;

public class Pathfinder {
	protected static Terrain terrain;
	
	private static final float diagCaseDist = (float)Math.sqrt(2);
	
	private final Map<Integer, PathNode> cache = new HashMap<>();
	
	private static float sq(float x) {
		return x*x;
	}
	
	private float getH(float x, float y, float goalX, float goalY) {
		return (float)Math.sqrt(sq(goalX-x)+sq(goalY-y));
	}

	public static PathNode computePath(float fromX, float fromY, float toX, float toY) {
		final int toXi = (int)toX, toYi = (int)toY;
		if (!terrain.isIn(toXi, toYi) || !terrain.get(toXi, toYi).isWalkable())
			return null;
		else {
			PathNode endNode = new Pathfinder().computePathReversed(
					(int)fromX+0.5f, (int)fromY+0.5f, toXi+0.5f, toYi+0.5f); 
			if (endNode == null)
				return null;
			else {
				return reverseAndAddEndNodes(fromX, fromY, toX, toY, endNode);
			} 
		}
	}

	public static PathNode computePathToRessource(float fromX, float fromY, float toX, float toY, Type caseType) {
		final int toXi = (int)toX, toYi = (int)toY;
		if (!terrain.isIn(toXi, toYi))
			return null;
		else {
			PathNode endNode = new Pathfinder().computePathReversedToResource(
					(int)fromX+0.5f, (int)fromY+0.5f, toXi+0.5f, toYi+0.5f, caseType); 
			if (endNode == null)
				return null;
			else {
				return reverseAndAddEndNodes(fromX, fromY, endNode.x, endNode.y, endNode);
			} 
		}
	}
	
	private static PathNode reverseAndAddEndNodes(float fromX, float fromY, float toX, float toY, PathNode endNode) {
		if (endNode.parent != null) {
			//on ajoute le dernier noeud
			PathNode realEndNode = new PathNode(toX, toY);
			realEndNode.parent = endNode.parent;
			
			//on inverse la parenté jusqu'au commencement
			PathNode lastNode = null;
			for (PathNode node = realEndNode ; node != null;) {
				PathNode next = node.parent;
				node.parent = lastNode;
				lastNode = node;
				node = next;
			}
			
			//on ajoute le premier noeud
			PathNode realStartNode = new PathNode(fromX, fromY);
			realStartNode.parent = lastNode.parent;
			
			return realStartNode;
		} else {
			return new PathNode(toX, toY);
		}
	}
	
	private PathNode computePathReversed(float fromX, float fromY, float toX, float toY) {
		Set<PathNode> openList = new HashSet<>();
		PathNode bestNode = getNode((int)fromX, (int)fromY);
		bestNode.D = 0;
		bestNode.H = getH(fromX, fromX, toX, toY);
		openList.add(bestNode);

		while (!openList.isEmpty()) {
			//si on a terminé
			if ((int)bestNode.x == (int)toX && (int)bestNode.y == (int)toY) {
				return bestNode;
			}

			openList.remove(bestNode);
			
			//on ajoute ses voisins à l'openList
			List<PathNode> voisins = getCachedVoisins(bestNode);
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
							v.H = getH(v.x, v.y, toX, toY);
							v.parent = bestNode;
						} else if (v.D > futurD) {
							v.D = futurD;
							v.H = getH(v.x, v.y, toX, toY);
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
	
	
	
	/** Return a case surrounded by a free walkable case. */
	private PathNode computePathReversedToResource(float fromX, float fromY, float toX, float toY, Type caseType) {
		Set<PathNode> openList = new HashSet<>();
		PathNode bestNode = getNode((int)fromX, (int)fromY);
		bestNode.D = 0;
		bestNode.H = getH(fromX, fromX, toX, toY);
		openList.add(bestNode);

		while (!openList.isEmpty()) {
			openList.remove(bestNode);
			
			//on ajoute ses voisins à l'openList
			List<PathNode> voisins = getCachedVoisins(bestNode);
			for (PathNode v : voisins) {
				//si on a terminé
				if (v.c.type == caseType && !bestNode.c.occupied)
					return bestNode;
				else if (v.c.isWalkable()) {
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
							v.H = getH(v.x, v.y, toX, toY);
							v.parent = bestNode;
						} else if (v.D > futurD) {
							v.D = futurD;
							v.H = getH(v.x, v.y, toX, toY);
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
	
	private List<PathNode> getCachedVoisins(PathNode node) {
		int x = (int)node.x, y = (int)node.y;
		List<PathNode> voisins = new ArrayList<>();
		int[] xs = new int[] {x+1, x+1, x,   x-1, x-1, x-1, x,   x+1};
		int[] ys = new int[] {y,   y+1, y+1, y+1, y,   y-1, y-1, y-1};
		for (int i=0; i<8; i++) {
			if (terrain.isIn(xs[i], ys[i])) {
				voisins.add(getNode(xs[i], ys[i]));
			}
		}
		return voisins;
	}
	
	private PathNode getNode(int x, int y) {
		final int location = x + (y<<16);
		PathNode cached = cache.get(location);
		if (cached == null) {
			PathNode newNode = new PathNode(x+0.5f,y+0.5f);
			cache.put(location, newNode);
			return newNode;
		} else
			return cached;
			
	}
	
	public static void init(Terrain terrain) {
		Pathfinder.terrain = terrain;
	}
}
