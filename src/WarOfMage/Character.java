package WarOfMage;

import java.awt.Image;
import java.util.ArrayList;

public class Character {
	// 腳色面對方向
	String direction = "down";
	boolean isMoving = false;
	GIF moveup;
	GIF movedown;
	GIF moveleft;
	GIF moveright;

	// 腳色座標
	double x = 2000;
	double y = 2000;
	// 腳色寬高
	double w = 30;
	double h = 70;

	// 腳色名稱
	String name;

	// 血量 最大血量
	int hp = 150;
	int maxhp = 150;

	// 跑素 金錢
	int baseRunSpeed = 100;
	int nowRunSpeed = 100;
	int money = 1000;

	// array int 擁有技能 index 0=火球 value=技能等級
	// 1=冰球 2=石頭 3=電 4=毒 5=光
	ArrayList<Integer> skill = new ArrayList<Integer>();
	// 造成總傷害
	int totalDamge = 0;

	// 總擊殺數
	int kill = 0;

	int lastonetimes = 0;

	int Score = 0;
	// 更新速度 每10毫秒更新一次
	int updateMilliSec = 10;

	// 是否死亡
	boolean isDead = false;
	// 初始化時不重生
	// 斷線時true 死亡 true
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

	// //////////////////////////////////CD模組///////////////////////////////////
	// array int 擁有技能 index 0=火球 value=目前CD時間 EX CD剩兩秒 :2000 沒有CD:0
	// 1=冰球 2=石頭 3=電 4=毒 5=光
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
	// 是否暈眩
	boolean isStun = false;
	// 暈眩毫秒
	double stunMilliSec = 0;
	// 目前暈眩毫秒 每次+更新毫秒
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

	// 是否緩速
	boolean isSlowed = false;
	// 緩速毫秒
	double slowedMilliSec;
	// 目前緩速毫秒
	double allreadySlowedMilliSec = 0;

	// 傳入緩速%數 跟緩數時間
	// 單位 減速百分比 EX30%=30 緩速30% 基礎跑速變70%
	// 緩速時間 毫秒 1秒=1000
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
	// 是否中毒
	boolean isPoisoned = false;
	// 中毒毫秒
	double poisonedMilliSec;
	// 目前中毒毫秒
	double allreadyPoisonedMilliSec = 0;
	// 目前中毒傷害 單位 傷害/每秒
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
	// 是否開啟聖御
	boolean isLighted = false;
	// 聖御毫秒
	double lightedMilliSec;
	// 目前聖御毫秒
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
	// 是否被撞飛
	boolean isImpacked = false;
	double impackedTotalDistance = 0;
	// if isImpacked
	// 往direction 飛行單位撞飛速度
	// 如果行走距離>=撞飛距離 isImpacked=false;
	// allreadyImpackedDistance=0;
	double allreadyImpackedDistance = 0;
	// 右邊0 順時針 角度 拍為單位
	double impackedDirection;
	double impackedSpeed;

	public void getImpacked(double impackedTotalDistance,
			double impackedDirection, double impackedSpeed) {
		isImpacked = true;
		// 如果綜合this.impackedDirection+ = impackedDirection;
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
	// 是否按下移動
	// 上下左右
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
	// 初始化己色
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
	 * 函示: 回合初始化-座標 血量 金錢家每回合的數量 造成總傷害
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
