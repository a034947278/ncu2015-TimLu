package Skill;

import java.awt.Image;

import WarOfMage.*;
import WarOfMage.Character;

public class BaseSkill {
	// 發射人角色

	Character castcharacter;
	// paintboard
	PaintBoard paintboard;
	// 是否看的到 OR 存在
	public boolean visible = true;
	public GIF gif;
	// 落石用
	public boolean isCollisonAble = false;
	public boolean alreadyCollison = false;
	int level;
	// 技能座標
	public double x;
	public double y;
	// 技能號碼
	public int skillnum;
	// 基礎傷害
	int basedamage = 10;
	// 等級增加傷害
	int increasedamage = 5;

	// 碰撞半徑 暫時不用寫 等圖片
	int radius = 28;

	// 單位 毫秒

	// 基礎負面狀態時間 1000=1秒
	int debuffsec;
	// 等級增加負面狀態時間
	int increasedebuffsec;

	// 基礎狀態時間 1000=1秒
	int buffsec;
	// 等級增加狀態時間
	int increasebuffsec;

	// 緩速百分比 緩速40%=40
	int decreasespeed;

	// 冷卻時間
	int cooltime = 0;

	// 碰撞彈飛距離
	int collisondistance;
	// 碰撞彈飛速度
	int collisonspeed;

	// 技能發射速度
	int castspeed = 1000;
	// 技能發射距離
	int castdistance = 75;

	// 幾號使用這個技能
	public int whocast;

	// 右邊0 上面90 左邊180 下面270
	public double direction;

	// 最大等級
	public int maxlevel = 10;

	public BaseSkill() {

	}

	public String getSkillDescribe(int level) {
		return "";
	}

	public String getSkillName() {
		return null;
	}

	// 造成的傷害
	public int getDamage() {
		return 1;
	}

	// 碰撞後的效果///////////////////////////////////////
	public void collison(BaseSkill anotherskill) {
		// 火冰毒電 技能互碰的時候 兩邊直接消失 用baseskill的skillnum去判斷碰撞甚麼技能
		// 碰到其他的甚麼事情都不做
		// 落石碰到技能不做事包括護盾
		// 護盾碰到火冰毒電 會反彈 方像 direction變反相(+180)

	}

	// 碰到人的時候
	public void collison(Character anotherskill) {
		// 護盾落石火冰毒電 就依據技能效果去呼叫character裡面的函式
		// 護盾落石不用消失

	}

	public void move() {
		// 冰火毒雷可參考我character的 碰撞部分
		// 護盾則是xy = 角色的xy
		// 落石不變

	}

	public double Direction(double x, double y) {// direction為角度
		double direction = 0;

		direction = Math.atan2(y - 310, x - 640) / Math.PI * 180;

		return direction;
	}

	public double Direction(double xx, double yy, int i) {// direction為角度
		double direction = 0;

		direction = Math.atan2(yy - y, xx - x) / Math.PI * 180;

		return direction;
	}

	public Image getImage() {
		// 不用寫
		return gif.getNowImage();
	}

	public boolean isVisible() {
		return visible;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	// 暫時不用寫 等圖片
	public int getdrawx() {
		return (int) x;

	}

	// 暫時不用寫 等圖片
	public int getdrawy() {

		return (int) y;

	}

	// 暫時不用寫 等圖片
	public int getradius() {
		return radius;
	}
}
