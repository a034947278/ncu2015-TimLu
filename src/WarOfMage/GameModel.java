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
			// �ǰe�}��NUM �y�� hp ���`���A ���Ȥ�ݧ�s
		}
		// �ǰe�C�ӫȤ�ݶ}�l�C��
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
			// �ǰe�}��s�y�е��Ȥ�ݧ�s
		}
	}

	public void allSkillMove() {
		for (int i = 0; i < paintboard.allskill.size(); i++) {
			BaseSkill skill = paintboard.allskill.get(i);
			skill.move();
			// �ǰe�ޯ�s�y�е��Ȥ�ݧ�s
		}
	}

	// ////////////////////////////////////////////////�I���Ҳ�/////////////////////////////////////////////

	public void checkcollison() {
		for (int i = 0; i < paintboard.allskill.size(); i++) {
			BaseSkill skill = paintboard.allskill.get(i);
			for (int j = i + 1; j < paintboard.allskill.size(); j++) {
				BaseSkill skill2 = paintboard.allskill.get(j);
				// �p�G���s�b�~���I��
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
				// �p�G�S���ӥB�ޯ�s�b�~���I��
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

	// ��Τ�θI��
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

	// ��ζ�θI��
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

	// ��Τ�θI��
	private boolean isCollisonCircleToRect(int x1, int y1, int r1, int x2,
			int y2, int w2, int h2) {
		// �|�Ө��O�_�b��̭�
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
		// ���Y�y�Цb��νd�򤺮�
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
	// ////////////////////////////////////////////////���A�ˬd��s�Ҳ�//////////////////////////////////////////
	public void checkState() {
		checkCharacterState();
		checkSkillState();
	}

	private void checkCharacterState() {
		// �����ܤƶǰe��sHP��H���y��
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
			// TODO �p�G�S�_�u�~��

			// �ǰe�}��NUM �y�� hp ���`���A �ثe��V���Ȥ�ݧ�s
		}
	}

	private void checkSkillState() {
		// �����ܤƶǰe��sskilllist ��ޯ�y��
		// �p�Gvisible=false �Nremove �åB�s���Ȥ��Remove
		// 0=���y 1=�B�y 2=���Y 3=�q 4=�r 5=��
		// �p�G�O�t��
		// �p�G�O����

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
			// �ǰe���w�ޯ�NUM �s���A isvisible ��y�� gif isStop ���Ȥ�ݧ�s
			// �p�G�ݤ���N����skilllist�ӧޯ�
		}

	}

	// //////////////////////////////����Ҧ���////////////////////////////////////
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
		// TODO ����
		if (lastpeoplenum <= leftpeoplenumber) {
			paintboard.allcharacter.get(lastpersonnum).lastonetimes++;
			return true;
		} else {
			return false;
		}
	}

	private void checkGameOver() {
		if (isGameover()) {
			// ��^�ʶR�e���ε��⭶��
			gametimes++;
			int maxgametimes = Integer
					.valueOf(this.paintboard.lobbymenu.levelinput.data);
			if (gametimes == maxgametimes) {
				// TODO ����
				paintboard.map.stop();
				for (int i = 0; i < paintboard.allcharacter.size(); i++) {
					Character character = paintboard.allcharacter.get(i);
					this.paintboard.gamecontroller.deliverScoreState(i,
							character.kill, character.totalDamge);
				}
				this.paintboard.gamecontroller.deliverStartScoreMenu();
			} else {
				// �^�ʶR�e��
				// TODO �ǰe���j�a�C������ �^���ʶR�e��
				this.paintboard.gamecontroller.deliverStartBuy();
			}
			isGaming = false;

		}
	}

	public void updategamestate() {
		for (int i = 0; i < paintboard.allcharacter.size(); i++) {
			Character character = paintboard.allcharacter.get(i);

			// TODO �p�G�S�_�u�~��
			if (!character.isDisconnected) {
				this.paintboard.gamecontroller.deliverUpdateClientGameState(i
						+ "", (int) character.x, (int) character.y,
						character.hp, character.isDead, character.direction,
						character.money);
			}
			// �ǰe�}��NUM �y�� hp ���`���A �ثe��V���Ȥ�ݧ�s
		}

		for (int i = 0; i < paintboard.allskill.size(); i++) {
			BaseSkill skill = paintboard.allskill.get(i);

			this.paintboard.gamecontroller.deliverUpdateClientGameSkillState(i,
					skill.x, skill.y, skill.direction, skill.visible,
					skill.gif.isStop);

			// �ǰe�ޯ�NUM �y�� ��V �O�_�ݪ������Ȥ�ݧ�s
		}

	}

	public void updategame() {
		// �]�|������P�ҥH����
		// allCharacterMove();
		// allSkillMove();
		// checkcollison();
		// checkState();
		updategamestate();
		checkGameOver();
	}

	// �C�Q�@���s�@��
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
