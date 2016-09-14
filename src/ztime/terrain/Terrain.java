package ztime.terrain;

public class Terrain {
	Case[][] terrain;
	
	public final int size;
	
	public Terrain(int size) {
		this.size = size;
		int seed = 107;
		terrain = new Case[size][size];
		for (int i=0; i<size; i++) {
	        for (int j=0; j<size; j++) {
	        	terrain[i][j] = CaseGenerator.genereCase(seed, i, j);
	        }
	    }
	}
	
	public Case get(int x, int y) {
		return terrain[x][y];
	}
	
	public boolean isIn(int x, int y) {
		return x>0 && y>0 && x<size && y<size;
	}
}
