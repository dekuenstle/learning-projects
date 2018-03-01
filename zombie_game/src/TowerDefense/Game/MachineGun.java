package TowerDefense.Game;

import java.awt.Image;

import me.lasergans.game.GameManager;

public class MachineGun extends Turret
{
	public static boolean perforating = false;
	public static double shotInterval_s = 0.08;
	public static int dmg_s = 4;
	public static double devModifier_s = 9;
	
	public MachineGun(Image img, int posX, int posY, double z)
	{
		super(img, posX, posY, z);
		dmg = 4;
		devModifier = 9;
		shotInterval = 0.08;
		sound = "med_gun";
		bulletLife = 1;
		flashActive = true;
		ammoMax = 800;
	}

}
