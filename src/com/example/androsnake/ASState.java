package com.example.androsnake;
import android.graphics.Canvas;
import android.view.MotionEvent;


public interface ASState {
	public void init();
	public void tick(long delta);
	public void onDraw(Canvas canvas);
	public void onPause();
	public void onTouch(MotionEvent event);
}
