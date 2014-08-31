package rbadia.voidspace.sounds;

import java.applet.Applet;
import java.applet.AudioClip;

import rbadia.voidspace.main.GameScreen;

/**
 * Manages and plays the game's sounds.
 */
public class SoundManager {
	private static final boolean SOUND_ON = true;

	private AudioClip shipExplosionSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/shipExplosion.wav"));
	private AudioClip bulletSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/laser.wav"));
	private AudioClip bulletEnemySound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/laserEnemy.wav"));
	private AudioClip soundtrack = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/Theme.wav"));
	private AudioClip goliath = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/goliath.wav"));

	/**
	 * Plays sound for bullets fired by the ship.
	 */
	public void playBulletSound(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					bulletSound.play();
				}
			}).start();
		}
	}
	/**
	 * Plays sound for enemy bullets fired by the ships.
	 */
	public void playEnemyBulletSound(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					bulletEnemySound.play();
				}
			}).start();
		}
	}

	/**
	 * Plays sound for ship explosions.
	 */
	public void playShipExplosionSound(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					shipExplosionSound.play();
				}
			}).start();
		}
	}

	/**
	 * Plays sound for asteroid explosions.
	 */
	public void playAsteroidExplosionSound(){
		// play sound for asteroid explosions
		if(SOUND_ON){

		}
	}

	/**
	 * Play music for background game
	 */
	public void playSound(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					soundtrack.play();
				}
			}).start();
		}
	}

	/**
	 * Play music for background game
	 */
	public void playShip(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					goliath.play();
				}
			}).start();
		}
	}
}
