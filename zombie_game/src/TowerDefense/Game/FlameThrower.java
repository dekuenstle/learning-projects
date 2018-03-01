package TowerDefense.Game;

import java.awt.Image;

import me.lasergans.game.GameManager;
import me.lasergans.game.Mouse;
import me.lasergans.game.SoundManager;
import me.lasergans.game.Vector;

public class FlameThrower extends Turret
{
	public static boolean perforating = true;
	public static double shotInterval_s = 0.005;
	public static double dmg_s = 0.1;
	public static double devModifier_s = 12;
	
	private Timer t_sound;
	private boolean soundA = true;
	
	public FlameThrower(Image img, int posX, int posY, double z)
	{
		super(img, posX, posY, z);
		flashActive = false;
		
		dmg = 0.1;
		devModifier = 12;
		shotInterval = 0.005;
		sound = "flame_thrower";
		ammoMax = 1000;
		
		t_sound = new Timer(0.245, 0, true);
	}
	public void update(double dt)
	{
		t_sound.update(dt);
		t_shot.update(dt);
		
		double dX = Mouse.position.getXf() - position.getXf();
		double dY = Mouse.position.getYf() - position.getYf();
		
		rotation = -Math.atan2(dX, dY);
		rotation = (rotation * 180 / Math.PI - 180);
		
		t_shot.setInterval(shotInterval);
				
		if (Mouse.button1Clicked && shotAvailable && ammo > 0)
		{
			shotAvailable = false;
			double deviation = (Math.random() - 0.5) * devModifier;
			Vector fPos = new Vector(Math.sin(Math.PI * rotation / 180) * 30 + position.x, -Math.cos(Math.PI * rotation / 180) * 30 + position.y);
			Flames bullet = new Flames(Texture.flames, (int)fPos.x, (int)fPos.y, 1, rotation + deviation, dmg, 9, 1);
			parent.addChild(bullet);
			((GameScene)(GameManager.getInstance().getActiveScene())).ammo[((GameScene)(GameManager.getInstance().getActiveScene())).activeAmmoType]--;
			
			t_shot.reset();
			
			if (soundA)
			{
				SoundManager.play(sound);
				soundA = false;
			}
		}
		if (t_shot.trigger)
		{
			shotAvailable = true;
		}
		if (t_sound.trigger)
		{
			soundA = true;
		}
	}

}
