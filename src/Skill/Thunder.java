package Skill;

import WarOfMage.Character;
import WarOfMage.GIF;
import WarOfMage.PaintBoard;

public class Thunder extends BaseSkill {
	PaintBoard paintboard;
	int basedamage = 10;
	int increasedamage = 5;
	public int maxlevel = 10;

	public Thunder(PaintBoard a, int castnum, double x, double y, int level) {
		this.level = level;
		this.whocast = castnum;
		this.skillnum = 3;
		this.paintboard = a;
		this.basedamage = 5;
		this.increasedamage = 3;
		this.debuffsec = 1000;
		this.increasedebuffsec = 500;
		this.castspeed = 7000;
		this.castdistance = 85;
		this.radius = 28;
		this.cooltime = 10000;
		if (castnum != -1) {
			this.castcharacter = paintboard.allcharacter.get(castnum);
			this.castcharacter.inCoolDown(this.skillnum, this.cooltime);
			gif = new GIF("ThunderBall", a, true);
			this.direction = Direction(x, y);
			this.x = castcharacter.getX()
					+ Math.cos(Math.toRadians(this.direction))
					* this.castdistance;
			this.y = castcharacter.getY()
					+ Math.sin(Math.toRadians(this.direction))
					* this.castdistance - 50;
		}

	}

	public String getSkillDescribe(int level) {
		int damage = (level - 1) * increasedamage + basedamage;
		float sec = debuffsec + (level - 1) * increasedebuffsec;
		sec = sec / 1000;
		String describe = "投射一個電球往指定方向，碰撞到敵人會暈眩" + sec + "秒，並且造成" + damage
				+ "點傷害。";
		if (level == 0) {
			describe = "尚未學習此技能。";
		}
		return describe;
	}

	public String getSkillName() {
		return "雷球術";
	}

	public void move() {
		this.x = this.x + Math.cos(Math.toRadians(this.direction))
				* this.castspeed / 1000;
		this.y = this.y + Math.sin(Math.toRadians(this.direction))
				* this.castspeed / 1000;
		if (x < 0 || x > 40000 || y < 0 || y > 40000) {
			this.visible = false;
		}
	}

	public void collison(BaseSkill anotherskill) {
		// 火冰毒電 技能互碰的時候 兩邊直接消失 用baseskill的skillnum去判斷碰撞甚麼技能
		// 碰到其他的甚麼事情都不做
		// 落石碰到技能不做事包括護盾
		// 護盾碰到火冰毒電 會反彈 方像 direction變反相(+180)

		switch (anotherskill.skillnum) {
		case 0:
			anotherskill.visible = false;
			visible = false;
			break;
		case 1:
			anotherskill.visible = false;
			visible = false;
			break;
		case 2:
			break;
		case 3:
			anotherskill.visible = false;
			visible = false;
			break;
		case 4:
			anotherskill.visible = false;

			visible = false;
			break;
		case 5:
			direction = Direction(anotherskill.x, anotherskill.y, 1) + 180;
			this.whocast = anotherskill.whocast;
			break;
		default:
			break;
		}

	}

	// 碰到人的時候
	public void collison(Character character) {
		// 護盾落石火冰毒電 就依據技能效果去呼叫character裡面的函式
		// 護盾落石不用消失
		paintboard.music.playHitSe();
		character.getStun(debuffsec + increasedebuffsec * (level - 1));
		character.getDamage((level - 1) * increasedamage + basedamage,this.whocast);

		visible = false;
	}
	
	public int getdrawx() {
		return (int) x ;

	}

	// 暫時不用寫 等圖片
	public int getdrawy() {

		return (int) y +5;

	}
}
