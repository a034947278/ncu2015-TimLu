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
	// ��ѼҲ�
	//boolean isEnterPressed = false;
	Chat chat;
	// ��e���
	/*
	 * 0:�a�� 1:�^�X���� 2:�ʶR�ɶ� 3:�C�^�X�W�[���� 4:�}�l�C�� 5:���}�C�� 6:��ѫǤ��e�ǰe
	 */
	int selection = 0;

	PaintBoard paintboard;
	// �C�ӿ�J��
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

		// �e�I��
		g.drawImage(paintboard.imagelibrary.getImage("background1"), 0, 0,
				paintboard);

		// �e��J��
		mapinput.drawscene(g);
		levelinput.drawscene(g);
		buytimeinput.drawscene(g);
		moneyinput.drawscene(g);
		chatinput.drawscene(g);

		// �e���s
		g.drawImage(paintboard.imagelibrary.getImage("button"), 1025, 520, 150,
				40, paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("button"), 1025, 580, 150,
				40, paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("button"), 1025, 670, 100,
				40, paintboard);

		// �e�r
		g.setFont(new Font("�L�n������", Font.BOLD, 30));

		// �P�_�O�_�襤 �襤�C�� Yellow ���襤Black
		setcolorBY(g, 0);
		g.drawString("�a��", 1070, 70);
		setcolorBY(g, 1);
		g.drawString("�^�X����", 1040, 180);
		setcolorBY(g, 2);
		g.drawString("�ʶR�ɶ�", 1040, 290);
		setcolorBY(g, 3);
		g.drawString("�C�^�X�W�[���B", 993, 400);

		// �P�_�O�_�襤 �襤�C�� Yellow ���襤White
		setcolorWY(g, 4);
		g.drawString("�}�l�C��", 1040, 550);
		setcolorWY(g, 5);
		g.drawString("���}�C��", 1040, 610);
		setcolorWY(g, 6);
		g.drawString("�o  �e", 1035, 700);

		// �]�w�z����0.8
		g.setComposite(AlphaComposite.SrcOver.derive(0.8f));
		// �e�X�C�� �}��s�u���A�� ��:�s�u ��:���s�u
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				// num �ثe �ĴX�Ӫ��a
				int num = j + 1;
				if (i == 1) {
					num = j + 5;
				}
				// �u�n��W�X�Ҧ����a�� �N��ܬ�:���s�u
				if (paintboard.allcharacter.size() < num) {
					g.setColor(new Color(255, 0, 0, 255));
				} else {
					g.setColor(new Color(0, 255, 0, 255));
				}
				g.fill3DRect(50 + j * 230, 50 + i * 230, 200, 200, true);
			}
		}

		// ��Ѥ��e��
		g.setColor(new Color(0, 0, 0, 255));
		g.fill3DRect(10, 495, 1000, 165, true);

		// �٭�z����
		g.setComposite(AlphaComposite.SrcOver.derive(1f));
		g.setColor(new Color(255, 255, 255, 255));
		Font font = new Font("�L�n������", Font.BOLD, 40);
		g.setFont(font);

		// �e�X�C�Ӹ}��W��
		for (int i = 0; i < paintboard.allcharacter.size(); i++) {
			/*
			 * mapx :X�b�ĴX�� mapy :Y�b�ĴX��
			 */
			int mapx = 0;
			int mapy = 0;
			if (i > 3) {
				mapx = i - 4;
				mapy = 1;
			} else {
				mapx = i;
			}
			// ���o�r��Metrics
			FontMetrics metrics = g.getFontMetrics(font);
			// �m��
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

		// �e��Ѥ��e
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
		// �����ܤj�U�e��
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
		// �ǰe��Ѥ��e
		if (!chatinput.getData().isEmpty()) {
			chat.sendMessage(this.chatinput.getData());
		}
		// �M�Ų�ѿ�J��
		chatinput.data = "";
		// System.exit(0);
	}

	public void pressDown() {
		paintboard.music.playSe(0);
		selectdown(7);
	}

	// �ۤv��

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

	// �ھکҷ�e�ҿ�ܥh���欰
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

	// �ھڷ�e�ҿ�h������J���e
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
