package TowerDefense.Game;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.net.URI;
import java.util.Random;

import javax.swing.JOptionPane;

import me.lasergans.game.GameManager;
import me.lasergans.game.Key;
import me.lasergans.game.Scene;
import me.lasergans.game.SoundManager;
import me.lasergans.game.Sprite;
import me.lasergans.game.Vector;

public class GameScene extends Scene
{
	public Turret turret;
	public Bar healthBar;
	public Bar ammoBar;
	public Bar shieldBar;
	
	public int zSTD_Count;
	public int zSTD_Max;
	public int zF_Count;
	public int zF_Max;
	double elapsedTime;
	
	public double health;
	public double shield;
	
	public int killCounter;
	
	public int money;
	public int ammo[] = new int[3];
	public int activeAmmoType;
	
	private boolean wChangeAvailable;
	private boolean spacePressed;
	private boolean p = true;
	public String turretType = "Small_Gun";
	
	public boolean[] weaponA = { true, false, false, false };
	public int selectedWeapon = 0;
	
	public String[] str1 = { "Weapon Selected: ", "" };
	public Vector[] pos1 = { new Vector(5, getHeight() - 42 - 6), new Vector(130,  getHeight() - 42 - 6) };
	public DrawString STR1 = new DrawString(new Color(78,111,76), pos1, str1);
	
	public String[] str2 = { "Life", "Shield", "Ammo" };
	public Vector[] pos2 = { new Vector(0,0), new Vector(0,0), new Vector(0,0) };
	public DrawString STR2 = new DrawString(new Color(0,0,0), pos2, str2);
	
	public GameScene()
	{
		turret = new SmallGun(Texture.turret, this.getWidth()/2, this.getHeight()/2, 0.8);
		
		healthBar = new Bar(new Color(78, 111, 76), getWidth() - 259, 20, health);
		ammoBar = new Bar(new Color(189, 180, 180), getWidth() - 6, 14, health);
		shieldBar = new Bar(new Color(151, 180, 180), 250, 20, health);
		
		this.addChild(turret);
		this.addChild(new Sprite(Texture.platform, this.getWidth()/2, this.getHeight()/2));
		this.addChild(turret.flash);
		
		healthBar.anchorPoint.set(0,0);
		healthBar.position.set(3, getHeight() - 42);
		this.addChild(healthBar);
		
		shieldBar.anchorPoint.set(0,0);
		this.addChild(shieldBar);
		
		ammoBar.anchorPoint.set(0,0);
		ammoBar.position.set(3, getHeight() - 18);
		this.addChild(ammoBar);
		
		health = 100;
		shield = 0;
		
		ammo[0] = 200;
		ammo[1] = 0;
		
		this.addChild(STR1);
		this.addChild(STR2);
		
		//SoundManager.playBackgroundMusic("Soundtrack_du_riechst_so_gut");
	}
	
	@Override 
	public void update(double dt)
	{
		healthBar.value = health / 100;
		shieldBar.value = shield / 100;
		ammoBar.value = turret.ammo / turret.ammoMax;
		
		shieldBar.position.set(healthBar.getWidth() * healthBar.value + 6, getHeight() - 42);
		
		if (shield < 0) shield = 0;
		if (shield > 100) shield = 100;
		if (health > 100) health = 100;
		
		if ( turret instanceof SmallGun || turret instanceof MachineGun) turret.ammo = ammo[0];
		if ( turret instanceof HeavyGun )  turret.ammo = ammo[1];
		if ( turret instanceof FlameThrower ) turret.ammo = ammo[2];
		
		if (Key.D)	// TODO: Cheat entfernen ^^
		{
			money += 10;
		}
		
		if (Key.SPACE && spacePressed == false)
		{
			spacePressed = true;
			switchToShop();
		}
		if (Key.P && !p){
			p = true;
			GameManager.getInstance().setActiveScene(new PauseScene(this));
		}
		if (!Key.P){
			p = false;
		}
		if (Key.W && wChangeAvailable)
		{
			wChangeAvailable = false;
			
			if (turret instanceof SmallGun)
			{
				if (weaponA[1]){
					changeToMachineGun();
					selectedWeapon = 1;
				}
				else if (weaponA[2]){
					changeToHeavyGun();
					selectedWeapon = 2;
				}
				else if (weaponA[3]){
					changeToFlameThrower();
					selectedWeapon = 3;
			}
			}
			else if (turret instanceof MachineGun)
			{
				if (weaponA[2]){
					changeToHeavyGun();
					selectedWeapon = 2;
				}
				else if (weaponA[3]){
					changeToFlameThrower();
					selectedWeapon = 3;
                }
				else if (weaponA[0]){
					changeToSmallGun();
					selectedWeapon = 0;
				}
			}
			else if (turret instanceof HeavyGun)
			{
				if (weaponA[3]){
					changeToFlameThrower();
					selectedWeapon = 3;
                }
				if (weaponA[0]){
					changeToSmallGun();
					selectedWeapon = 0;
				}
				else if (weaponA[1]){
					changeToMachineGun();
					selectedWeapon = 1;
				}
			}
			else if (turret instanceof FlameThrower)
			{
				if (weaponA[0]){
					changeToSmallGun();
					selectedWeapon = 0;
                }
				if (weaponA[1]){
					changeToMachineGun();
					selectedWeapon = 1;
				}
				else if (weaponA[2]){
					changeToHeavyGun();
					selectedWeapon = 2;
				}
			}
		}
		
		if (!Key.W)
		{
			wChangeAvailable = true;
		}
		if (!Key.SPACE)
		{
			spacePressed = false;
		}
		
		if (selectedWeapon == 0) STR1.string[1] = "Automatic Gun";
		if (selectedWeapon == 1) STR1.string[1] = "Machine Gun";
		if (selectedWeapon == 2) STR1.string[1] = "Heavy Gun";
		if (selectedWeapon == 3) STR1.string[1] = "FlameThrower";
		
		if (health <= 0)
		{
			die();
		}
		
		if (healthBar.value > 0.08 ) STR2.positions[0].set(healthBar.position.x + healthBar.getWidth()*healthBar.value - 28, getHeight() - 27); else STR2.positions[0].set(-20,-20);
		if (shieldBar.value > 0.17 ) STR2.positions[1].set(shieldBar.position.x + shieldBar.getWidth()*shieldBar.value - 44, getHeight() - 27); else STR2.positions[1].set(-20,-20);
		if (ammoBar.value > 0.08 ) STR2.positions[2].set(ammoBar.position.x + ammoBar.getWidth()*ammoBar.value - 44, getHeight() - 7); else STR2.positions[2].set(-20,-20);
	}
	
	@Override
	public void render(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.green);
		g.drawString("FPS:" + String.valueOf(GameManager.getInstance().gameLoop.getFPS()), getWidth() - 47, 12);
		g.drawImage(Texture.coin, 10, 10, null);
		g.drawImage(Texture.killC, 10, 26, null);
		g.setColor(new Color(140,140,40));
		g.drawString(String.valueOf(money), 30, 22);
		g.setColor(new Color(200,80,80));
		g.drawString(String.valueOf(killCounter), 30, 38);
	}
	void switchToShop()
	{
		ShopScene shscene = new ShopScene(this);
		shscene.weaponController.weaponA = this.weaponA;
		GameManager.getInstance().setActiveScene(shscene);
	}
	void changeToMachineGun()
	{
		this.removeChild(turret);
		this.removeChild(turret.flash);
		turret = new MachineGun(Texture.turret, (int)turret.position.x, (int)turret.position.y, 0.8);
		turret.ammo = ammo[0];
		this.addChild(turret);
		this.addChild(turret.flash);
		activeAmmoType = 0;
	}
	void changeToHeavyGun()
	{
		this.removeChild(turret);
		this.removeChild(turret.flash);
		turret = new HeavyGun(Texture.turret, (int)turret.position.x, (int)turret.position.y, 0.8);
		turret.ammo = ammo[1];
		this.addChild(turret);
		this.addChild(turret.flash);
		activeAmmoType = 1;
	}
	void changeToSmallGun()
	{
		this.removeChild(turret);
		this.removeChild(turret.flash);
		turret = new SmallGun(Texture.turret, (int)turret.position.x, (int)turret.position.y, 0.8);
		turret.ammo = ammo[0];
		this.addChild(turret);
		this.addChild(turret.flash);
		activeAmmoType = 0;
	}
	void changeToFlameThrower()
	{
		this.removeChild(turret);
		this.removeChild(turret.flash);
		turret = new FlameThrower(Texture.turret, (int)turret.position.x, (int)turret.position.y, 0.8);
		turret.ammo = ammo[2];
		this.addChild(turret);
		activeAmmoType = 2;
	}
	
	void die()
	{
		GameManager.getInstance().setActiveScene(new DefeatScene(killCounter, money));
	}

}
