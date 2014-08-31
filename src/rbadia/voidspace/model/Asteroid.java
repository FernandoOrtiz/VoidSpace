package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class Asteroid extends Rectangle {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_SPEED = 4;

	private int asteroidWidth = 32;
	private int asteroidHeight = 32;
	private int speed = DEFAULT_SPEED;

	//When an asteroid is destroyed you get 100 points
	private int scorePoints =100;



	/**
	 * @return the scorePoints
	 */
	public int getScorePoints() {
		return scorePoints;
	}

	/**
	 * @param scorePoints the scorePoints to set
	 */
	public void setScorePoints(int scorePoints) {
		this.scorePoints = scorePoints;
	}

	private int movement; // Asteroid trajectory movements
	private int xSpeed = DEFAULT_SPEED; // Asteroid travel speed
	private boolean bottom = false; // Detection variable if a object of the game has reached the bottom of the frame


	private Random rand = new Random();

	/**
	 * Crates a new asteroid at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public Asteroid(GameScreen screen){
		this.setLocation(
				getRand().nextInt(screen.getWidth() - asteroidWidth),
				0);
		this.setSize(asteroidWidth, asteroidHeight);
	}

	public int getAsteroidWidth() {
		return asteroidWidth;
	}
	public int getAsteroidHeight() {
		return asteroidHeight;
	}

	/**
	 * Returns the current asteroid speed
	 * @return the current asteroid speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Set the current asteroid speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Returns the default asteroid speed.
	 * @return the default asteroid speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}

	public Random getRand() {
		return rand;
	}

	public void setRand(Random rand) {
		this.rand = rand;
	}




	/**
	 * Set the movement trajectory of the asteroid
	 * @param move
	 */
	public void setMove(int move)
	{
		this.movement = move;
	}
	/**
	 * Return the move of the asteroid
	 * @return movement
	 */
	public int getmove() {
		return movement;
	}

	/**
	 * Return the x speed of the asteroid 
	 * @return xSpeed
	 */
	public int getxSpeed() {
		return xSpeed;
	}

	/**
	 * Set the x speed of the asteroid 
	 * @param speed the speed to set
	 */
	public void setXSpeed(int speed) {
		this.xSpeed = speed;
	}

	/**
	 * Return the bottom of the asteroid
	 * @return bottom
	 */
	public boolean getBottom() 
	{
		return bottom;
	}

	/**
	 * Set the bottom of the asteroid
	 * @param bottom 
	 */
	public void setBottom(boolean bottom) 
	{
		this.bottom = bottom;
	}

















}
