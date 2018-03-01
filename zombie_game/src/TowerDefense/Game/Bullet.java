package TowerDefense.Game;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import me.lasergans.game.Node;
import me.lasergans.game.Sprite;

public class Bullet extends Sprite
{
	public double dmg;
	public int life;
	
	private List<Enemy> hitEnemy = new ArrayList<Enemy>();
	
	public Bullet(Image img, int posX, int posY, double posZ, double rot, double damage, int life)
	{
		super(img, posX, posY, posZ);
		this.rotation = rot;
		dmg = damage;
		this.life = life;
		
		this.collisionType = CollisionType.POINT;
	}
	
	public void update(double dt)
	{
		position.add(Math.sin(Math.PI * rotation / 180) * 300 * dt, -Math.cos(Math.PI * rotation / 180) * 300 * dt);
		if (!isVisible())
		{
			parent.removeChild(this);
			return;
		}
		
		Node[] en = parent.visibleChildren();
		
		for ( Node enemy : en )
		{
			if (collidesWith(enemy) && (enemy instanceof Enemy))
			{
				if (hitEnemy.contains((Enemy)enemy) == false)
				{
					Blood blood = new Blood(Texture.blood, enemy.position.getXf(), enemy.position.getYf(), 5, 1);
					blood.rotation = this.rotation - 180;
					parent.addChild(blood);
					((Enemy)enemy).life -= dmg;
					hitEnemy.add(((Enemy)enemy));
					life -= 1;
				}
				if (life <= 0)
				{
					parent.removeChild(this);
				}
				return;
			}
		}
	}

}
