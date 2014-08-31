package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

/**
 * Represents a ship/space craft.
 *
 */
public class EnemyShip extends Rectangle {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_SPEED = 3;
	private static final int Y_OFFSET = 5; // initial y distance of the ship from the bottom of the screen 

	private int shipWidth = 25;
	private int shipHeight = 25;
	private int speed = DEFAULT_SPEED;
	private boolean sidesBorder = true;
	private boolean bottomBorder= false;
	private Random rand = new Random();
	private long lastEnemyBullet;
	private long cTime;
	private long lastEnemyMove;
	private int xSpeed = DEFAULT_SPEED;
	private int scorePoints = 500;

	/**
	 * Creates a new ship at the default initial location. 
	 * @param screen the game screen
	 */
	public EnemyShip(GameScreen screen){
		this.setLocation(rand.nextInt(screen.getWidth() - this.width+5),
				shipHeight - Y_OFFSET);
		this.setSize(shipWidth, shipHeight);
		this.lastEnemyBullet = System.currentTimeMillis();
		this.lastEnemyMove = cTime;
	}

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
	/**
	 * Get the default ship width
	 * @return the default ship width
	 */
	public int getShipWidth() {
		return shipWidth;
	}

	/**
	 * Get the default ship height
	 * @return the default ship height
	 */
	public int getShipHeight() {
		return shipHeight;
	}

	/**
	 * Returns the current ship speed
	 * @return the current ship speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Set the current ship speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Returns the default ship speed.
	 * @return the default ship speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}

	/**
	 * Returns the enemy ship sides
	 * @return sidesBorder
	 */
	public boolean getSides()
	{
		return sidesBorder;
	}

	/**
	 * Set the sides
	 * @param hi
	 */
	public void setSides(boolean hi)
	{
		this.sidesBorder = hi;

	}

	/**
	 * Set the boottom
	 * @param hi
	 */
	public void setBottom(boolean hi)
	{
		this.bottomBorder = hi;
	}

	/**
	 * Return the bottom
	 * @return bottomBorder
	 */
	public boolean getBottom()
	{
		return bottomBorder;
	}

	/**
	 * Return true if get the fire. Return false if not;
	 * @param lastEnemyBullet
	 * @return 
	 */
	public boolean getFire(long lastEnemyBullet)
	{
		cTime = System.currentTimeMillis();
		if((cTime - this.lastEnemyBullet) > 2000)
		{
			return true;
		}

		else
		{
			return false;
		}
	}

	/**
	 * Set the last Fire
	 */
	public void setLastFire()
	{
		long cTime = System.currentTimeMillis();
		this.lastEnemyBullet = cTime;
	}

	public void setLastMove()
	{
		long cTime = System.currentTimeMillis();
		this.lastEnemyMove = cTime;
	}
	public boolean getMove()
	{
		cTime = System.currentTimeMillis();
		if((cTime - this.lastEnemyMove) > 2000)
		{
			return true;
		}

		else
		{
			return false;
		}
	}

	public int getxSpeed() {
		return xSpeed;
	}

	public void setXSpeed(int speed) {
		this.xSpeed = speed;
	}

}
