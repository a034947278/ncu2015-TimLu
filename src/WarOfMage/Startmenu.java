package WarOfMage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Startmenu {
	int selection = 0;

	int scene = 0;
	PaintBoard paintboard;
	public Textfield nameinput = new Textfield(440, 380, 400, 100, 7);
	public Textfield ipinput = new Textfield(440, 500, 400, 100, 15);

	Startmenu(PaintBoard a) {
		paintboard = a;
	}

	public void drawscene(Graphics2D g) {
		// Graphics2D g2d = (Graphics2D)g;
		g.drawImage(paintboard.imagelibrary.getImage("background0"), 0, 0,
				paintboard);
		switch (scene) {
		case 0:
			// g.drawImage(paintboard.background.getImage("background0"), 0,
			// 0,paintboard);
			// 透明度
			// g2d.setComposite(AlphaComposite.SrcOver.derive(0.2f));

			// AffineTransform at = new AffineTransform();

			// at.translate(490, 380);

			// at.rotate(-Math.toRadians(30));

			// at.translate(
			// -paintboard.imagelibrary.getImage("button").getWidth(
			// paintboard) / 2,
			// -paintboard.imagelibrary.getImage("button").getHeight(
			// paintboard) / 2);

			// g.drawImage(paintboard.imagelibrary.getImage("button"), at,
			// paintboard);

			g.drawImage(paintboard.imagelibrary.getImage("button"), 490, 380,
					paintboard);
			g.drawImage(paintboard.imagelibrary.getImage("button"), 490, 480,
					paintboard);
			g.drawImage(paintboard.imagelibrary.getImage("button"), 490, 580,
					paintboard);
			// g2d.setComposite(AlphaComposite.SrcOver.derive(1f));
			g.setFont(new Font("微軟正黑體", Font.BOLD, 40));
			setcolor(g, 0);
			g.drawString("創    建    房    間", 500, 420);
			setcolor(g, 1);
			g.drawString("加    入    房    間", 500, 520);
			setcolor(g, 2);
			g.drawString("離    開    遊    戲", 500, 620);
			break;
		case 1:
			// g.drawImage(paintboard.background.getImage("background0"), 0,
			// 0,paintboard);

			nameinput.drawscene(g);
			g.drawImage(paintboard.imagelibrary.getImage("button"), 490, 520,
					paintboard);
			g.setFont(new Font("微軟正黑體", Font.BOLD, 40));
			setcolor(g, 0);
			g.drawString("ID:", 380, 445);
			setcolor(g, 1);
			g.drawString("確                    定", 500, 560);

			break;
		case 2:
			nameinput.drawscene(g);
			ipinput.drawscene(g);
			g.drawImage(paintboard.imagelibrary.getImage("button"), 490, 620,
					paintboard);
			g.setFont(new Font("微軟正黑體", Font.BOLD, 40));
			setcolor(g, 0);
			g.drawString("ID:", 380, 445);
			setcolor(g, 1);
			g.drawString("IP:", 380, 560);
			setcolor(g, 2);
			g.drawString("確                    定", 500, 660);
		default:
		}
		g.setColor(new Color(0, 0, 0, 255));
		g.setFont(new Font("Curlz MT", Font.BOLD, 200));
		g.drawString("War of Mage", 50, 200);
	}

	public void selectCreatRoom() {
		if(selection!=0)
		paintboard.music.playSe(0);
		selection = 0;
	}

	public void selectTypeId() {
		if(selection!=0)
		paintboard.music.playSe(0);
		selection = 0;
	}

	public void selectTypeIp() {
		if(selection!=1)
		paintboard.music.playSe(0);
		selection = 1;
	}

	public void selectCreatRoomConfirm() {
		if(selection!=1)
		paintboard.music.playSe(0);
		selection = 1;
	}

	public void selectJoinRoomConfirm() {
		if(selection!=2)
		paintboard.music.playSe(0);
		selection = 2;
	}

	public void selectJoinRoom() {
		if(selection!=1)
		paintboard.music.playSe(0);
		selection = 1;
	}

	public void selectLeave() {
		if(selection!=2)
		paintboard.music.playSe(0);
		selection = 2;
	}

	private void setcolor(Graphics g, int select) {
		if (selection == select)
			g.setColor(new Color(255, 255, 0, 255));
		else {
			g.setColor(new Color(255, 255, 255, 255));
		}

	}

	public void presscreatroom() {
		paintboard.music.playSe(1);
		selection = 0;
		scene = 1;
	}

	public void pressjoinroom() {
		paintboard.music.playSe(1);
		selection = 0;
		scene = 2;

	}

	public void pressleave() {
		paintboard.music.playSe(1);
		System.exit(0);
	}

	public void pressCreatRoomConfirm() {
		paintboard.music.playSe(1);
		if (nameinput.getData().isEmpty()) {
			JOptionPane.showMessageDialog(this.paintboard, "請輸入ID", "警告",
					JOptionPane.WARNING_MESSAGE);
		} else {
			// 建立TCP SERVER
			paintboard.net = new Server(paintboard);
			// 新增使用者
			paintboard.gamecontroller.deliverCharacterName(nameinput.getData());
			// 切換到大廳畫面
			paintboard.scene = 1;
		}

	}

	public void pressJoinRoomConfirm() {
		paintboard.music.playSe(1);
		if (nameinput.getData().isEmpty() || ipinput.getData().isEmpty()) {
			JOptionPane.showMessageDialog(this.paintboard, "請輸入ID 或 IP", "警告",
					JOptionPane.WARNING_MESSAGE);
		} else {
			// 建立TCP client 連接server
			try {
				paintboard.net = new Client(paintboard, this.ipinput.getData());
				// System.out.println(this.ipinput.getData());
				// 一樣
				paintboard.gamecontroller.deliverCharacterName(nameinput
						.getData());
				// System.out.println(this.nameinput.getData());
				paintboard.scene = 1;

			} catch (IOException e) {
				JOptionPane.showMessageDialog(this.paintboard, "IP無法連接", "警告",
						JOptionPane.WARNING_MESSAGE); // TODO Auto-generated
														// catch block
				System.out.println(e);
				e.printStackTrace();
			}

		}

	}

	public void pressDown() {
		paintboard.music.playSe(0);
		switch (scene) {
		case 0:
			selectdown(3);
			break;
		case 1:
			selectdown(2);
			break;
		case 2:
			selectdown(3);
			break;
		default:
		}
	}

	public void selectdown(int maxselect) {
		if (selection == maxselect - 1)
			selection = 0;
		else
			selection++;
	}

	public void pressUp() {
		paintboard.music.playSe(0);
		switch (scene) {
		case 0:
			selectup(3);
			break;
		case 1:
			selectup(2);
			break;
		case 2:
			selectup(3);
			break;
		default:
		}

	}

	public void selectup(int maxselect) {
		if (selection == 0)
			selection = maxselect - 1;
		else
			selection--;
	}

	public void pressEnter() {
		switch (scene) {
		case 0:
			switch (selection) {
			case 0:
				this.presscreatroom();
				break;
			case 1:
				this.pressjoinroom();
				break;
			case 2:
				this.pressleave();
				break;
			default:
			}
			break;
		case 1:
			switch (selection) {
			case 1:
				this.pressCreatRoomConfirm();
				break;
			default:
			}
			break;
		case 2:
			switch (selection) {
			case 2:
				this.pressJoinRoomConfirm();
				break;
			default:
			}
			break;
		default:
		}

	}

	public void KeyType(KeyEvent arg0) {
		switch (scene) {
		case 1:
			switch (selection) {
			case 0:
				nameinput.keyTyped(arg0);
				break;
			default:
			}
			break;
		case 2:
			switch (selection) {
			case 0:
				nameinput.keyTyped(arg0);
				break;
			case 1:
				ipinput.keyTyped(arg0);
				break;
			default:
			}
			break;
		default:
		}

	}

}
