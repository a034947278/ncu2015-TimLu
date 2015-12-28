package Skill;

import WarOfMage.GIF;
import WarOfMage.PaintBoard;
import WarOfMage.Character;

public class Fire extends BaseSkill {

	/*
	 * PaintBoard paintboard; int x; int y; int basedamage = 10; int
	 * increasedamage = 5; int debuffsec = 3; int decreasespeed = 40; int
	 * cooltime; public int maxlevel = 10;
	 */

	/*
	 * x y 是滑鼠的做標 castnum是發射人的num 依據 castcharacter 的x y 去 初始化技能的xy 根據這兩個去得知方向是多少
	 * 然後在以人物座標 畫圓心 以技能施放距離當半徑 滑鼠座標與人物座標 的線 與 圓上的交點座標為xy 可用距離跟方向去做scale 冰火毒雷皆相同
	 * 
	 * 其他請根據技能需求去初始化
	 */
	public Fire(PaintBoard a, int castnum, double x, double y, int level) {
		this.level = level;
		this.castdistance = 85;
		this.paintboard = a;
		this.radius=28;
		this.collisondistance=400;
		this.collisonspeed=2000;
		this.cooltime = 300;
		this.castspeed=3000;
		if (castnum != -1) {
			gif = new GIF("FireBall", a, true);
			this.castcharacter = paintboard.allcharacter.get(castnum);
			
			this.castcharacter.inCoolDown(this.skillnum, this.cooltime);
			this.direction = Direction(x, y);
			this.x = castcharacter.getX()
					+ Math.cos(Math.toRadians(this.direction))
					* this.castdistance;
			this.y = castcharacter.getY()
					+ Math.sin(Math.toRadians(this.direction))
					* this.castdistance - 50;
			
		}
		this.whocast = castnum;
		this.skillnum = 0;
		this.basedamage = 10;
		
		this.increasedamage = 5;

	}

	public String getSkillDescribe(int level) {
		int damage = (level - 1) * increasedamage + basedamage;
		String describe = "投射一個火球往指定方向，碰撞到敵人會彈飛，並且造成" + damage + "點傷害。";
		if (level == 0) {
			describe = "尚未學習此技能。";
		}
		return describe;
	}

	public String getSkillName() {
		return "火球術";
	}

	public void move() {
		
		this.x = this.x + Math.cos(Math.toRadians(this.direction))
				* this.castspeed/1000;
		this.y = this.y + Math.sin(Math.toRadians(this.direction))
				* this.castspeed/1000;
		if(x<0 ||x>40000 ||y<0 || y>40000){
			this.visible=false;
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
			direction=Direction(anotherskill.x,anotherskill.y,1)+180;
			this.whocast=anotherskill.whocast;
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
		character.getImpacked(this.collisondistance,Direction(character.getX(), character.getY(), 1),
				this.collisonspeed);
		character.getDamage((level - 1) * increasedamage + basedamage,this.whocast);

		visible = false;
	}

}
