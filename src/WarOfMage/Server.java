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

		// �s�WSERVER IP�� �����
		// this.paintboard.gamecontroller.characternumtoip.add(InetAddress
		// .getLocalHost().getHostAddress());
		this.paintboard.gamecontroller.porttocharacternum.put("0", "0");

		this.Receive();

	}

	public void startBuildUDP() {
		// UDP Server�ݵ��ݱ���Client�ݳs�u
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
		//System.out.println(input + ",Sever�ϥ�TCPDeliver");

		analysis[0] = this.paintboard.gamecontroller.portToUser(analysis[0]);

		input = analysis[0] + "," + analysis[1] + "," + analysis[2] + ","
				+ analysis[3];
		//System.out.println(analysis[0] + "�ϥΪ̸��X");

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
					byte buffer[] = input.getBytes(); // �N�T���r�� msg �ഫ���줸��C
					// �ʸ˸Ӧ줸�ꦨ���ʥ] DatagramPacket�A�P�ɫ��w�ǰe��H�C
					DatagramPacket packet = new DatagramPacket(buffer,
							buffer.length, InetAddress.getByName(this.Ip[i]),
							5002);
					DatagramSocket socket = new DatagramSocket(); // �إ߶ǰe�� UDP
																	// Socket�C
					socket.send(packet); // �ǰe
					socket.close(); // ���� UDP socket.
				} catch (Exception e) {
					e.printStackTrace();
				} // �Y�����~���͡A�C�L��ƩI�s���|�C
			}
		}

	}

	@Override
	public void Receive() {
		// TCP Server�ݵ��ݫإߩMClient�ݪ��s�u
		buildTCP receiver = new buildTCP();
		this.threadServerSocket = new Thread(receiver);
		this.threadServerSocket.start();
		// UDP Server�ݵ��ݱ���Client�ݳs�u
		// buildUDP receiver2 = new buildUDP();
		// new Thread(receiver).start();

	}

	public void Analysis(String analysis[]) // input ��Ѧ� IP+Class �W��+�D�D+���e
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

					// ���o�_�usocket client ip
					// �ϥ�server ip to user ���o�_�u�}��num
					// socket ����
					// �R��server �}��C�� �_�u�}��(�åB�s����s�Ҧ��}��)
					// �R��server �}��NUM ����IP �C��
					// �ǰe���a�ݧ�sNUM

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
					// TODO �p�G�b�C���� �Ӹ}���ܦ�disconncet �������}��
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
							+ ",gamecontroller,��s���aPORTandNum,"
							+ socket.getPort() + ";" + clientnum;
					//System.out.println(data);
					outputToClient.writeUTF(data);
					outputToClient.flush();
					/*
					 * server.paintboard.gamecontroller.characternumtoip
					 * .add(socket.getInetAddress().getHostAddress());
					 */
					//System.out.println(socket.getPort() + "<<�Ȥ�ݳs�u�ɷs�W��PORT");
					new Thread(task).start();

				}
			} catch (IOException ex) {
				System.err.println(ex);
			}

		}
	}

	class buildUDP implements Runnable {
		final int SIZE = 8192; // �]�w�̤j���T���j�p�� 8192.
		byte buffer[] = new byte[SIZE]; // �]�w�T���Ȧs��
		private DatagramPacket packet = new DatagramPacket(buffer,
				buffer.length);
		private DatagramSocket socket = null;

		public buildUDP() {
			// System.out.print("fdslfjsdpo");
		}

		@Override
		public void run() {

			//System.out.println("TCP �إ� UDP ���إ�");
			while (true) {
				try {
					socket = new DatagramSocket(3001);

				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // �]�w������ UDP Socket.
				try {
					socket.receive(packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // �����ʥ]�C
				String input = new String(buffer, 0, packet.getLength()); // �N�����T���ഫ���r��C
				UDPDeliver(input);
				socket.close(); // ���� UDP Socket.
			}
		}

	}

}