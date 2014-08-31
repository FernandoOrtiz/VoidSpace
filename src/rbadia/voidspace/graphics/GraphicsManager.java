package rbadia.voidspace.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigAsteroid;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.LifeBar;
import rbadia.voidspace.model.LittleAsteroid;
import rbadia.voidspace.model.Ship;


/**
 * Manages and draws game graphics and images.
 */
public class GraphicsManager {
	private BufferedImage shipImg;
	private BufferedImage bulletImg;
	private BufferedImage asteroidImg;
	private BufferedImage asteroidExplosionImg;
	private BufferedImage shipExplosionImg;
	private BufferedImage bigExplosionImg;
	private BufferedImage bigAsteroidImg;
	private BufferedImage littleAsteroidImg;
	private BufferedImage enemyShipImg;
	private BufferedImage enemyBulletImg;

	/**
	 * Creates a new graphics manager and loads the game images.
	 */
	public GraphicsManager(){
		// load images
		try {
			this.shipImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/ship1.png"));
			this.asteroidImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid.png"));
			this.asteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));
			this.shipExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/shipExplosion.png"));
			this.bulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bullet.png"));
			this.bigAsteroidImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BigAsteroid1.png"));
			this.bigExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BigExplosion.png"));
			this.littleAsteroidImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/SubAsteroid.png"));
			this.enemyShipImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/TheEnemy1.PNG"));
			this.enemyBulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/enemyBullet.png"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
					"VoidSpace - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}


	/**
	 * Draws an  little asteroid image to the specified graphics canvas.
	 * @param asteroid the asteroid to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawLittleAsteroid(LittleAsteroid asteroid, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(littleAsteroidImg, asteroid.x, asteroid.y, observer);
	}

	/**
	 * Draws an little  asteroid explosion image to the specified graphics canvas.
	 * @param asteroidExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawLittleAsteroidExplosion(Rectangle asteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidExplosionImg, asteroidExplosion.x, asteroidExplosion.y, observer);
	}

	/**
	 * Draws a ship with boos fire to the specified graphics canvas
	 * @param shipUpDown
	 * @param g2d
	 * @param observer
	 */

	public void drawEnemyShip(EnemyShip enemyShip, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(enemyShipImg, enemyShip.x, enemyShip.y, observer);
	}



	//draw life
	public void drawLife(Rectangle life, Graphics2D g2d){

		if(LifeBar.getLife()==100){
			g2d.setColor(Color.GREEN);}
		if(LifeBar.getLife()==75){
			g2d.setColor(Color.YELLOW);}
		if(LifeBar.getLife()==50){
			g2d.setColor(Color.decode("#FF8C00"));}
		if(LifeBar.getLife()==25){
			g2d.setColor(Color.RED);}

		g2d.fillRect(380, 380, LifeBar.getLife(), 10);

		g2d.setColor(Color.CYAN);
		g2d.drawRect(379, 379, 101, 11);
		g2d.drawString("HP", 358, 390);


	}

	/**
	 * Draws a enemy bullet image to the specified graphics canvas.
	 * @param enemybullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawEnemyBullet(Bullet enemyBullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(enemyBulletImg, enemyBullet.x, enemyBullet.y, observer);
	}




	/**
	 * Draws a ship image to the specified graphics canvas.
	 * @param ship the ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawShip(Ship ship, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipImg, ship.x, ship.y, observer);
	}

	/**
	 * Draws a bullet image to the specified graphics canvas.
	 * @param bullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBullet(Bullet bullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bulletImg, bullet.x, bullet.y, observer);
	}

	/**
	 * Draws an asteroid image to the specified graphics canvas.
	 * @param asteroid the asteroid to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroid(Asteroid asteroid, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidImg, asteroid.x, asteroid.y, observer);
	}

	/**
	 * Draws an bigAsteroid image to the specified graphics canvas.
	 * @param asteroid the asteroid to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBigAsteroid(BigAsteroid bigAsteroid, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bigAsteroidImg, bigAsteroid.x, bigAsteroid.y, observer);
	}







	/**
	 * Draws a ship explosion image to the specified graphics canvas.
	 * @param shipExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawShipExplosion(Rectangle shipExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipExplosionImg, shipExplosion.x, shipExplosion.y, observer);
	}

	/**
	 * Draws an asteroid explosion image to the specified graphics canvas.
	 * @param asteroidExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroidExplosion(Rectangle asteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidExplosionImg, asteroidExplosion.x, asteroidExplosion.y, observer);
	}



	/**
	 * Draws a bigAsteroid explosion image to the specified graphics canvas.
	 * @param asteroidExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBigAsteroidExplosion(Rectangle asteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bigExplosionImg, asteroidExplosion.x, asteroidExplosion.y, observer);
	}








}
