package me.lasergans.game;

public class Circle {
	public final Vector center = new Vector();
	public double radius;
	
	public Circle(Vector center, double radius){
		this.center.set(center);
		this.radius = radius;
	}

	public Circle() {
	}
 }
