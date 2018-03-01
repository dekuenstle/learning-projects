package TowerDefense.Game;

import java.awt.Image;

import me.lasergans.game.Sprite;

public class BloodSpot extends Sprite
{
	double i;
	public BloodSpot(Image img, double posX, double posY)
	{
		super(img, posX, posY, 0);
	}
	public void update(double dt)
	{
		i += dt;
		
		alpha = 1 - ( i / 10 );
		
		if (alpha < 0)
		{
			parent.removeChild(this);
		}
	}
	

}
