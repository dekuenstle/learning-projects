package TowerDefense.Game;

import java.awt.Image;

import me.lasergans.game.GameManager;

public class Zombie_Fast extends Enemy
{
	double iframe;
	public Zombie_Fast(Image img, int posX, int posY, int xtw, int ytw)
	{
		super(img, posX, posY, xtw, ytw);
		
		life = 5;
		speed = 1.5f;
		strength = 2;
		
		chanceToDrop_Money = 0.9;
		mValue = 20;
		chanceToDrop_Life = 0.01;
		chanceToDrop_Shield = 0.01;
		chanceToDrop_Ammo = 0.01;
	}
	public void update(double dt)
	{
		super.update(dt);
		
		iframe += dt;
        if (iframe > 0.4)
        {
        	iframe = 0;
        }
        selectedFrame = (int)(iframe * 5);
	}
	public void die()
	{
		super.die();
		((GameScene)(GameManager.getInstance().getActiveScene())).zF_Count--;
	}
}
