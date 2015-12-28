package Skill;

import WarOfMage.Character;
import WarOfMage.GIF;
import WarOfMage.PaintBoard;

public class Rock extends BaseSkill {
	PaintBoard paintboard;

	/*
	 * x y �O�ƹ������� castnum�O�o�g�H��num �̾� castcharacter ��x y �h ��l�Ƨޯ઺xy �ھڳo��ӥh�o����V�O�h��
	 * �M��b�H�H���y�� �e��� �H�ޯ�I��Z����b�| �ƹ��y�лP�H���y�� ���s�u�Z�� �j��I��Z�� �h�H��W���I��xy �Z�����h�H�ƹ��y�Ь�xy
	 * 
	 * 
	 * ��L�Юھڧޯ�ݨD�h��l��
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
		String describe = "�I��@�Ӹ��ۦb���w�a�I�A�I����ĤH�|�H��߼u���A�åB�y��" + damage + "�I�ˮ`�P"
				+ decreasespeed + "%���w�t" + debuffsec + "��C";
		if (level == 0) {
			describe = "�|���ǲߦ��ޯ�C";
		}
		return describe;
	}

	public String getSkillName() {
		return "���۳N";
	}

	public void move(double x, double y) {

	}

	public void collison(BaseSkill anotherskill) {
		// ���B�r�q �ޯब�I���ɭ� ���䪽������ ��baseskill��skillnum�h�P�_�I���ƻ�ޯ�
		// �I���L���ƻ�Ʊ�������
		// ���۸I��ޯण���ƥ]�A�@��
		// �@�޸I����B�r�q �|�ϼu �蹳 direction�ܤϬ�(+180)

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

	// �I��H���ɭ�
	public void collison(Character character) {
		// �@�޸��ۤ��B�r�q �N�̾ڧޯ�ĪG�h�I�scharacter�̭����禡
		// �@�޸��ۤ��ή���
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

	// �Ȯɤ��μg ���Ϥ�
	public int getdrawy() {

		return (int) y - 170;

	}

}
