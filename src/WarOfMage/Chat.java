package WarOfMage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;

public class Chat {
	/*
	 * 變數:
	 */
	// 聊天訊息的八個欄位
	String chatmessage[] = { " ", " ", " ", " ", " ", " ", " ", " " };
	// 取得paintboard控制
	PaintBoard paintboard;

	public Chat(PaintBoard a) {
		this.paintboard = a;
	}

	public void cleanMessage() {
		for (int i = 0; i < 8; i++) {
			chatmessage[i] = " ";
		}
	}

	// 負責傳送聊天內容
	public void sendMessage(String input) {
		//System.out.println("執行sedMessage");
		String data = "";

		data = this.paintboard.net.port + ",chat,發送訊息," + input;

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 更新聊天訊息
	private void updateMessage(String characternnum, String data) {
		// 所有訊息往上移
		for (int i = 0; i < 8; i++) {
			if (i < 7)
				chatmessage[i] = chatmessage[i + 1];
		}
		// 更新最下面訊息
		chatmessage[7] = paintboard.allcharacter.get(Integer
				.parseInt(characternnum)).name + ": " + data;
	}

	// 分析
	public void Analysis(String ip, String topic, String data) {
		//System.out.println("執行chat分析");
		switch (topic) {
		case "發送訊息":
			updateMessage(ip, data);
			break;

		default:

		}
	}

	// 劃出所有聊天內容
	public void drawscene(Graphics2D g) {
		g.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		g.setColor(new Color(255, 255, 255, 255));
		for (int i = 0; i < 8; i++) {
			g.drawString(chatmessage[i], 10, 515 + 20 * i);
		}
	}

}
