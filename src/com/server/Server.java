/*
 * Class Server
 * Stephen Brandon
 * October 2014
 */
package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
	
	private ServerSocket serverSocket;
	private static final ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
	
	//Constructor
	public Server(ServerSocket serverSocket){
		this.serverSocket = serverSocket;
	}
	
	//Start the server
	public void start(){
		try{
			while(true){
				if(executorService.getActiveCount() < executorService.getMaximumPoolSize()){
					Socket socket = serverSocket.accept();
					Server.executorService.execute(new ClientWorker(socket, this));
				}
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	//Stop the server and kill all the threads
	public void killService(){
		try {
			executorService.shutdownNow();
			serverSocket.close();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Main method
	public static void main(String[] args) throws IOException {
		int portNumber = Integer.parseInt(args[0]);
		Server server = new Server(new ServerSocket(portNumber));
		server.start();
	}
	
}