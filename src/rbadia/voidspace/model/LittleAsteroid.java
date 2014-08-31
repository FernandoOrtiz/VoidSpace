package rbadia.voidspace.model;

import rbadia.voidspace.main.GameScreen;

//Little asteroids will appear only when a big asteroid is hit
//They will live for a very short peroid of time until they are hit by a bullet or ship collision or have 
// reached the end of the map
public class LittleAsteroid extends Asteroid {




	private int littleAsteroidWidth = getAsteroidWidth();
	private int littleAsteroidHeight = getAsteroidHeight();

	public LittleAsteroid(GameScreen screen) {
		super(screen);

		//Little asteroids will initially be place at the top of the screen for now!
		this.setLocation(getRand().nextInt(screen.getWidth() - littleAsteroidWidth),0);
		this.setSize(littleAsteroidWidth, littleAsteroidHeight);
		//little asteroids are valued to 50 points when you destroyed one
		super.setScorePoints(50);

	}







}
