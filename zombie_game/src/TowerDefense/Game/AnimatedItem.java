package TowerDefense.Game;

import java.awt.Image;

import me.lasergans.game.SpriteSheet;

public class AnimatedItem extends SpriteSheet
{
	private double i = 0;
	
	public AnimatedItem(Image img, double posX, double posY, int xTileCount, int yTileCount)
	{
		super(img, posX, posY, 1, xTileCount, yTileCount);
	}
	public void update(double dt)
	{
		i += dt;
		
		selectedFrame = (int)(i*20);
		
		if (selectedFrame == 19)
		{
		parent.removeChild(this);
		}
	}
}
