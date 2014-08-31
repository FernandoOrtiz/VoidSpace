package rbadia.voidspace.main;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * The game's main frame. Contains all the game's labels, file menu, and game screen.
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private GameScreen gameScreen = null;
	private GameStatus status = new GameStatus();

	private JLabel destroyedLabel;
	private JLabel destroyedValueLabel;

	private JLabel enemyShipDestroyedLabel;
	private JLabel enemyShipDestroyedValueLabel;

	private JLabel shipsLabel;
	private JLabel shipsValueLabel;

	private JLabel scoreLabel;
	private JLabel scoreValueLabel;

	private JLabel levelLabel;
	private JLabel levelValueLabel;
	/**
	 * This is the default constructor
	 */
	public MainFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(520, 490);
		this.setContentPane(getJContentPane());
		this.setTitle("Void Space");
		this.setResizable(true);

		Dimension dim = this.getToolkit().getScreenSize();
		Rectangle bounds = this.getBounds();
		this.setLocation(
				(dim.width - bounds.width) / 2,
				(dim.height - bounds.height) / 2);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			// Enemy Ship Destroyed's Grid
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints12.gridy = 2;
			gridBagConstraints12.anchor = GridBagConstraints.WEST;
			gridBagConstraints12.weightx = 1.0D;
			gridBagConstraints12.gridx = 3;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints11.gridy = 2;
			gridBagConstraints11.anchor = GridBagConstraints.EAST;
			gridBagConstraints11.weightx = 1.0D;
			gridBagConstraints11.gridx = 2;

			// Level's Grid
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints10.gridy = 3;
			gridBagConstraints10.anchor = GridBagConstraints.WEST;
			gridBagConstraints10.weightx = 1.0D;
			gridBagConstraints10.gridx = 3;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints9.gridy = 3;
			gridBagConstraints9.anchor = GridBagConstraints.EAST;
			gridBagConstraints9.weightx = 1.0D;
			gridBagConstraints9.gridx = 2;


			// Score's Grid
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints6.gridy = 2;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.weightx = 1.0D;
			gridBagConstraints6.gridx = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.anchor = GridBagConstraints.EAST;
			gridBagConstraints5.weightx = 1.0D;
			gridBagConstraints5.gridx = 0;

			// AsteroidDestroyed's Grid
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.gridx = 3;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.anchor = GridBagConstraints.EAST;
			gridBagConstraints3.weightx = 1.0D;
			gridBagConstraints3.gridx = 2;

			// ShipLeft's Grid
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.weightx = 1.0D;
			gridBagConstraints2.gridx = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.anchor = GridBagConstraints.EAST;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.gridx = 0;

			// GamesSreen's grid
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.fill = GridBagConstraints.NONE;
			gridBagConstraints.gridwidth = 4;

			shipsLabel = new JLabel("Ships Left: ");
			shipsValueLabel = new JLabel("5");

			destroyedLabel = new JLabel("Asteroids Destroyed: ");
			destroyedValueLabel = new JLabel("0");

			enemyShipDestroyedLabel = new JLabel("Enemy Ship Destroyed: ");
			enemyShipDestroyedValueLabel = new JLabel("0");

			scoreLabel = new JLabel("Score: ");
			scoreValueLabel = new JLabel("0");



			levelLabel = new JLabel("Level: ");
			levelValueLabel = new JLabel("1");

			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getGameScreen(), gridBagConstraints);
			jContentPane.add(shipsLabel, gridBagConstraints1);
			jContentPane.add(shipsValueLabel, gridBagConstraints2);
			jContentPane.add(destroyedLabel, gridBagConstraints3);
			jContentPane.add(destroyedValueLabel, gridBagConstraints4);
			jContentPane.add(scoreLabel, gridBagConstraints5);
			jContentPane.add(scoreValueLabel, gridBagConstraints6);
			jContentPane.add(levelLabel, gridBagConstraints9);
			jContentPane.add(levelValueLabel, gridBagConstraints10);
			jContentPane.add(enemyShipDestroyedLabel, gridBagConstraints11);
			jContentPane.add(enemyShipDestroyedValueLabel, gridBagConstraints12);


		}
		return jContentPane;
	}

	/**
	 * This method initializes gameScreen	
	 * 	
	 * @return GameScreen
	 */
	public GameScreen getGameScreen() {
		if (gameScreen == null) {
			gameScreen = new GameScreen();
			gameScreen.setShipsValueLabel(shipsValueLabel);
			gameScreen.setDestroyedValueLabel(destroyedValueLabel);
			gameScreen.setScoreValueLabel(scoreValueLabel);

			gameScreen.setLevelValueLabel(levelValueLabel);
			gameScreen.setEnemyShipDestroyedValueLabel(enemyShipDestroyedValueLabel);
		}
		return gameScreen;
	}

} 
