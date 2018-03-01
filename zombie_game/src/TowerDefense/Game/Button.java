package TowerDefense.Game;

import java.awt.Image;
import java.awt.Rectangle;

import me.lasergans.game.Mouse;
import me.lasergans.game.Sprite;

public class Button extends Sprite
{
	public boolean bp;
	private Rectangle rect;
	public Button(Image img, Rectangle r)
	{
		super(img, r.x, r.y);
		rect = r;
	}
	public void update(double dt)
	{
		if (Mouse.button1Clicked == false)
				bp = false;
		if (rect.contains(Mouse.position.x-6, Mouse.position.y-6))
		{
			if ( Mouse.button1Clicked && bp == false )
			{
				OnClick();
				bp = true;
			}
			else
			{
				OnRollover();
			}
		}
		else 
		{
			OnLeave();
		}
		if (rect.contains(Mouse.position.x-6, Mouse.position.y-6) == false && Mouse.button1Clicked)
		{
			OnDeselect();
		}
	}
	public void OnClick()
	{
		
	}
	public void OnRollover()
	{
		
	}
	public void OnLeave()
	{
		
	}
	public void OnDeselect()
	{
		
	}
}
