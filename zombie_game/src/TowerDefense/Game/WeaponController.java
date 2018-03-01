package TowerDefense.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import me.lasergans.game.GameManager;
import me.lasergans.game.Node;
import me.lasergans.game.Sprite;
import me.lasergans.game.Vector;

public class WeaponController extends Node
{
	public Button_SelectW[] buttons = { new Button_SelectW(Texture.btn_2, new Rectangle(318,118,83,16), 0, 0),
										new Button_SelectW(Texture.btn_2, new Rectangle(318,138,83,16), 0, 1),
										new Button_SelectW(Texture.btn_2, new Rectangle(318,158,83,16), 0, 2),
										new Button_SelectW(Texture.btn_2, new Rectangle(318,178,83,16), 0, 3)};
	public Button_Buy buyWeapon = new Button_Buy(Texture.btn_buy, new Rectangle(420, 190, 40, 16), "weapon");
	public boolean[] weaponA = { true, false, false, false };
	public Sprite[] markedS = { new Sprite(Texture.m, 300, 126),
								new Sprite(Texture.m, 300, 146),
								new Sprite(Texture.m, 300, 166),
								new Sprite(Texture.m, 300, 186)};
	private String[] str1 = {  "PRICE: ", "Damage: ", "Deviation: ", "RoF: ", "Perforating: ", "", "", "", "", "" };
	private Vector[] positions1 = { new Vector(420,230), new Vector(420,250), new Vector(420,270), new Vector(420,290), new Vector(420,310),
									new Vector(500,230), new Vector(500,250), new Vector(500,270), new Vector(500,290), new Vector(500,310) };
	public DrawString STR1 = new DrawString(Color.green, positions1, str1);
	public WeaponController()
	{
		for (int i=0; i < markedS.length; i++)
		{
			markedS[i].opaque = false;
		}
	}
	public void update(double dt)
	{
		ShopScene scene = null;
		if (GameManager.getInstance().getActiveScene() instanceof ShopScene)
			scene = ((ShopScene)(GameManager.getInstance().getActiveScene()));
		else return;
		
		for (int i=0;i < markedS.length; i++)
		{
			if (weaponA[i] == true)
			{
				markedS[i].opaque = true;
			}
		}
		if (scene.selectedWeapon == 0)
		{
			STR1.string[5] = String.valueOf(scene.weaponsCost[0]);
			STR1.string[6] = String.valueOf(SmallGun.dmg_s);
			STR1.string[7] = String.valueOf(SmallGun.devModifier_s);
			STR1.string[8] = String.valueOf((int)(60 /SmallGun.shotInterval_s));
			STR1.string[9] = new Boolean(SmallGun.perforating).toString();
		}
		if (scene.selectedWeapon == 1)
		{
			STR1.string[5] = String.valueOf(scene.weaponsCost[1]);
			STR1.string[6] = String.valueOf(MachineGun.dmg_s);
			STR1.string[7] = String.valueOf(MachineGun.devModifier_s);
			STR1.string[8] = String.valueOf((int)(60 / MachineGun.shotInterval_s));
			STR1.string[9] = new Boolean(MachineGun.perforating).toString();
		}
		if (scene.selectedWeapon == 2)
		{
			STR1.string[5] = String.valueOf(scene.weaponsCost[2]);
			STR1.string[6] = String.valueOf(HeavyGun.dmg_s);
			STR1.string[7] = String.valueOf(HeavyGun.devModifier_s);
			STR1.string[8] = String.valueOf((int)(60 /HeavyGun.shotInterval_s));
			STR1.string[9] = new Boolean(HeavyGun.perforating).toString();
		}
		if (scene.selectedWeapon == 3)
		{
			STR1.string[5] = String.valueOf(scene.weaponsCost[3]);
			STR1.string[6] = String.valueOf(FlameThrower.dmg_s);
			STR1.string[7] = String.valueOf(FlameThrower.devModifier_s);
			STR1.string[8] = String.valueOf((int)(60 /FlameThrower.shotInterval_s));
			STR1.string[9] = new Boolean(FlameThrower.perforating).toString();
		}
	}
}
