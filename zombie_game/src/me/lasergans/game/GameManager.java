package me.lasergans.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;



import javax.swing.JFrame;
import javax.swing.JPanel;


public class GameManager extends Canvas implements Loopable, MouseListener, MouseMotionListener, KeyListener {
    
	private static final long serialVersionUID = 1L;
	public static final int SCALE = 1;
	private static GameManager instance;
	public GameLoop gameLoop;
	private Scene activeScene;
	public static final int gameWidth = 640;
	public static final int gameHeight = gameWidth * 3/4;
	
	protected GameManager(){			
		
        
		 this.setPreferredSize(new Dimension(gameWidth * SCALE, gameHeight * SCALE));
		 this.setMinimumSize(new Dimension(gameWidth * SCALE, gameHeight * SCALE));
		 this.setMaximumSize(new Dimension(gameWidth * SCALE, gameHeight * SCALE));
		 
		 addMouseListener(this);
		 addMouseMotionListener(this);
		 addKeyListener(this);
		 this.gameLoop = new GameLoop(0, this);

	}
	
	public static void setupWindow(){

		System.out.println("Hello!" );
		JFrame frame = new JFrame();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(GameManager.getInstance());
        frame.setContentPane(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        GameManager.getInstance().requestFocus();
	}
	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}
	
	
	public void setActiveScene(Scene activeScene){
		this.activeScene = activeScene;
	}
	
	
	public Scene getActiveScene() {
		return activeScene;
	}

	@Override
	public void tick(double deltaTime){
		if(activeScene != null)
			activeScene.triggerUpdate(deltaTime);
	}

	@Override
	public void redraw() {

		if(activeScene != null) {
			
			BufferStrategy bs = this.getBufferStrategy();
	        if (bs == null) {
	        	this.createBufferStrategy(3);
	            bs = this.getBufferStrategy();
	        }
			
			//Graphics g = bs.getDrawGraphics();
			Graphics g = this.getGraphics();

			//g.setColor(Color.red);
	        //g.fillRect(0, 0, this.getWidth(), this.getHeight());
			activeScene.triggerRedraw(null);
	       g.translate((getWidth() - gameWidth * SCALE) / 2, (this.getHeight() - gameHeight * SCALE) / 2);
	        g.clipRect(0, 0, gameWidth * SCALE, gameHeight * SCALE);
			
			
			
			g.drawImage(activeScene.getImage(), 0, 0, gameWidth * SCALE, gameHeight * SCALE,null);

		}
		
	}
	


	@Override
	public void mouseClicked(MouseEvent e) {
		Mouse.position.set(e.getX()/SCALE, e.getY()/SCALE);
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		Mouse.position.set(e.getX()/SCALE, e.getY()/SCALE);
		int b = e.getButton();
		if(b == MouseEvent.BUTTON1)
			Mouse.button1Clicked = true;
		if(b == MouseEvent.BUTTON2)
			Mouse.button2Clicked = true;
		if(b == MouseEvent.BUTTON3)
			Mouse.button3Clicked = true;
	}


	@Override
	public void mouseExited(MouseEvent e) {
		Mouse.position.set(e.getX()/SCALE, e.getY()/SCALE);
		Mouse.button1Clicked = false;
		Mouse.button2Clicked = false;
		Mouse.button3Clicked = false;
	}
	


	@Override
	public void mousePressed(MouseEvent e) {
		Mouse.position.set(e.getX()/SCALE, e.getY()/SCALE);
		int b = e.getButton();
		if(b == MouseEvent.BUTTON1)
			Mouse.button1Clicked = true;
		if(b == MouseEvent.BUTTON2)
			Mouse.button2Clicked = true;
		if(b == MouseEvent.BUTTON3)
			Mouse.button3Clicked = true;
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		Mouse.position.set(e.getX()/SCALE, e.getY()/SCALE);
		int b = e.getButton();
		if(b == MouseEvent.BUTTON1)
			Mouse.button1Clicked = false;
		if(b == MouseEvent.BUTTON2)
			Mouse.button2Clicked = false;
		if(b == MouseEvent.BUTTON3)
			Mouse.button3Clicked = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Mouse.position.set(e.getX()/SCALE, e.getY()/SCALE);
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		Mouse.position.set(e.getX()/SCALE, e.getY()/SCALE);
	}


	@Override
	public void keyPressed(KeyEvent e) {
		Key.setKeyForKeyCode(e.getKeyCode(), true);
	}


	@Override
	public void keyReleased(KeyEvent e) {
		Key.setKeyForKeyCode(e.getKeyCode(), false);
	}


	@Override
	public void keyTyped(KeyEvent e) {
	
	}
	
	public static void Exit()
	{
		System.exit(0);
	}
}
