package com.example.androsnake;
import java.util.ArrayList;


public class ASStateMachine {
	private static ASStateMachine m_Singleton = null;
	private ArrayList<ASState> m_States = new ArrayList<ASState>();
	
	private ASStateMachine() {
		
	}
	
	public boolean popCurrentState() {
		if(m_States.size() > 0){
			m_States.get(0).onEnd();
			m_States.remove(0);
			
			return true;
		}
		
		return false;
	}
	
	public ASState getCurrentState() {
		ASState currentState = null;
		if(m_States.size() > 0){
			currentState = m_States.get(0);
		}
		
		return currentState;
	}
	
	public void pushNewState(ASState state) {
		m_States.add(0, state);
		state.onInit();
	}
	
	public ArrayList<ASState> getStateList() {
		return m_States;
	}
	
	public static ASStateMachine getInstance() {
		if(m_Singleton == null){
			m_Singleton = new ASStateMachine();
		}
		
		return m_Singleton;
	}
}
