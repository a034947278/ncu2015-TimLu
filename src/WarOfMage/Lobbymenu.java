package WarOfMage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

public class Lobbymenu {
	// 聊天模組
	//boolean isEnterPressed = false;
	Chat chat;
	// 當前選擇
	/*
	 * 0:地圖 1:回合次數 2:購買時間 3:每回合增加金錢 4:開始遊戲 5:離開遊戲 6:聊天室內容傳送
	 */
	int selection = 0;

	PaintBoard paintboard;
	// 每個輸入框
	public Textfield mapinput = new Textfield(1000, 80, 200, 50, 8, 0, "A_Map");
	public Textfield levelinput = new Textfield(1000, 190, 200, 50, 2, 0, "5");
	public Textfield buytimeinput = new Textfield(1000, 300, 200, 50, 2, 0,
			"3");
	public Textfield moneyinput = new Textfield(1000, 410, 200, 50, 8, 0,
			"1000");
	public Textfield chatinput = new Textfield(10, 670, 1000, 40, 100, "left");

	Lobbymenu(PaintBoard a) {
		paintboard = a;
		chat = new Chat(a);
	}

	public void drawscene(Graphics2D g) {
		// Graphics2D g2d = (Graphics2D)g;

		// 畫背景
		g.drawImage(paintboard.imagelibrary.getImage("background1"), 0, 0,
				paintboard);

		// 畫輸入框
		mapinput.drawscene(g);
		levelinput.drawscene(g);
		buytimeinput.drawscene(g);
		moneyinput.drawscene(g);
		chatinput.drawscene(g);

		// 畫按鈕
		g.drawImage(paintboard.imagelibrary.getImage("button"), 1025, 520, 150,
				40, paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("button"), 1025, 580, 150,
				40, paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("button"), 1025, 670, 100,
				40, paintboard);

		// 畫字
		g.setFont(new Font("微軟正黑體", Font.BOLD, 30));

		// 判斷是否選中 選中顏色 Yellow 未選中Black
		setcolorBY(g, 0);
		g.drawString("地圖", 1070, 70);
		setcolorBY(g, 1);
		g.drawString("回合次數", 1040, 180);
		setcolorBY(g, 2);
		g.drawString("購買時間", 1040, 290);
		setcolorBY(g, 3);
		g.drawString("每回合增加金額", 993, 400);

		// 判斷是否選中 選中顏色 Yellow 未選中White
		setcolorWY(g, 4);
		g.drawString("開始遊戲", 1040, 550);
		setcolorWY(g, 5);
		g.drawString("離開遊戲", 1040, 610);
		setcolorWY(g, 6);
		g.drawString("發  送", 1035, 700);

		// 設定透明度0.8
		g.setComposite(AlphaComposite.SrcOver.derive(0.8f));
		// 畫出每個 腳色連線狀態框 綠:連線 紅:未連線
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				// num 目前 第幾個玩家
				int num = j + 1;
				if (i == 1) {
					num = j + 5;
				}
				// 只要比超出所有玩家數 就顯示紅:未連線
				if (paintboard.allcharacter.size() < num) {
					g.setColor(new Color(255, 0, 0, 255));
				} else {
					g.setColor(new Color(0, 255, 0, 255));
				}
				g.fill3DRect(50 + j * 230, 50 + i * 230, 200, 200, true);
			}
		}

		// 聊天內容框
		g.setColor(new Color(0, 0, 0, 255));
		g.fill3DRect(10, 495, 1000, 165, true);

		// 還原透明度
		g.setComposite(AlphaComposite.SrcOver.derive(1f));
		g.setColor(new Color(255, 255, 255, 255));
		Font font = new Font("微軟正黑體", Font.BOLD, 40);
		g.setFont(font);

		// 畫出每個腳色名稱
		for (int i = 0; i < paintboard.allcharacter.size(); i++) {
			/*
			 * mapx :X軸第幾個 mapy :Y軸第幾個
			 */
			int mapx = 0;
			int mapy = 0;
			if (i > 3) {
				mapx = i - 4;
				mapy = 1;
			} else {
				mapx = i;
			}
			// 取得字形Metrics
			FontMetrics metrics = g.getFontMetrics(font);
			// 置中
			int x = 50
					+ mapx
					* 230
					+ (200 - metrics
							.stringWidth(paintboard.allcharacter.get(i).name))
					/ 2;
			// System.out.println(xmap);
			g.drawString(paintboard.allcharacter.get(i).name, x,
					165 + mapy * 230);

		}

		// 畫聊天內容
		chat.drawscene(g);

	}

	public void selectMap() {
		if(selection!=0)
			paintboard.music.playSe(0);
		selection = 0;
	}

	public void selectLevel() {
		if(selection!=1)
			paintboard.music.playSe(0);
		selection = 1;
	}

	public void selectButTime() {
		if(selection!=2)
			paintboard.music.playSe(0);
		selection = 2;
	}

	public void selectLevelmMoney() {
		if(selection!=3)
			paintboard.music.playSe(0);
		selection = 3;
	}

	public void selectStartGame() {
		if(selection!=4)
			paintboard.music.playSe(0);
		selection = 4;
	}

	public void selectLeave() {
		if(selection!=5)
			paintboard.music.playSe(0);
		selection = 5;
	}

	public void selectChat() {
		if(selection!=6)
		paintboard.music.playSe(0);
		selection = 6;
	}

	private void setcolorBY(Graphics g, int select) {
		if (selection == select)
			g.setColor(new Color(255, 255, 0, 255));
		else {
			g.setColor(new Color(0, 0, 0, 255));
		}

	}

	private void setcolorWY(Graphics g, int select) {
		if (selection == select)
			g.setColor(new Color(255, 255, 0, 255));
		else {
			g.setColor(new Color(255, 255, 255, 255));
		}

	}

	public void pressStartGame() {
		// 切換至大廳畫面
		if (paintboard.net.isServer) {
			//this.paintboard.net.stopServerSocket();
			//this.paintboard.net.startBuildUDP();
			this.paintboard.gamecontroller.deliverStartBuy();
		}
	}

	public void pressLeave() {
		System.exit(0);
	}

	public void pressChatinputEnter() {
		// 傳送聊天內容
		if (!chatinput.getData().isEmpty()) {
			chat.sendMessage(this.chatinput.getData());
		}
		// 清空聊天輸入框
		chatinput.data = "";
		// System.exit(0);
	}

	public void pressDown() {
		paintboard.music.playSe(0);
		selectdown(7);
	}

	// 自己看

	public void selectdown(int maxselect) {
		if (selection == maxselect - 1)
			selection = 0;
		else
			selection++;
	}

	public void pressUp() {
		paintboard.music.playSe(0);
		selectup(7);

	}

	public void selectup(int maxselect) {
		if (selection == 0)
			selection = maxselect - 1;
		else
			selection--;
	}

	// 根據所當前所選擇去做行為
	public void pressEnter() {
		
		paintboard.music.playSe(1);
		switch (selection) {
		case 0:
		case 1:
		case 2:
		case 3:
			this.pressDown();
			break;
		case 4:
			//this.pressStartGame();
			break;
		case 5:
			this.pressLeave();
			break;
		case 6:
			this.pressChatinputEnter();
			break;
		default:
		}

	}
	
	public void releaseEnter() {

		switch (selection) {
		case 4:
			this.pressStartGame();
			
			break;
		default:
		}

	}

	// 根據當前所選去偵測輸入內容
	public void KeyType(KeyEvent arg0) {

		switch (selection) {
		case 0:
			if (paintboard.net.isServer)
				mapinput.keyTyped(arg0);
			break;
		case 1:
			if (paintboard.net.isServer)
				levelinput.keyTyped(arg0);
			break;
		case 2:
			if (paintboard.net.isServer)
				buytimeinput.keyTyped(arg0);
			break;
		case 3:
			if (paintboard.net.isServer)
				moneyinput.keyTyped(arg0);
			break;
		case 6:
			chatinput.keyTyped(arg0);
			break;
		default:
		}
		if (paintboard.net.isServer && selection >= 0 && selection <= 3) {
			paintboard.gamecontroller.deliverUpdateClientGameSetting();
		}

	}
}
