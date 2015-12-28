package Skill;

import WarOfMage.Character;
import WarOfMage.GIF;
import WarOfMage.PaintBoard;

public class Light extends BaseSkill {

	/*
	 * ��l�� ����y��=�ޯ�y��
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
		String describe = "�}�Ҥ@�ӵL�Ĩ��@�n�b�ۨ��P��" + sec + "��A�I����ĤH�|�y�L�u���A�åB�y��" + damage
				+ "�I�ˮ`�C";
		if (level == 0) {
			describe = "�|���ǲߦ��ޯ�C";
		}
		return describe;
	}

	public String getSkillName() {
		return "�t�s�N";
	}

	public void move() {
		this.x = this.castcharacter.getX();
		this.y = this.castcharacter.getY()-50;
	}

	public void collison(BaseSkill anotherskill) {
		// ���B�r�q �ޯब�I���ɭ� ���䪽������ ��baseskill��skillnum�h�P�_�I���ƻ�ޯ�
		// �I���L���ƻ�Ʊ�������
		// ���۸I��ޯण���ƥ]�A�@��
		// �@�޸I����B�r�q �|�ϼu �蹳 direction�ܤϬ�(+180)
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

	// �I��H���ɭ�
	public void collison(Character character) {
		// �@�޸��ۤ��B�r�q �N�̾ڧޯ�ĪG�h�I�scharacter�̭����禡
		// �@�޸��ۤ��ή���
		paintboard.music.playHitSe();
		character.getImpacked(this.collisondistance, -Direction(character.getX(),character.getY(),1),
				this.collisonspeed);
		character.getDamage((level - 1) * increasedamage + basedamage,this.whocast);

	}
	public int getdrawx() {
		return (int) x-30;

	}

	// �Ȯɤ��μg ���Ϥ�
	public int getdrawy() {

		return (int) y-50;

	}
}