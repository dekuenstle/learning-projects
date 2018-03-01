package me.lasergans.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


public class Scene extends Node {
	public Scene(){
		setWidth(GameManager.gameWidth);
		setHeight(GameManager.gameHeight);
		permanentVisible = true;
		ignoresTransformation = true;
	

	}
	@Override 
	public void update(double dt){
		
		
	}
	@Override
	public void render(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.green);
		g.drawString("FPS:" + String.valueOf(GameManager.getInstance().gameLoop.getFPS()), getWidth()-80, getHeight()-10);		
	}
	
	public Node[] byMouseLocatedChildren(){
		Vector v = Mouse.position;
		Node[] children = this.children();
		List<Node> locChildren = new ArrayList<Node>();
		for (int i=0; i<children.length; i++){
			if(children[i].collidesWith(v))
				locChildren.add(children[i]);
		}
		Node[] result = new Node[locChildren.size()];
		locChildren.toArray(result);
		return result;
	}
	
	
	
}
