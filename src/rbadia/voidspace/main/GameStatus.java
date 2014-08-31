package rbadia.voidspace.main;

/**
 * Container for game flags and/or status variables.
 */
public class GameStatus {
	// game flags
	private boolean gameStarted = false;
	private boolean gameStarting = false;
	private boolean gameOver = false;

	// status variables
	private boolean newEnemyShip;
	private boolean newShip;
	private boolean newAsteroid;
	private boolean newLife;
	private long asteroidsDestroyed = 0;
	private int shipsLeft;

	//Added variables for extra game features
	private boolean newBigAsteroid;
	private long bigAsteroidsDestroyed =0; 

	private boolean littleAsteroid;
	private long enemyShipDestroyed = 0;
	private int score = 0;
	private int level = 1;

	public GameStatus(){

	}

	/**
	 * Set the number of enemy ship destroyed
	 * @param enemyShipDestroyed
	 */
	public synchronized void setEnemyShipDestroyed(long enemyShipDestroyed){
		this.enemyShipDestroyed = enemyShipDestroyed;
	}
	/**
	 * Returns the score obtained.
	 * @return the score
	 */
	public synchronized int getScore()
	{
		return score;
	}

	/**
	 * Set score
	 * @param score
	 */
	public synchronized void setScore(int score)
	{
		this.score = score;
	}
	/**
	 * Set level
	 * @param level
	 */
	public synchronized void setLevel(int level)
	{
		this.level = level;
	}

	/**
	 * Return the level
	 * @return level
	 */
	public synchronized int getLevel()
	{
		return this.level;
	}

	/**
	 * Set new enemy ship
	 * @param newEnemyShip
	 */
	public synchronized void setEnemyNewShip(boolean newEnemyShip) {
		this.newEnemyShip = newEnemyShip;
	}

	/**
	 * Indicates if a new enemy ship should be created/drawn.
	 * @return if a new enemy ship should be created/drawn
	 */
	public synchronized boolean isNewEnemyShip() {
		return newEnemyShip;
	}


	/**
	 * Indicates if the game has already started or not.
	 * @return if the game has already started or not
	 */
	public synchronized boolean isGameStarted() {
		return gameStarted;
	}

	public synchronized void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	/**
	 * Indicates if the game is starting ("Get Ready" message is displaying) or not.
	 * @return if the game is starting or not.
	 */
	public synchronized boolean isGameStarting() {
		return gameStarting;
	}

	public synchronized void setGameStarting(boolean gameStarting) {
		this.gameStarting = gameStarting;
	}

	/**
	 * Indicates if the game has ended and the "Game Over" message is displaying.
	 * @return if the game has ended and the "Game Over" message is displaying.
	 */
	public synchronized boolean isGameOver() {
		return gameOver;
	}

	public synchronized void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	/**
	 * Indicates if a new ship should be created/drawn.
	 * @return if a new ship should be created/drawn
	 */
	public synchronized boolean isNewShip() {
		return newShip;
	}

	public synchronized void setNewShip(boolean newShip) {
		this.newShip = newShip;
	}

	/**
	 * Indicates if a new asteroid should be created/drawn.
	 * @return if a new asteroid should be created/drawn
	 */
	public synchronized boolean isNewAsteroid() {
		return newAsteroid;
	}

	public synchronized void setNewAsteroid(boolean newAsteroid) {
		this.newAsteroid = newAsteroid;
	}




	/**
	 * Indicates if a new bigAsteroid should be created/drawn.
	 * @return if a new asteroid should be created/drawn
	 */
	public synchronized boolean isNewBigAsteroid() {
		return newBigAsteroid;
	}

	public synchronized void setNewBigAsteroid(boolean theNewBigAsteroid) {
		this.newBigAsteroid = theNewBigAsteroid;
	}




	/**
	 * Indicates if a new littleAsteroid should be created/drawn.
	 * @return if a new asteroid should be created/drawn
	 */
	public synchronized boolean isNewLittleAsteroid() {
		return littleAsteroid;
	}

	public synchronized void setNewLittleAsteroid(boolean theNewLittleAsteroid) {
		this.littleAsteroid= theNewLittleAsteroid;
	}







	/**
	 * Returns the number of bigAsteroids destroyed. 
	 * @return the number of bigAsteroids destroyed
	 */
	public synchronized long getBigAsteroidsDestroyed() {
		return this.bigAsteroidsDestroyed;
	}

	public synchronized void setBigAsteroidsDestroyed(long asteroidsDestroyed) {
		this.bigAsteroidsDestroyed = asteroidsDestroyed;
	}


	public synchronized boolean isNewLife() {
		return newLife;
	}

	public synchronized void setNewLife(boolean theNewLife) {
		this.newLife = theNewLife;
	}







	/**
	 * Returns the number of asteroid destroyed. 
	 * @return the number of asteroid destroyed
	 */
	public synchronized long getAsteroidsDestroyed() {
		return asteroidsDestroyed;
	}

	public synchronized void setAsteroidsDestroyed(long asteroidsDestroyed) {
		this.asteroidsDestroyed = asteroidsDestroyed;
	}

	/**
	 * Returns the number ships/lives left.
	 * @return the number ships left
	 */
	public synchronized int getShipsLeft() {
		return shipsLeft;
	}

	public synchronized void setShipsLeft(int shipsLeft) {
		this.shipsLeft = shipsLeft;
	}

	public long getEnemyShipDestroyed() {

		return enemyShipDestroyed;
	}

}
