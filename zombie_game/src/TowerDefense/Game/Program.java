package TowerDefense.Game;

import me.lasergans.game.GameManager;

public class Program{
	/**
	 * @param args
	 */	
	public static int money;
	
	public static void main(String[] args)
	{

		GameManager.setupWindow();
		GameManager.getInstance().setActiveScene(new Scene01());
		GameManager.getInstance().gameLoop.setFPS(40);

	}

}
