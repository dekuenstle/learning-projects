package TowerDefense.Game;

import java.awt.Image;
import java.awt.Rectangle;

import me.lasergans.game.Mouse;
import me.lasergans.game.SpriteSheet;

public class ShopAnimation extends SpriteSheet
{
	private double i;
	public ShopAnimation(Image img, double posX, double posY, double z,	int xTileCount, int yTileCount)
	{
		super(img, posX, posY, z, xTileCount, yTileCount);
	}
	public void update(double dt)
	{
		double dX = Mouse.position.getXf() - position.getXf();
		double dY = Mouse.position.getYf() - position.getYf();
		
		double j = -Math.atan2(dX, dY);
		j = (j * 180 / Math.PI - 180);
		
		i += dt;
		if ((i*30) < 179)
		{
			selectedFrame = (int)(i*30);
		}
		else
		{
			i = 0;
			selectedFrame = 0;
		}
	}
	

}
