package TowerDefense.Game;

import java.awt.Color;
import java.awt.Graphics;

import me.lasergans.game.GameManager;
import me.lasergans.game.Key;
import me.lasergans.game.Scene;

public class PauseScene extends Scene
{
	Scene sc;
	private boolean p = true;
	public PauseScene(Scene sc)
	{
		this.sc = sc;
	}
	public void update(double dt)
	{
		if (Key.P && !p)
		{
			GameManager.getInstance().setActiveScene(sc);
		}
		if (!Key.P)
		{
			p = false;
		}
			
	}
	public void render(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(Color.white);
		g.drawString("PAUSE", getWidth()/2 - 20, getHeight()/2);
	}

}
