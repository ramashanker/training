package com.rama.socket.server.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.stereotype.Component;

@Component
public class SocketCreate {
	private  Socket socket;
	public void startServer() {
		try {

			int port = 25000;
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server Started and listening to the port 25000");

			// Server is running always. This is done using this while(true) loop

			while (true) {

				// Reading the message from the client

				socket = serverSocket.accept();
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String number = br.readLine();

				System.out.println("Message received from client is " + number);
				String returnMessage = "Hello from Server";
				String sendreturnMessage = returnMessage + "\n";
				// Sending the response back to the client.

				OutputStream os = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(osw);
				bw.write(sendreturnMessage);
				bw.flush();
				System.out.println("Message sent to the client is " + returnMessage);
				System.out.println("\nProcess Complete\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
			}
		}
	}

}
