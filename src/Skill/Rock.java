package Skill;

import WarOfMage.Character;
import WarOfMage.GIF;
import WarOfMage.PaintBoard;

public class Rock extends BaseSkill {
	PaintBoard paintboard;

	/*
	 * x y 是滑鼠的做標 castnum是發射人的num 依據 castcharacter 的x y 去 初始化技能的xy 根據這兩個去得知方向是多少
	 * 然後在以人物座標 畫圓心 以技能施放距離當半徑 滑鼠座標與人物座標 的連線距離 大於施放距離 則以圓上的點為xy 距離內則以滑鼠座標為xy
	 * 
	 * 
	 * 其他請根據技能需求去初始化
	 */

	public Rock(PaintBoard a, int castnum, double x, double y, int level) {
		this.level = level;

		this.whocast = castnum;
		this.skillnum = 2;
		this.paintboard = a;
		this.increasedamage=25;
		this.basedamage = 10;
		this.debuffsec = 1000;
		this.decreasespeed = 90;
		this.collisondistance=1500;
		this.collisonspeed=8000;
		this.radius=80;
		this.cooltime=5000;
		if (castnum != -1) {
			this.castcharacter = paintboard.allcharacter.get(castnum);
			this.castcharacter.inCoolDown(this.skillnum, this.cooltime);
			gif = new GIF("Rock", a, false);
			this.x = castcharacter.getX() + x - 640;
			this.y = castcharacter.getY() + y - 360;
		}

	}

	public String getSkillDescribe(int level) {
		int damage = (level - 1) * increasedamage + basedamage;
		String describe = "施放一個落石在指定地點，碰撞到敵人會以圓心彈飛，並且造成" + damage + "點傷害與"
				+ decreasespeed + "%的緩速" + debuffsec + "秒。";
		if (level == 0) {
			describe = "尚未學習此技能。";
		}
		return describe;
	}

	public String getSkillName() {
		return "落石術";
	}

	public void move(double x, double y) {

	}

	public void collison(BaseSkill anotherskill) {
		// 火冰毒電 技能互碰的時候 兩邊直接消失 用baseskill的skillnum去判斷碰撞甚麼技能
		// 碰到其他的甚麼事情都不做
		// 落石碰到技能不做事包括護盾
		// 護盾碰到火冰毒電 會反彈 方像 direction變反相(+180)

		switch (anotherskill.skillnum) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
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
		if (this.isCollisonAble) {
			paintboard.music.playHitSe();
			character.getImpacked(this.collisondistance,
					Direction(character.getX(), character.getY(), 1),
					this.collisonspeed);
			character.getSlowSpeed(decreasespeed, debuffsec);
			character.getDamage((level - 1) * increasedamage + basedamage,this.whocast);
		}


	}

	public int getdrawx() {
		return (int) x - 200;

	}

	// 暫時不用寫 等圖片
	public int getdrawy() {

		return (int) y - 170;

	}

}
