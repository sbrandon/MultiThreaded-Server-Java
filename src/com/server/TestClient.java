package com.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TestClient {
	
	private BufferedReader inFromUser;
	private DataOutputStream sendMessage;
	private BufferedReader reader;
	private int portNumber;
	private boolean running;
	private Socket socket;
	
	public TestClient(int portNumber){
		inFromUser = new BufferedReader(new InputStreamReader(System.in));
		this.portNumber = portNumber;
		try{
			this.socket = new Socket("localhost", portNumber);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void start(){
		System.out.println("Client is running. Port No. " + portNumber);
		running = true;
		while(running)
		{
			sendCommand();
		}
	}
	
	public void sendCommand(){
		try
		{
			sendMessage = new DataOutputStream(socket.getOutputStream());
			System.out.println("Enter Command:");
			sendMessage.writeBytes(inFromUser.readLine() + "\n");
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("FROM SERVER: " + reader.readLine());
		}
		catch(Exception e)
		{
			running = false;
			System.out.println("Cannot Connect With Server");
		}
	}
	
	public static void main(String args[]) throws Exception {
		int portNumber = Integer.parseInt(args[0]);
		TestClient client = new TestClient(portNumber);
		client.start();
	}
	
}