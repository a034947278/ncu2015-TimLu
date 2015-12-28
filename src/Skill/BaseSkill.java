package Skill;

import java.awt.Image;

import WarOfMage.*;
import WarOfMage.Character;

public class BaseSkill {
	// �o�g�H����

	Character castcharacter;
	// paintboard
	PaintBoard paintboard;
	// �O�_�ݪ��� OR �s�b
	public boolean visible = true;
	public GIF gif;
	// ���ۥ�
	public boolean isCollisonAble = false;
	public boolean alreadyCollison = false;
	int level;
	// �ޯ�y��
	public double x;
	public double y;
	// �ޯู�X
	public int skillnum;
	// ��¦�ˮ`
	int basedamage = 10;
	// ���żW�[�ˮ`
	int increasedamage = 5;

	// �I���b�| �Ȯɤ��μg ���Ϥ�
	int radius = 28;

	// ��� �@��

	// ��¦�t�����A�ɶ� 1000=1��
	int debuffsec;
	// ���żW�[�t�����A�ɶ�
	int increasedebuffsec;

	// ��¦���A�ɶ� 1000=1��
	int buffsec;
	// ���żW�[���A�ɶ�
	int increasebuffsec;

	// �w�t�ʤ��� �w�t40%=40
	int decreasespeed;

	// �N�o�ɶ�
	int cooltime = 0;

	// �I���u���Z��
	int collisondistance;
	// �I���u���t��
	int collisonspeed;

	// �ޯ�o�g�t��
	int castspeed = 1000;
	// �ޯ�o�g�Z��
	int castdistance = 75;

	// �X���ϥγo�ӧޯ�
	public int whocast;

	// �k��0 �W��90 ����180 �U��270
	public double direction;

	// �̤j����
	public int maxlevel = 10;

	public BaseSkill() {

	}

	public String getSkillDescribe(int level) {
		return "";
	}

	public String getSkillName() {
		return null;
	}

	// �y�����ˮ`
	public int getDamage() {
		return 1;
	}

	// �I���᪺�ĪG///////////////////////////////////////
	public void collison(BaseSkill anotherskill) {
		// ���B�r�q �ޯब�I���ɭ� ���䪽������ ��baseskill��skillnum�h�P�_�I���ƻ�ޯ�
		// �I���L���ƻ�Ʊ�������
		// ���۸I��ޯण���ƥ]�A�@��
		// �@�޸I����B�r�q �|�ϼu �蹳 direction�ܤϬ�(+180)

	}

	// �I��H���ɭ�
	public void collison(Character anotherskill) {
		// �@�޸��ۤ��B�r�q �N�̾ڧޯ�ĪG�h�I�scharacter�̭����禡
		// �@�޸��ۤ��ή���

	}

	public void move() {
		// �B���r�p�i�Ѧҧ�character�� �I������
		// �@�ޫh�Oxy = ���⪺xy
		// ���ۤ���

	}

	public double Direction(double x, double y) {// direction������
		double direction = 0;

		direction = Math.atan2(y - 310, x - 640) / Math.PI * 180;

		return direction;
	}

	public double Direction(double xx, double yy, int i) {// direction������
		double direction = 0;

		direction = Math.atan2(yy - y, xx - x) / Math.PI * 180;

		return direction;
	}

	public Image getImage() {
		// ���μg
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

	// �Ȯɤ��μg ���Ϥ�
	public int getdrawx() {
		return (int) x;

	}

	// �Ȯɤ��μg ���Ϥ�
	public int getdrawy() {

		return (int) y;

	}

	// �Ȯɤ��μg ���Ϥ�
	public int getradius() {
		return radius;
	}
}
