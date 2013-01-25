package com.example.androsnake;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class ASSnakePart {
	protected float m_Rotation = 0.0f;
	protected float m_Scale = 1.0f;
	protected float m_Speed = 20.0f;
	
	protected Bitmap m_Bitmap = null;
	protected Paint m_Paint = new Paint();
	
	protected ASPoint m_Position = new ASPoint(0,0);
	protected ASPoint m_Orientation = new ASPoint(0,0);
	
	protected Matrix m_Transformation = null;
	
	public ASSnakePart(int bitmapID) {	
		m_Bitmap = BitmapFactory.decodeResource(ASActivity.GAME_RESOURCES, bitmapID);
	}
	
	public float getScale() {
		return m_Scale;
	}
	
	public float getRotate() {
		return m_Rotation;
	}
	
	public ASPoint getTranslate() {
		return m_Position;
	}
	
	public ASPoint getOrientation() {
		return m_Orientation;
	}
	
	public void setScale(float scaleFactor) {
		m_Scale = scaleFactor;
	}
	
	public void setRotate(float degrees) {
		m_Rotation = degrees;
	}
	
	public void setTranslate(ASPoint position) {
		m_Position = position;
	}
	
	public void setOrientation(ASPoint orientation) {
		m_Orientation = orientation;
	}
	
	public void onDraw(Canvas canvas) {
		if(m_Bitmap != null && m_Transformation != null) {
			canvas.drawBitmap(m_Bitmap, m_Transformation, m_Paint);
		}
	}
	
	public void move() {
		
		m_Position.x += (m_Orientation.x * m_Speed);
		m_Position.y += (m_Orientation.y * m_Speed);

		
		m_Transformation = new Matrix();
		m_Transformation.setTranslate(m_Position.x,m_Position.y);
		m_Transformation.preRotate(m_Rotation);
		m_Transformation.preScale(m_Scale, m_Scale);
	}
}
