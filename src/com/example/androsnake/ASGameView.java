package com.example.androsnake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ASGameView extends SurfaceView {
	
	private SurfaceHolder m_Holder = null;
	private ASGame m_Game = null;
	private Thread m_GameThread = null;
	private Paint m_ClearPaint = new Paint();
	
	public ASGameView(Context context) {
		super(context);
		m_Holder = getHolder();
		m_Game = new ASGame();
		m_ClearPaint.setColor(Color.BLACK);
		
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
						if(m_Holder.getSurface().isValid()){
							Canvas canvas = m_Holder.lockCanvas();
							canvas.drawRect(new Rect(0,0,canvas.getWidth(),canvas.getHeight()), m_ClearPaint);
						
							oneFrame = 1.0f / (float)m_Game.getFPS();
							
							now = System.currentTimeMillis();
							delta = now - last;
							deltaInSeconds = (float)delta / 1000.0f;
							last = now;
							
							if(deltaInSeconds < oneFrame){
								sleepTime = (long)((oneFrame - deltaInSeconds) * 1000);
								Thread.sleep(sleepTime);
							}else{
								delta = (long)(oneFrame * 1000);
							}
							
							m_Game.tick(delta);
							m_Game.onDraw(canvas);
							
							m_Holder.unlockCanvasAndPost(canvas);
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
		boolean succeed = false;
		while(!succeed){
			try{
				m_GameThread.join();
				m_Game.onPause();
				succeed = true;
			}catch(InterruptedException ex){
				
			}
		}
	}
	
	public void onResume()
	{
		m_GameThread.start();
	}
}
