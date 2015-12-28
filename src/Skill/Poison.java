package Skill;

import WarOfMage.Character;
import WarOfMage.GIF;
import WarOfMage.PaintBoard;

public class Poison extends BaseSkill {
	PaintBoard paintboard;

	public Poison(PaintBoard a, int castnum, double x, double y, int level) {
		this.level = level;

		this.whocast = castnum;
		this.skillnum = 4;
		this.paintboard = a;
		this.basedamage = 5;
		this.increasedamage = 5;
		this.castspeed=10000;
		this.radius = 28;
		this.cooltime=5000;
		if (castnum != -1) {
			this.castcharacter = paintboard.allcharacter.get(castnum);
			this.castcharacter.inCoolDown(this.skillnum, this.cooltime);
			gif = new GIF("PoisonBall", a, true);
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
		String describe = "��g�@�Ӭr�y�����w��V�A�I���쪺�ĤH�|���r5���A�åB�y���C��" + damage + "�I�ˮ`�C";
		if (level == 0) {
			describe = "�|���ǲߦ��ޯ�C";
		}
		return describe;
	}

	public String getSkillName() {
		return "�r�y�N";
	}

	public void move() {
		this.x = this.x + Math.cos(Math.toRadians(this.direction))
				* this.castspeed / 1000;
		this.y = this.y + Math.sin(Math.toRadians(this.direction))
				* this.castspeed / 1000;
		if(x<0 ||x>40000 ||y<0 || y>40000){
			this.visible=false;
		}
	}

	public void collison(BaseSkill anotherskill) {
		// ���B�r�q �ޯब�I���ɭ� ���䪽������ ��baseskill��skillnum�h�P�_�I���ƻ�ޯ�
		// �I���L���ƻ�Ʊ�������
		// ���۸I��ޯण���ƥ]�A�@��
		// �@�޸I����B�r�q �|�ϼu �蹳 direction�ܤϬ�(+180)

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
			direction = Direction(anotherskill.x, anotherskill.y, 1)+180;
			this.whocast = anotherskill.whocast;
			break;
		default:
			break;
		}

	}

	// �I��H���ɭ�
	public void collison(Character character) {
		// �@�޸��ۤ��B�r�q �N�̾ڧޯ�ĪG�h�I�scharacter�̭����禡
		// �@�޸��ۤ��ή���
		paintboard.music.playHitSe();
		character.getPoisoned(basedamage + (level - 1) * this.increasedamage,
				5000,this.whocast);
	
		visible = false;
	}
}