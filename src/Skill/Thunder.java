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
		String describe = "��g�@�ӹq�y�����w��V�A�I����ĤH�|�w�t" + sec + "��A�åB�y��" + damage
				+ "�I�ˮ`�C";
		if (level == 0) {
			describe = "�|���ǲߦ��ޯ�C";
		}
		return describe;
	}

	public String getSkillName() {
		return "�p�y�N";
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
			direction = Direction(anotherskill.x, anotherskill.y, 1) + 180;
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
		character.getStun(debuffsec + increasedebuffsec * (level - 1));
		character.getDamage((level - 1) * increasedamage + basedamage,this.whocast);

		visible = false;
	}
	
	public int getdrawx() {
		return (int) x ;

	}

	// �Ȯɤ��μg ���Ϥ�
	public int getdrawy() {

		return (int) y +5;

	}
}
