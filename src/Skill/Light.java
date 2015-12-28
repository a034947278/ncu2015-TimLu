package Skill;

import WarOfMage.Character;
import WarOfMage.GIF;
import WarOfMage.PaintBoard;

public class Light extends BaseSkill {

	/*
	 * 初始化 角色座標=技能座標
	 */
	public Light(PaintBoard a, int castnum, int level) {
		this.level = level;
		this.whocast = castnum;
		this.skillnum = 5;
		this.paintboard = a;
		this.buffsec = 5000;
		this.cooltime = 20000;
		this.increasebuffsec = 1000;
		this.maxlevel = 5;
		this.radius=60;
		this.collisondistance=200;
		this.collisonspeed=10000;
		if (castnum != -1) {
			this.castcharacter = paintboard.allcharacter.get(castnum);
			this.castcharacter.inCoolDown(this.skillnum, this.cooltime);
			gif = new GIF("Light", a, true);
			this.x = castcharacter.getX();
			this.y = castcharacter.getY()-50;
			castcharacter.getLighted(buffsec + (level - 1) * this.increasebuffsec);

			
		}

	}

	public String getSkillDescribe(int level) {
		int damage = (level - 1) * increasedamage + basedamage;
		float sec = this.buffsec + (level - 1) * increasebuffsec;
		sec = sec / 1000;
		String describe = "開啟一個無敵防護罩在自身周圍" + sec + "秒，碰撞到敵人會稍微彈飛，並且造成" + damage
				+ "點傷害。";
		if (level == 0) {
			describe = "尚未學習此技能。";
		}
		return describe;
	}

	public String getSkillName() {
		return "聖御術";
	}

	public void move() {
		this.x = this.castcharacter.getX();
		this.y = this.castcharacter.getY()-50;
	}

	public void collison(BaseSkill anotherskill) {
		// 火冰毒電 技能互碰的時候 兩邊直接消失 用baseskill的skillnum去判斷碰撞甚麼技能
		// 碰到其他的甚麼事情都不做
		// 落石碰到技能不做事包括護盾
		// 護盾碰到火冰毒電 會反彈 方像 direction變反相(+180)
		switch (anotherskill.skillnum) {
		case 0:
			anotherskill.direction = Direction(anotherskill.x, anotherskill.y, 1);
			anotherskill.whocast = whocast;
			break;
		case 1:
			anotherskill.direction = Direction(anotherskill.x, anotherskill.y, 1);
			anotherskill.whocast = whocast;
			break;
		case 2:
			break;
		case 3:
			anotherskill.direction = Direction(anotherskill.x, anotherskill.y, 1);
			anotherskill.whocast = whocast;
			break;
		case 4:
			anotherskill.direction = Direction(anotherskill.x, anotherskill.y, 1);
			anotherskill.whocast = whocast;
			break;
		case 5:
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
		character.getImpacked(this.collisondistance, -Direction(character.getX(),character.getY(),1),
				this.collisonspeed);
		character.getDamage((level - 1) * increasedamage + basedamage,this.whocast);

	}
	public int getdrawx() {
		return (int) x-30;

	}

	// 暫時不用寫 等圖片
	public int getdrawy() {

		return (int) y-50;

	}
}