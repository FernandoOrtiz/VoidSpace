package rbadia.voidspace.model;

import java.awt.Rectangle;

import rbadia.voidspace.main.GameScreen;

/**
 * Represents a ship/space craft.
 *
 */
public class LifeBar extends Rectangle {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_SPEED = 5;
	private static final int Y_OFFSET = 5; // initial y distance of the ship from the bottom of the screen 

	private int lifeWidth = 100;
	private int lifeHeight = 25;
	static int avLife = 100;



	/**
	 * Creates a new ship at the default initial location. 
	 * @param screen the game screen
	 */
	public LifeBar(GameScreen screen){
		this.setLocation((screen.getWidth() - lifeWidth),
				screen.getHeight() - lifeHeight - Y_OFFSET);
		this.setSize(lifeWidth, lifeHeight);
	}

	/**
	 * Get the default ship width
	 * @return the default ship width
	 */
	public int getLifeWidth() {
		return lifeWidth;
	}

	/**
	 * Get the default ship height
	 * @return the default ship height
	 */
	public int getLifeHeight() {
		return lifeHeight;
	}

	public static int getLife(){


		return avLife;


	}

	public static void setLife(int val){
		avLife = avLife-val;
	}

	public static void resetLife(){
		avLife=100;
	}


}
