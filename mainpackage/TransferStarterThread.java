package mainpackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TransferStarterThread extends Thread{
	
	int port = 13371;
	
	public void run() {
		try {
			ServerSocket serverSocket = null;
			Socket socket = null;
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();

			}
			while (true) {
				try {
					socket = serverSocket.accept();
				} catch (IOException e) {
					System.out.println("I/O error: " + e);
				}
				// new threa for a client
				new TransferThread(socket).start();
			}
		}catch(Exception e){
			
		}
	}
}
