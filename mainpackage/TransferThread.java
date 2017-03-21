package mainpackage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

public class TransferThread extends Thread {

	protected Socket socket;
	Calendar now = Calendar.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH mm ss");

	public TransferThread(Socket socket) {
		this.socket = socket;
	}

	public static void makeFolder(String folder) {

		if (new File("C:\\TEST\\" + folder).exists() == true) {
			System.out.println("Directory already exist");
		} else {
			File f = new File("C:\\TEST\\" + folder);
			try {
				if (f.mkdir()) {
					System.out.println("Directory Created");
				} else {
					System.out.println("Directory is not created");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void run() {
		try {
			DataInputStream inputStream = new DataInputStream(socket.getInputStream());

			System.out.println("Reading: " + System.currentTimeMillis());

			byte[] sizeAr = new byte[4]; // [4]

			int size = inputStream.readInt();
			byte[] imageAr = new byte[size];
			inputStream.readFully(imageAr);

			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
			String ipadr = socket.getInetAddress().toString();
			makeFolder(ipadr);
			System.out.println(
					"Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
			ImageIO.write(image, "jpg",
					new File("C:\\TEST\\" + ipadr + "\\" + formatter.format(now.getTime()) + ".jpg"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
