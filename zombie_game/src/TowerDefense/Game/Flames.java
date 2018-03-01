package TowerDefense.Game;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import me.lasergans.game.GameManager;
import me.lasergans.game.Node;
import me.lasergans.game.SpriteSheet;

public class Flames extends SpriteSheet
{
	double i;
	
	public double dmg;
	public Timer t_dmg;
	
	public Flames(Image img, int posX, int posY, double posZ, double rot, double damage, int xTW, int yTW)
	{
		super(img, posX, posY, posZ, xTW, yTW);
		t_dmg = new Timer(0.1, 0, true);
		dmg = damage;
		this.rotation = rot;
		this.collisionType = CollisionType.CIRCLE;
	}
	public void update(double dt)
	{
		i += dt;
		if ((int)(i*12) < 9)
			selectedFrame = (int)(i*12);
		else
		{
			parent.removeChild(this);
			return;
		}
		t_dmg.update(dt);
		
		
		position.add(Math.sin(Math.PI * rotation / 180) * 200 * dt, -Math.cos(Math.PI * rotation / 180) * 200 * dt);
		
		Node[] en = parent.visibleChildren();
		
		for ( Node enemy : en )
		{
			if (collidesWith(enemy) && (enemy instanceof Enemy))
			{
				((Enemy)(enemy)).life -= dmg;
			}
		}
	}

}
