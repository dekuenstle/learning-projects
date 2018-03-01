package me.lasergans.game;

import java.awt.Image;

import java.io.IOException;

import javax.imageio.ImageIO;


public class Texture
{			
	public static Image load(String path)
	{
		
		try
		{
			//System.out.println( GameManager.class.getResource(path));
            Image img = ImageIO.read(Texture.class.getResource(path));
            return img;
		}
		catch (IOException e)
		{
			System.err.println("Couldn't load Image! <"+ path +">");
			System.err.println(e);
			return null;
		}
	}

}
