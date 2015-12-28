package WarOfMage;

import java.io.IOException;
import java.util.HashMap;

import Skill.BaseSkill;
import Skill.Fire;
import Skill.Ice;
import Skill.Light;
import Skill.Poison;
import Skill.Rock;
import Skill.Thunder;

public class GameController {
	PaintBoard paintboard;
	// �}��IP �����}��NUM��
	// ArrayList<String> characternumtoip = new ArrayList<>();
	HashMap<String, String> porttocharacternum = new HashMap<String, String>();
	int selfnum = 0;

	public GameController(PaintBoard a) {
		paintboard = a;
	}

	// IP�ഫ�}��NUM�禡
	public String portToUser(String port) {
		return this.porttocharacternum.get(port);
		/*
		 * for (int i = 0; i < characternumtoip.size(); i++) {
		 * 
		 * if (this.characternumtoip.get(i).equals(ip)) { ip =
		 * String.valueOf(i);
		 * 
		 * }
		 * 
		 * } return ip;
		 */
	}

	public String UserToPort(String usernum) {
		String port = "";
		for (String key : porttocharacternum.keySet()) {
			if (this.porttocharacternum.get(key) == usernum) {
				port = key;
			}
		}
		return port;
	}

	// �R���}��IP����NUM�� ���w�}��
	/*
	 * public void deleteIpInCharacterNumToIp(int i) {
	 * 
	 * this.characternumtoip.remove(i); }
	 */

	// �R���}��IP����NUM�� ���w�}��
	public void deleteCharacterNumInPortToCharacterNum(String key) {
		int deletenum = Integer.parseInt(this.porttocharacternum.get(key));

		this.porttocharacternum.remove(key);

		// �᭱���e
		for (String keyy : porttocharacternum.keySet()) {
			int value = Integer.parseInt(this.porttocharacternum.get(keyy));
			if (value > deletenum) {

				this.porttocharacternum.put(keyy, (value - 1) + "");
			}
		}

	}

	// �ǰe�s�W�ϥΪ̭n�D���D����
	public void deliverCharacterName(String name) {

		String data = "";

		data = this.paintboard.net.port + ",gamecontroller,�s�W�ϥΪ�," + name;
		// System.out.println(data);
		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// �ǰe��s�Ҧ��}�⵹�Ȥ��

	public void deliverAllCharacterName() {

		String data = "";

		// ��Ҧ��}��W�ٲզX�ǰe ex name1,name2,name3
		for (int i = 0; i < this.paintboard.allcharacter.size(); i++) {
			if (i != this.paintboard.allcharacter.size() - 1) {
				data = data + this.paintboard.allcharacter.get(i).name + ";";
			} else {
				data = data + this.paintboard.allcharacter.get(i).name;
			}
		}

		data = this.paintboard.net.port + ",gamecontroller,��s�Ҧ��ϥΪ�," + data;
		// System.out.println(data);

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// ��s�Ҧ��ϥΪ�
	public void updateAllCharacterName(String data) {
		String analysis[] = { "", "", "", "", "", "", "", "" };
		analysis = data.split(";");
		/*
		 * for (int i = 0; i < 8; i++) { // System.out.println(analysis[i]); }
		 */

		for (int i = 0; i < analysis.length; i++) {
			// �p�G�S���N�s�W�}��
			if (paintboard.allcharacter.size() - 1 < i) {
				addCharacter(analysis[i]);
			} else if (analysis.length == paintboard.allcharacter.size() - 1) {
				// �p�G�}���� �h�����̫�@�Ӹ}��
				paintboard.allcharacter
						.remove(paintboard.allcharacter.size() - 1);
				//System.out.println("��s�}��C��:�����̫�@�Ӹ}��");
			} else {
				// ��s�Ҧ��}��W�� ����
				paintboard.allcharacter.get(i).name = analysis[i];
			}

		}
	}

	// �R���_�u�}�� �åB ��s�Ҧ��}��
	public void deleteCharacter(int num) {
		paintboard.allcharacter.remove(num);
		deliverAllCharacterName();
	}

	// �s�W�}��
	public void addCharacter(String name) {

		paintboard.allcharacter.add(new Character(name, paintboard));

	}

	// �j�U�_�u�欰
	public void whenServerDisconnected() {
		paintboard.lobbymenu.chatinput.data = "";
		paintboard.lobbymenu.mapinput.initial();
		paintboard.lobbymenu.levelinput.initial();
		paintboard.lobbymenu.moneyinput.initial();
		paintboard.lobbymenu.buytimeinput.initial();
		paintboard.lobbymenu.chat.cleanMessage();
		paintboard.allcharacter.clear();
		paintboard.scene = 0;
		paintboard.startmenu.scene = 0;
	}

	// �ǰe�Ҧ��]�w���Ȥ�ݧ�s
	public void deliverUpdateClientGameSetting() {

		String data = "";

		// ��Ҧ��C���]�w�զX

		data = paintboard.lobbymenu.mapinput.getData() + ";";
		data = data + paintboard.lobbymenu.levelinput.getData() + ";";
		data = data + paintboard.lobbymenu.buytimeinput.getData() + ";";
		data = data + paintboard.lobbymenu.moneyinput.getData();

		data = this.paintboard.net.port + ",gamecontroller,��s�Ҧ��C���]�w," + data;
		// System.out.println(data);

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// �ǰe��s���aNUM
	public void deliverUpdateClientNum(String disconnectednum) {

		String data = "";

		data = this.paintboard.net.port + ",gamecontroller,��s���anum,"
				+ disconnectednum;
		// System.out.println(data);

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	public void updateClientNum(String disconnectednum) {
		int num = Integer.valueOf(disconnectednum);
		if (this.selfnum > num) {
			this.selfnum = this.selfnum - 1;
		}

	}

	// ��s�Ҧ��]�w

	public void updataAllGameSetting(String data) {

		String analysis[] = { "", "", "", "" };
		analysis = data.split(";");

		paintboard.lobbymenu.mapinput.data = analysis[0];
		paintboard.lobbymenu.levelinput.data = analysis[1];
		paintboard.lobbymenu.buytimeinput.data = analysis[2];
		paintboard.lobbymenu.moneyinput.data = analysis[3];
		//System.out.println("��s�Ҧ��C���]�w");
	}

	public void updatePortAndNum(String data) {
		String analysis[] = { "", "" };
		analysis = data.split(";");
		this.paintboard.net.port = analysis[0];
		this.selfnum = Integer.parseInt(analysis[1]);

	}

	// //////////////////////buy skill model///////////////////////
	public void deliverStartBuy() {

		String data = "�˹���";
		// �o�e�}�l�ʪ��ШD

		data = this.paintboard.net.port + ",gamecontroller,�}�l�ʶR," + data;
		// System.out.println("�}�l�ʶR");

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	public void deliverBuy(String name) {

		String data = name;

		// ��n�R���F��զX

		data = this.paintboard.net.port + ",gamecontroller,�R�F��," + data;
		// System.out.println(data);

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	public void determineCharacterCanAffort(String num, String objectname) {
		int cost = 0;
		int number = Integer.valueOf(num);
		// ���A���ݥ��������P�_ �i�������ˬd

		// �P�_cost
		int skillnum = -1;
		switch (objectname) {
		case "hp":
			cost = 1000;
			break;
		case "fire":
			cost = 1000;
			skillnum = 0;
			break;
		case "ice":
			skillnum = 1;
			cost = 1000;
			break;
		case "rock":
			skillnum = 2;
			cost = 1000;
			break;
		case "thunder":
			skillnum = 3;
			cost = 1000;
			break;
		case "poison":
			skillnum = 4;
			cost = 1000;
			break;
		case "light":
			skillnum = 5;
			cost = 1000;
			break;
		case "speed":
			cost = 500;
			break;
		default:
		}
		Character character = paintboard.allcharacter.get(number);
		if (character.money >= cost && character.money > 0) {
			// paintboard.allcharacter.get(number).money =
			// paintboard.allcharacter
			// .get(number).money - cost;

			if (skillnum >= 0 && character.skill.get(skillnum) == 10
					|| (skillnum == 5 && character.skill.get(skillnum) == 5)) {

			} else {
				if (objectname.equals("speed") && character.baseRunSpeed >= 200) {
				} else {
					this.deliverCharacterSkillAndAbility(objectname, num, cost);
				}
			}
		}

	}

	public void deliverCharacterSkillAndAbility(String buyname, String num,
			int cost) {

		String data;
		// �ǰe �R���F��;�ֶR��;�h�֫e
		data = buyname + ";";
		data = data + num + ";";
		data = data + cost;

		data = this.paintboard.net.port + ",gamecontroller,�ʶR���\," + data;
		// System.out.println(data);

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	public void updataCharacterSkillAndAbility(String data) {

		String analysis[] = { "", "", "" };
		analysis = data.split(";");
		String objectname = analysis[0];
		int number = Integer.valueOf(analysis[1]);
		int cost = Integer.valueOf(analysis[2]);

		paintboard.allcharacter.get(number).money = paintboard.allcharacter
				.get(number).money - cost;

		// array int �֦��ޯ� index 0=���y value=�ޯ൥��
		// 1=�B�y 2=���Y 3=�q 4=�r 5=��
		switch (objectname) {
		case "hp":
			paintboard.allcharacter.get(number).maxhp = paintboard.allcharacter
					.get(number).maxhp + 30;
			break;
		case "fire":
			paintboard.allcharacter.get(number).skill.set(0,
					paintboard.allcharacter.get(number).skill.get(0) + 1);
			break;
		case "ice":
			paintboard.allcharacter.get(number).skill.set(1,
					paintboard.allcharacter.get(number).skill.get(1) + 1);
			break;
		case "rock":
			paintboard.allcharacter.get(number).skill.set(2,
					paintboard.allcharacter.get(number).skill.get(2) + 1);
			break;
		case "thunder":
			paintboard.allcharacter.get(number).skill.set(3,
					paintboard.allcharacter.get(number).skill.get(3) + 1);
			break;
		case "poison":
			paintboard.allcharacter.get(number).skill.set(4,
					paintboard.allcharacter.get(number).skill.get(4) + 1);
			break;
		case "light":
			paintboard.allcharacter.get(number).skill.set(5,
					paintboard.allcharacter.get(number).skill.get(5) + 1);
			break;
		case "speed":
			paintboard.allcharacter.get(number).baseRunSpeed = paintboard.allcharacter
					.get(number).baseRunSpeed + 5;
			break;
		default:
		}

		//System.out
				//.println("��" + number + "�����a���\�ʶR" + analysis[0] + "�åB��s�������]�w");
	}

	// /////////////////////////////////////////////////////////////
	// //////////////////////////////////�C��model��///////////////////////////////////////////////////

	public void deliverStartGame() {

		String data = "�˹���";
		// �o�e�}�l�ʪ��ШD

		data = this.paintboard.net.port + ",gamecontroller,�}�l�C��," + data;
		// System.out.println("�}�l�ʶR");
		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// �ǰe��s���a���w�}��num,�s�y��,hp,�O�_���` ��V
	public void deliverUpdateClientGameState(String num, int x, int y, int hp,
			boolean isDead, String Direction, int money) {

		String data = "";
		data = num + ";";
		data = data + x + ";";
		data = data + y + ";";
		data = data + hp + ";";
		data = data + isDead + ";";
		data = data + Direction + ";";
		data = data + money;

		data = this.paintboard.net.port + ",gamecontroller,��s���a�}��C�����A," + data;
		// System.out.println(data);

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// ���a���w�}��num,�s�y��,hp,�O�_���`
	public void updateClientGameState(String data) {
		String analysis[] = { "", "", "", "", "", "" };
		analysis = data.split(";");
		int number = Integer.valueOf(analysis[0]);
		int x = Integer.valueOf(analysis[1]);
		int y = Integer.valueOf(analysis[2]);
		int hp = Integer.valueOf(analysis[3]);
		int money = Integer.valueOf(analysis[6]);
		boolean isDead;
		if (analysis[4].equals("true")) {
			isDead = true;
		} else {
			isDead = false;
		}
		String direction = analysis[5];
		Character character = this.paintboard.allcharacter.get(number);
		character.hp = hp;
		character.x = x;
		character.y = y;
		character.isDead = isDead;
		character.direction = direction;
		character.money = money;
	}

	// �ǰe��s���a���w�ޯ�num,�s�y��,isvisible,gif isstop
	public void deliverUpdateClientGameSkillState(int num, double x, double y,
			double direction, boolean isvisible, boolean isStop) {

		String data = "";
		data = num + ";";
		data = data + x + ";";
		data = data + y + ";";
		data = data + direction + ";";
		data = data + isvisible + ";";
		data = data + isStop;

		data = this.paintboard.net.port + ",gamecontroller,��s���a�}��C���ޯબ�A,"
				+ data;
		// System.out.println(data);

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// ���a���w�}��num,�s�y��,hp,�O�_���`
	public void updateClientGameSkillState(String data) {
		String analysis[] = { "", "", "", "", "" };
		analysis = data.split(";");
		int number = Integer.valueOf(analysis[0]);
		double x = Double.valueOf(analysis[1]);
		double y = Double.valueOf(analysis[2]);
		boolean isvisible;
		boolean isStop;
		double direction = Double.valueOf(analysis[3]);
		if (analysis[4].equals("true")) {
			isvisible = true;
		} else {
			isvisible = false;
		}
		if (analysis[5].equals("true")) {
			isStop = true;
		} else {
			isStop = false;
		}
		// assert number <= this.paintboard.allskill.size() : "number�W�X���";

		if (number < this.paintboard.allskill.size()) {
			BaseSkill skill = this.paintboard.allskill.get(number);
			skill.x = x;
			skill.y = y;
			skill.gif.isStop = isStop;
			skill.visible = isvisible;
			skill.direction = direction;
		}
	}

	// //////////////////////////////////////////INPUT����Ҳ�//////////////////////////////////////////////////////
	public void deliverUpdateGameMoveState(String direction, boolean isPress) {

		String data = "";
		data = direction + ";";
		data = data + isPress;
		data = this.paintboard.net.port + ",gamecontroller,��s���A���C�����ʪ��A," + data;
		// System.out.println(data);

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// ���a���w�}��num,�s�y��,hp,�O�_���`
	public void updateServerGameMoveState(String num, String data) {
		int number = Integer.valueOf(num);
		String analysis[] = { "", "" };
		analysis = data.split(";");
		String direction = analysis[0];
		boolean isPress;
		if (analysis[1].equals("true")) {
			isPress = true;
		} else {
			isPress = false;
		}
		Character character = this.paintboard.allcharacter.get(number);
		// System.out.println(analysis[1]);
		// System.out.println(direction);
		// System.out.println(isPress);
		switch (direction) {
		case "up":
			if (isPress) {
				// System.out.println(2);
				character.upPress();
			} else
				character.upRelease();
			break;
		case "down":
			if (isPress)
				character.downPress();
			else
				character.downRelease();
			break;
		case "left":
			if (isPress)
				character.leftPress();
			else
				character.leftRelease();
			break;
		case "right":
			if (isPress)
				character.rightPress();
			else
				character.rightRelease();
			break;
		}

	}

	public void deliverCheckCharacterSkillCastAble(int x, int y, int skillnum) {

		String data = "";
		data = x + ";";
		data = data + y + ";";
		data = data + skillnum;
		data = this.paintboard.net.port + ",gamecontroller,�ˬd����ޯ�CD," + data;
		//System.out.println(data);

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void checkCharacterSkillCastAble(String num, String data) {
		int number = Integer.valueOf(num);
		String analysis[] = { "", "", "" };
		analysis = data.split(";");
		int x = Integer.valueOf(analysis[0]);
		int y = Integer.valueOf(analysis[1]);
		int skillnum = Integer.valueOf(analysis[2]);
		//System.out.println("�ˬd����ޯ�CD");
		Character character = this.paintboard.allcharacter.get(number);
		if (character.isSkillCastAble(skillnum) && !character.isDead) {
			deliverAddNewSkillToSkillList(number, x, y, skillnum);
		}

	}

	public void deliverAddNewSkillToSkillList(int whocastnum, int x, int y,
			int skillnum) {

		String data = "";
		data = whocastnum + ";";
		data = data + x + ";";
		data = data + y + ";";
		data = data + skillnum;
		data = this.paintboard.net.port + ",gamecontroller,�s�W�ޯ��ޯ�C��," + data;
	//	System.out.println(data);

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void addNewSkillToSkillList(String data) {
		String analysis[] = { "", "", "", "" };
		analysis = data.split(";");
		int whocastnum = Integer.valueOf(analysis[0]);
		int x = Integer.valueOf(analysis[1]);
		int y = Integer.valueOf(analysis[2]);
		int skillnum = Integer.valueOf(analysis[3]);
		Character character = paintboard.allcharacter.get(whocastnum);
		switch (skillnum) {
		case 0:
			paintboard.music.playSe(2);
			this.paintboard.allskill.add(new Fire(this.paintboard, whocastnum,
					x, y, character.skill.get(skillnum)));
			break;
		case 1:
			paintboard.music.playSe(2);
			this.paintboard.allskill.add(new Ice(this.paintboard, whocastnum,
					x, y, character.skill.get(skillnum)));
			// TODO
			break;
		case 2:
			// TODO
			this.paintboard.allskill.add(new Rock(this.paintboard, whocastnum,
					x, y, character.skill.get(skillnum)));
			break;
		case 3:
			// TODO
			paintboard.music.playSe(4);
			this.paintboard.allskill.add(new Thunder(this.paintboard,
					whocastnum, x, y, character.skill.get(skillnum)));
			break;
		case 4:
			// TODO
			paintboard.music.playSe(3);
			this.paintboard.allskill.add(new Poison(this.paintboard,
					whocastnum, x, y, character.skill.get(skillnum)));
			break;
		case 5:
			// TODO
			this.paintboard.allskill.add(new Light(this.paintboard, whocastnum,
					character.skill.get(skillnum)));
			break;
		}

	}

	// ////////////�����/////////////////////
	public void deliverStartScoreMenu() {

		String data = "�˹���";
		// �o�e�}�l�ʪ��ШD

		data = this.paintboard.net.port + ",gamecontroller,����C��," + data;
		// System.out.println("�}�l�ʶR");

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// �ǰe��s���a���w�ޯ�num,�s�y��,isvisible,gif isstop
	public void deliverScoreState(int num, int kill, int damage) {

		String data = "";
		data = num + ";";
		data = data + kill + ";";
		data = data + damage;

		data = this.paintboard.net.port + ",gamecontroller,��s���ƪ��A," + data;
		// System.out.println(data);

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// ���a���w�}��num,�s�y��,hp,�O�_���`
	public void updateScoreState(String data) {
		String analysis[] = { "", "", "" };
		analysis = data.split(";");
		int number = Integer.valueOf(analysis[0]);
		int kill = Integer.valueOf(analysis[1]);
		int damage = Integer.valueOf(analysis[2]);
		Character character = paintboard.allcharacter.get(number);
		character.kill = kill;
		character.totalDamge = damage;
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////
	// ���R�n�D
	public void Analysis(String numorport, String topic, String data) {
		switch (topic) {
		case "�s�W�ϥΪ�":
			if (paintboard.net.isServer) {

				// �p�G�Oserver �s�W�ϥΪ� �Ȥ�ݤ�����
				this.addCharacter(data);
				// �A�ǰe��s�Ҧ��ϥΪ̭n�D���Ҧ��Ȥ��
				deliverAllCharacterName();
				// �A�ǰe��s�Ҧ��ϥΪ̹C���]�w�n�D
				this.deliverUpdateClientGameSetting();
			}
			break;
		case "��s�Ҧ��ϥΪ�":
			if (!paintboard.net.isServer) {
				// �Ȥ�ݰ���s�Ҧ��ϥΪ� ���A���ݤ�����
				updateAllCharacterName(data);
			}
			break;
		case "��s�Ҧ��C���]�w":
			if (!paintboard.net.isServer) {
				// �Ȥ�ݰ���s�Ҧ��ϥΪ� ���A���ݤ�����
				this.updataAllGameSetting(data);
			}
			break;
		case "��s���aPORTandNum":
			this.updatePortAndNum(data);
			break;
		case "��s���anum":
			this.updateClientNum(data);
			break;
		case "�R�F��":
			if (paintboard.net.isServer)
				this.determineCharacterCanAffort(numorport, data);
			break;
		case "�ʶR���\":
			updataCharacterSkillAndAbility(data);
			break;
		case "�}�l�ʶR":
			int buytime = Integer
					.valueOf(this.paintboard.lobbymenu.buytimeinput.getData());
			this.paintboard.shopmenu.startShop(buytime);
			break;
		case "�}�l�C��":
			if (!paintboard.net.isServer) {
				this.paintboard.gamemodel.ClientStartGame();
			}
			break;
		case "��s���a�}��C�����A":
			if (!paintboard.net.isServer) {
				this.updateClientGameState(data);
			}
			break;
		case "��s���a�}��C���ޯબ�A":
			if (!paintboard.net.isServer) {
				this.updateClientGameSkillState(data);
			}
			break;
		case "��s���A���C�����ʪ��A":
			this.updateServerGameMoveState(numorport, data);
			break;
		case "�ˬd����ޯ�CD":
			if (paintboard.net.isServer) {
				this.checkCharacterSkillCastAble(numorport, data);
			}
			break;
		case "�s�W�ޯ��ޯ�C��":
			this.addNewSkillToSkillList(data);
			break;
		case "����C��":
			this.paintboard.scoremenu.StartScoreMenu();
			break;
		case "��s���ƪ��A":
			if (!paintboard.net.isServer) {
				this.updateScoreState(data);
			}
		default:

		}
	}
}
