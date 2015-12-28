package WarOfMage;

import java.awt.Image;
import java.util.ArrayList;

public class Character {
	// �}�⭱���V
	String direction = "down";
	boolean isMoving = false;
	GIF moveup;
	GIF movedown;
	GIF moveleft;
	GIF moveright;

	// �}��y��
	double x = 2000;
	double y = 2000;
	// �}��e��
	double w = 30;
	double h = 70;

	// �}��W��
	String name;

	// ��q �̤j��q
	int hp = 150;
	int maxhp = 150;

	// �]�� ����
	int baseRunSpeed = 100;
	int nowRunSpeed = 100;
	int money = 1000;

	// array int �֦��ޯ� index 0=���y value=�ޯ൥��
	// 1=�B�y 2=���Y 3=�q 4=�r 5=��
	ArrayList<Integer> skill = new ArrayList<Integer>();
	// �y���`�ˮ`
	int totalDamge = 0;

	// �`������
	int kill = 0;

	int lastonetimes = 0;

	int Score = 0;
	// ��s�t�� �C10�@���s�@��
	int updateMilliSec = 10;

	// �O�_���`
	boolean isDead = false;
	// ��l�Ʈɤ�����
	// �_�u��true ���` true
	boolean isDisconnected = false;

	public void getDamage(int Damage, int whocast) {
		if (!isDead) {
			hp = hp - Damage;
			if (whocast != 8) {
				paintboard.allcharacter.get(whocast).totalDamge += Damage;
			}
			if (hp < 0) {
				hp = 0;
				isDead = true;
				if (whocast != 8) {
					paintboard.allcharacter.get(whocast).money += 1000;
					paintboard.allcharacter.get(whocast).kill++;
				}
			}
		}
	}

	public void initialCharacter() {
		if (!this.isDisconnected) {
			hp = maxhp;
			isUpPressed = false;
			isDownPressed = false;
			isLeftPressed = false;
			isRightPressed = false;
			isMoving = false;
			this.StopImpacked();
			this.StopLighted();
			this.StopPoisoned();
			this.StopSlowSpeed();
			this.StopStun();
			hp = this.maxhp;
			isDead = false;
			direction = "down";
			for (int i = 0; i < skillcooltime.size(); i++) {
				skillcooltime.set(i, 0);
			}
		}
	}

	// //////////////////////////////////CD�Ҳ�///////////////////////////////////
	// array int �֦��ޯ� index 0=���y value=�ثeCD�ɶ� EX CD�Ѩ�� :2000 �S��CD:0
	// 1=�B�y 2=���Y 3=�q 4=�r 5=��
	ArrayList<Integer> skillcooltime = new ArrayList<Integer>();

	public void inCoolDown(int skillnum, int cooltime) {
		skillcooltime.set(skillnum, cooltime);
	}

	public void CoolDown() {
		for (int i = 0; i < skillcooltime.size(); i++) {
			if (skillcooltime.get(i) > 0) {
				int newcooltime = skillcooltime.get(i) - updateMilliSec;
				if (newcooltime > 0) {
					skillcooltime.set(i, newcooltime);
				} else {
					skillcooltime.set(i, 0);
				}
			}
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// �O�_�w�t
	boolean isStun = false;
	// �w�t�@��
	double stunMilliSec = 0;
	// �ثe�w�t�@�� �C��+��s�@��
	double allreadyStunMilliSec = 0;

	public void getStun(double stunMilliSec) {
		isStun = true;
		this.stunMilliSec += stunMilliSec;
	}

	public void Stun() {
		allreadyStunMilliSec += updateMilliSec;
		if (allreadyStunMilliSec >= stunMilliSec) {
			StopStun();
		}
	}

	public void StopStun() {
		isStun = false;
		allreadyStunMilliSec = 0;
		stunMilliSec = 0;

	}

	// //////////////////////////////////////////////////////////////////////////

	// �O�_�w�t
	boolean isSlowed = false;
	// �w�t�@��
	double slowedMilliSec;
	// �ثe�w�t�@��
	double allreadySlowedMilliSec = 0;

	// �ǤJ�w�t%�� ��w�Ʈɶ�
	// ��� ��t�ʤ��� EX30%=30 �w�t30% ��¦�]�t��70%
	// �w�t�ɶ� �@�� 1��=1000
	public void getSlowSpeed(double slowedPercent, double slowedMilliSec) {
		this.isSlowed = true;
		int slowspeed = (int) (this.baseRunSpeed * (100 - slowedPercent) / 100);
		if (nowRunSpeed > slowspeed) {
			this.nowRunSpeed = slowspeed;
		}
		slowedMilliSec += slowedMilliSec;
	}

	public void SlowSpeed() {
		allreadySlowedMilliSec += updateMilliSec;
		if (allreadySlowedMilliSec >= slowedMilliSec) {
			StopSlowSpeed();
		}
	}

	public void StopSlowSpeed() {
		isSlowed = false;
		allreadySlowedMilliSec = 0;
		slowedMilliSec = 0;
		this.nowRunSpeed = this.baseRunSpeed;

	}

	// //////////////////////////////////////////////////////////////////////////
	// �O�_���r
	boolean isPoisoned = false;
	// ���r�@��
	double poisonedMilliSec;
	// �ثe���r�@��
	double allreadyPoisonedMilliSec = 0;
	// �ثe���r�ˮ` ��� �ˮ`/�C��
	double poisonedDamage = 0;
	int poisonWhoCast;

	public void getPoisoned(double poisonedDamage, double poisonedMilliSec,
			int whocast) {
		this.isPoisoned = true;
		poisonWhoCast = whocast;
		if (poisonedDamage > this.poisonedDamage) {
			this.poisonedDamage = poisonedDamage;
		}
		this.poisonedMilliSec += poisonedMilliSec;
	}

	public void Poisoned() {
		if (allreadyPoisonedMilliSec % 1000 == 0) {
			this.getDamage((int) poisonedDamage, poisonWhoCast);
		}
		allreadyPoisonedMilliSec += updateMilliSec;
		if (allreadyPoisonedMilliSec >= poisonedMilliSec) {
			StopPoisoned();
		}
	}

	public void StopPoisoned() {
		isPoisoned = false;
		allreadyPoisonedMilliSec = 0;
		poisonedDamage = 0;
	}

	// //////////////////////////////////////////////////////////////////////////
	// �O�_�}�Ҹt�s
	boolean isLighted = false;
	// �t�s�@��
	double lightedMilliSec;
	// �ثe�t�s�@��
	double allreadyLightedMilliSec = 0;

	public void getLighted(double lightedMilliSec) {
		isLighted = true;
		this.lightedMilliSec = lightedMilliSec;
	}

	public void Lighted() {
		allreadyLightedMilliSec += updateMilliSec;
		if (allreadyLightedMilliSec >= lightedMilliSec || isDead) {
			StopLighted();
		}
	}

	public void StopLighted() {
		isLighted = false;
		allreadyLightedMilliSec = 0;
	}

	// //////////////////////////////////////////////////////////////////////////
	// �O�_�Q����
	boolean isImpacked = false;
	double impackedTotalDistance = 0;
	// if isImpacked
	// ��direction �����켲���t��
	// �p�G�樫�Z��>=�����Z�� isImpacked=false;
	// allreadyImpackedDistance=0;
	double allreadyImpackedDistance = 0;
	// �k��0 ���ɰw ���� �笰���
	double impackedDirection;
	double impackedSpeed;

	public void getImpacked(double impackedTotalDistance,
			double impackedDirection, double impackedSpeed) {
		isImpacked = true;
		// �p�G��Xthis.impackedDirection+ = impackedDirection;
		this.impackedDirection = impackedDirection;
		this.impackedSpeed = impackedSpeed;
		this.impackedTotalDistance += impackedTotalDistance;
	}

	public void Impacked() {
		double scale = impackedSpeed / 1000;
		x = x + Math.cos(Math.toRadians(impackedDirection)) * scale;
		y = y + Math.sin(Math.toRadians(impackedDirection)) * scale;
		allreadyImpackedDistance += 1 * scale;
		if (allreadyImpackedDistance >= impackedTotalDistance) {
			StopImpacked();
		}
	}

	public void StopImpacked() {
		isImpacked = false;
		allreadyImpackedDistance = 0;
		impackedTotalDistance = 0;
		impackedSpeed = 0;
	}

	// //////////////////////////////////////////////////////////////////////////
	// �O�_���U����
	// �W�U���k
	boolean isUpPressed = false;
	boolean isDownPressed = false;
	boolean isLeftPressed = false;
	boolean isRightPressed = false;

	public void upPress() {
		// System.out.println(13333);
		this.isUpPressed = true;
		// this.isDownPressed = false;
	}

	public void downPress() {
		// this.isUpPressed = false;
		this.isDownPressed = true;
	}

	public void leftPress() {
		this.isLeftPressed = true;
		// this.isRightPressed = false;
	}

	public void rightPress() {
		// this.isLeftPressed = false;
		this.isRightPressed = true;
	}

	public void upRelease() {
		this.isUpPressed = false;
	}

	public void downRelease() {
		this.isDownPressed = false;
	}

	public void leftRelease() {
		this.isLeftPressed = false;
	}

	public void rightRelease() {
		this.isRightPressed = false;
	}

	public boolean isSkillCastAble(int skillnum) {
		if (this.skillcooltime.get(skillnum) == 0 && skill.get(skillnum) > 0) {
			return true;
		} else
			return false;
	}

	public void move() {
		double unitditance = this.nowRunSpeed / 50;
		if (!isStun) {

			if (isUpPressed) {
				y = y - unitditance;
				this.direction = "up";
				this.isMoving = true;
			}
			if (isDownPressed) {
				y = y + unitditance;
				this.direction = "down";
				this.isMoving = true;
			}
			if (isRightPressed) {
				// System.out.println(13333);
				x = x + unitditance;
				this.direction = "right";
				this.isMoving = true;
			}
			if (isLeftPressed) {
				x = x - unitditance;
				this.direction = "left";
				this.isMoving = true;
			}
			if (!isRightPressed && !isLeftPressed && !isUpPressed
					&& !isDownPressed) {
				this.isMoving = false;
			}
		} else {
			this.isMoving = false;
		}
	}

	public Image getImage() {
		if (isMoving) {
			switch (direction) {
			case "up":
				return this.moveup.getNowImage();
			case "down":
				return this.movedown.getNowImage();
			case "left":
				return this.moveleft.getNowImage();
			case "right":
				return this.moveright.getNowImage();
			}
		} else {
			switch (direction) {
			case "up":
				return this.paintboard.imagelibrary.getImage("StandUp");
			case "down":
				return this.paintboard.imagelibrary.getImage("StandDown");
			case "left":
				return this.paintboard.imagelibrary.getImage("StandLeft");
			case "right":
				return this.paintboard.imagelibrary.getImage("StandRight");
			}

		}
		return null;
	}

	// //////////////////////////////////////////////////////////////////////////
	// ��l�Ƥv��
	PaintBoard paintboard;

	public Character(String name, PaintBoard a) {
		paintboard = a;
		this.name = name;
		skill.add(0);
		skill.add(0);
		skill.add(0);
		skill.add(0);
		skill.add(0);
		skill.add(0);
		skillcooltime.add(0);
		skillcooltime.add(0);
		skillcooltime.add(0);
		skillcooltime.add(0);
		skillcooltime.add(0);
		skillcooltime.add(0);
		moveup = new GIF("RunUp", a, true);
		movedown = new GIF("RunDown", a, true);
		moveleft = new GIF("RunLeft", a, true);
		moveright = new GIF("RunRight", a, true);
	}

	/*
	 * ���: �^�X��l��-�y�� ��q �����a�C�^�X���ƶq �y���`�ˮ`
	 */
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getCollX() {
		return x - 17;
	}

	public double getCollY() {
		return y - 80;
	}

	public double getDrawX() {
		return x;
	}

	public double geDrawX() {
		return y;
	}

}
