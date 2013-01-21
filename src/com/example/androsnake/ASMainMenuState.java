package com.example.androsnake;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;


public class ASMainMenuState implements ASState {

	public void init() {
		
	}

	public void tick(long delta) {
	
	}

	@SuppressLint("DrawAllocation")
	public void onDraw(Canvas canvas) {
		Paint p = new Paint();
		p.setColor(Color.BLUE);
		canvas.drawColor(Color.RED);
		canvas.drawCircle(300, 300, 50, p);
	}

	public void onPause() {
	
	}

	public void onTouch(MotionEvent event) {
	
	}

}
