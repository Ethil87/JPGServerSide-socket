package mainpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class MainClass extends JFrame implements ActionListener {
	private Thread thread = null;

	int port = 13371;
	private JTextField portfield = new JTextField();
	JButton listenBut = new JButton("Nasluchuj");
	JButton listenButStop = new JButton("Exit");

	public MainClass() {
		// JFrame props
		setSize(230, 120);
		setTitle("ServerController");
		setLayout(null);
		setLocationRelativeTo(null);
		listenBut.setBounds(0, 0, 100, 50);
		listenBut.addActionListener(this);
		add(listenBut);
		listenButStop.setBounds(110, 0, 100, 50);
		listenButStop.addActionListener(this);
		add(listenButStop);
	}

	public void SocketConnection() {
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
	}

	public static void main(String[] args) throws Exception {

		MainClass mc = new MainClass();
		mc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mc.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == listenBut) {
			thread = new TransferStarterThread();
			thread.start();
		}
		if (source == listenButStop){
			System.exit(0);
		}
	}
}
