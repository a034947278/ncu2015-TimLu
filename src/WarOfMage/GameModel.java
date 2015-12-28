package WarOfMage;

import java.util.ArrayList;

import Skill.BaseSkill;

public class GameModel implements Runnable {
	// int
	PaintBoard paintboard;
	int updatetime = 10;
	int gametimes = 0;
	int leftpeoplenumber=1;
	boolean isGaming = false;

	public GameModel(PaintBoard a) {
		this.paintboard = a;

	}

	public void ServerStartGame() {

		// TODO RANDOM LOCATION
		paintboard.map.start();
		paintboard.map.initialCharacterLocation();
		for (int i = 0; i < paintboard.allcharacter.size(); i++) {
			Character character = paintboard.allcharacter.get(i);
			character.initialCharacter();
		}
		// TODO MAP START

		for (int i = 0; i < paintboard.allcharacter.size(); i++) {
			Character character = paintboard.allcharacter.get(i);
			this.paintboard.gamecontroller.deliverUpdateClientGameState(i + "",
					(int) character.x, (int) character.y, character.hp,
					character.isDead, character.direction, character.money);
			// 傳送腳色NUM 座標 hp 死亡狀態 給客戶端更新
		}
		// 傳送每個客戶端開始遊戲
		this.isGaming = true;
		Thread gamingthread = new Thread(this);
		gamingthread.start();
		paintboard.gamecontroller.deliverStartGame();
		paintboard.scene = 3;
	}

	public void ClientStartGame() {
		// TODO MAP START
		paintboard.map.start();
		for (int i = 0; i < paintboard.allcharacter.size(); i++) {
			Character character = paintboard.allcharacter.get(i);
			character.initialCharacter();
		}
		paintboard.scene = 3;
	}

	public void allCharacterMove() {
		for (int i = 0; i < paintboard.allcharacter.size(); i++) {
			Character character = paintboard.allcharacter.get(i);
			character.move();
			// 傳送腳色新座標給客戶端更新
		}
	}

	public void allSkillMove() {
		for (int i = 0; i < paintboard.allskill.size(); i++) {
			BaseSkill skill = paintboard.allskill.get(i);
			skill.move();
			// 傳送技能新座標給客戶端更新
		}
	}

	// ////////////////////////////////////////////////碰撞模組/////////////////////////////////////////////

	public void checkcollison() {
		for (int i = 0; i < paintboard.allskill.size(); i++) {
			BaseSkill skill = paintboard.allskill.get(i);
			for (int j = i + 1; j < paintboard.allskill.size(); j++) {
				BaseSkill skill2 = paintboard.allskill.get(j);
				// 如果都存在才做碰撞
				if (skill2.isVisible() && skill.isVisible()
						&& skill.whocast != skill2.whocast) {
					if (this.isCollisonCircleToCircle((int) skill.x,
							(int) skill.y, (int) skill.getradius(),
							(int) skill2.x, (int) skill2.y,
							(int) skill2.getradius())) {
						//System.out.println("skillcollison");
						skill.collison(skill2);
					}
				}
			}

			for (int j = 0; j < paintboard.allcharacter.size(); j++) {
				Character character = paintboard.allcharacter.get(j);
				// 如果沒死而且技能存在才做碰撞
				if (!character.isDead && skill.isVisible()
						&& (j != skill.whocast)) {
					if (this.isCollisonCircleToRect((int) skill.x,
							(int) skill.y, (int) skill.getradius(),
							(int) character.getCollX(),
							(int) character.getCollY(), (int) character.w,
							(int) character.h)) {
						//System.out.println("personcollison");
						skill.collison(character);
					}
				}
			}

		}
	}

	// 方形方形碰撞
	private boolean isCollisonRectToRect(int x1, int y1, int w1, int h1,
			int x2, int y2, int w2, int h2) {
		if ((x1 >= x2 && x1 <= (x2 + w2)) && (y1 >= y2 && y1 <= (y2 + h2))) {
			return true;
		} else if (((x1 + w1) >= x2 && (x1 + w1) <= (x2 + w2))
				&& (y1 >= y2 && y1 <= (y2 + h2))) {
			return true;
		} else if ((x1 >= x2 && x1 <= (x2 + w2))
				&& ((y1 + h1) >= y2 && (y1 + h1) <= (y2 + h2))) {
			return true;
		} else if (((x1 + w1) >= x2 && (x1 + w1) <= (x2 + w2))
				&& ((y1 + h1) >= y2 && (y1 + h1) <= (y2 + h2))) {
			return true;
		} else {
			return false;
		}
	}

	// 圓形圓形碰撞
	private boolean isCollisonCircleToCircle(int x1, int y1, int r1, int x2,
			int y2, int r2) {
		double distance = Math.sqrt(Math.pow((x2 - x1), 2)
				+ Math.pow((y2 - y1), 2));
		if (distance < r1 + r2) {
			return true;
		} else {
			return false;
		}
	}

	// 圓形方形碰撞
	private boolean isCollisonCircleToRect(int x1, int y1, int r1, int x2,
			int y2, int w2, int h2) {
		// 四個角是否在圓裡面
		if (this.isCollisonCircleToCircle(x1, y1, r1, x2, y2, 0)) {
			return true;
		} else if (this.isCollisonCircleToCircle(x1, y1, r1, x2 + w2, y2, 0)) {
			return true;
		} else if (this.isCollisonCircleToCircle(x1, y1, r1, x2, y2 + h2, 0)) {
			return true;
		} else if (this.isCollisonCircleToCircle(x1, y1, r1, x2 + w2, y2 + h2,
				0)) {
			return true;
		}
		// 圓心Y座標在方形範圍內時
		if (y1 >= y2 && y1 <= y2 + h2) {
			if (x1 >= x2 - r1 && x1 <= x2 + w2 + r1) {
				return true;
			}
		}
		if (x1 >= x2 && x1 <= x2 + w2) {
			if (y1 >= y2 - r1 && y1 <= y2 + h2 + r1) {
				return true;
			}
		}
		return false;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////狀態檢查更新模組//////////////////////////////////////////
	public void checkState() {
		checkCharacterState();
		checkSkillState();
	}

	private void checkCharacterState() {
		// 做完變化傳送更新HP跟人物座標
		for (int i = 0; i < paintboard.allcharacter.size(); i++) {
			Character character = paintboard.allcharacter.get(i);
			if (character.isStun) {
				character.Stun();
			}
			if (character.isSlowed) {
				character.SlowSpeed();
			}
			if (character.isPoisoned) {
				character.Poisoned();
			}
			if (character.isLighted) {
				character.Lighted();
			}
			if (character.isImpacked) {
				character.Impacked();
			}
			if (this.paintboard.map.isOutOfBounds((int) character.x,
					(int) character.y)) {
				character.getDamage(1 + gametimes / 2, 8);
			}
			character.CoolDown();
			// TODO 如果沒斷線才傳

			// 傳送腳色NUM 座標 hp 死亡狀態 目前方向給客戶端更新
		}
	}

	private void checkSkillState() {
		// 做完變化傳送更新skilllist 跟技能座標
		// 如果visible=false 就remove 並且廣報客戶端Remove
		// 0=火球 1=冰球 2=石頭 3=電 4=毒 5=光
		// 如果是聖光
		// 如果是落石

		for (int i = 0; i < paintboard.allskill.size(); i++) {
			BaseSkill skill = paintboard.allskill.get(i);
			if (skill.visible) {
				if (!paintboard.allcharacter.get(skill.whocast).isLighted
						&& skill.skillnum == 5) {
					skill.visible = false;
				} else if (skill.skillnum == 2 && skill.gif.index >= 8) {
					if (skill.isCollisonAble) {
						skill.isCollisonAble = false;
					} else if (!skill.isCollisonAble && !skill.alreadyCollison) {
						skill.isCollisonAble = true;
						skill.alreadyCollison = true;
					}
					if (skill.gif.index >= (skill.gif.lastindex)) {
						skill.visible = false;
						skill.gif.stop();
					}
				}
			}
			// TODO
			// this.paintboard.gamecontroller.deliverUpdateClientGameSkillState(i,
			// skill.x, skill.y, skill.visible, skill.gif.istop);
			// 傳送指定技能NUM 新狀態 isvisible 跟座標 gif isStop 給客戶端更新
			// 如果看不到就移除skilllist該技能
		}

	}

	// //////////////////////////////結算模式用////////////////////////////////////
	/*
	 * public void addCauseDamge() { for (int i = 0; i <
	 * paintboard.allskill.size(); i++) { BaseSkill skill =
	 * paintboard.allskill.get(i);
	 * paintboard.allcharacter.get(skill.whocast).totalDamge +=
	 * skill.causedamage; } }
	 */
	// //////////////////////////////////////////////////////////////////////////////////////////////////////
	private boolean isGameover() {
		int lastpeoplenum = 0;
		int lastpersonnum = 0;
		for (int i = 0; i < paintboard.allcharacter.size(); i++) {
			if (!paintboard.allcharacter.get(i).isDead) {
				lastpeoplenum++;
				lastpersonnum = i;
			}
		}
		// TODO 測試
		if (lastpeoplenum <= leftpeoplenumber) {
			paintboard.allcharacter.get(lastpersonnum).lastonetimes++;
			return true;
		} else {
			return false;
		}
	}

	private void checkGameOver() {
		if (isGameover()) {
			// 轉回購買畫面或結算頁面
			gametimes++;
			int maxgametimes = Integer
					.valueOf(this.paintboard.lobbymenu.levelinput.data);
			if (gametimes == maxgametimes) {
				// TODO 結算
				paintboard.map.stop();
				for (int i = 0; i < paintboard.allcharacter.size(); i++) {
					Character character = paintboard.allcharacter.get(i);
					this.paintboard.gamecontroller.deliverScoreState(i,
							character.kill, character.totalDamge);
				}
				this.paintboard.gamecontroller.deliverStartScoreMenu();
			} else {
				// 回購買畫面
				// TODO 傳送給大家遊戲結束 回到購買畫面
				this.paintboard.gamecontroller.deliverStartBuy();
			}
			isGaming = false;

		}
	}

	public void updategamestate() {
		for (int i = 0; i < paintboard.allcharacter.size(); i++) {
			Character character = paintboard.allcharacter.get(i);

			// TODO 如果沒斷線才傳
			if (!character.isDisconnected) {
				this.paintboard.gamecontroller.deliverUpdateClientGameState(i
						+ "", (int) character.x, (int) character.y,
						character.hp, character.isDead, character.direction,
						character.money);
			}
			// 傳送腳色NUM 座標 hp 死亡狀態 目前方向給客戶端更新
		}

		for (int i = 0; i < paintboard.allskill.size(); i++) {
			BaseSkill skill = paintboard.allskill.get(i);

			this.paintboard.gamecontroller.deliverUpdateClientGameSkillState(i,
					skill.x, skill.y, skill.direction, skill.visible,
					skill.gif.isStop);

			// 傳送技能NUM 座標 方向 是否看的見給客戶端更新
		}

	}

	public void updategame() {
		// 因會有延遲感所以取消
		// allCharacterMove();
		// allSkillMove();
		// checkcollison();
		// checkState();
		updategamestate();
		checkGameOver();
	}

	// 每十毫秒更新一次
	public void run() {
		int moveCount = 0;
		long timeDiff, sleep;
		long second = 0;

		while (isGaming) {

			long startTime = System.currentTimeMillis();
			updategame();
			timeDiff = System.currentTimeMillis() - startTime;
			sleep = updatetime - timeDiff;
			// System.out.println(sleep);
			if (sleep < 0) {
				sleep = 0;
			}
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("Interrupted: " + e.getMessage());
			}
			moveCount++;
			second += System.currentTimeMillis() - startTime;
			if (second >= 1000L) {
				// fps.setText(" FPS:" + moveCount);
				moveCount = 0;
				second = 0L;
			}

		}
	}

}
