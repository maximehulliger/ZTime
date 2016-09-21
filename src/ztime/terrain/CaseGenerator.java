package ztime.terrain;

public class CaseGenerator {

	static Case genereCase(int seed, int x, int y) {
	    float hauteur = computePerlin(x,y,60,0.5f,seed);
	    //on regarde la hauteur.
	    if (hauteur<0.35f) return new Case(Case.Type.Eau);
	    else if (hauteur>0.65f) return new ResourceCase(Case.Type.Pierre);
	
	    float humidite = computePerlin(x,y,60,0.5f,seed+1);
	    //on regarde l'humidité
	    if (humidite>0.6f) return new ResourceCase(Case.Type.Vegetation);
	    else if (humidite<0.3f) return new Case(Case.Type.Sable);
	    else return new Case(Case.Type.Plaine);
	}
	
	static float computePerlin(int x, int y, int pasInitial, float persistence, int seed) {
		//on calcul un bruit cohérent pour les position x+y
	    int NbOctave = 4;
	    float hauteur = 0f;

	    for (int i=0; i<NbOctave; i++) {
	        float pe=pow(persistence, i);
	        int pa=(int) (pasInitial/(pow(2, i)));

	        int palierPrecX = (x/pa)*pa;
	        int palierProcX = palierPrecX+pa;
	        int palierPrecY = (y/pa)*pa;
	        int palierProcY = palierPrecY+pa;

	        float posX = ((float)(x-palierPrecX))/(float)pa;
	        float posY = ((float)(y-palierPrecY))/(float)pa;

	        float a = randomPerso(palierPrecX/pa, palierPrecY/pa, seed);
	        float b = randomPerso(palierProcX/pa, palierPrecY/pa, seed);
	        float c = randomPerso(palierPrecX/pa, palierProcY/pa, seed);
	        float d = randomPerso(palierProcX/pa, palierProcY/pa, seed);

	        hauteur+=pe*interpole2D(a, b, c, d, posX, posY);
	    }
	    //on ramène à 1
	    hauteur *= (1-persistence)/(1-pow(persistence, NbOctave));
	    //return un nobre en 0 et 1;
	    return hauteur;
	}
	
    static float interpole2D(float a, float b, float c, float d, float x, float y) {
    	return interpole(interpole(a, b, x), interpole(c, d, x), y);
    }
    
    static float interpole(float a, float b, float x) {
    	//interpolation cosinus
        float f = (1 - (float)Math.cos(x * Math.PI)) * 0.5f;
    	return  a*(1-f) + b*f;
    }

    static float pow(float n, int puissance) {
    	float x = 1;
    	for (int i=0; i<puissance; i++)
    		x *= n;
    	return x;
    }

    static float randomPerso(int x, int y, int seed) {
    	x = x*x+3*y+seed*seed-seed;
    	x = (x<<13) ^ x;
    	float z = ( 1f - ( (x * (x * x * 15731 + 789221) + 1376312589) & 2147483647) / 1073741824f);
    	if (z<0) z += 1.0;
    	return z;
    }
}
