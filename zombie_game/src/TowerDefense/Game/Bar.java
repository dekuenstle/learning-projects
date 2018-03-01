package TowerDefense.Game;

import java.awt.Color;
import java.awt.Graphics;

import me.lasergans.game.Node;

public class Bar extends Node
{
	public Color color;
	public double value;
	
	public Bar(Color col, int width, int height, double value)
	{
		color = col;
		setWidth(width);
		setHeight(height);
		this.value = value;
		z = 10D;
	}
	public void render(Graphics g)
	{
		g.setColor(color);
		g.fillRect(0, 0, (int)(getWidth() * value), getHeight());
	}

}
