package WarOfMage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;


public class Client extends Net {
	PaintBoard paintboard;
	private DataInputStream fromServer;
	private DataOutputStream toServer;
	private String ServerIP;
	private InetAddress server;
	private Client client;

	public Client(PaintBoard a, String ip) throws UnknownHostException,
			IOException {
		this.client = this;
		this.isServer = false;
		paintboard = a;
		this.ServerIP = ip;

		this.socket = new Socket(ServerIP, 5000);

		this.fromServer = new DataInputStream(this.socket.getInputStream());
		this.toServer = new DataOutputStream(this.socket.getOutputStream());
		this.Receive();

		this.server = InetAddress.getByName(this.ServerIP);

	}

	@Override
	public void Receive() {
		buildTCPReceive receiver = new buildTCPReceive(fromServer);
		new Thread(receiver).start();
		// buildUDPReceive receiver2 = new buildUDPReceive();
		// new Thread(receiver2).start();
		;
	}

	@Override
	public void TCPDeliver(String input) throws IOException {
		//System.out.println(input + ",客戶端執行TCP傳送");
		toServer.writeUTF(input);
		toServer.flush();

	}

	@Override
	public void UDPDeliver(String input) throws IOException {
		try {
			byte buffer[] = input.getBytes(); // 將訊息字串 msg 轉換為位元串。
			// 封裝該位元串成為封包 DatagramPacket，同時指定傳送對象。
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
					server, 5001);
			DatagramSocket socket = new DatagramSocket(); // 建立傳送的 UDP Socket。
			socket.send(packet); // 傳送
			socket.close(); // 關閉 UDP socket.
		} catch (Exception e) {
			e.printStackTrace();
		} // 若有錯誤產生，列印函數呼叫堆疊。
	}

	public void Analysis(String input) // input 拆解成 IP+Class 名稱+主題+內容
	{
		String analysis[] = { "", "", "", "" };
		analysis = input.split(",");
		//System.out.println(input);

		switch (analysis[1]) {
		case "gamecontroller":
			paintboard.gamecontroller.Analysis(analysis[0], analysis[2],
					analysis[3]);
			break;
		case "chat":
			paintboard.lobbymenu.chat.Analysis(analysis[0], analysis[2],
					analysis[3]);
			break;
		default:
		}

	}

	class buildTCPReceive implements Runnable {
		public DataInputStream fromServer;

		public buildTCPReceive(DataInputStream fromServer) {
			this.fromServer = fromServer;
		}

		@Override
		public void run() {
			while (true) {
				try {
					String input = this.fromServer.readUTF();
					Analysis(input);
				} catch (IOException e) {
					try {
						client.paintboard.gamecontroller.selfnum = 0;
						client.socket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(client.paintboard, "主機斷線",
							"警告", JOptionPane.WARNING_MESSAGE);
					client.paintboard.gamecontroller.whenServerDisconnected();
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}

			}

		}
	}
	/*
	 * class buildUDPReceive implements Runnable { public buildUDPReceive() {
	 * 
	 * }
	 * 
	 * @Override public void run() { final int SIZE = 8192; // 設定最大的訊息大小為 8192.
	 * byte buffer[] = new byte[SIZE]; // 設定訊息暫存區 while (true) { DatagramPacket
	 * packet = new DatagramPacket(buffer, buffer.length); DatagramSocket socket
	 * = null; try { socket = new DatagramSocket(5002); } catch (SocketException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } // 設定接收的
	 * UDP Socket. try { socket.receive(packet); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } // 接收封包。 String
	 * input = new String(buffer, 0, packet.getLength()); // 將接收訊息轉換為字串。
	 * socket.close(); }
	 * 
	 * }
	 * 
	 * }
	 */

	@Override
	public void stopServerSocket() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startBuildUDP() {
		// TODO Auto-generated method stub
		
	}
}
