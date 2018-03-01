package me.lasergans.game;

import java.util.*;

public final class GameLoop extends Thread {
	
	private int destFPS = 0;
	public int updateScale = 1;
	public int FPS = 0;
	public Set<Loopable> loopTargets;
	
	public GameLoop(){
		this(0,1,null);
	}

	public GameLoop(int fPS, Loopable  loopObj){
		this(fPS,1,loopObj);
	}
	
	public GameLoop(int fPS, int  updateScale, Loopable loopObj){
		super();
		if (fPS < 0) fPS = 0;
		if (updateScale < 1) updateScale = 1;
		this.setUpdateScale(updateScale);
		this.setPriority(MAX_PRIORITY);
		this.loopTargets = new HashSet<Loopable>() ;
		loopTargets.add(loopObj);
		setFPS(fPS);
	}
	

	@Override
	public void run(){
		double sDeltaTime = 0;
		long nsOldTime = System.nanoTime();
		long nanoTimeUpdate = 0;
		long nsBeginTime = 0;
		int fps = 0;
		int uScale = 0;
		int updateCount = 0;
		long sleepTimeNano = 0;
		long sleepTimeMilli = 0;
		int frameCount = 0;
		long milliSecondCount = System.currentTimeMillis();
		double sleepCorrectur = 1;
		
		/*
		 * 
		 * TODO:   Ticks stabilisieren (evtl. mit sleepCorrectur)
		 * 
		 * 
		 * 
		 * */
		
		while (destFPS > 0) {
			fps = destFPS;
			uScale = updateScale;
			updateCount++;

			//System.out.println("DestFPS:" + destFPS);
			nsBeginTime = System.nanoTime();
			sDeltaTime =  ((nsBeginTime - nsOldTime) / Math.pow(10, 9)); 
			if(sDeltaTime < 0) sDeltaTime *= -1;
				nsOldTime = nsBeginTime;
			try {
				nanoTimeUpdate = System.nanoTime() + ((1000000000/fps)/uScale);
			} 
			catch (Exception e) {  }
			
			//----------------
			update(sDeltaTime);
			//----------------
			
				
			if (updateCount >= uScale) {
				//------------
				render();
				//------------
				updateCount = 0;
				frameCount++;

			}

			sleepTimeNano = nanoTimeUpdate - System.nanoTime();
			sleepTimeMilli = 0;

			sleepTimeNano *= sleepCorrectur;
			while (sleepTimeNano > 999999) {
				sleepTimeNano -= 1000000;
				sleepTimeMilli += 1;
			}
			if ( sleepTimeNano > 0 ) {

				try {
					sleep(sleepTimeMilli,(int) (sleepTimeNano));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				//System.err.println("ERROR: FRAMERATE BROKEN");

			}
			
			if (System.currentTimeMillis() >= milliSecondCount+1000){
				milliSecondCount += 1000;
				FPS = frameCount;
				frameCount = 0;
			}
	

		}
		FPS = 0;
		//System.out.println("GameLoop: STOPPED");
	}


	private synchronized void update(double deltaTime){
		
		//System.out.println("TPS:" + 1/deltaTime + ", FPS:" + FPS);
		Object[] loopables =  this.loopTargets.toArray();
		for (int i= 0; i< loopables.length; i++){
			if ((loopables[i] != null) && (loopables[i] instanceof  Loopable)){
				((Loopable)loopables[i]).tick(deltaTime);
			}
		}
		
	}
	
	private synchronized void render(){
		//System.out.println("RENDER");

		Object[] drawables =  this.loopTargets.toArray();
		
		for (int i= 0; i< drawables.length; i++){
			if (drawables[i] != null && (drawables[i] instanceof  Loopable)){
				((Loopable)drawables[i]).redraw();
			}
		}
	}
	
	
	
	
	
	
	// ---------------------------------------------- //
	// ********************************************** //
	
	

	public int getFPS() {
		return FPS;
	}

	public void setFPS(int fPS) {
		if (!this.isAlive() && fPS > 0){
			destFPS = fPS;
			System.out.println("GameLoop: STARTED. Destinatet Framerate: " + fPS);
			start();
		} else if (fPS <= 0) {
			destFPS = 0;			
		} else {
			destFPS = fPS;
		}
		
		
	}


	public int getUpdateScale() {
		return updateScale;
	}


	public void setUpdateScale(int updateScale) {
		this.updateScale = updateScale;
		if (this.updateScale < 1) {
			this.updateScale = 1;			
		}
	}

}
