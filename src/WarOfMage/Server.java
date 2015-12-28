package WarOfMage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server extends Net {
	PaintBoard paintboard;
	public static String[] Ip = new String[8];
	public static Socket[] sockets = new Socket[8];
	// ArrayList<String> characternumtoip = new ArrayList<>();
	Thread threadServerSocket = null;
	Server server = this;

	public Server(PaintBoard a)

	{
		// System.out.println(characternumtoip.get(0)+);
		this.isServer = true;
		paintboard = a;

		// 新增SERVER IP到 互轉表
		// this.paintboard.gamecontroller.characternumtoip.add(InetAddress
		// .getLocalHost().getHostAddress());
		this.paintboard.gamecontroller.porttocharacternum.put("0", "0");

		this.Receive();

	}

	public void startBuildUDP() {
		// UDP Server端等待接收Client端連線
		buildUDP receiver = new buildUDP();
		new Thread(receiver).start();
	}

	public void stopServerSocket() {
		this.threadServerSocket.stop();
	}

	@Override
	public void TCPDeliver(String input) throws IOException {
		String analysis[] = { "", "", "", "" };
		analysis = input.split(",");
		//System.out.println(input + ",Sever使用TCPDeliver");

		analysis[0] = this.paintboard.gamecontroller.portToUser(analysis[0]);

		input = analysis[0] + "," + analysis[1] + "," + analysis[2] + ","
				+ analysis[3];
		//System.out.println(analysis[0] + "使用者號碼");

		Analysis(analysis);
		//System.out.println("SERVER HERE");
		for (int i = 0; i < 8; i++) {
			if (this.sockets[i] != null && !this.sockets[i].isClosed()) {
				//System.out.println(i);
				DataOutputStream outputToClient = new DataOutputStream(
						this.sockets[i].getOutputStream());
				//System.out.println(i+1);
			
				
				outputToClient.writeUTF(input);
	
			
			
				//System.out.println(i+2);
				outputToClient.flush();
			}
		}

	}

	@Override
	public void UDPDeliver(String input) {

		for (int i = 0; i < 8; i++) {
			if (this.sockets[i] != null) {
				try {
					byte buffer[] = input.getBytes(); // 將訊息字串 msg 轉換為位元串。
					// 封裝該位元串成為封包 DatagramPacket，同時指定傳送對象。
					DatagramPacket packet = new DatagramPacket(buffer,
							buffer.length, InetAddress.getByName(this.Ip[i]),
							5002);
					DatagramSocket socket = new DatagramSocket(); // 建立傳送的 UDP
																	// Socket。
					socket.send(packet); // 傳送
					socket.close(); // 關閉 UDP socket.
				} catch (Exception e) {
					e.printStackTrace();
				} // 若有錯誤產生，列印函數呼叫堆疊。
			}
		}

	}

	@Override
	public void Receive() {
		// TCP Server端等待建立和Client端的連線
		buildTCP receiver = new buildTCP();
		this.threadServerSocket = new Thread(receiver);
		this.threadServerSocket.start();
		// UDP Server端等待接收Client端連線
		// buildUDP receiver2 = new buildUDP();
		// new Thread(receiver).start();

	}

	public void Analysis(String analysis[]) // input 拆解成 IP+Class 名稱+主題+內容
	{

		switch (analysis[1]) {
		case "gamecontroller":
			paintboard.gamecontroller.Analysis(analysis[0], analysis[2],
					analysis[3]);
			break;
		case "chat":
			// System.out.println("999");
			paintboard.lobbymenu.chat.Analysis(analysis[0], analysis[2],
					analysis[3]);
			// System.out.println("99999");
			break;
		default:
		}

	}

	class HandleAClient implements Runnable {
		private Socket socket; // A connect socket

		public HandleAClient(Socket socket) {
			this.socket = socket;

		}

		@Override
		public void run() {
			try {
				// System.out.println("run");
				DataInputStream inputFromClient = new DataInputStream(
						socket.getInputStream());

				while (true) {

					String input = inputFromClient.readUTF();

					TCPDeliver(input);
				}
			} catch (IOException e) {
				try {

					// 取得斷線socket client ip
					// 使用server ip to user 取得斷線腳色num
					// socket 關閉
					// 刪除server 腳色列表 斷線腳色(並且廣播更新所有腳色)
					// 刪除server 腳色NUM 對應IP 列表
					// 傳送本地端更新NUM

					String port = this.socket.getPort() + "";
					int characternum = Integer
							.valueOf(server.paintboard.gamecontroller
									.portToUser(port));

					this.socket.close();
					server.paintboard.gamecontroller
							.deleteCharacter(characternum);
					server.paintboard.gamecontroller
							.deleteCharacterNumInPortToCharacterNum(port);
					server.paintboard.gamecontroller
							.deliverUpdateClientNum(characternum + "");
					// TODO 如果在遊戲中 該腳色變成disconncet 不移除腳色
					/*
					 * server.paintboard.gamecontroller
					 * .deleteIpInCharacterNumToIp(characternum);
					 */

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.err.println(e);
			}
		}

	}

	class buildTCP implements Runnable {

		public buildTCP() {

		}

		@Override
		public void run() {
			try {

				ServerSocket serverSocket = new ServerSocket(5000);
				int index = 0;
				while (true) {

					Socket socket = serverSocket.accept();
					sockets[index] = socket;
					Ip[index] = socket.getLocalAddress().toString();
					index++;
					HandleAClient task = new HandleAClient(socket);

					int clientnum = server.paintboard.gamecontroller.porttocharacternum
							.size();

					server.paintboard.gamecontroller.porttocharacternum.put(""
							+ socket.getPort(), String.valueOf(clientnum));

					DataOutputStream outputToClient = new DataOutputStream(
							socket.getOutputStream());
					String data = "" + socket.getPort()
							+ ",gamecontroller,更新本地PORTandNum,"
							+ socket.getPort() + ";" + clientnum;
					//System.out.println(data);
					outputToClient.writeUTF(data);
					outputToClient.flush();
					/*
					 * server.paintboard.gamecontroller.characternumtoip
					 * .add(socket.getInetAddress().getHostAddress());
					 */
					//System.out.println(socket.getPort() + "<<客戶端連線時新增的PORT");
					new Thread(task).start();

				}
			} catch (IOException ex) {
				System.err.println(ex);
			}

		}
	}

	class buildUDP implements Runnable {
		final int SIZE = 8192; // 設定最大的訊息大小為 8192.
		byte buffer[] = new byte[SIZE]; // 設定訊息暫存區
		private DatagramPacket packet = new DatagramPacket(buffer,
				buffer.length);
		private DatagramSocket socket = null;

		public buildUDP() {
			// System.out.print("fdslfjsdpo");
		}

		@Override
		public void run() {

			//System.out.println("TCP 建立 UDP 未建立");
			while (true) {
				try {
					socket = new DatagramSocket(3001);

				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // 設定接收的 UDP Socket.
				try {
					socket.receive(packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // 接收封包。
				String input = new String(buffer, 0, packet.getLength()); // 將接收訊息轉換為字串。
				UDPDeliver(input);
				socket.close(); // 關閉 UDP Socket.
			}
		}

	}

}