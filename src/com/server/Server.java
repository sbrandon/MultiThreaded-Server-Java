package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
	
	private ServerSocket serverSocket;
	private static final ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
	
	public Server(ServerSocket serverSocket){
		this.serverSocket = serverSocket;
	}
	
	public void killService(){
		try {
			executorService.shutdownNow();
			serverSocket.close();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		int portNumber = Integer.parseInt(args[0]);
		Server server = new Server(new ServerSocket(portNumber));
		while(true){
			if(executorService.getActiveCount() < executorService.getMaximumPoolSize()){
				Socket socket = server.serverSocket.accept();
				Server.executorService.execute(new ClientWorker(socket, server));
			}
		}
	}
	
}
