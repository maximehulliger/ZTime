package ztime;

public class Time {
	
	public static float deltaTime = 0;
	
	public static void update() {
		int fps = ZTime.gc.getFPS();
		if (fps > 0)
			deltaTime = 1f/fps;
	}
}
