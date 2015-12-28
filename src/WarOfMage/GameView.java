package WarOfMage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import Skill.BaseSkill;

public class GameView {
	PaintBoard paintboard;
	int selection = 0;

	GameView(PaintBoard a) {
		paintboard = a;
	}

	public void drawscene(Graphics2D g) {
		
		
		paintboard.gamemodel.checkcollison();
		paintboard.gamemodel.checkState();
		paintboard.gamemodel.allCharacterMove();
		paintboard.gamemodel.allSkillMove();
		// Graphics2D g2d = (Graphics2D)g;

		// 畫背景

		paintboard.map.Drawmap((int) paintboard.allcharacter
				.get(paintboard.gamecontroller.selfnum).x,
				(int) paintboard.allcharacter
						.get(paintboard.gamecontroller.selfnum).y, g);

		int selftnum = paintboard.gamecontroller.selfnum;

		ArrayList<Double> allcharactery = new ArrayList<Double>();
		ArrayList<Integer> allcharacternum = new ArrayList<Integer>();
		for (int i = 0; i < paintboard.allcharacter.size(); i++) {
			Character character = paintboard.allcharacter.get(i);
			allcharactery.add(character.y);
			allcharacternum.add(i);
		}
		for (int i = 0; i < paintboard.allcharacter.size() - 1; i++) {
			for (int j = 0; j < paintboard.allcharacter.size() - 1 - i; j++) {
				if (allcharactery.get(j + 1) < allcharactery.get(j)) {
					double tmp2 = allcharactery.get(j);
					allcharactery.set(j, allcharactery.get(j + 1));
					allcharactery.set(j + 1, tmp2);
					int tmp = allcharacternum.get(j);
					allcharacternum.set(j, allcharacternum.get(j + 1));
					allcharacternum.set(j + 1, tmp);
				}
			}
		}

		Font font = new Font("微軟正黑體", Font.BOLD, 25);
		g.setFont(font);

		FontMetrics metrics = g.getFontMetrics(font);
		for (int i = 0; i < allcharacternum.size(); i++) {
			Character character = paintboard.allcharacter.get(allcharacternum
					.get(i));
			if (!character.isDead) {
				int x = (int) (590 + (character.x - paintboard.allcharacter
						.get(selftnum).x));
				int y = (int) (260 + (character.y - paintboard.allcharacter
						.get(selftnum).y));
				g.drawImage(character.getImage(), x, y, paintboard);

				// 置中
				// 人名
				String name = character.name;
				int namex = x + (100 - metrics.stringWidth(name)) / 2;
				int namey = y;
				g.setColor(new Color(0, 0, 0, 255));
				g.drawString(name, namex, namey);

				int hp = character.hp;
				int maxhp = character.maxhp;
				// 血量底
				g.fillRoundRect(x + 25, namey + 5, 50, 10, 2, 2);
				g.setColor(new Color(255, 0, 0, 255));
				// 現有血量
				g.fillRoundRect(x + 25, namey + 5, (50 * hp / maxhp), 10, 2, 2);
				x = (int) (640 + (character.getCollX() - paintboard.allcharacter
						.get(selftnum).x));
				y = (int) (360 + (character.getCollY() - paintboard.allcharacter
						.get(selftnum).y));
				g.setColor(new Color(0, 0, 0, 255));
				g.drawRect(x, y, (int) character.w, (int) character.h);
			}

		}

		for (int i = 0; i < this.paintboard.allskill.size(); i++) {
			BaseSkill skill = this.paintboard.allskill.get(i);
			if (skill.visible) {
				int x = (int) (590 + (skill.getdrawx() - paintboard.allcharacter
						.get(selftnum).x));
				int y = (int) (310 + (skill.getdrawy() - paintboard.allcharacter
						.get(selftnum).y));
				// System.out.println(x+" "+y);

				g.drawImage(skill.getImage(), x, y, paintboard);

				x = (int) (640 + (skill.getX() - skill.getradius() - paintboard.allcharacter
						.get(selftnum).x));
				y = (int) (360 + (skill.getY() - skill.getradius() - paintboard.allcharacter
						.get(selftnum).y));
				g.setColor(new Color(0, 0, 0, 255));
				g.drawOval(x, y, skill.getradius() * 2, skill.getradius() * 2);
			} else {
				skill.gif.stop();
			}

		}

		/*
		 * for (int i = 0; i < paintboard.allcharacter.size(); i++) { Character
		 * character = paintboard.allcharacter.get(i);
		 * 
		 * int x = (int) (590 + (character.x - paintboard.allcharacter
		 * .get(selftnum).x)); int y = (int) (260 + (character.y -
		 * paintboard.allcharacter .get(selftnum).y));
		 * g.drawImage(character.getImage(), x, y, paintboard); }
		 */

		// System.out.println((int) paintboard.allcharacter.get(0).x);
		// System.out.println((int) paintboard.allcharacter.get(0).y);
		// 畫HUD

		g.setComposite(AlphaComposite.SrcOver.derive(0.8f));
		g.setColor(new Color(255, 255, 255, 255));
		g.fillOval(20, 20, 150, 150);
		g.setColor(new Color(0, 0, 0, 255));

		int hp = paintboard.allcharacter.get(selftnum).hp;
		int maxhp = paintboard.allcharacter.get(selftnum).maxhp;

		// 血量底
		g.fillRoundRect(180, 20, 200, 70, 20, 20);
		// 錢
		g.fillRoundRect(180, 100, 200, 70, 20, 20);

		g.setColor(new Color(255, 0, 0, 255));
		// 現有血量
		g.fillRoundRect(180, 20, 200 * hp / maxhp, 70, 10, 10);
		g.setComposite(AlphaComposite.SrcOver.derive(1f));
		g.setColor(new Color(0, 0, 0, 255));
		font = new Font("微軟正黑體", Font.BOLD, 20);
		g.setFont(font);
		metrics = g.getFontMetrics(font);
		// 置中

		// 人名
		String name = paintboard.allcharacter.get(selftnum).name;
		int x = 20 + (150 - metrics.stringWidth(name)) / 2;
		g.drawString(name, x, 105);

		// 血量
		font = new Font("微軟正黑體", Font.BOLD, 35);
		g.setFont(font);
		metrics = g.getFontMetrics(font);
		x = 180 + (200 - metrics.stringWidth(hp + "/" + maxhp)) / 2;
		g.setColor(new Color(255, 255, 255, 255));
		g.drawString(hp + "/" + maxhp, x, 67);

		int money = paintboard.allcharacter.get(selftnum).money;
		x = 180 + (200 - metrics.stringWidth("$" + money)) / 2;
		g.setColor(new Color(255, 255, 0, 255));

		g.drawString("$" + money, x, 147);

		g.setComposite(AlphaComposite.SrcOver.derive(0.8f));
		g.setColor(new Color(0, 0, 0, 255));
		g.fillOval(20, 615, 95, 95);
		g.fillOval(119, 615, 95, 95);
		g.fillOval(218, 615, 95, 95);
		g.fillOval(317, 615, 95, 95);
		g.fillOval(416, 615, 95, 95);
		g.fillOval(515, 615, 95, 95);
		g.setComposite(AlphaComposite.SrcOver.derive(1f));
		g.drawImage(paintboard.imagelibrary.getImage("gamefire"), 31, 620,
				paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("gameframe"), 30, 625,
				paintboard);

		g.drawImage(paintboard.imagelibrary.getImage("gameice"), 128, 620,
				paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("gameframe"), 130, 625,
				paintboard);

		g.drawImage(paintboard.imagelibrary.getImage("gamerock"), 230, 625,
				paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("gameframe"), 228, 625,
				paintboard);

		g.drawImage(paintboard.imagelibrary.getImage("gamethunder"), 327, 622,
				paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("gameframe"), 327, 625,
				paintboard);

		g.drawImage(paintboard.imagelibrary.getImage("gamepoison"), 428, 621,
				paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("gameframe"), 425, 625,
				paintboard);

		g.drawImage(paintboard.imagelibrary.getImage("gamelight"), 525, 623,
				paintboard);
		g.drawImage(paintboard.imagelibrary.getImage("gameframe"), 525, 625,
				paintboard);
		// +94
		g.drawImage(paintboard.imagelibrary.getImage("gameselect"),
				25 + 99 * selection, 618, paintboard);

		paintboard.map.DrawLittlemap(paintboard.gamecontroller.selfnum, 300,
				300, g);

		// /畫CD時間
		g.setColor(new Color(0, 0, 0, 255));
		font = new Font("微軟正黑體", Font.BOLD, 30);
		g.setFont(font);
		for (int i = 0; i < 6; i++) {
			Character character = paintboard.allcharacter.get(selftnum);
			g.drawString(character.skillcooltime.get(i)+"", 30 + 100 * (i), 720);
		}

		// 畫按鈕
		/*
		 * g.drawImage(paintboard.imagelibrary.getImage("button"), 1025, 520,
		 * 150, 40, paintboard);
		 * g.drawImage(paintboard.imagelibrary.getImage("button"), 1025, 580,
		 * 150, 40, paintboard);
		 * g.drawImage(paintboard.imagelibrary.getImage("button"), 1025, 670,
		 * 100, 40, paintboard);
		 * 
		 * // 畫字 g.setFont(new Font("微軟正黑體", Font.BOLD, 30));
		 * 
		 * // 判斷是否選中 選中顏色 Yellow 未選中Black
		 * 
		 * g.drawString("地圖", 1070, 70);
		 * 
		 * g.drawString("回合次數", 1040, 180);
		 * 
		 * g.drawString("購買時間", 1040, 290);
		 * 
		 * g.drawString("每回合增加金額", 993, 400);
		 * 
		 * // 判斷是否選中 選中顏色 Yellow 未選中White
		 * 
		 * g.drawString("開始遊戲", 1040, 550);
		 * 
		 * g.drawString("離開遊戲", 1040, 610);
		 * 
		 * g.drawString("發  送", 1035, 700);
		 * 
		 * // 設定透明度0.8 g.setComposite(AlphaComposite.SrcOver.derive(0.8f)); //
		 * 畫出每個 腳色連線狀態框 綠:連線 紅:未連線 for (int i = 0; i < 2; i++) { for (int j = 0;
		 * j < 4; j++) { // num 目前 第幾個玩家 int num = j + 1; if (i == 1) { num = j
		 * + 5; } // 只要比超出所有玩家數 就顯示紅:未連線 if (paintboard.allcharacter.size() <
		 * num) { g.setColor(new Color(255, 0, 0, 255)); } else { g.setColor(new
		 * Color(0, 255, 0, 255)); } g.fill3DRect(50 + j * 230, 50 + i * 230,
		 * 200, 200, true); } }
		 * 
		 * // 聊天內容框 g.setColor(new Color(0, 0, 0, 255)); g.fill3DRect(10, 495,
		 * 1000, 165, true);
		 * 
		 * // 還原透明度 g.setComposite(AlphaComposite.SrcOver.derive(1f));
		 * g.setColor(new Color(255, 255, 255, 255)); Font font = new
		 * Font("微軟正黑體", Font.BOLD, 40); g.setFont(font);
		 */
		// 畫出每個腳色名稱

		// for (int i = 0; i < paintboard.allcharacter.size(); i++) {
		/*
		 * mapx :X軸第幾個 mapy :Y軸第幾個
		 */
		/*
		 * int mapx = 0; int mapy = 0; if (i > 3) { mapx = i - 3; mapy = 2; }
		 * else { mapx = i; } // 取得字形Metrics FontMetrics metrics =
		 * g.getFontMetrics(font); // 置中 int x = 50 + mapx 230 + (200 - metrics
		 * .stringWidth(paintboard.allcharacter.get(i).name)) / 2; //
		 * System.out.println(xmap);
		 * g.drawString(paintboard.allcharacter.get(i).name, x, 165 + mapy *
		 * 230);
		 */
	}

	// ///////////////////////控制模組/////////////////////////////////
	boolean isPressUpAble = true;
	boolean isPressDownAble = true;
	boolean isPressLeftAble = true;
	boolean isPressRightAble = true;

	public void pressUp() {
		// 傳送給伺服器更新狀態
		// 沒按下去可傳送
		// 按下去就不可再傳送
		// 要release 才可再傳送
		if (isPressUpAble) {
			isPressUpAble = false;
			isPressDownAble = true;
			this.paintboard.gamecontroller.deliverUpdateGameMoveState("up",
					true);
		}
	}

	public void releaseUp() {
		isPressDownAble = true;
		isPressUpAble = true;
		this.paintboard.gamecontroller.deliverUpdateGameMoveState("up", false);
	}

	public void pressDown() {
		if (isPressDownAble) {
			isPressDownAble = false;
			isPressUpAble = true;
			this.paintboard.gamecontroller.deliverUpdateGameMoveState("down",
					true);
		}
	}

	public void releaseDown() {
		isPressDownAble = true;
		isPressUpAble = true;
		this.paintboard.gamecontroller
				.deliverUpdateGameMoveState("down", false);
	}

	public void pressRight() {
		if (isPressRightAble) {
			isPressRightAble = false;
			isPressLeftAble = true;
			this.paintboard.gamecontroller.deliverUpdateGameMoveState("right",
					true);
		}
	}

	public void releaseRight() {
		isPressRightAble = true;
		isPressLeftAble = true;
		this.paintboard.gamecontroller.deliverUpdateGameMoveState("right",
				false);
	}

	public void pressLeft() {
		if (isPressLeftAble) {
			isPressLeftAble = false;
			isPressRightAble = true;
			this.paintboard.gamecontroller.deliverUpdateGameMoveState("left",
					true);
		}
	}

	public void releaseLeft() {
		isPressRightAble = true;
		isPressLeftAble = true;
		this.paintboard.gamecontroller
				.deliverUpdateGameMoveState("left", false);
	}

	public void pressLeftClick(int x, int y) {
		// 傳送座標跟目前選項技能
		// 伺服器端判斷是否在CD 如果沒有則傳送給大家要NEW 技能到技能列表裡面
		//
	}

	public void pressSelectRight() {
		selectright(6);
	}

	public void selectright(int maxselect) {
		if (selection == maxselect - 1)
			selection = 0;
		else
			selection++;
	}

	public void pressSelectLeft() {
		selectleft(6);

	}

	public void selectleft(int maxselect) {
		if (selection == 0)
			selection = maxselect - 1;
		else
			selection--;
	}

}
