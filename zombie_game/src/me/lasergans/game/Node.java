package me.lasergans.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Node {
	public boolean active = true;
	public double z = 0;
	public boolean useYasZ = false;
	public final Vector anchorPoint = new Vector(0.5, 0.5) ;  // the geometric center. It's from 0,0 to 1,1. 
	// ORIGIN IS AT THE UPPER LEFT!
	private final List<Node> children_ = new ArrayList<Node>();
	//private final List<Node> garbageChildren_ = new ArrayList<Node>();
	private int height_ = 1;
	private BufferedImage image_ = null;
	public  Node parent = null;
	public final Vector position = new Vector(0, 0); // position on the parent in pixels
	//private final List<Node> recentChildren_ = new ArrayList<Node>();
	public double rotation = 0; // in radian                                                                        
	public double scale = 1; 
	public double alpha = 1.0f;                                        //With it, the node is pinned on it's parent
//With it, the node is pinned on it's parent
	private int width_ = 1;
	public boolean opaque = true;
	protected boolean permanentVisible = false; // Only use in special cases (like Scene)
	public boolean ignoresTransformation = false;
	public CollisionType collisionType = CollisionType.NONE;
	public int collisionGroup = 0;
	private Rectangle bounds_;
	private Rectangle boundsRotated_;
	private Circle collisionCircle_;
	private Vector collisionPoint_;
	public enum CollisionType {
		   NONE ,POINT, CIRCLE, BOX 
		 }
	
	
	public final void addChild(Node child){
		//this.recentChildren_.add(child);
		this.children_.add(child);
		child.parent = this;
	}
	
	
	
	public final Node[] children(){
		Node[] n = new Node[children_.size()];
		children_.toArray(n);
		return n;
	}
	
	public final Graphics2D createGraphics(){		
		return this.getImage().createGraphics();
	}
	
	public int getHeight() {	
		return this.height_;
	}
	
	public BufferedImage getImage() {
		if(image_ == null) 
			image_ = new BufferedImage(this.getWidth()  ,this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		else if(image_.getWidth() != this.getWidth() || image_.getHeight() != this.getHeight())
			image_ = new BufferedImage(this.getWidth()  ,this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		return image_;
	}
	
	public final Node[] visibleChildren(){
		Node[] allChildren = children();
		List<Node> visibleChildren = new ArrayList<Node>();
		for (int i=0; i < allChildren.length; i++){
			if (allChildren[i].isVisible())
				visibleChildren.add(allChildren[i]);

		}
		
		Node[] n = new Node[visibleChildren.size()];
		visibleChildren.toArray(n);
		return n;
	}
	
	public int getWidth() {
		return this.width_;
	}  
	
	public boolean isVisible() {

		if (permanentVisible)
			return true;
		if (parent == null)
			return false;
		if (!parent.isVisible())
			return false;
		Rectangle r = parent.bounds();
		r.x = 0;
		r.y = 0;
		return r.intersects(this.boundsRotated());
	}
	

	
//	private final void refreshChildren() {
//		children_.removeAll(garbageChildren_);
//		for(int i=0; i < garbageChildren_.size(); i++){
//			Node n = garbageChildren_.get(i);
//			if(n.parent.equals(this))
//				n.parent = null;
//		}
//		children_.addAll(recentChildren_);
//		for(int i=0; i < recentChildren_.size(); i++){
//			Node n = recentChildren_.get(i);
//			n.parent = this;
//		}
//		
//		garbageChildren_.clear();
//		recentChildren_.clear();
//	}
	
	public final void removeChild(Node child){
		//this.garbageChildren_.add(child);
		this.children_.remove(child);
		child.parent = null;
	}
	
	public void render(Graphics g){
		
	}
	
	protected void setHeight(int height) {
		if (height <= 0) height=1;
		this.height_ = height;
	}

	protected void setWidth(int width) {
		if (width <= 0) width=1;
		this.width_ = width;
	}

	private final void transform(Graphics2D g){
		if (ignoresTransformation)
			return;
		Node n = this;
		double ax, ay;
		int w, h, x,y;
		ax = n.anchorPoint.getXf();
		ay = n.anchorPoint.getYf();
		w = n.getWidth();
		h = n.getHeight();
		x = position.getX();
		y = position.getY();
		g.translate(-ax * w *scale,-ay*h*scale);
		g.rotate(rotation*Math.PI/180, x+ax*w*scale, y+ay*h*scale);
	
	}

	private final void antiTransform(Graphics2D g){
		if (ignoresTransformation)
			return;
		Node n = this;
		double ax, ay;
		int w, h, x,y;
		ax = n.anchorPoint.getXf();
		ay = n.anchorPoint.getYf();
		w = n.getWidth();
		h = n.getHeight();
		x = position.getX();
		y = position.getY();
		g.rotate(-rotation*Math.PI/180, x+ax*w*scale, y+ay*h*scale);
		g.translate(ax * w *scale  ,ay*h*scale );
	}

	public final void triggerRedraw(Graphics pg){
		Graphics2D g = createGraphics();
		g.setBackground(new Color(0,0,0,0));
		if (pg == null)
			pg = g;
		if(!opaque)
			return;
		if(!isVisible())
			return;
		
		g.clearRect(0, 0, getWidth(), getHeight());
		if (this.children_.size() < 1) {
			render(g);
		} else {
			Node[] children = nodesZOrdered(visibleChildren());
			int i=0;
			for (; i<children.length; i++ ){
				if (useYasZ){
					if (children[i].position.getY() >= this.position.getY()){
						break;
					}
				} else {
					if (children[i].z >=0){
						break;
					}
				}
				children[i].triggerRedraw(g);
			}
			render(g);
			
			for (; i<children.length; i++ ){
				children[i].triggerRedraw(g);
			}
		}
		transform((Graphics2D)pg);
		((Graphics2D) pg).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) this.alpha));

		pg.drawImage(getImage(), position.getX(), position.getY(),(int) (getWidth()*scale),(int) (getHeight()*scale), null);
		antiTransform((Graphics2D) pg);
	}

	
	protected final Node[] nodesZOrdered(Node[] oldNode){
		Node[] reorderedNode = new Node[oldNode.length];
		if (reorderedNode.length < 2)
			return oldNode;
	
		if (useYasZ) {
			for(Node n : oldNode){
				int biggerAs = 0;
				for(Node nn : oldNode){
					if (n.position.getYf() > nn.position.getYf()){
						biggerAs++;
					}
				}
				while (reorderedNode[biggerAs]!= null){
					biggerAs++;
				}
				reorderedNode[biggerAs] = n;
				biggerAs = 0;
			}
			
		} else {
			for(Node n : oldNode){
				int biggerAs = 0;
				for(Node nn : oldNode){
					if (n.z > nn.z){
						biggerAs++;
					}
				}
				while (reorderedNode[biggerAs]!= null){
					biggerAs++;
				}
				reorderedNode[biggerAs] = n;
				biggerAs = 0;
			}
			
		}
	
		return reorderedNode;
	}
	public final void triggerUpdate(double deltaTime){
		if (!active)
			return;
	//	refreshChildren();
		update(deltaTime);
		
		Node[] allChildren = children();
		for(int i=0; i < allChildren.length; i++){
			allChildren[i].triggerUpdate(deltaTime);
		}
	}

	public void update(double deltaTime){
		
		
	}
	
	
	public Rectangle bounds(){
		if (bounds_ == null)
			this.bounds_ = new Rectangle();
		
		this.bounds_.x = (int) ((position.getX() - scale*getWidth()*anchorPoint.getXf()));
		this.bounds_.y = (int) ((position.getY() - scale*getHeight()*anchorPoint.getYf()));
		this.bounds_.width = (int)(getWidth() * scale);
		this.bounds_.height = (int)(getHeight() * scale);
		return this.bounds_;
	}
	
	public Rectangle boundsRotated(){
		if(ignoresTransformation)
			return bounds();
		if (boundsRotated_ == null)
			boundsRotated_ = new Rectangle();
		/*
		 * 
		 * TODO: Implement rotated bounds!
		 * 
		 * */
		return bounds();
	}
	
	public Vector collisionPoint(){
		Rectangle b = bounds();
		if (collisionPoint_ == null)
			collisionPoint_ = new Vector();
		collisionPoint_.set(b.getCenterX(), b.getCenterY());
		return collisionPoint_;
	}
	
	public Circle collisionCircle(){
		Rectangle b = this.bounds();
		if (collisionCircle_ == null)
			collisionCircle_ = new Circle();
	
		collisionCircle_.center.set(b.getCenterX(), b.getCenterY());
		double r = b.height;
		if (b.width > r)
			r = b.width;
		collisionCircle_.radius = r;
		return collisionCircle_;
	}
	
	public Rectangle collisionBox(){
		return this.boundsRotated();
	}
	
	public CollisionType collisionType(){
		return this.collisionType;
	}
	
	public boolean collidesWith(Node n){
		if ( (this.collisionType() == CollisionType.NONE) || (n.collisionType() == CollisionType.NONE)) 
			return false;
		if ( (this.collisionGroup == n.collisionGroup) && (n.collisionGroup*this.collisionGroup != 0) )
			return false;
		
		if (n.collisionType == CollisionType.POINT){
			return this.collidesWith(n.collisionPoint());
		}
		if (n.collisionType == CollisionType.CIRCLE){
			return this.collidesWith(n.collisionCircle());
		}
		if (n.collisionType == CollisionType.BOX){
			return this.collidesWith(n.collisionBox());
		}	
		System.err.println("CollisionType not implemented! " + this.collisionType() + " " + n.collisionType() );

		return false;
	}
	
	public boolean collidesWith(Vector v){
		if (this.collisionType() == CollisionType.NONE) {
			return false;
		}
		if (this.collisionType() == CollisionType.POINT) {
			Vector v0 = this.collisionPoint();
			return ( (v0.getX() == v.getX()) && 
						(v0.getY() == v.getY()));	
		}
		if (this.collisionType() == CollisionType.CIRCLE) {
			Circle c = this.collisionCircle();
			double length = v.distanceTo(c.center);
			return ( length <= c.radius);	
		}
		if (this.collisionType() == CollisionType.BOX) {
			return this.collisionBox().contains(v.getXf(), v.getYf());	
		}
		System.err.println("CollisionType not implemented!  " + this.collisionType() + " - Vector  :::" +  this.toString() );
		return false;
	}
	
	public boolean collidesWith(Rectangle rect){
		if (this.collisionType() == CollisionType.NONE) {
			return false;
		}
		if (this.collisionType() == CollisionType.POINT) {
			Vector v = this.collisionPoint();
			return rect.contains(v.getXf(), v.getYf());	
		}
		if (this.collisionType() == CollisionType.CIRCLE) {
			Rectangle box = rect;
			Circle circle = this.collisionCircle();
			double r = circle.radius;
			Vector c = circle.center;
			Vector v = new Vector(box.getX(), box.getY());
			if(c.distanceTo(v) <= r)
				return true;
			v.set(box.getX()+box.getWidth(), box.getY());
			if(c.distanceTo(v) <= r)
				return true;
			v.set(box.getX(), box.getY()+box.getHeight());
			if(c.distanceTo(v) <= r)
				return true;
			v.set(box.getX()+box.getWidth(), box.getY()+ box.getHeight());
			if(c.distanceTo(v) <= r)
				return true;
			v.set(c); v.addX(r);
			if(box.contains(v.getPoint()))
				return true;
			v.set(c); v.addX(-r);
			if(box.contains(v.getPoint()))
				return true;
			v.set(c); v.addY(r);
			if(box.contains(v.getPoint()))
				return true;
			v.set(c); v.addY(-r);
			if(box.contains(v.getPoint()))
				return true;
			return false;
		}	
		if (this.collisionType() == CollisionType.BOX) {
			return this.collisionBox().intersects(rect);
		}
		System.err.println("CollisionType not implemented!  " + this.collisionType() + " - Box" );
		return false;
	}
	
	public boolean collidesWith(Circle circ){
		if (this.collisionType() == CollisionType.NONE) {
			return false;
		}
		if (this.collisionType() == CollisionType.POINT) {
			Vector p = this.collisionPoint();
			double distance = p.distanceTo(circ.center);
			return ( distance <= circ.radius);
		}
		if (this.collisionType() == CollisionType.CIRCLE) {
				Circle c1 = this.collisionCircle();
				Circle c2 = circ;
				double distance = c2.center.distanceTo(c1.center);
				return ( distance <= c1.radius+c2.radius);
		}
		if (this.collisionType() == CollisionType.BOX) {
			Rectangle box = this.collisionBox();
			Circle circle = circ;
			double r = circle.radius;
			Vector c = circle.center;
			Vector v = new Vector(box.getX(), box.getY());
			if(c.distanceTo(v) <= r)
				return true;
			v.set(box.getX()+box.getWidth(), box.getY());
			if(c.distanceTo(v) <= r)
				return true;
			v.set(box.getX(), box.getY()+box.getHeight());
			if(c.distanceTo(v) <= r)
				return true;
			v.set(box.getX()+box.getWidth(), box.getY()+ box.getHeight());
			if(c.distanceTo(v) <= r)
				return true;
			v.set(c); v.addX(r);
			if(box.contains(v.getPoint()))
				return true;
			v.set(c); v.addX(-r);
			if(box.contains(v.getPoint()))
				return true;
			v.set(c); v.addY(r);
			if(box.contains(v.getPoint()))
				return true;
			v.set(c); v.addY(-r);
			if(box.contains(v.getPoint()))
				return true;
			return false;
		}
		System.err.println("CollisionType not implemented!  " + this.collisionType() + " - Circle" );
		return false;
	}
	
	public Node collidesWithSibling(){
		Node[] n = parent.children();
		for(int i=0; i < n.length; i++){
			if(this.collidesWith(n[i]))
				if(!this.equals(n[i]))
					return n[i];
		}
		return null;
	} 
	
}
