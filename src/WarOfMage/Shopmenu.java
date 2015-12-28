package WarOfMage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Skill.BaseSkill;
import WarOfMage.Server.HandleAClient;

public class Shopmenu {
	// int selection = 0;
	int selectionx = 0;
	int selectiony = 0;
	PaintBoard paintboard;
	timer timer;

	Shopmenu(PaintBoard a) {
		paintboard = a;
	}

	public void startShop(int shoptime) {
		// 清空skilllist
		// paintboard.gamemodel.addCauseDamge();
		paintboard.map.stop();
		for (int i = 0; i < paintboard.allskill.size(); i++) {
			// 停止gif的thread
			paintboard.allskill.get(i).visible = false;
			paintboard.allskill.get(i).gif.stop();

		}
		paintboard.allskill = new ArrayList<BaseSkill>();
		int plusmoney = Integer.valueOf(paintboard.lobbymenu.moneyinput.data);
		for (int i = 0; i < paintboard.allcharacter.size(); i++) {
			Character character = paintboard.allcharacter.get(i);
			character.money = character.money + plusmoney
					* paintboard.gamemodel.gametimes;
			this.paintboard.gamecontroller.deliverUpdateClientGameState(i + "",
					(int) character.x, (int) character.y, character.hp,
					character.isDead, character.direction, character.money);
		}
		selectionx = 0;
		selectiony = 0;
		timer = new timer(shoptime);
		Thread timerr = new Thread(timer);
		timerr.start();
		paintboard.scene = 2;
	}

	private int getselectx() {
		return 90 + selectionx * 200;
	}

	private int getselecty() {
		return 85 + selectiony * 200;
	}

	public void drawscene(Graphics2D g) {
		// Graphics2D g2d = (Graphics2D)g;
		g.drawImage(paintboard.imagelibrary.getImage("background2"), 0, 0,
				paintboard);
		// g.drawImage(paintboard.background.getImage("background0"), 0,
		// 0,paintboard);
		// 透明度
		// g2d.setComposite(AlphaComposite.SrcOver.derive(0.2f));

		g.setComposite(AlphaComposite.SrcOver.derive(0.8f));
		g.setColor(new Color(0, 0, 0, 255));
		g.fill3DRect(15, 15, 1250, 690, true);
		g.setComposite(AlphaComposite.SrcOver.derive(1f));

		g.drawImage(paintboard.imagelibrary.getImage("hp"), 137, 145,
				paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("frame"), 100, 100,
				paintboard);

		g.drawImage(paintboard.imagelibrary.getImage("speed"), 135, 335,
				paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("frame"), 100, 300,
				paintboard);

		g.drawImage(paintboard.imagelibrary.getImage("fire"), 303, 95,
				paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("frame"), 300, 100,
				paintboard);

		g.drawImage(paintboard.imagelibrary.getImage("ice"), 295, 295,
				paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("frame"), 300, 300,
				paintboard);

		g.drawImage(paintboard.imagelibrary.getImage("rock"), 503, 100,
				paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("frame"), 500, 100,
				paintboard);

		g.drawImage(paintboard.imagelibrary.getImage("thunder"), 500, 295,
				paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("frame"), 500, 300,
				paintboard);

		g.drawImage(paintboard.imagelibrary.getImage("poison"), 703, 92,
				paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("frame"), 700, 100,
				paintboard);

		g.drawImage(paintboard.imagelibrary.getImage("light"), 700, 295,
				paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("frame"), 700, 300,
				paintboard);

		g.setComposite(AlphaComposite.SrcOver.derive(0.7f));
		g.drawImage(paintboard.imagelibrary.getImage("frame2"), 0, 0,
				paintboard);
		g.setComposite(AlphaComposite.SrcOver.derive(1f));

		g.setColor(new Color(255, 0, 0, 240));
		g.setFont(new Font("微軟正黑體", Font.BOLD, 40));
		Character self = paintboard.allcharacter
				.get(this.paintboard.gamecontroller.selfnum);
		g.drawString(self.maxhp + "", 190, 240);
		g.drawString(self.baseRunSpeed + "", 190, 440);
		g.drawString(self.skill.get(0) + "", 410, 240);
		g.drawString(self.skill.get(1) + "", 410, 440);
		g.drawString(self.skill.get(2) + "", 610, 240);
		g.drawString(self.skill.get(3) + "", 610, 440);
		g.drawString(self.skill.get(4) + "", 810, 240);
		g.drawString(self.skill.get(5) + "", 810, 440);

		g.drawImage(paintboard.imagelibrary.getImage("select"), getselectx(),
				getselecty(), paintboard);
		drawDescribe(g);
		g.setColor(new Color(255, 255, 0, 255));
		g.setFont(new Font("微軟正黑體", Font.BOLD, 40));
		g.drawString("$" + self.money, 974, 445);

		g.setColor(new Color(255, 255, 255, 255));
		g.setFont(new Font("微軟正黑體", Font.BOLD, 200));
		g.drawString("" + timer.sec, 967, 257);

		/*
		 * g.drawString("火球術", 95, 527); g.drawString("火球術", 95, 572);
		 * g.drawString("火球術", 95, 617); g.drawString("火球術", 95, 662);
		 */

	}

	private void drawSkillDetail(Graphics2D g, BaseSkill baseskill) {

		Character self = paintboard.allcharacter
				.get(this.paintboard.gamecontroller.selfnum);
		int skilllevel = self.skill.get(baseskill.skillnum);
		// Skill skill = this.paintboard.skill;

		g.drawString(baseskill.getSkillName() + "  Lv." + skilllevel + "/"
				+ baseskill.maxlevel + "($1000)", 95, 527);
		g.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		g.drawString(baseskill.getSkillDescribe(skilllevel), 95, 572);
		if (skilllevel == baseskill.maxlevel) {
		} else {
			g.drawString("Next Level", 95, 617);
			g.drawString(baseskill.getSkillDescribe(skilllevel + 1), 95, 662);
		}

	}

	private void drawDescribe(Graphics2D g) {
		g.setColor(new Color(0, 0, 0, 255));
		g.setFont(new Font("微軟正黑體", Font.BOLD, 40));
		// Character self = paintboard.allcharacter
		// .get(this.paintboard.gamecontroller.selfnum);
		// int skilllevel = self.skill.get(0);
		// Skill skill = this.paintboard.skill;
		switch (selectiony) {
		case 0:

			switch (selectionx) {
			case 0:
				// this.pressHp();
				g.drawString("提升血量($1000)", 95, 527);
				g.setFont(new Font("微軟正黑體", Font.BOLD, 20));
				g.drawString("每次購買升級可以獲得30點的總血量提升。", 95, 572);
				// g.drawString("火球術", 95, 617);
				// g.drawString("火球術", 95, 662);
				break;
			case 1:
				// this.pressFire();
				drawSkillDetail(g, this.paintboard.skill.fire);
				break;
			case 2:
				// this.pressRock();
				drawSkillDetail(g, this.paintboard.skill.rock);

				break;
			case 3:
				// this.pressPoison();
				drawSkillDetail(g, this.paintboard.skill.poison);

				break;
			default:
			}
			break;
		case 1:
			switch (selectionx) {
			case 0:
				// this.pressSpeed();
				g.drawString("提升跑速($500)", 95, 527);
				g.setFont(new Font("微軟正黑體", Font.BOLD, 20));
				g.drawString("每次購買升級可以獲得額外5%的跑速。", 95, 572);
				break;
			case 1:
				// this.pressIce();
				drawSkillDetail(g, this.paintboard.skill.ice);
				break;
			case 2:
				// this.pressThunder();
				drawSkillDetail(g, this.paintboard.skill.thunder);
				break;
			case 3:
				// this.pressLight();
				drawSkillDetail(g, this.paintboard.skill.light);
				break;

			default:
			}
			break;

		default:
		}

	}

	public void selectHp() {
		if (selectionx != 0 || selectiony != 0)
			paintboard.music.playSe(0);
		selectionx = 0;
		selectiony = 0;
	}

	public void selectSpeed() {
		if (selectionx != 0 || selectiony != 1)
			paintboard.music.playSe(0);
		selectionx = 0;
		selectiony = 1;
	}

	public void selectFire() {
		if (selectionx != 1 || selectiony != 0)
			paintboard.music.playSe(0);
		selectionx = 1;
		selectiony = 0;
	}

	public void selectIce() {
		if (selectionx != 1 || selectiony != 1)
			paintboard.music.playSe(0);
		selectionx = 1;
		selectiony = 1;
	}

	public void selectRock() {
		if (selectionx != 2 || selectiony != 0)
			paintboard.music.playSe(0);
		selectionx = 2;
		selectiony = 0;
	}

	public void selectThunder() {
		if (selectionx != 2 || selectiony != 1)
			paintboard.music.playSe(0);
		selectionx = 2;
		selectiony = 1;
	}

	public void selectPoison() {
		if (selectionx != 3 || selectiony != 0)
			paintboard.music.playSe(0);
		selectionx = 3;
		selectiony = 0;
	}

	public void selectLight() {
		if (selectionx != 3 || selectiony != 1)
			paintboard.music.playSe(0);

		selectionx = 3;
		selectiony = 1;
	}

	private boolean determineCanBuySkillLevel(BaseSkill baseskill) {

		Character self = paintboard.allcharacter
				.get(this.paintboard.gamecontroller.selfnum);
		int skilllevel = self.skill.get(baseskill.skillnum);

		if (skilllevel < baseskill.maxlevel) {
			return true;
		} else {
			return false;
		}

	}

	public void pressHp() {
		paintboard.music.playSe(1);
		this.paintboard.gamecontroller.deliverBuy("hp");

	}

	public void pressSpeed() {
		paintboard.music.playSe(1);
		this.paintboard.gamecontroller.deliverBuy("speed");

	}

	public void pressFire() {
		paintboard.music.playSe(1);
		if (determineCanBuySkillLevel(this.paintboard.skill.fire))
			this.paintboard.gamecontroller.deliverBuy("fire");
	}

	public void pressIce() {
		paintboard.music.playSe(1);
		if (determineCanBuySkillLevel(this.paintboard.skill.ice))
			this.paintboard.gamecontroller.deliverBuy("ice");
	}

	public void pressRock() {
		paintboard.music.playSe(1);
		if (determineCanBuySkillLevel(this.paintboard.skill.rock))
			this.paintboard.gamecontroller.deliverBuy("rock");

	}

	public void pressThunder() {
		paintboard.music.playSe(1);
		if (determineCanBuySkillLevel(this.paintboard.skill.thunder))
			this.paintboard.gamecontroller.deliverBuy("thunder");
	}

	public void pressPoison() {
		paintboard.music.playSe(1);
		if (determineCanBuySkillLevel(this.paintboard.skill.poison))
			this.paintboard.gamecontroller.deliverBuy("poison");
	}

	public void pressLight() {
		paintboard.music.playSe(1);
		if (determineCanBuySkillLevel(this.paintboard.skill.light))
			this.paintboard.gamecontroller.deliverBuy("light");
	}

	public void pressDown() {
		paintboard.music.playSe(0);
		selectdown(2);
		// System.out.println(selectionx);
		// System.out.println(selectiony);
	}

	public void selectdown(int maxselect) {
		if (selectiony == maxselect - 1)
			selectiony = 0;
		else
			selectiony++;
	}

	public void pressUp() {
		paintboard.music.playSe(0);
		selectup(2);

	}

	public void selectup(int maxselect) {
		if (selectiony == 0)
			selectiony = maxselect - 1;
		else
			selectiony--;
	}

	public void pressRight() {
		paintboard.music.playSe(0);
		selectright(4);
	}

	public void selectright(int maxselect) {
		if (selectionx == maxselect - 1)
			selectionx = 0;
		else
			selectionx++;
	}

	public void pressLeft() {
		paintboard.music.playSe(0);
		selectleft(4);

	}

	public void selectleft(int maxselect) {
		if (selectionx == 0)
			selectionx = maxselect - 1;
		else
			selectionx--;
	}

	public void releaseEnter() {
		paintboard.music.playSe(1);
		switch (selectiony) {
		case 0:
			switch (selectionx) {
			case 0:
				this.pressHp();
				break;
			case 1:
				this.pressFire();
				break;
			case 2:
				this.pressRock();
				break;
			case 3:
				this.pressPoison();
				break;
			default:
			}
			break;
		case 1:
			switch (selectionx) {
			case 0:
				this.pressSpeed();
				break;
			case 1:
				this.pressIce();
				break;
			case 2:
				this.pressThunder();
				break;
			case 3:
				this.pressLight();
				break;

			default:
			}
			break;

		default:
		}

	}

	class timer implements Runnable {

		int sec;
		int buytime;
		boolean isStop = false;

		public timer(int time) {
			buytime = time;
			sec = buytime;
		}

		public void initial() {
			sec = buytime;
			isStop = false;
		}

		@Override
		public void run() {
			// long timeDiff, sleep;
			long second = 0;

			while (!isStop) {

				long startTime = System.currentTimeMillis();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println("Interrupted: " + e.getMessage());
				}
				// System.out.println(second);
				second += System.currentTimeMillis() - startTime;
				if (second >= 1000L) {
					// fps.setText(" FPS:" + moveCount);
					sec = sec - 1;
					second = 0L;
				}
				if (sec == 0) {
					// doStartGame
					if (paintboard.net.isServer) {
						// TODO doStartGame
						paintboard.gamemodel.ServerStartGame();
						// paintboard.scene=3;
					}
					isStop = true;
				}

			}

		}
	}

}
