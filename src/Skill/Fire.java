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
	 * x y �O�ƹ������� castnum�O�o�g�H��num �̾� castcharacter ��x y �h ��l�Ƨޯ઺xy �ھڳo��ӥh�o����V�O�h��
	 * �M��b�H�H���y�� �e��� �H�ޯ�I��Z����b�| �ƹ��y�лP�H���y�� ���u �P ��W�����I�y�Ь�xy �i�ζZ�����V�h��scale �B���r�p�ҬۦP
	 * 
	 * ��L�Юھڧޯ�ݨD�h��l��
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
		String describe = "��g�@�Ӥ��y�����w��V�A�I����ĤH�|�u���A�åB�y��" + damage + "�I�ˮ`�C";
		if (level == 0) {
			describe = "�|���ǲߦ��ޯ�C";
		}
		return describe;
	}

	public String getSkillName() {
		return "���y�N";
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
			direction=Direction(anotherskill.x,anotherskill.y,1)+180;
			this.whocast=anotherskill.whocast;
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
		character.getImpacked(this.collisondistance,Direction(character.getX(), character.getY(), 1),
				this.collisonspeed);
		character.getDamage((level - 1) * increasedamage + basedamage,this.whocast);

		visible = false;
	}

}
