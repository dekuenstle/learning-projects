package TowerDefense.Game;

import java.awt.Image;

import me.lasergans.game.GameManager;

public class SmallGun extends Turret
{
	public static boolean perforating = false;
	public static double shotInterval_s = 0.12;
	public static int dmg_s = 4;
	public static double devModifier_s = 5;
	
	public SmallGun(Image img, int posX, int posY, double z)
	{
		super(img, posX, posY, z);
		dmg = 4;
		devModifier = 5;
		shotInterval = 0.12;
		sound = "smg";
		bulletLife = 1;
		flashActive = true;
		ammoMax = 800;
	}

}
