package me.lasergans.game;

import java.awt.event.KeyEvent;

public class Key {
	public static boolean A = false;
	public static boolean D = false;
	public static boolean S = false;
	public static boolean W = false;
	public static boolean P = false;
	
	public static boolean ESCAPE = false;
	public static boolean ENTER = false;
	public static boolean SPACE = false;
	
	public static boolean LEFT = false;
	public static boolean RIGHT = false;
	public static boolean UP = false;
	public static boolean DOWN = false;
	
	
	
	public static boolean setKeyForKeyCode(int code, boolean pressed){ 
		switch (code) {
		
			case KeyEvent.VK_A:
				A = pressed;
				break;
			case KeyEvent.VK_D:
				D = pressed;
				break;
			case KeyEvent.VK_S:
				S = pressed;
				break;
			case KeyEvent.VK_W:
				W = pressed;
				break;
			case KeyEvent.VK_P:
				P = pressed;
				break;
                
			case KeyEvent.VK_ESCAPE:
				ESCAPE = pressed;
				break; 
			case KeyEvent.VK_ENTER: 
				ENTER = pressed;
	            break;    
			case KeyEvent.VK_SPACE: 
				SPACE = pressed;
            	break;
            	
			case KeyEvent.VK_LEFT:
				LEFT = pressed;
				break;     
			case KeyEvent.VK_RIGHT: 
				RIGHT = pressed;
            	break;
			case KeyEvent.VK_UP:
				UP = pressed;
				break;     
			case KeyEvent.VK_DOWN: 
				DOWN = pressed;
            	break;
                    
			default:
				return false;   
		}
        return true;
	}
	
}
