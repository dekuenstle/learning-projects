package me.lasergans.game;

import java.awt.Point;

public class Vector {
	public double x = 0;
	public double y = 0;
	
	public Vector(){
		this(0,0);
	}
	
	public Vector(double x, double y){
		this.x = x; 
		this.y = y;
	}

	public void add(double x, double y){
		this.x += x;
		this.y += y;
	}
	public void add(Point p){
		this.x += p.x;
		this.y += p.y;
	}
	
	public void add(Vector v){
		this.x += v.getX();
		this.y += v.getY();
	}
	
	public void addf(Vector v){
		this.x += v.getXf();
		this.y += v.getYf();
	}
	
	public void add(int x, int y){
		this.x += x;
		this.y += y;
	}
	
	public void addX(double x){
		this.x += x;
	}
	public void addY(double y){
		this.y += y;
	}
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(Point p){
		this.x = p.x;
		this.y = p.y;
	}
	
	public void set(Vector v){
		this.x = v.getXf();
		this.y = v.getYf();
	}
	
	public int getX() {
		return (int)Math.round(x);
	}
	
	public double getXf() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return (int)Math.round(y);
	}
	
	public Point getPoint() {
		Point p = new Point(getX(), getY());
		return p;
	}
	
	
	public double getYf() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public double distanceTo(Vector v){
		double x0, y0, r;
		x0 = v.getXf();
		y0 = v.getYf();
		r = Math.sqrt( (x-x0)*(x-x0) + (y-y0)*(y-y0) );
		return r;
	}
	
}
