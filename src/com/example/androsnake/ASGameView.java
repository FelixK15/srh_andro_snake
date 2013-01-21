package com.example.androsnake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class ASGameView extends SurfaceView implements Callback {
	
	private SurfaceHolder m_Holder = null;
	private ASGame m_Game = null;
	private Thread m_GameThread = null;
	
	public ASGameView(Context context) {
		super(context);
		m_Holder = getHolder();
		m_Holder.addCallback(this);
		m_Game = new ASGame();
		
		m_GameThread = new Thread(new Runnable() {
			public void run() {
				long delta = 0;
				long sleepTime = 0;
				float deltaInSeconds = 0.0f;
				float oneFrame = 0.0f;
				
				long now = System.currentTimeMillis();
				long last = System.currentTimeMillis();
				
				try{
					while(m_Game.isRunning()){
						oneFrame = 1.0f / (float)m_Game.getFPS();
						
						now = System.currentTimeMillis();
						delta = now - last;
						deltaInSeconds = (float)delta / 1000.0f;
						last = now;
						
						if(deltaInSeconds < oneFrame){
							sleepTime = (long)((oneFrame - deltaInSeconds) * 1000);
							//Thread.sleep(sleepTime);
						}else{
							delta = (long)(oneFrame * 1000);
						}
						
						//m_Game.tick(delta);
						synchronized(m_Holder){
							Canvas canvas = m_Holder.lockCanvas();
							if(canvas != null){
								//m_Game.onDraw(canvas);
								Paint p = new Paint();
								p.setColor(Color.RED);
								
								canvas.drawCircle(55,55,100,p);
								m_Holder.unlockCanvasAndPost(canvas);
							}
						}
						
					}
				}catch(Exception ex){
					
				}
			}
		});
	}
	
	public void onTouch(MotionEvent event)
	{
		m_Game.onTouch(event);
	}
	
	public void onPause()
	{
		m_Game.onPause();
	}
	
	public void onResume()
	{
		
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) 
	{
		
	}

	public void surfaceCreated(SurfaceHolder holder) 
	{
		m_GameThread.run();
	}

	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		
	}

	
	
	
}
