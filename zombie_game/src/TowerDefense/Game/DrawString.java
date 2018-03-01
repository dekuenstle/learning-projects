package TowerDefense.Game;

import java.awt.Color;
import java.awt.Graphics;

import me.lasergans.game.GameManager;
import me.lasergans.game.Node;
import me.lasergans.game.Vector;

public class DrawString extends Node
{
	public String[] string;
	public Color color;
	public Vector[] positions;
	public DrawString(Color col, Vector[] positions, String[] str)
	{
		color = col;
		z = 10;
		setWidth(GameManager.gameWidth);
		setHeight(GameManager.gameHeight);
		anchorPoint.set(0,0);
		this.positions = positions;
		this.string = str;
	}
	public void render(Graphics g)
	{
		g.setColor(color);
		for (int i=0; i < string.length; i++)
		{
			g.drawString(string[i], (int)positions[i].x, (int)positions[i].y);
		}
	}
}
