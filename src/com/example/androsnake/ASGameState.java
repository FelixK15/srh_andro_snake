package com.example.androsnake;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class ASGameState implements ASState {

	private long m_TickInterval = 160;
	private long m_Ticks = 0;
	
	private int m_Points = 0;
	private int m_Width = 0;
	private int m_Height = 0;
	
	private boolean m_PositionSet = false;
	private ASPoint m_TouchedPosition = new ASPoint(0,0);
	
	private Paint m_Paint = new Paint();
	
	private ArrayList<ASSnakePart> m_Parts = new ArrayList<ASSnakePart>();
	private ASGoody m_Goody = null;
	
	private Random m_Random = new Random();
	
	public void addPart() {
		ASSnakePart part = new ASSnakePart(R.drawable.snake_part);
		ASSnakePart last_part = m_Parts.get(m_Parts.size() - 1);
		if(last_part.m_Orientation.x == 0){
			part.setTranslate(new ASPoint(last_part.getTranslate().x,last_part.getTranslate().y - (20 * last_part.m_Orientation.y)));
		}else{
			part.setTranslate(new ASPoint(last_part.getTranslate().x - (20 * last_part.m_Orientation.x),last_part.getTranslate().y));
		}
		m_Parts.add(part);
	}
	
	public void onInit() {	
		//Add the player.
		m_Paint.setColor(Color.YELLOW);
		m_Paint.setTextSize(20.0f);
		m_Parts.add(new ASSnakePart(R.drawable.snake_head));
	}

	public void onEnd() {
		// TODO Auto-generated method stub

	}

	//Tick will be called once per frame.
	public void tick(long delta) {
		if(m_Width != 0 && !m_PositionSet){
			m_Parts.get(0).setTranslate(new ASPoint((int)(m_Width*0.5),(int)(m_Height*0.5f)));
			m_Parts.get(0).setOrientation(new ASPoint(0,-1));
			m_PositionSet = true;

		}else{
			if(m_Goody == null && m_Width != 0){
				m_Goody = new ASGoody(new ASPoint(m_Random.nextInt(m_Width),m_Random.nextInt(m_Height)));
			}
		}
		
		m_Ticks -= delta;
		if(m_Ticks <= 0) {
			//move
			
			m_Ticks = m_TickInterval;
			for(int i = m_Parts.size() - 1;i >= 1;i --){
				m_Parts.get(i).m_Transformation = m_Parts.get(i - 1).m_Transformation;
			}
			evaluateTouch();
			
			ASSnakePart player = m_Parts.get(0);
			player.move();
			
			if(m_Goody != null){
				if(player.m_Position.x + player.m_Bitmap.getWidth() > m_Goody.getPosition().x && player.m_Position.x < m_Goody.getPosition().x + m_Goody.getWidth()){
					if(player.m_Position.y + player.m_Bitmap.getHeight() > m_Goody.getPosition().y && player.m_Position.y < m_Goody.getPosition().y + m_Goody.getHeight()){
						m_Points += m_Goody.getPoints();
						m_Goody = null;
						addPart();
						--m_TickInterval;
					}
				}
			}
		}
	}

	public void onDraw(Canvas canvas) {
		if(m_Width == 0){
			m_Width = canvas.getWidth();
			m_Height = canvas.getHeight();
		}
		
		for(int i = m_Parts.size() - 1;i >= 0;--i){
			m_Parts.get(i).onDraw(canvas);
		}
		
		if(m_Goody != null){
			m_Goody.onDraw(canvas);
		}
		
		canvas.drawText("Points:" + m_Points, 10, 20, m_Paint);
	}

	public void onPause() {
		// TODO Auto-generated method stub

	}
	
	private void evaluateTouch() {	
		synchronized(m_TouchedPosition){
			if(m_TouchedPosition.x != 0 && m_TouchedPosition.y != 0){
				ASPoint orientation = m_Parts.get(0).getOrientation();
				ASPoint position = m_Parts.get(0).getTranslate();
				ASPoint newOrientation = new ASPoint(0,0);
				
				float rotation = m_Parts.get(0).getRotate();
				
				if(orientation.x == 0){
					if(m_TouchedPosition.x < position.x){
						newOrientation.x = -1;
					}else{
						newOrientation.x = 1;
					}
				}else{
					if(m_TouchedPosition.y < position.y){
						newOrientation.y = -1;
					}else{
						newOrientation.y = 1;
					}
				}
				
				if(newOrientation.y == 1){
					rotation = 180;
				}else if(newOrientation.y == -1){
					rotation = 0;
				}else if(newOrientation.x == -1){
					rotation = 270;
				}else{
					rotation = 90;
				}
				
				m_Parts.get(0).setOrientation(newOrientation);
				m_Parts.get(0).setRotate(rotation);
				
				m_TouchedPosition.x = m_TouchedPosition.y = 0;
			}
		}
	}

	public void onTouch(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			synchronized(m_TouchedPosition){
				m_TouchedPosition.x = (int)event.getRawX();
				m_TouchedPosition.y = (int)event.getRawY();
			}	
		}
	} 
}
