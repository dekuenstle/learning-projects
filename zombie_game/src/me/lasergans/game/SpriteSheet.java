package me.lasergans.game;

import java.awt.Graphics;
import java.awt.Image;

public class SpriteSheet extends Sprite {

	private int tileWidth = 1;
	private int tileHeight = 1;
	private int countXTiles = 1;
	private int countYTiles = 1;

	public int selectedFrame = 0;

	public SpriteSheet(Image img, double posX, double posY, int xTileCount, int yTileCount) {
		this(img, posX, posY, 0, xTileCount, yTileCount);
	}
	
	public SpriteSheet(Image img, double posX, double posY, double z, int xTileCount, int yTileCount) {
		super(img, posX, posY, z);
		this.countXTiles = xTileCount;
		this.countYTiles = yTileCount;
		int w,h;
		w= spriteImage.getWidth(null);
		h= spriteImage.getHeight(null);
		this.tileWidth = w / xTileCount;
		this.tileHeight = h / yTileCount;

		this.setWidth(tileWidth);
		this.setHeight(tileHeight);

	}

	@Override
	public void render(Graphics g){
		if (spriteImage == null){
			System.err.println("A Sprite needs a Image! <" + this + ">");
			return;
		}
		int tilesCount = countXTiles*countYTiles;
		if (tilesCount <= selectedFrame || selectedFrame < 0){
			System.err.println("Unreal selected Frame! i:" + selectedFrame + " <" + this + ">");
			return;
		}
		int tempSelFrame = selectedFrame;
		int row = 0;
		while(tempSelFrame >= countXTiles){
			row++;
			tempSelFrame -= countXTiles;
		}
		int column = tempSelFrame;
		g.drawImage(spriteImage, -(column)*tileWidth, -(row)*tileHeight, null);
	
	}
	
}
