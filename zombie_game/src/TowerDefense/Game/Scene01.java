package TowerDefense.Game;

import java.util.Random;

import me.lasergans.game.GameManager;

public class Scene01 extends GameScene
{
	public void update(double dt)
	{
		super.update(dt);
		elapsedTime += dt;
		zSTD_Max = (int)((5*(Math.tan(0.1*elapsedTime)*Math.sin(0.1*elapsedTime)*Math.cos(0.1*elapsedTime)) + 4)*(0.1*Math.sqrt(elapsedTime)));
		zF_Max = (int)((3*(Math.tan(0.1*elapsedTime)*Math.sin(0.1*elapsedTime)*Math.cos(0.1*elapsedTime)) + 1)*(0.1*Math.sqrt(elapsedTime)));
		
		// CREATE MONSTERS
		if (zSTD_Count < zSTD_Max)
		{
			Random rand = new Random();
			int xPos = 0, yPos = 0;
			int side = rand.nextInt(4);
			
			if (side == 0) { yPos = 0; 							xPos = rand.nextInt(getWidth()); }
			if (side == 1) { yPos = rand.nextInt(getHeight()); 	xPos = 0; }
			if (side == 2) { yPos = GameManager.gameHeight; 	xPos = rand.nextInt(getWidth()); }
			if (side == 3) { yPos = rand.nextInt(getHeight());	xPos = GameManager.gameWidth; }
			
			Enemy enemy = new Zombie_STD(Texture.zombie_small, xPos, yPos, 1, 1);
			this.addChild(enemy);
			
			zSTD_Count++;
		}
		if (zF_Count < zF_Max)
		{
			Random rand = new Random();
			int xPos = 0, yPos = 0;
			int side = rand.nextInt(4);
			
			if (side == 0) { yPos = 0; 							xPos = rand.nextInt(getWidth()); }
			if (side == 1) { yPos = rand.nextInt(getHeight()); 	xPos = 0; }
			if (side == 2) { yPos = GameManager.gameHeight; 	xPos = rand.nextInt(getWidth()); }
			if (side == 3) { yPos = rand.nextInt(getHeight());	xPos = GameManager.gameWidth; }
			
			Enemy enemy = new Zombie_Fast(Texture.zombie_fast, xPos, yPos, 2, 1);
			this.addChild(enemy);
			
			zF_Count++;
		}
	}

}
