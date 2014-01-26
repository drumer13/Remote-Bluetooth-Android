package com.luugiathuy.apps.remotebluetooth;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.InputStream;

import javax.microedition.io.StreamConnection;

public class ProcessConnectionThread implements Runnable{

	private StreamConnection mConnection;
	
	// Constant that indicate command from devices
	private static final int EXIT_CMD = -1;
	private static final int KEY_RIGHT = 1;
	private static final int KEY_LEFT = 2;
	private static final int MOVE_MOUSE = 3;
	
	public ProcessConnectionThread(StreamConnection connection)
	{
		mConnection = connection;
	}
	
	@Override
	public void run() {
		try {
			
			// prepare to receive data
			InputStream inputStream = mConnection.openInputStream();
	        
			System.out.println("waiting for input");
	        
	        while (true) {
	        	int command = inputStream.read();
	        	
	        	if (command == EXIT_CMD)
	        	{	
	        		System.out.println("finish process");
	        		break;
	        	}
	        	
	        	processCommand(inputStream.read());
        	}
        } catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
	/**
	 * Process the command from client
	 * @param command the command code
	 */
	private void processCommand(int command) {
		try {
			Robot robot = new Robot();
			switch (command) {
	    	case KEY_RIGHT:
	    		robot.keyPress(KeyEvent.VK_RIGHT);
	    		System.out.println("Right");
	    		// release the key after it is pressed. Otherwise the event just keeps getting trigged	    		
	    		robot.keyRelease(KeyEvent.VK_RIGHT);
	    		break;
	    	case KEY_LEFT:
	    		robot.keyPress(KeyEvent.VK_LEFT);
	    		System.out.println("Left");
	    		// release the key after it is pressed. Otherwise the event just keeps getting trigged	    		
	    		robot.keyRelease(KeyEvent.VK_LEFT);
	    		break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void processCommand(byte[] coordinate) {
	
			byte x = coordinate[0];
			byte y = coordinate[1];
			Robot robot;
			try {
				robot = new Robot();
				robot.mouseMove(x, y);
			} catch (AWTException e) {
				e.printStackTrace();
			}
			
		
	}
}