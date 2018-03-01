package TowerDefense.Game;

import java.awt.Image;
import java.awt.Rectangle;

import me.lasergans.game.GameManager;

public class Button_SelectW extends Button
{
	boolean s;
	int cost;
	int type;
	public Button_SelectW(Image img, Rectangle r, int m, int t)
	{
		super(img, r);
		opaque = false;
		cost = m;
		anchorPoint.set(0,0);
		type = t;
	}
	
	public void OnClick()
	{
		ShopScene scene = ((ShopScene)(GameManager.getInstance().getActiveScene()));
		if (s == false)
		{
			s = true;
			spriteImage = Texture.btn_2s;
		
			scene.selectedWeapon = type;
		}
	}
	public void OnRollover()
	{
		if (s == false)
			opaque = true;
	}
	public void OnLeave()
	{
		if (s == false)
			opaque = false;
	}
	public void OnDeselect()
	{
		s = false;
		spriteImage = Texture.btn_2;
	}
}
