package TowerDefense.Game;

import java.awt.Image;
import java.awt.Rectangle;

import me.lasergans.game.GameManager;

public class Button_Buy extends Button
{
	public String type;
	public Button_Buy(Image img, Rectangle r, String type)
	{
		super(img, r);
		this.type = type;
		anchorPoint.set(0,0);
	}
	public void OnClick()
	{
		ShopScene scene = (ShopScene)(GameManager.getInstance().getActiveScene());
		if (type == "life" && scene.money >= 20 && scene.life + 5 <= 100)
		{
			scene.life += 5;
			scene.money -= 20;
		}
		if (type == "shield" && scene.money >= 20 && scene.shield + 5 <= 100)
		{
			scene.shield += 5;
			scene.money -= 20;
		}
		if (type == "ammo0" && scene.money >= 10 && scene.ammo[0] + 10 <= 800)
		{
			scene.ammo[0] += 10;
			scene.money -= 10;
		}
		if (type == "ammo1" && scene.money >= 30 && scene.ammo[1] + 5 <= 300)
		{
			scene.ammo[1] += 5;
			scene.money -= 30;
		}
		if (type == "ammo2" && scene.money >= 100 && scene.ammo[2] + 50 <= 1000)
		{
			scene.ammo[2] += 50;
			scene.money -= 100;
		}
		
		if (type == "weapon")
		{
			if (scene.money >= scene.weaponsCost[scene.selectedWeapon] && scene.weaponController.weaponA[scene.selectedWeapon] == false)
			{
				scene.weaponController.weaponA[scene.selectedWeapon] = true;
				scene.money -= scene.weaponsCost[scene.selectedWeapon];
			}
		}
	}
	public void OnRollover()
	{
		spriteImage = Texture.btn_buy_hl;
	}
	public void OnLeave()
	{
		spriteImage = Texture.btn_buy;
	}

}
