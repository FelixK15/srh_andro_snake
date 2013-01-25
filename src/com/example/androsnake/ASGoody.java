package com.example.androsnake;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ASGoody {
	
	private ASPoint m_Position = null;
	
	private Paint m_Paint = new Paint();
	
	private Bitmap m_Bitmap = null;
	private int m_Points = 0;
	
	public static int MAX_POINTS = 100;
	public static int MIN_POINTS = 20;
	
	private static Random POINTS_RANDOM = new Random();
	
	public ASGoody(ASPoint position) {
		m_Position = position;
		
		m_Points = POINTS_RANDOM.nextInt(MAX_POINTS - MIN_POINTS) + MIN_POINTS;
		m_Paint.setColor(Color.RED);
		if(m_Points < 26){
			m_Bitmap = BitmapFactory.decodeResource(ASActivity.GAME_RESOURCES, R.drawable.goody_1);
		}else if(m_Points > 26 && m_Points < 52){
			m_Bitmap = BitmapFactory.decodeResource(ASActivity.GAME_RESOURCES, R.drawable.goody_2);
		}else{
			m_Bitmap = BitmapFactory.decodeResource(ASActivity.GAME_RESOURCES, R.drawable.goody_3);
		}
	}
	
	public int getPoints() {
		return m_Points;
	}
	
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(m_Bitmap,m_Position.x,m_Position.y,m_Paint);
	}
	
	public ASPoint getPosition() {
		return m_Position;
	}
	
	public int getWidth() {
		return m_Bitmap.getWidth();
	}
	
	public int getHeight() {
		return m_Bitmap.getHeight();
	}
	
}
