package com.example.androsnake;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


public class ASActivity extends Activity {

	public static Resources GAME_RESOURCES = null;
	
	private ASGameView m_GameView = null;
	
	public ASActivity() {
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		GAME_RESOURCES = getResources();
		ASStateMachine.getInstance().pushNewState(new ASGameState());
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
		m_GameView = new ASGameView(this);
		setContentView(R.layout.basic_layout);
		
		LinearLayout layout = (LinearLayout)findViewById(R.id.layout);
		layout.addView(m_GameView);
		
		//setContentView(m_GameView);
		

		
		m_GameView.setFocusable(true);
		
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onPause() {
		m_GameView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		m_GameView.onResume();
		super.onResume();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		m_GameView.onTouch(event);
		return super.onTouchEvent(event);
	}


}
