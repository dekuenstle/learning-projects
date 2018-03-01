package TowerDefense.Game;

import java.awt.Image;
import me.lasergans.game.GameManager;
import me.lasergans.game.SoundManager;
import me.lasergans.game.Sprite;
import me.lasergans.game.SpriteSheet;
import me.lasergans.game.Vector;

public class Enemy extends SpriteSheet {
	
	public enum Type { NONE, SMALL_ZOMBIE }
	public Type type = Type.NONE;
	
	private Timer t_attack;
	private boolean collided;
	
	double life;
	double speed;
	int strength;
	double chanceToDrop_Money;
	public int mValue;
	double chanceToDrop_Life;
	double chanceToDrop_Shield;
	double chanceToDrop_Ammo;
	
	public Enemy(Image img, int xPos, int yPos, int xtw, int ytw)
	{
		super(img, xPos, yPos, 1, xtw, ytw);
		this.collisionType = CollisionType.BOX;
		t_attack = new Timer(1, 0, true);
	}
	
	
	public void update(double dt)
	{
		t_attack.update(dt);
		
		Vector tPos = new Vector(	GameManager.gameWidth - position.getX(), GameManager.gameHeight - position.getY()	);
		rotation = Math.toDegrees(Math.atan2(tPos.getY() - position.getY(), tPos.getX() - position.getX())) + 90D;
		
		double rotR = Math.toRadians(rotation);
		
		GameScene scene = null;
		if ((GameManager.getInstance().getActiveScene()) instanceof GameScene)
			scene = ((GameScene)(GameManager.getInstance().getActiveScene()));
		else return;
        if (this.collidesWith(scene.turret) == false)
        {
        	position.add(Math.sin(rotR) * speed, -Math.cos(rotR) * speed);
        }
        else collided = true;

        if (life <= 0) die();
        
        if (collided && t_attack.trigger)
        {
        	if (scene.shield < 0)
        	{
        		scene.health -= strength;
        	}
        	else
        	{
        		scene.shield -= (int)Math.round(strength * 0.7);
        		scene.health -= (int)Math.round(strength * 0.3);
        	}
        }
	}
	
	void die()
	{
		if ( Math.random() < chanceToDrop_Money )
		{
			AnimatedItem money = new AnimatedItem(Texture.moneyFly_fading_strip20, position.x, position.y - 20, 20, 1);
			parent.addChild(money);
			((GameScene)(GameManager.getInstance().getActiveScene())).money += mValue;
		}
		if ( Math.random() < chanceToDrop_Ammo )
		{
			Package ammoPack = new Package(Texture.ammoPackage, position.x, position.y, "ammo");
			parent.addChild(ammoPack);
		}
		if ( Math.random() < chanceToDrop_Shield )
		{
			Package shieldPack = new Package(Texture.ammoPackage, position.x, position.y, "shield");
			parent.addChild(shieldPack);
		}
		if ( Math.random() < chanceToDrop_Life )
		{
			Package lifePack = new Package(Texture.ammoPackage, position.x, position.y, "life");
			parent.addChild(lifePack);
		}
		BloodSpot bloodspot = new BloodSpot(Texture.bloodspot, position.x, position.y);
		parent.addChild(bloodspot);
		parent.removeChild(this);
		((GameScene)(GameManager.getInstance().getActiveScene())).killCounter++;
		//SoundManager.play("monster_death");
	}


	

}
