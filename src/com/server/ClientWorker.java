package com.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientWorker implements Runnable {
	
	private Socket socket;
	private String command;
	private Server server;
	
	public ClientWorker(Socket socket, Server server){
		this.socket = socket;
		this.server = server;
	}
	
	public String heloResponse(){
		return command + " IP: " + socket.getLocalAddress() + " Port: " + socket.getLocalPort() + " StudentID: 14303108\n";  
	}
	
	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			command = reader.readLine();
			if(command.equals("HELO text")){
				writer.println(heloResponse());
			}
			else if(command.equals("KILL_SERVICE")){
				writer.println("Stopping Service...");
				server.killService();
			}
			else{
				writer.println("Unknown Comand: " + command);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
