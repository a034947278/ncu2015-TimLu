package WarOfMage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;

public class Chat {
	/*
	 * �ܼ�:
	 */
	// ��ѰT�����K�����
	String chatmessage[] = { " ", " ", " ", " ", " ", " ", " ", " " };
	// ���opaintboard����
	PaintBoard paintboard;

	public Chat(PaintBoard a) {
		this.paintboard = a;
	}

	public void cleanMessage() {
		for (int i = 0; i < 8; i++) {
			chatmessage[i] = " ";
		}
	}

	// �t�d�ǰe��Ѥ��e
	public void sendMessage(String input) {
		//System.out.println("����sedMessage");
		String data = "";

		data = this.paintboard.net.port + ",chat,�o�e�T��," + input;

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ��s��ѰT��
	private void updateMessage(String characternnum, String data) {
		// �Ҧ��T�����W��
		for (int i = 0; i < 8; i++) {
			if (i < 7)
				chatmessage[i] = chatmessage[i + 1];
		}
		// ��s�̤U���T��
		chatmessage[7] = paintboard.allcharacter.get(Integer
				.parseInt(characternnum)).name + ": " + data;
	}

	// ���R
	public void Analysis(String ip, String topic, String data) {
		//System.out.println("����chat���R");
		switch (topic) {
		case "�o�e�T��":
			updateMessage(ip, data);
			break;

		default:

		}
	}

	// ���X�Ҧ���Ѥ��e
	public void drawscene(Graphics2D g) {
		g.setFont(new Font("�L�n������", Font.BOLD, 20));
		g.setColor(new Color(255, 255, 255, 255));
		for (int i = 0; i < 8; i++) {
			g.drawString(chatmessage[i], 10, 515 + 20 * i);
		}
	}

}
