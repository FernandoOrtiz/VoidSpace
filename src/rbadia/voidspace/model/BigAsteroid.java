package rbadia.voidspace.model;

import rbadia.voidspace.main.GameScreen;

//Class that represents a BigAsteroid. When this asteroid is hitted.
//It should pop 3 little subclass asteroids. A big asteroid is a asteroid but with different image
// and different size


public class BigAsteroid extends Asteroid {

	private int bigAsteroidWidth = getAsteroidWidth()*2;
	private int bigAsteroidHeight = getAsteroidHeight()*2;
	private int bigAsteroidHealth = 200;// Big asteroids should take two shots to destroyed

	public BigAsteroid(GameScreen screen) {
		super(screen);

		this.setLocation(
				getRand().nextInt(screen.getWidth() - bigAsteroidWidth),
				0);
		this.setSize(bigAsteroidWidth, bigAsteroidHeight);
		super.setScorePoints(200);//Big asteroids are set to have 200 points when destroyed

	}

	public int getBigAsteroidHealth() {
		return bigAsteroidHealth;
	}

	public void setBigAsteroidHealth(int bigAsteroidHealth) {
		this.bigAsteroidHealth = bigAsteroidHealth;
	}

}
