package rbadia.voidspace.model;
import java.awt.Rectangle;

/**
 * Represents a bullet fired by a ship.
 */
public class Bullet extends Rectangle {
	private static final long serialVersionUID = 1L;

	private int bulletWidth = 8;
	private int bulletHeight = 8;
	private int speed = 12;

	/**
	 * Creates a new bullet above the ship, centered on it
	 * @param ship
	 */
	public Bullet(Ship ship) {
		this.setLocation(ship.x + ship.width/2 - bulletWidth/2,
				ship.y - bulletHeight);
		this.setSize(bulletWidth, bulletHeight);
	}



	/**
	 * Creates a new bullet above the enemy ship, centered on it
	 * @param enemyShip
	 */
	public Bullet(EnemyShip enemyShip)
	{
		this.setLocation(enemyShip.x + enemyShip.width/2 - bulletWidth/2,
				enemyShip.y + bulletHeight);
		this.setSize(bulletWidth, bulletHeight);
		this.speed = 5;
	}


	/**
	 * Return the bullet's speed.
	 * @return the bullet's speed.
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Set the bullet's speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
