/*
 * Class ClientWorker is the thread created by the server.
 * Stephen Brandon
 * October 2014
 */
package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientWorker implements Runnable {
	
	private Socket socket;
	private String command;
	private Server server;
	private boolean killServer;
	
	//Constructor
	public ClientWorker(Socket socket, Server server){
		this.socket = socket;
		this.server = server;
		this.killServer = false;
	}
	
	//This method forms the String that will be sent to the client following a HELO command.
	public String heloResponse(){
		return command + "\nIP:" + socket.getLocalAddress().toString().substring(1) + "\nPort:" + socket.getLocalPort() + "\nStudentID:14303108\n";  
	}
	
	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			while(!killServer){
				command = reader.readLine();
				if(command.startsWith("HELO")){
					writer.println(heloResponse());
				}
				else if(command.equals("KILL_SERVICE")){
					killServer = true;
					socket.close();
					server.killService();
				}
				else{
					//Do Nothing
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}