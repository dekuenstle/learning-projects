package TowerDefense.Game;

import java.awt.Image;

import me.lasergans.game.GameManager;

public class Zombie_STD extends Enemy
{

	public Zombie_STD(Image img, int posX, int posY, int xtw, int ytw)
	{
		super(img, posX, posY, xtw, ytw);
		
		life = 10;
		speed = 0.5f;
		strength = 4;
		
		chanceToDrop_Money = 0.01;
		mValue = 10;
		chanceToDrop_Life = 0.01;
		chanceToDrop_Shield = 0.01;
		chanceToDrop_Ammo = 0.2;
	}
	public void die()
	{
		((GameScene)(GameManager.getInstance().getActiveScene())).zSTD_Count--;
		super.die();
	}

}
