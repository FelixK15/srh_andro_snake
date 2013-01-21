package com.example.androsnake;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;


public class ASGame {
	
	private ASStateMachine m_StateMachine = null;
	private boolean m_Running = true;
	private int m_FPS = 0;

	public ASGame() 
	{
		m_FPS = 30;
		m_StateMachine = ASStateMachine.getInstance();
	}
	
	public void onPause()
	{
		if(m_StateMachine.getCurrentState() != null){
			m_StateMachine.getCurrentState().onPause();
		}
	}
	
	public void onTouch(MotionEvent event)
	{
		if(m_StateMachine.getCurrentState() != null){
			m_StateMachine.getCurrentState().onTouch(event);
		}
	}
	
	public boolean isRunning()
	{
		return m_Running;
	}
	
	public void tick(long delta)
	{
		if(m_StateMachine.getCurrentState() != null){
			m_StateMachine.getCurrentState().tick(delta);
		}
	}
	
	public int getFPS()
	{
		return m_FPS;
	}

	public void onDraw(Canvas canvas) {
		for(ASState state : m_StateMachine.getStateList()){
			state.onDraw(canvas);
		}
	}
}
