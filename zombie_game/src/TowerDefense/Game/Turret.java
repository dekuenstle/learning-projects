package TowerDefense.Game;

import java.awt.Image;

import me.lasergans.game.GameManager;
import me.lasergans.game.Mouse;
import me.lasergans.game.SoundManager;
import me.lasergans.game.Sprite;

public class Turret extends Sprite
{
	protected String sound;
	protected int bulletLife;
	protected boolean shotAvailable;
	
	public double shotInterval = 0;
	public double dmg = 0;
	public double devModifier;
	
	public Sprite flash;
	protected boolean flashActive;
	protected boolean fl;
	public Timer t_shot;
	public Timer t_flash;
	
	public String type = "SmallGun";
	
	public double ammo;
	public double ammoMax;
	
	public Turret(Image img, int posX, int posY, double z)
	{
		super(img, posX, posY, z);
		this.collisionType = CollisionType.BOX;
		
		flash = new Sprite(Texture.muzzleFlash, position.x, position.y, 1);
		flash.opaque = false;
		
		t_shot = new Timer(shotInterval, 0, false);
		t_flash = new Timer(0.04, 0, false);
	}

	public void update(double dt)
	{
		t_shot.update(dt);
		t_flash.update(dt);
		
		double dX = Mouse.position.getXf() - position.getXf();
		double dY = Mouse.position.getYf() - position.getYf();
		
		rotation = -Math.atan2(dX, dY);
		rotation = (rotation * 180 / Math.PI - 180);
		
		flash.rotation = rotation;
		
		t_shot.setInterval(shotInterval);
				
		if (Mouse.button1Clicked && shotAvailable && ammo > 0)
		{
			shotAvailable = false;
			double deviation = (Math.random() - 0.5) * devModifier;
			Bullet bullet = new Bullet(Texture.bullet_small, position.getX(), position.getY(), 0.5, rotation + deviation, dmg, bulletLife);
			parent.addChild(bullet);
			((GameScene)(GameManager.getInstance().getActiveScene())).ammo[((GameScene)(GameManager.getInstance().getActiveScene())).activeAmmoType]--;
			SoundManager.play(sound);
			
			if (fl == true)
			{
				flash.opaque = true;
				fl = false;
			}
			t_flash.reset();
			t_shot.reset();
		}
		if (t_shot.trigger)
		{
			shotAvailable = true;
		}
		if (t_flash.trigger)
		{
			fl = true;
			flash.opaque = false;
		}
	}
	
}
