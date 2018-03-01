package TowerDefense.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.TextField;

import me.lasergans.game.GameManager;
import me.lasergans.game.Key;
import me.lasergans.game.Scene;
import me.lasergans.game.Sprite;
import me.lasergans.game.SpriteSheet;
import me.lasergans.game.Vector;

public class ShopScene extends Scene
{
	public int money;
	public int life;
	public int shield;
	public int ammo[] = new int[3];
	private GameScene oldScene;
	private boolean spaceP = true;
	
	private Vector[] STR1positions = {	new Vector(70,88), new Vector(70,110), new Vector(70,132), new Vector(70,162), new Vector(70,184), new Vector(70,206),
										new Vector(174,110), new Vector(174,132), new Vector(174,162), new Vector(174,184), new Vector(174,206),
										new Vector(220,110), new Vector(220,132), new Vector(220,162), new Vector(220,184), new Vector(220,206)};
	private String[] STR1values = { "","","","","","","+ 5%","+ 5%", "+ 10", "+ 5", "+ 50", "$ 20","$ 20", "$ 10", "$ 30", "$ 100"};
	private DrawString STR1 = new DrawString(Color.green, STR1positions, STR1values);
	
	private Vector[] STR2positions = {	new Vector(300,88), new Vector(320,130), new Vector(320,150), new Vector(320,170), new Vector(320,190) };
	private String[] STR2values = { "Weapons:","Automatic Gun", "Machine Gun", "Heavy Gun", "FlameThrower"};
	private DrawString STR2 = new DrawString(Color.green, STR2positions, STR2values);
	
	private Button_Buy[] btnS_buy = { 	new Button_Buy(Texture.btn_buy, new Rectangle(120,98,40,16), "life"),
										new Button_Buy(Texture.btn_buy, new Rectangle(120,118,40,16), "shield"),
										new Button_Buy(Texture.btn_buy, new Rectangle(120,148,40,16), "ammo0"),
										new Button_Buy(Texture.btn_buy, new Rectangle(120,170,40,16), "ammo1"),
										new Button_Buy(Texture.btn_buy, new Rectangle(120,192,40,16), "ammo2") };
	public ShopAnimation smg_anim;
	public ShopAnimation lmg_anim;
	public ShopAnimation hmg_anim;
	
	public WeaponController weaponController;
	
	public int selectedWeapon = 0;
	public int[] weaponsCost = { 0, 600, 1000, 5000 };
	
	public ShopScene(GameScene oldScene)
	{
		money = oldScene.money;
		life = (int) oldScene.health;
		shield = (int) oldScene.shield;
		ammo = oldScene.ammo;
		this.addChild(STR1);
		this.addChild(STR2);
		this.oldScene = oldScene;
		
		smg_anim = new ShopAnimation(Texture.shop_strip_smgAnim, 0, 0, 1, 180, 1);
		lmg_anim = new ShopAnimation(Texture.shop_strip_lmgAnim, 0, 0, 1, 180, 1);
		hmg_anim = new ShopAnimation(Texture.shop_strip_hmgAnim, 0, 0, 1, 180, 1);
		smg_anim.anchorPoint.set(0,0);
		lmg_anim.anchorPoint.set(0,0);
		hmg_anim.anchorPoint.set(0,0);
		smg_anim.position.set(480,110);
		lmg_anim.position.set(480,110);
		hmg_anim.position.set(480,110);
		smg_anim.opaque = true;
		lmg_anim.opaque = false;
		hmg_anim.opaque = false;
		this.addChild(smg_anim);
		this.addChild(lmg_anim);
		this.addChild(hmg_anim);
		Sprite bg = new Sprite(Texture.interface_bg, 0, 0);
		bg.anchorPoint.set(0,0);
		this.addChild(bg);
		
		for (int i=0; i<btnS_buy.length; i++)
			this.addChild(btnS_buy[i]);
		
		weaponController = new WeaponController();
		this.addChild(weaponController);
		for (int i=0; i<weaponController.buttons.length; i++)
		{
			this.addChild(weaponController.buttons[i]);
			this.addChild(weaponController.markedS[i]);
		}
		this.addChild(weaponController.buyWeapon);
		this.addChild(weaponController.STR1);
	}
	public void update(double dt)
	{
		STR1.string[0] = String.valueOf(money);
		STR1.string[1] = String.valueOf(life);
		STR1.string[2] = String.valueOf(shield);
		
		STR1.string[3] = String.valueOf(ammo[0]);
		STR1.string[4] = String.valueOf(ammo[1]);
		STR1.string[5] = String.valueOf(ammo[2]);
		if (Key.SPACE && !spaceP)
		{
			returnToMainScene();
		}
		if (selectedWeapon == 0)
		{
			smg_anim.opaque = true;
			lmg_anim.opaque = false;
			hmg_anim.opaque = false;
		}
		if (selectedWeapon == 1)
		{
			smg_anim.opaque = false;
			lmg_anim.opaque = true;
			hmg_anim.opaque = false;
		}
		if (selectedWeapon == 2)
		{
			smg_anim.opaque = false;
			lmg_anim.opaque = false;
			hmg_anim.opaque = true;
		}
		if (selectedWeapon == 3)
		{
			smg_anim.opaque = false;
			lmg_anim.opaque = false;
			hmg_anim.opaque = false;
		}
		if (!Key.SPACE)
		{
			spaceP = false;
		}
	}
	public void render(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	public void returnToMainScene()
	{
		oldScene.money = money;
		oldScene.health = life;
		oldScene.shield = shield;
		oldScene.ammo = ammo;
		oldScene.weaponA = weaponController.weaponA;
		GameManager.getInstance().setActiveScene(oldScene);
	}
}
