package TowerDefense.Game;

import java.awt.Image;

import me.lasergans.game.GameManager;

public class HeavyGun extends Turret
{
	public static boolean perforating = true;
	public static double shotInterval_s = 0.19;
	public static int dmg_s = 10;
	public static double devModifier_s = 3;
	

	public HeavyGun(Image img, int posX, int posY, double z)
	{
		super(img, posX, posY, z);
		dmg = 10;
		devModifier = 3;
		shotInterval = 0.19;
		sound = "machine_gun";
		bulletLife = 2;
		flashActive = true;
		ammoMax = 300;
	}

}
