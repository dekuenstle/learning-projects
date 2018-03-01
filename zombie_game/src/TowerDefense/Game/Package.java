package TowerDefense.Game;

import java.awt.Image;

import me.lasergans.game.GameManager;
import me.lasergans.game.Mouse;
import me.lasergans.game.SoundManager;
import me.lasergans.game.Sprite;

public class Package extends Sprite
{
	String type;
	public Package(Image img, double posX, double posY, String t)
	{
		super(img, posX, posY);
		type = t;
	}
	public void update(double dt)
	{
		if (Mouse.position.x > position.x-14 && Mouse.position.x < position.x+30
				&& Mouse.position.y > position.y-14 && Mouse.position.y < position.y+30)
		{
			GameScene scene = ((GameScene)(GameManager.getInstance().getActiveScene()));
			
			if (type == "ammo")
			{
				if (scene.turret instanceof SmallGun || scene.turret instanceof MachineGun)
				{
					scene.ammo[scene.activeAmmoType] += 30;
				}
				if (scene.turret instanceof HeavyGun)
				{
					scene.ammo[scene.activeAmmoType] += 10;
				}
				if (scene.turret instanceof FlameThrower)
				{
					scene.ammo[scene.activeAmmoType] += 40;
				}
				AnimatedItem ammo = new AnimatedItem(Texture.ammo_fading_strip20, position.x, position.y-20, 20, 1);
				parent.addChild(ammo);
				SoundManager.play("reloaded");
			}
			if (type == "shield")
			{
				scene.shield += 10;
				AnimatedItem shield = new AnimatedItem(Texture.shield_fading_strip20, position.x, position.y-20, 20, 1);
				parent.addChild(shield);
				SoundManager.play("shield");
			}
			if (type == "life")
			{
				scene.health += 10;
				AnimatedItem life = new AnimatedItem(Texture.life_fading_strip20, position.x, position.y-20, 20, 1);
				parent.addChild(life);
				SoundManager.play("heal");
			}
			
			parent.removeChild(this);
		}
	}

}
