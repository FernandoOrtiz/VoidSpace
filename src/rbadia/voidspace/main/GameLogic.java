package rbadia.voidspace.main;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigAsteroid;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.LifeBar;
import rbadia.voidspace.model.LittleAsteroid;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;


/**
 * Handles general game logic and status.
 */
public class GameLogic {
	private GameScreen gameScreen;
	private GameStatus status;
	private SoundManager soundMan;

	private Ship ship;
	private Asteroid asteroid;
	private List<Bullet> bullets;
	private List<Asteroid> moreAsteroids; //make more asteroids  with arraylists
	private BigAsteroid bigAsteroid;
	private LifeBar life;


	private EnemyShip enemyShip;
	private List<EnemyShip> enemies;
	private List<Bullet> enemyBullets;
	private List<LittleAsteroid> littleAsteroids;
	private List<BigAsteroid> bigAsteroids;



	private LittleAsteroid littleAsteroid;



	/**
	 * Craete a new game logic handler
	 * @param gameScreen the game screen
	 */
	public GameLogic(GameScreen gameScreen){
		this.gameScreen = gameScreen;

		// initialize game status information
		status = new GameStatus();
		// initialize the sound manager
		soundMan = new SoundManager();

		// init some variables
		bullets = new ArrayList<Bullet>();
		moreAsteroids = new ArrayList<Asteroid>();

		enemyBullets = new ArrayList<Bullet>();
		enemies = new ArrayList<EnemyShip>();
		littleAsteroids = new ArrayList<LittleAsteroid>();

	}

	/**
	 * Returns the game status
	 * @return the game status 
	 */
	public GameStatus getStatus() {
		return status;
	}

	public SoundManager getSoundMan() {
		return soundMan;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	/**
	 * Prepare for a new game.
	 */
	public void newGame(){
		status.setGameStarting(true);

		// init game variables
		bullets = new ArrayList<Bullet>();
		moreAsteroids = new ArrayList<Asteroid>(); // make more asteroids
		enemyBullets = new ArrayList<Bullet>(); // make enemy ships appear
		enemies = new ArrayList<EnemyShip>(); // make enemy bullets
		littleAsteroids = new ArrayList<LittleAsteroid>();
		bigAsteroids = new ArrayList<BigAsteroid>();

		status.setShipsLeft(5);//lives 
		status.setGameOver(false);
		status.setAsteroidsDestroyed(0);
		status.setBigAsteroidsDestroyed(0);
		status.setNewAsteroid(false);
		status.setNewBigAsteroid(false);
		status.setNewLittleAsteroid(false); // No little asteroids should be drawn
		status.setNewLife(false);
		status.setEnemyNewShip(false);

		// init the ship and the asteroid and the big asteroid
		newShip(gameScreen);
		newAsteroid(gameScreen);
		newBigAsteroid(gameScreen);
		newLifeBar(gameScreen);
		newEnemyShip(gameScreen);
		newLittleAsteroid(gameScreen);

		//Basic level start up
		createAsteroids(3);
		createEnemies(1);
		createBigAsteroids(1);
		createLittleAsteroids(3);


		soundMan.playSound();
		soundMan.playShip();



		// prepare game screen
		gameScreen.doNewGame();

		// delay to display "Get Ready" message for 1.5 seconds
		Timer timer = new Timer(1500, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameStarting(false);
				status.setGameStarted(true);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}




	/**
	 * Check game or level ending conditions.
	 */
	public void checkConditions(){
		// check game over conditions
		if(!status.isGameOver() && status.isGameStarted()){
			if(status.getShipsLeft() == 0){
				gameOver();


			}
		}
	}

	/**
	 * Actions to take when the game is over.
	 */
	public void gameOver(){
		status.setGameStarted(false);
		status.setGameOver(true);
		gameScreen.doGameOver();

		// delay to display "Game Over" message for 3 seconds
		Timer timer = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameOver(false);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	/**
	 * Fire a bullet from ship.
	 */
	public void fireBullet(){
		Bullet bullet = new Bullet(ship);
		bullets.add(bullet);
		soundMan.playBulletSound();
	}

	/**
	 * Move a bullet once fired.
	 * @param bullet the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveBullet(Bullet bullet){
		if(bullet.getY() - bullet.getSpeed() >= 0){
			bullet.translate(0, -bullet.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}

	/**
	 * Create a new ship (and replace current one).
	 */
	public Ship newShip(GameScreen screen){
		this.ship = new Ship(screen);
		return ship;
	}

	/**
	 * Create a new asteroid.
	 */
	public Asteroid newAsteroid(GameScreen screen){
		this.asteroid = new Asteroid(screen);
		return asteroid;
	}

	/**
	 * Create a new bigAsteroid.
	 */

	private BigAsteroid newBigAsteroid(GameScreen screen) {
		this.bigAsteroid = new BigAsteroid(screen);
		return bigAsteroid;

	}



	public LifeBar getLife(){
		return life;
	}

	public LifeBar newLifeBar(GameScreen screen){
		this.life = new LifeBar(screen);

		return life;


	}



	/**
	 * Returns the ship.
	 * @return the ship
	 */
	public Ship getShip() {
		return ship;
	}

	/**
	 * Returns the asteroid.
	 * @return the asteroid
	 */
	public Asteroid getAsteroid() {
		return asteroid;
	}



	/**
	 * Returns the bigAsteroid.
	 * @return the bigAsteroid
	 */
	public List<BigAsteroid> getBigAsteroids() {
		return bigAsteroids;
	}

	/**
	 * Returns the list of bullets.
	 * @return the list of bullets
	 */
	public List<Bullet> getBullets() {
		return bullets;
	}

	/**
	 * Set some conditions to the levels
	 */
	public void levelUp()
	{
		status.setLevel(status.getLevel()+1);

		//Level 1
		if(status.getLevel() == 5)
		{
			addAsteroid(gameScreen);
		}
		if(status.getLevel() == 8)
		{
			addAsteroid(gameScreen);
			addEnemy(gameScreen);
		}


	}




	//Little asteroid methods
	//***************************************//

	/**
	 * Returns the asteroid.
	 * @return the asteroid
	 */
	public LittleAsteroid getLittleAsteroid() {
		return littleAsteroid;
	}
	/**
	 * Create a new asteroid.
	 */
	public LittleAsteroid newLittleAsteroid(GameScreen screen){
		this.littleAsteroid = new LittleAsteroid(screen);
		return littleAsteroid;
	}

	public void createLittleAsteroids(int k)
	{
		for(int i=0;i<k;i++)
		{
			addLittleAsteroid(gameScreen);

		}
	}


	public void addLittleAsteroid(GameScreen screen)
	{
		this.littleAsteroid= newLittleAsteroid(screen);
		littleAsteroids.add(littleAsteroid);
	}

	public void moveLittleAsteroidDiagonallyright(Asteroid e)
	{

		if(((e.getX()) + e.getSpeed() > 0)&&(e.getX() + e.getSpeed() < (gameScreen.getWidth()))){
			e.translate(e.getxSpeed(), e.getSpeed());



		}
		else{
			e.setXSpeed(-e.getxSpeed());
			e.translate(e.getxSpeed(), e.getSpeed());
		}	

	}

	public void moveLittleAsteroidDiagonallyleft(Asteroid e)
	{

		if(((e.getX()) + e.getSpeed() > 0)&&(e.getX() + e.getSpeed() < (gameScreen.getWidth()))){
			e.translate(-e.getxSpeed(), e.getSpeed());


		}
		else{
			e.setXSpeed(-e.getxSpeed());
			e.translate(-e.getxSpeed(), e.getSpeed());
		}	
	}
	public void moveLittleAsteroidDown(Asteroid e)
	{
		e.translate(0, e.getSpeed());
	}


	public void moveLittleAsteroids(LittleAsteroid littleAsteroid)
	{
		int numberCase = littleAsteroid.getmove();
		if(numberCase == 0)
		{
			moveAsteroidDiagonallyright(littleAsteroid);
		} 
		if(numberCase == 1)
		{
			moveAsteroidDiagonallyleft(littleAsteroid);
		}
		if(numberCase == 2)
		{
			moveAsteroidDown(littleAsteroid);
		}

	}


	/**
	 * Returns the list of asteroids
	 * @return asteroids
	 */
	public List<LittleAsteroid> getLittleAsteroids() {
		return littleAsteroids;
	}


	public void createBigAsteroids(int k)
	{
		for(int i=0;i<k;i++)
		{
			addBigAsteroid(gameScreen);

		}
	}





	/**************************************?



	//Asteroids methods 
	/*****************************************************/



	public void createAsteroids(int k)
	{
		for(int i=0;i<k;i++)
		{
			addAsteroid(gameScreen);

		}
	}


	public void addAsteroid(GameScreen screen)
	{
		this.asteroid = newAsteroid(screen);
		moreAsteroids.add(asteroid);
	}


	public void addBigAsteroid(GameScreen screen)
	{
		this.bigAsteroid = newBigAsteroid(screen);
		bigAsteroids.add(bigAsteroid);
	}


	public void moveAsteroidDiagonallyright(Asteroid e)
	{

		if(((e.getX()) + e.getSpeed() > 0)&&(e.getX() + e.getSpeed() < (gameScreen.getWidth()))){
			e.translate(e.getxSpeed(), e.getSpeed());



		}
		else{
			e.setXSpeed(-e.getxSpeed());
			e.translate(e.getxSpeed(), e.getSpeed());
		}	

	}

	/**
	 * Move the asteroid diagonally to the left
	 * @param e the asteroid to move
	 */
	public void moveAsteroidDiagonallyleft(Asteroid e)
	{

		if(((e.getX()) + e.getSpeed() > 0)&&(e.getX() + e.getSpeed() < (gameScreen.getWidth()))){
			e.translate(-e.getxSpeed(), e.getSpeed());


		}
		else{
			e.setXSpeed(-e.getxSpeed());
			e.translate(-e.getxSpeed(), e.getSpeed());
		}	
	}

	/**
	 * Move the asteroid diagonally to the bottom of the screen
	 * @param e the asteroid to move
	 */
	public void moveAsteroidDown(Asteroid e)
	{
		e.translate(0, e.getSpeed());
	}


	public void moveBigAsteroids(BigAsteroid bigAsteroid)
	{
		int numberCase = bigAsteroid.getmove();
		if(numberCase == 0)
		{
			moveAsteroidDiagonallyright(bigAsteroid);
		} 
		if(numberCase == 1)
		{
			moveAsteroidDiagonallyleft(bigAsteroid);
		}
		if(numberCase == 2)
		{
			moveAsteroidDown(bigAsteroid);
		}

	}


	public void moveAsteroids(Asteroid asteroid)
	{
		int numberCase = asteroid.getmove();
		if(numberCase == 0)
		{
			moveAsteroidDiagonallyright(asteroid);
		} 
		if(numberCase == 1)
		{
			moveAsteroidDiagonallyleft(asteroid);
		}
		if(numberCase == 2)
		{
			moveAsteroidDown(asteroid);
		}

	}

	/**
	 * Returns the list of asteroids
	 * @return asteroids
	 */
	public List<Asteroid> getAsteroids() {
		return moreAsteroids;
	}







	/**
	 * Move enemy ship down
	 * @param enemyShip
	 */
	public void enemyMoveDown(EnemyShip enemyShip)
	{
		System.currentTimeMillis();
		long currentTime = 0;
		if(((enemyShip.getX()-5) + enemyShip.getxSpeed() > 0)&&(enemyShip.getX() + enemyShip.getxSpeed() < (gameScreen.getWidth()-20))){
			enemyShip.translate(enemyShip.getxSpeed(), 0);
			//graphicsMan.drawEnemyShip(enemyShip, g2d, screen);
			enemyShip.setSides(true);
			currentTime = System.currentTimeMillis();
		}
		else{
			enemyShip.setXSpeed(-enemyShip.getxSpeed());
		}	
		//wait(5);
	}


	/**
	 * Move the enemy ship to right
	 * @param enemyShip
	 */
	public void enemyMoveRight(EnemyShip enemyShip)
	{
		long startTime = System.currentTimeMillis();
		System.currentTimeMillis();
		long currentTime = 0;

		enemyShip.translate(0, enemyShip.getSpeed());

		//wait(5);
	}

	/**
	 * Move the enemy ship to left
	 * @param enemyShip
	 */
	public void enemyMoveLeft(EnemyShip enemyShip)
	{
		long startTime = System.currentTimeMillis();
		long currentTime = 0;
		do{
			if((enemyShip.getY() + enemyShip.getSpeed() < gameScreen.getHeight())&&(enemyShip.getX() + enemyShip.getSpeed() < gameScreen.getWidth())){
				enemyShip.translate(enemyShip.getSpeed(), 3);
				//graphicsMan.drawEnemyShip(enemyShip, g2d, screen);
				enemyShip.setSides(true);
				currentTime = System.currentTimeMillis();
			}
			else{
				enemyShip.setSides(false);
			}
		}while((currentTime-startTime>500));

		//wait(5);
	}

	/**
	 * Create a new enemy ship (and replace current one)
	 * @param screen
	 * @return enemyShip
	 */
	public EnemyShip newEnemyShip(GameScreen screen){
		this.enemyShip = new EnemyShip(screen);

		return enemyShip;
	}

	/**
	 * Returns the enemy ship
	 * @return
	 */
	public EnemyShip getEnemyShip()
	{
		return enemyShip;
	}

	/**
	 * Returns the list of enemy bullets
	 * @return
	 */
	public List<Bullet> getEnemyBullets(){
		return enemyBullets;
	}

	/**
	 * Create enemies ship
	 * @param k the number of enemies to create
	 */
	public void createEnemies(int k)
	{
		for(int i=0;i<k;i++)
		{
			addEnemy(gameScreen);
		}
	}

	/**
	 * Add ship enemies to the sceen
	 * @param screen
	 */
	public void addEnemy(GameScreen screen)
	{
		this.enemyShip = new EnemyShip(screen);
		enemies.add(enemyShip);
	}

	public List<EnemyShip> getEnemies() {
		return enemies;
	}



	/**
	 * Move the enemy bullet when fired
	 * @param enemyBullet the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveEnemyBullet(Bullet enemyBullet){
		if(enemyBullet.getY() + enemyBullet.getSpeed() >= 0){
			enemyBullet.translate(0, +enemyBullet.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}




	/**
	 * Fire a bullet from enemy ship
	 */
	public void enemyFireBullet(EnemyShip s)
	{
		Bullet enemyBullet = new Bullet(s);
		enemyBullets.add(enemyBullet);
		soundMan.playEnemyBulletSound();
	}

	public BigAsteroid getBigAsteroid() {

		return bigAsteroid;
	}













}
