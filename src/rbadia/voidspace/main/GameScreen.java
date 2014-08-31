package rbadia.voidspace.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;



import javax.swing.JLabel;
import javax.swing.JPanel;



import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigAsteroid;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.LifeBar;
import rbadia.voidspace.model.LittleAsteroid;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;
// finally
/**
 * Main game screen. Handles all game graphics updates and some of the game logic.
 */
public class GameScreen extends JPanel {
	private static final long serialVersionUID = 1L;


	private BufferedImage backBuffer;
	private Graphics2D g2d;


	private static final int NEW_SHIP_DELAY = 500;
	private static final int NEW_ASTEROID_DELAY = 500;
	private static final int NEW_ENEMYSHIP_DELAY = 500;






	private long lastShipTime;
	private long lastAsteroidTime;
	private long lastEnemyShipTime;
	private long lastEnemyBulletTime;

	private String messeage = "Survive !!!";




	/**
	 * @return the messeage
	 */
	public String getMesseage() {
		return messeage;
	}


	/**
	 * @param messeage the messeage to set
	 */
	public void setMesseage(String messeage) {
		this.messeage = messeage;
	}
	private Rectangle asteroidExplosion;
	private Rectangle shipExplosion;


	private Rectangle bigAsteroidExplosion;


	private Rectangle littleAsteroidExplosion;


	private Rectangle life;


	private JLabel shipsValueLabel;
	private JLabel destroyedValueLabel;
	private JLabel scoreValueLabel;
	private JLabel levelValueLabel;
	private JLabel enemyShipDestroyedValueLabel;



	private Random rand;


	private Font originalFont;
	private Font bigFont;
	private Font biggestFont;


	private GameStatus status;
	private SoundManager soundMan;
	private GraphicsManager graphicsMan;
	private GameLogic gameLogic;
	private boolean detection = false;

	private double BigX;
	private double BigY;
	private Point BigLocation;
	private double BigX2;
	private double BigY2;
	private Point BigLocation2;
	private int levelStantard=1000;// Incremente level by 1000 each level up




	/**
	 * This method initializes 
	 * 
	 */
	public GameScreen() {
		super();
		// initialize random number generator
		rand = new Random();


		initialize();


		// init graphics manager
		graphicsMan = new GraphicsManager();


		// init back buffer image
		backBuffer = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
	}


	/**
	 * Initialization method (for VE compatibility).
	 */
	private void initialize() {
		// set panel properties
		this.setSize(new Dimension(500, 400));
		this.setPreferredSize(new Dimension(500, 400));
		this.setBackground(Color.BLACK);
	}


	/**
	 * Update the game screen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw current backbuffer to the actual game screen
		g.drawImage(backBuffer, 0, 0, this);
	}


	/**
	 * Update the game screen's backbuffer image.
	 */
	public void updateScreen(){
		Ship ship = gameLogic.getShip();
		Asteroid asteroid = gameLogic.getAsteroid();
		BigAsteroid bigAsteroid = gameLogic.getBigAsteroid();

		List<Bullet> bullets = gameLogic.getBullets();





		List<BigAsteroid> bigAsteroids = gameLogic.getBigAsteroids();
		List<Asteroid> asteroids = gameLogic.getAsteroids(); // get the additional asteroids to draw


		LittleAsteroid littleAsteroid = gameLogic.getLittleAsteroid();
		EnemyShip enemy = gameLogic.getEnemyShip();
		List<Bullet> enemyBullets = gameLogic.getEnemyBullets();
		List<EnemyShip> enemies = gameLogic.getEnemies();
		List<LittleAsteroid> littles = gameLogic.getLittleAsteroids();




		// set orignal font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}


		// erase screen
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);


		// draw 50 random stars
		drawStars(50);


		// if the game is starting, draw "Get Ready" message
		if(status.isGameStarting()){
			drawGetReady();
			return;
		}


		// if the game is over, draw the "Game Over" message
		if(status.isGameOver()){
			// draw the message
			drawGameOver();


			long currentTime = System.currentTimeMillis();
			// draw the explosions until their time passes
			if((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY){
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
			if((currentTime - lastShipTime) < NEW_SHIP_DELAY){
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
			return;
		}


		// the game has not started yet
		if(!status.isGameStarted()){
			// draw game title screen
			initialMessage(messeage);
			return;
		}


		//Draw objects to the frame
		/*******************************************************************/



		for(int i=0; i<enemyBullets.size(); i++){
			Bullet enemyBullet = enemyBullets.get(i);
			graphicsMan.drawEnemyBullet(enemyBullet, g2d, this);
			boolean remove = gameLogic.moveEnemyBullet(enemyBullet);
			if(remove){
				enemyBullets.remove(i);
				i--;
			}
		}


		for(int i=0; i<enemyBullets.size(); i++){
			Bullet enemyBullet = enemyBullets.get(i);
			graphicsMan.drawEnemyBullet(enemyBullet, g2d, this);
			boolean remove = gameLogic.moveEnemyBullet(enemyBullet);
			if(remove){
				enemyBullets.remove(i);
				i--;
			}
		}
		// draw enemy ship
		for(EnemyShip s: enemies )
		{
			if(!s.getBottom()){
				// draw it in its current location
				graphicsMan.drawEnemyShip(s, g2d, this);
				gameLogic.enemyMoveDown(s);
				if(s.getMove())
				{
					if(s.getY() + s.getSpeed() < this.getHeight())
					{
						gameLogic.enemyMoveRight(s);
						graphicsMan.drawEnemyShip(s, g2d, this);
					}
					else
					{
						s.setBottom(true);
						s.setLastMove();
					}
				}


			}
			else{
				// draw a new one
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastEnemyShipTime) > NEW_ENEMYSHIP_DELAY){
					lastEnemyShipTime = currentTime;
					status.setEnemyNewShip(false);
					s.setBottom(false);
					s.setLocation(rand.nextInt(getWidth() - s.width+5), 0);


				}
				else{
					// draw explosion
					//graphicsMan.drawShipExplosion(enemyShipExplosion, g2d, this);
				}
			}
		}
		for(EnemyShip e: enemies )
		{
			if(e.getFire(lastEnemyBulletTime)){
				e.setLastFire();
				gameLogic.enemyFireBullet(e);
			}
		}



		// draw little asteroid


		if(detection && !status.isNewLittleAsteroid()){



			// draw the asteroid until it reaches the bottom of the screen
			if(littleAsteroid.getY() + littleAsteroid.getSpeed() < this.getHeight()){

				gameLogic.moveAsteroidDown(littleAsteroid);
				graphicsMan.drawLittleAsteroid(littleAsteroid, g2d, this);


			}

		}



		// draw bigAsteroid
		if(!status.isNewBigAsteroid()){


			// draw the asteroid until it reaches the bottom of the screen
			if(bigAsteroid.getY() + bigAsteroid.getSpeed() < this.getHeight()){
				bigAsteroid.translate(0, bigAsteroid.getSpeed()/3);
				graphicsMan.drawBigAsteroid(bigAsteroid, g2d, this);
			}
			else{
				bigAsteroid.setLocation(rand.nextInt(getWidth() - bigAsteroid.width), 0);
			}
		}
		else{
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
				// draw a new asteroid
				lastAsteroidTime = currentTime;
				status.setNewBigAsteroid(false);
				bigAsteroid.setLocation(rand.nextInt(getWidth() - bigAsteroid.width), 0);
			}
			else{
				// draw explosion
				graphicsMan.drawBigAsteroidExplosion(bigAsteroidExplosion, g2d, this);
			}
		}




		// draw asteroid
		if(!status.isNewAsteroid()){
			// draw the asteroid until it reaches the bottom of the screen
			if(asteroid.getY() + asteroid.getSpeed() < this.getHeight()){
				asteroid.translate(0, asteroid.getSpeed());
				graphicsMan.drawAsteroid(asteroid, g2d, this);
			}
			else{
				asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), 0);
			}
		}
		else{
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
				// draw a new asteroid
				lastAsteroidTime = currentTime;
				status.setNewAsteroid(false);
				asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), 0);
			}
			else{
				// draw explosion
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}






		// draw  additional asteroids
		for(Asteroid e: asteroids)
		{
			if(!e.getBottom())
			{
				// draw the asteroid until it reaches the bottom of the screen
				if(e.getY() + e.getSpeed() < this.getHeight())
				{
					//Speed no so fast
					e.setSpeed(2);
					gameLogic.moveAsteroids(e);
					graphicsMan.drawAsteroid(e, g2d, this);
				}
				else
				{
					e.setMove(rand.nextInt(3));
					e.setLocation(rand.nextInt(getWidth() - e.width), 0);
				}
			}
			else
			{
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY)
				{
					// draw a new asteroid
					lastAsteroidTime = currentTime;
					status.setNewAsteroid(false);
					e.setBottom(false);
					e.setLocation(rand.nextInt(getWidth() - e.width), 0);
				}
				else
				{
					// draw explosion
					graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);


				}
			}
		}

		//////////////////////////////////////////////////
		for(BigAsteroid e: bigAsteroids)
		{
			if(!e.getBottom())
			{
				// draw the asteroid until it reaches the bottom of the screen
				if(e.getY() + e.getSpeed() < this.getHeight())
				{
					//Speed no so fast
					e.setSpeed(3);
					gameLogic.moveBigAsteroids(e);
					graphicsMan.drawBigAsteroid(e, g2d, this);
				}
				else
				{
					e.setMove(rand.nextInt(3));
					e.setLocation(rand.nextInt(getWidth() - e.width), 0);
				}
			}
			else
			{
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY)
				{
					// draw a new asteroid
					lastAsteroidTime = currentTime;
					status.setNewBigAsteroid(false);
					e.setBottom(false);
					e.setLocation(rand.nextInt(getWidth() - e.width), 0);
				}
				else
				{
					// draw explosion
					graphicsMan.drawBigAsteroidExplosion(bigAsteroidExplosion, g2d, this);


				}
			}
		}



		if(detection && !status.isNewLittleAsteroid()){
			for(LittleAsteroid e: littles)
			{
				if(!e.getBottom())
				{
					// draw the asteroid until it reaches the bottom of the screen
					if(e.getY() + e.getSpeed() < this.getHeight())
					{
						//Speed no so fast
						e.setSpeed(3);

						gameLogic.moveLittleAsteroids(e);
						graphicsMan.drawLittleAsteroid(e, g2d, this);

					}


				}
			}

		}





		// draw bullets
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			graphicsMan.drawBullet(bullet, g2d, this);


			boolean remove = gameLogic.moveBullet(bullet);
			if(remove){
				bullets.remove(i);
				i--;
			}
		}


		// draw ship
		if(!status.isNewShip()){
			// draw it in its current location
			graphicsMan.drawShip(ship, g2d, this);


		}
		else{
			// draw a new one
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastShipTime) > NEW_SHIP_DELAY){
				lastShipTime = currentTime;
				status.setNewShip(false);
				ship = gameLogic.newShip(this);
			}
			else{
				// draw explosion
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);


			}
		}


		//draw life bar
		if(!status.isGameOver()){
			// draw it in its current location
			graphicsMan.drawLife(life, g2d);
		}
		else{
			// draw a new one
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastShipTime) > NEW_SHIP_DELAY){
				lastShipTime = currentTime;
				status.setNewLife(false);
				life = gameLogic.newLifeBar(this);
			}
			else{


			}
		}




		/*********************************************************************************************************/








		//Collision detections 
		/*****************************************************************************************************/


		// check enemy bullets-ship collisions
		for(int i=0; i<enemyBullets.size(); i++)
		{
			Bullet enemyBullet = enemyBullets.get(i);
			if(ship.intersects(enemyBullet))
			{


				LifeBar.setLife(25);




				status.setEnemyShipDestroyed(status.getEnemyShipDestroyed()+ 1);
				status.setScore(status.getScore() + enemy.getScorePoints());
				//Check if we need to level up
				if(status.getScore()>=(status.getLevel()*levelStantard))
				{
					gameLogic.levelUp();									
				}




				// decrease number of ships left
				if(LifeBar.getLife()==0){
					status.setShipsLeft(status.getShipsLeft() - 1);
					LifeBar.resetLife();



					// "remove" ship
					shipExplosion = new Rectangle(
							ship.x,
							ship.y,
							ship.width,
							ship.height);
					ship.setLocation(-ship.width, -ship.height);
					status.setNewShip(true);
					lastShipTime = System.currentTimeMillis();

					// play asteroid explosion sound
					soundMan.playShipExplosionSound();
				}                           



				// remove bullet
				enemyBullets.remove(i);
				break;


			}        
		}


		// check enemyship-ship collision
		for(EnemyShip s: enemies )
		{
			if(s.intersects(ship))
			{

				LifeBar.setLife(25);



				status.setEnemyShipDestroyed(status.getEnemyShipDestroyed()+ 1);
				status.setScore(status.getScore() + enemy.getScorePoints());
				//Check if we need to level up
				if(status.getScore()>=(status.getLevel()*levelStantard))
				{
					gameLogic.levelUp();									
				}


				// "remove" enemy ship
				shipExplosion = new Rectangle(
						s.x,
						s.y,
						s.width,
						s.height);
				s.setLocation(-s.width, -s.height);
				//status.setEnemyNewShip(true);
				lastEnemyShipTime = System.currentTimeMillis();

				// decrease number of ships left
				if(LifeBar.getLife()==0){
					status.setShipsLeft(status.getShipsLeft() - 1);
					LifeBar.resetLife();

					// "remove" ship
					shipExplosion = new Rectangle(
							ship.x,
							ship.y,
							ship.width,
							ship.height);
					ship.setLocation(this.getWidth() + ship.width, -ship.height);
					status.setNewShip(true);
					lastShipTime = System.currentTimeMillis();
				}



				// play ship explosion sound
				soundMan.playShipExplosionSound();
				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();
			}
		}


		// check bullet-enemyShip single collisions
		for(int i=0; i<bullets.size(); i++)
		{

			Bullet bullet = bullets.get(i);
			if(enemy.intersects(bullet))
			{


				status.setEnemyShipDestroyed(status.getEnemyShipDestroyed()+ 1);
				status.setScore(status.getScore() + enemy.getScorePoints());
				//Check if we need to level up
				if(status.getScore()>=(status.getLevel()*levelStantard))
				{
					gameLogic.levelUp();									
				}


				// "remove"  enemy ship
				shipExplosion = new Rectangle(
						enemy.x,
						enemy.y,
						enemy.width,
						enemy.height);
				enemy.setLocation(-enemy.width, -enemy.height);
				status.setEnemyNewShip(true);
				enemy.setBottom(true);
				lastEnemyShipTime = System.currentTimeMillis();




				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();
				enemy.setLastFire();


				// remove bullet
				bullets.remove(i);
				break;


			}        
		}




		//ship colition with single enemy
		if(ship.intersects(enemy))
		{


			LifeBar.setLife(25);

			status.setEnemyShipDestroyed(status.getEnemyShipDestroyed() + 1);
			// "remove" asteroid
			shipExplosion = new Rectangle(
					enemy.x,
					enemy.y,
					enemy.width,
					enemy.height);
			enemy.setLocation(-enemy.width, -enemy.height);
			status.setEnemyNewShip(true);
			enemy.setBottom(true);
			lastAsteroidTime = System.currentTimeMillis();


			// decrease number of ships left
			if(LifeBar.getLife()==0){
				status.setShipsLeft(status.getShipsLeft() - 1);
				LifeBar.resetLife();

				// "remove" ship
				shipExplosion = new Rectangle(
						ship.x,
						ship.y,
						ship.width,
						ship.height);
				ship.setLocation(this.getWidth() + ship.width, -ship.height);
				status.setNewShip(true);
				lastShipTime = System.currentTimeMillis();

			}   




			// play ship explosion sound
			soundMan.playShipExplosionSound();

		}





		// check bullet-enemyShip collisions
		for(int i=0; i<bullets.size(); i++)
		{
			for(EnemyShip s: enemies )
			{
				Bullet bullet = bullets.get(i);
				if(s.intersects(bullet))
				{


					status.setEnemyShipDestroyed(status.getEnemyShipDestroyed()+ 1);
					status.setScore(status.getScore() + enemy.getScorePoints());
					//Check if we need to level up
					if(status.getScore()>=(status.getLevel()*levelStantard))
					{
						gameLogic.levelUp();									
					}


					// "remove"  enemy ship
					shipExplosion = new Rectangle(
							s.x,
							s.y,
							s.width,
							s.height);
					s.setLocation(-s.width, -s.height);
					status.setEnemyNewShip(true);
					s.setBottom(true);
					lastEnemyShipTime = System.currentTimeMillis();




					// play asteroid explosion sound
					soundMan.playAsteroidExplosionSound();
					s.setLastFire();


					// remove bullet
					bullets.remove(i);
					break;


				}        
			}
		}




		// check bullet-asteroid collisions
		for(Asteroid e : asteroids){
			// check vertically bullet-asteroid collisions
			for(int i=0; i<bullets.size(); i++){
				Bullet bullet = bullets.get(i);
				if(e.intersects(bullet)){
					// increase asteroids destroyed count
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

					status.setScore(status.getScore() + asteroid.getScorePoints());
					//Check we need to level up
					if(status.getScore()>=(status.getLevel()*levelStantard))
					{
						gameLogic.levelUp();									
					}


					// "remove" asteroid
					asteroidExplosion = new Rectangle(
							e.x,
							e.y,
							e.width,
							e.height);
					e.setLocation(-e.width, -e.height);
					// status.setNewAsteroid(true);
					e.setBottom(true);
					lastAsteroidTime = System.currentTimeMillis();




					// play asteroid explosion sound
					soundMan.playAsteroidExplosionSound();


					// remove bullet
					bullets.remove(i);
					//asteroids.remove(j);
					break;
				}
			}




		}








		// check bullet-asteroid with the initial asteroid 
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(asteroid.intersects(bullet)){



				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

				status.setScore(status.getScore() + asteroid.getScorePoints());
				//Check we need to level up
				if(status.getScore()>=(status.getLevel()*levelStantard))
				{
					gameLogic.levelUp();									
				}
				// "remove" asteroid
				asteroidExplosion = new Rectangle(
						asteroid.x,
						asteroid.y,
						asteroid.width,
						asteroid.height);
				asteroid.setLocation(-asteroid.width, -asteroid.height);
				status.setNewAsteroid(true);
				lastAsteroidTime = System.currentTimeMillis();




				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();


				// remove bullet
				bullets.remove(i);
				//asteroids.remove(j);
				break;
			}
		}






		// check vertically bullet collision with big asteroid
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(bigAsteroid.intersects(bullet)){

				//Decrement life
				bigAsteroid.setBigAsteroidHealth(bigAsteroid.getBigAsteroidHealth()-100);


				detection = true;// Little asteroids should appears


				if(detection){
					BigX = bigAsteroid.getCenterX();
					BigY = bigAsteroid.getCenterY();
					BigLocation = new Point();
					BigLocation.setLocation(BigX, BigY);
					littleAsteroid.setLocation(BigLocation);
					for(LittleAsteroid n: littles)
					{
						n.setLocation(BigLocation);
					}

					// graphicsMan.drawLittleAsteroid(littleAsteroid, g2d, this);
				}

				if(bigAsteroid.getBigAsteroidHealth() <= 0) {

					// increase asteroids destroyed count
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

					status.setScore(status.getScore() + bigAsteroid.getScorePoints());
					//Check we need to level up
					if(status.getScore()>=(status.getLevel()*levelStantard))
					{
						gameLogic.levelUp();									
					}





					// "remove" asteroid
					bigAsteroidExplosion = new Rectangle(
							bigAsteroid.x,
							bigAsteroid.y,
							bigAsteroid.width,
							bigAsteroid.height);
					bigAsteroid.setLocation(-bigAsteroid.width, -bigAsteroid.height);
					status.setNewBigAsteroid(true);
					bigAsteroid.setBottom(true);


					lastAsteroidTime = System.currentTimeMillis();




					// play asteroid explosion sound
					soundMan.playAsteroidExplosionSound();


					//Restore health

					// remove bullet
					bullets.remove(i);
					bigAsteroid.setBigAsteroidHealth(200);
					//asteroids.remove(j);
					break;

				}
			}
		}


		//Check collision if the big asteroid hits the  ship
		if(ship.intersects(bigAsteroid))
		{


			LifeBar.setLife(25);

			status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);
			// "remove" asteroid
			bigAsteroidExplosion = new Rectangle(
					bigAsteroid.x,
					bigAsteroid.y,
					bigAsteroid.width,
					bigAsteroid.height);
			bigAsteroid.setLocation(-bigAsteroid.width, -bigAsteroid.height);
			status.setNewBigAsteroid(true);
			bigAsteroid.setBottom(true);
			lastAsteroidTime = System.currentTimeMillis();


			// decrease number of ships left
			if(LifeBar.getLife()==0){
				status.setShipsLeft(status.getShipsLeft() - 1);
				LifeBar.resetLife();

				// "remove" ship
				shipExplosion = new Rectangle(
						ship.x,
						ship.y,
						ship.width,
						ship.height);
				ship.setLocation(this.getWidth() + ship.width, -ship.height);
				status.setNewShip(true);
				lastShipTime = System.currentTimeMillis();

			}   




			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}



		// check ship-asteroid collisions for the additional asteroids 
		for(Asteroid e: asteroids){
			if(e.intersects(ship)){


				LifeBar.setLife(25);


				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

				status.setScore(status.getScore() + asteroid.getScorePoints());
				//Check we need to level up
				if(status.getScore()>=(status.getLevel()*levelStantard))
				{
					gameLogic.levelUp();									
				}


				// "remove" asteroid
				asteroidExplosion = new Rectangle(
						e.x,
						e.y,
						e.width,
						e.height);
				e.setLocation(-e.width, -e.height);
				//status.setNewAsteroid(true);
				lastAsteroidTime = System.currentTimeMillis();


				// decrease number of ships left
				if(LifeBar.getLife()==0){
					status.setShipsLeft(status.getShipsLeft() - 1);
					LifeBar.resetLife();

					// "remove" ship
					shipExplosion = new Rectangle(
							ship.x,
							ship.y,
							ship.width,
							ship.height);
					ship.setLocation(this.getWidth() + ship.width, -ship.height);
					status.setNewShip(true);
					lastShipTime = System.currentTimeMillis();
				}    



				// play ship explosion sound
				soundMan.playShipExplosionSound();
				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();
			}
		}
		///////////////////////////////////////////////////////////////////////
		for(BigAsteroid e: bigAsteroids){
			if(e.intersects(ship)){


				LifeBar.setLife(25);


				// increase asteroids destroyed count
				status.setBigAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);


				//Check we need to level up
				if(status.getScore()>=(status.getLevel()*levelStantard))
				{
					gameLogic.levelUp();									
				}


				// "remove" asteroid
				bigAsteroidExplosion = new Rectangle(
						e.x,
						e.y,
						e.width,
						e.height);
				e.setLocation(-e.width, -e.height);
				//status.setNewAsteroid(true);
				lastAsteroidTime = System.currentTimeMillis();

				// decrease number of ships left
				if(LifeBar.getLife()==0){
					status.setShipsLeft(status.getShipsLeft() - 1);
					LifeBar.resetLife();

					// "remove" ship
					shipExplosion = new Rectangle(
							ship.x,
							ship.y,
							ship.width,
							ship.height);
					ship.setLocation(this.getWidth() + ship.width, -ship.height);
					status.setNewShip(true);
					lastShipTime = System.currentTimeMillis();
				}    



				// play ship explosion sound
				soundMan.playShipExplosionSound();
				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();
			}
		}
		//////////////////////////////////////////////////////////////
		for(BigAsteroid e: bigAsteroids){
			// check vertically bullet-asteroid collisions
			for(int i=0; i<bullets.size(); i++){
				Bullet bullet = bullets.get(i);
				if(e.intersects(bullet)){
					// increase asteroids destroyed count
					status.setBigAsteroidsDestroyed(status.getBigAsteroidsDestroyed() + 1);


					detection = true;// Little asteroids should appears


					if(detection){

						BigX2 = e.getX();
						BigY2 = e.getY();
						BigLocation2 = new Point();
						BigLocation2.setLocation(BigX2, BigY2);
						littleAsteroid.setLocation(BigLocation2);
						for(LittleAsteroid n: littles)
						{
							n.setLocation(BigLocation2);

							// graphicsMan.drawLittleAsteroid(littleAsteroid, g2d, this);
						}
					}

					//status.setScore(status.getScore() + asteroid.getScorePoints());
					//Check we need to level up
					if(status.getScore()>=(status.getLevel()*levelStantard))
					{
						gameLogic.levelUp();									
					}


					// "remove" asteroid
					bigAsteroidExplosion = new Rectangle(
							e.x,
							e.y,
							e.width,
							e.height);
					e.setLocation(-e.width, -e.height);
					// status.setNewAsteroid(true);
					e.setBottom(true);
					lastAsteroidTime = System.currentTimeMillis();




					// play asteroid explosion sound
					soundMan.playAsteroidExplosionSound();


					// remove bullet
					bullets.remove(i);
					//asteroids.remove(j);
					break;
				}
			}




		}





		// check ship-asteroid collisions for the single asteroid 
		if(asteroid.intersects(ship)){


			LifeBar.setLife(25);




			// increase asteroids destroyed count
			status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

			status.setScore(status.getScore() + asteroid.getScorePoints());
			//Check we need to level up
			if(status.getScore()>=(status.getLevel()*levelStantard))
			{
				gameLogic.levelUp();									
			}


			status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);


			// "remove" asteroid
			asteroidExplosion = new Rectangle(
					asteroid.x,
					asteroid.y,
					asteroid.width,
					asteroid.height);
			asteroid.setLocation(-asteroid.width, -asteroid.height);
			status.setNewAsteroid(true);
			lastAsteroidTime = System.currentTimeMillis();


			// decrease number of ships left
			if(LifeBar.getLife()==0){
				status.setShipsLeft(status.getShipsLeft() - 1);
				LifeBar.resetLife();

				// "remove" ship
				shipExplosion = new Rectangle(
						ship.x,
						ship.y,
						ship.width,
						ship.height);
				ship.setLocation(this.getWidth() + ship.width, -ship.height);
				status.setNewShip(true);
				lastShipTime = System.currentTimeMillis();

			}           




			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}




		// check ship-Little asteroid collisions for the single asteroid 
		if(littleAsteroid.intersects(ship)){


			LifeBar.setLife(25);





			// increase asteroids destroyed count
			status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

			status.setScore(status.getScore() + littleAsteroid.getScorePoints());
			//Check we need to level up
			if(status.getScore()>=(status.getLevel()*levelStantard))
			{
				gameLogic.levelUp();									
			}




			// "remove" asteroid
			littleAsteroidExplosion = new Rectangle(
					littleAsteroid.x,
					littleAsteroid.y,
					littleAsteroid.width,
					littleAsteroid.height);
			littleAsteroid.setLocation(-littleAsteroid.width, -littleAsteroid.height);
			status.setNewLittleAsteroid(false);
			lastAsteroidTime = System.currentTimeMillis();


			// decrease number of ships left
			if(LifeBar.getLife()==0){
				status.setShipsLeft(status.getShipsLeft() - 1);
				LifeBar.resetLife();

				// "remove" ship
				shipExplosion = new Rectangle(
						ship.x,
						ship.y,
						ship.width,
						ship.height);
				ship.setLocation(this.getWidth() + ship.width, -ship.height);
				status.setNewShip(true);
				lastShipTime = System.currentTimeMillis();
			}       



			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}




		// check vertically bullet collision with little asteroid
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(littleAsteroid.intersects(bullet)){

				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

				status.setScore(status.getScore() + littleAsteroid.getScorePoints());
				//Check we need to level up
				if(status.getScore()>=(status.getLevel()*levelStantard))
				{
					gameLogic.levelUp();									
				}


				// "remove" little asteroid
				littleAsteroidExplosion = new Rectangle(
						littleAsteroid.x,
						littleAsteroid.y,
						littleAsteroid.width,
						littleAsteroid.height);
				littleAsteroid.setLocation(-littleAsteroid.width, -littleAsteroid.height);
				status.setNewLittleAsteroid(false);
				littleAsteroid.setBottom(true);


				lastAsteroidTime = System.currentTimeMillis();
				graphicsMan.drawLittleAsteroidExplosion(littleAsteroidExplosion, g2d, this);


				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();


				// remove bullet
				bullets.remove(i);
				//asteroids.remove(j);
				break;
			}
		}


		for(LittleAsteroid e: littles){
			if(e.intersects(ship)){


				LifeBar.setLife(25);




				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

				status.setScore(status.getScore() + asteroid.getScorePoints());
				//Check we need to level up
				if(status.getScore()>=(status.getLevel()*levelStantard))
				{
					gameLogic.levelUp();									
				}


				// "remove" asteroid
				littleAsteroidExplosion = new Rectangle(
						e.x,
						e.y,
						e.width,
						e.height);
				e.setLocation(-e.width, -e.height);
				//status.setNewAsteroid(true);
				lastAsteroidTime = System.currentTimeMillis();


				// decrease number of ships left
				if(LifeBar.getLife()==0){
					status.setShipsLeft(status.getShipsLeft() - 1);
					LifeBar.resetLife();

					// "remove" ship
					shipExplosion = new Rectangle(
							ship.x,
							ship.y,
							ship.width,
							ship.height);
					ship.setLocation(this.getWidth() + ship.width, -ship.height);
					status.setNewShip(true);
					lastShipTime = System.currentTimeMillis();
				}                                




				// play ship explosion sound
				soundMan.playShipExplosionSound();
				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();
			}
		}


		for(LittleAsteroid e : littles){
			// check vertically bullet-asteroid collisions
			for(int i=0; i<bullets.size(); i++){
				Bullet bullet = bullets.get(i);
				if(e.intersects(bullet)){
					// increase asteroids destroyed count
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

					status.setScore(status.getScore() + asteroid.getScorePoints());
					//Check we need to level up
					if(status.getScore()>=(status.getLevel()*levelStantard))
					{
						gameLogic.levelUp();									
					}


					// "remove" asteroid
					littleAsteroidExplosion = new Rectangle(
							e.x,
							e.y,
							e.width,
							e.height);
					e.setLocation(-e.width, -e.height);
					// status.setNewAsteroid(true);
					e.setBottom(true);
					lastAsteroidTime = System.currentTimeMillis();




					// play asteroid explosion sound
					soundMan.playAsteroidExplosionSound();


					// remove bullet
					bullets.remove(i);
					//asteroids.remove(j);
					break;
				}
			}




		}








		// update asteroids destroyed label
		destroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));

		// update ships left label
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));

		// update score label
		scoreValueLabel.setText(Integer.toString(status.getScore()));



		// update Level label
		levelValueLabel.setText(Integer.toString(status.getLevel()));

		// update enemy ship destroyed label
		enemyShipDestroyedValueLabel.setText(Long.toString(status.getEnemyShipDestroyed()));









		// update asteroids destroyed label
		destroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));


		// update ships left label
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));
	}
	/*****************************************************************************************************************/
	/**
	 * Draws the "Game Over" message.
	 */
	private void drawGameOver() {
		String gameOverStr = "GAME OVER";
		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameOverStr);
		if(strWidth > this.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(gameOverStr);
		}
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.WHITE);
		g2d.drawString(gameOverStr, strX, strY);
	}


	/**
	 * Draws the initial "Get Ready!" message.
	 */
	public void drawGetReady() {
		String readyStr = "Get Ready!";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);
	}


	/**
	 * Draws the specified number of stars randomly on the game screen.
	 * @param numberOfStars the number of stars to draw
	 */
	private void drawStars(int numberOfStars) {
		g2d.setColor(Color.WHITE);
		for(int i=0; i<numberOfStars; i++){
			int x = (int)(Math.random() * this.getWidth());
			int y = (int)(Math.random() * this.getHeight());
			g2d.drawLine(x, y, x, y);
		}
	}


	/**
	 * Display initial game title screen.
	 */
	public void initialMessage(String gameTitleStr) {
		gameTitleStr = messeage;


		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD).deriveFont(Font.ITALIC);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > this.getWidth() - 10){
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2 - ascent;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(gameTitleStr, strX, strY);


		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start a New Game.";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = (this.getHeight() + fm.getAscent())/2 + ascent + 16;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(newGameStr, strX, strY);


		fm = g2d.getFontMetrics();
		String exitGameStr = "Press <Esc> to Exit the Game.";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(exitGameStr, strX, strY);
	}


	/**
	 * Prepare screen for game over.
	 */
	public void doGameOver(){
		shipsValueLabel.setForeground(new Color(128, 0, 0));
	}


	/**
	 * Prepare screen for a new game.
	 */
	public void doNewGame(){                
		lastAsteroidTime = -NEW_ASTEROID_DELAY;
		lastShipTime = -NEW_SHIP_DELAY;


		bigFont = originalFont;
		biggestFont = null;


		// set labels' text
		shipsValueLabel.setForeground(Color.BLACK);
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));
		destroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));
	}


	/**
	 * Sets the game graphics manager.
	 * @param graphicsMan the graphics manager
	 */
	public void setGraphicsMan(GraphicsManager graphicsMan) {
		this.graphicsMan = graphicsMan;
	}


	/**
	 * Sets the game logic handler
	 * @param gameLogic the game logic handler
	 */
	public void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
		this.status = gameLogic.getStatus();
		this.soundMan = gameLogic.getSoundMan();
	}


	/**
	 * Sets the label that displays the value for asteroids destroyed.
	 * @param destroyedValueLabel the label to set
	 */
	public void setDestroyedValueLabel(JLabel destroyedValueLabel) {
		this.destroyedValueLabel = destroyedValueLabel;
	}


	/**
	 * Sets the label that displays the value for ship (lives) left
	 * @param shipsValueLabel the label to set
	 */
	public void setShipsValueLabel(JLabel shipsValueLabel) {
		this.shipsValueLabel = shipsValueLabel;
	}


	/**
	 * Set the label that display the level
	 * @param levelValueLabel the label to set
	 */
	public void setLevelValueLabel(JLabel levelValueLabel)
	{
		this.levelValueLabel = levelValueLabel;
	}

	public void setEnemyShipDestroyedValueLabel(JLabel enemyShipDestroyedValueLabel)
	{
		this.enemyShipDestroyedValueLabel = enemyShipDestroyedValueLabel;
	}
	/**
	 * Set the label that display the score
	 * @param scoreValueLabel the label to set
	 */
	public void setScoreValueLabel(JLabel scoreValueLabel)
	{
		this.scoreValueLabel = scoreValueLabel;
	}
}
