package com.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TestClient {
	
	public static void main(String args[]) throws Exception {
		Socket socket = new Socket("localhost", 6789);
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DataOutputStream sendMessage = new DataOutputStream(socket.getOutputStream());
		sendMessage.writeBytes(inFromUser.readLine() + "\n");
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		System.out.println("FROM SERVER: " + reader.readLine());
	}
}
