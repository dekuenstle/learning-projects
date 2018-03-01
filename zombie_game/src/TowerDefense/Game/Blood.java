package TowerDefense.Game;

import java.awt.Image;

import me.lasergans.game.SpriteSheet;

public class Blood extends SpriteSheet
{
	private double i = 0;
	
	public Blood(Image img, double posX, double posY, int xTileCount, int yTileCount)
	{
		super(img, posX, posY, 0, xTileCount, yTileCount);
	}
	public void update(double dt)
	{
		i += dt;
		
		selectedFrame = (int)(i*16);
		
		if (selectedFrame == 4)
		{
			parent.removeChild(this);
		}
	}
	
}
