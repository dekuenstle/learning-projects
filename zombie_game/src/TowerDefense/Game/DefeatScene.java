package TowerDefense.Game;

import java.awt.Color;
import java.awt.Graphics;

import me.lasergans.game.GameManager;
import me.lasergans.game.Key;
import me.lasergans.game.Scene;

public class DefeatScene extends Scene
{
	int kills;
	int money;
	
	public void update(double dt)
	{
		if (Key.ENTER) GameManager.getInstance().setActiveScene(new Scene01());
		if (Key.ESCAPE) GameManager.Exit();
	}
	public DefeatScene(int k, int m)
	{
		kills = k;
		money = m;
	}
	public void render(Graphics g)
	{
		g.drawImage(Texture.defeatBG, 0, 0, null);
		g.setColor(new Color(200,40,40));
		g.drawString("Kills: ", 20, 60);
		g.drawString(String.valueOf(kills), 90, 60);
		g.drawString("Money: ", 20, 75);
		g.drawString(String.valueOf(money), 90, 75);

		g.drawString(">| Enter for new game |<", 20, 130);
		g.drawString(">| Escape for quit |<", 20, 145);
	}

}
