package me.lasergans.game;


import java.awt.Graphics;
import java.awt.Image;

public class Sprite extends Node {
	public Image spriteImage = null;
	//private Sprite(){}
	
	public Sprite(Image img){
		this(img, (double)0, (double)0, 0);
	}
	public Sprite(Image img, int posX, int posY){
		this(img, (double)posX, (double)posY, 0);
	}
	public Sprite(Image img, double posX, double posY){
		this(img, posX, posY, 0);
	}
	
	public Sprite(Image img, int posX, int posY, double z){
		this(img, (double)posX, (double)posY, z);
	}
	
	public Sprite(Image img, double posX, double posY, double z){
		if(img == null)
			System.err.println("A Sprite has to show a image! ::: Sprite");

		this.spriteImage = img;
		this.setHeight(img.getHeight(null));
		this.setWidth(img.getWidth(null));
		this.position.set(posX, posY);
		this.z = z;
	}
	
	@Override
	public void render(Graphics g){
		if (spriteImage == null){
			System.err.println("A Sprite needs a Image! <" + this + ">");
			return;
		}
		g.drawImage(spriteImage, 0, 0, getWidth(), getHeight() , null);
	
	}
	
	public void update(double dt){
		
		
	}
	
}
