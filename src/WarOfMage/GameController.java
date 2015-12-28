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
	// 腳色IP 對應腳色NUM表
	// ArrayList<String> characternumtoip = new ArrayList<>();
	HashMap<String, String> porttocharacternum = new HashMap<String, String>();
	int selfnum = 0;

	public GameController(PaintBoard a) {
		paintboard = a;
	}

	// IP轉換腳色NUM函式
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

	// 刪除腳色IP對應NUM表 指定腳色
	/*
	 * public void deleteIpInCharacterNumToIp(int i) {
	 * 
	 * this.characternumtoip.remove(i); }
	 */

	// 刪除腳色IP對應NUM表 指定腳色
	public void deleteCharacterNumInPortToCharacterNum(String key) {
		int deletenum = Integer.parseInt(this.porttocharacternum.get(key));

		this.porttocharacternum.remove(key);

		// 後面往前
		for (String keyy : porttocharacternum.keySet()) {
			int value = Integer.parseInt(this.porttocharacternum.get(keyy));
			if (value > deletenum) {

				this.porttocharacternum.put(keyy, (value - 1) + "");
			}
		}

	}

	// 傳送新增使用者要求給主機端
	public void deliverCharacterName(String name) {

		String data = "";

		data = this.paintboard.net.port + ",gamecontroller,新增使用者," + name;
		// System.out.println(data);
		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// 傳送更新所有腳色給客戶端

	public void deliverAllCharacterName() {

		String data = "";

		// 把所有腳色名稱組合傳送 ex name1,name2,name3
		for (int i = 0; i < this.paintboard.allcharacter.size(); i++) {
			if (i != this.paintboard.allcharacter.size() - 1) {
				data = data + this.paintboard.allcharacter.get(i).name + ";";
			} else {
				data = data + this.paintboard.allcharacter.get(i).name;
			}
		}

		data = this.paintboard.net.port + ",gamecontroller,更新所有使用者," + data;
		// System.out.println(data);

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// 更新所有使用者
	public void updateAllCharacterName(String data) {
		String analysis[] = { "", "", "", "", "", "", "", "" };
		analysis = data.split(";");
		/*
		 * for (int i = 0; i < 8; i++) { // System.out.println(analysis[i]); }
		 */

		for (int i = 0; i < analysis.length; i++) {
			// 如果沒有就新增腳色
			if (paintboard.allcharacter.size() - 1 < i) {
				addCharacter(analysis[i]);
			} else if (analysis.length == paintboard.allcharacter.size() - 1) {
				// 如果腳色減少 則移除最後一個腳色
				paintboard.allcharacter
						.remove(paintboard.allcharacter.size() - 1);
				//System.out.println("更新腳色列表:移除最後一個腳色");
			} else {
				// 更新所有腳色名稱 順序
				paintboard.allcharacter.get(i).name = analysis[i];
			}

		}
	}

	// 刪除斷線腳色 並且 更新所有腳色
	public void deleteCharacter(int num) {
		paintboard.allcharacter.remove(num);
		deliverAllCharacterName();
	}

	// 新增腳色
	public void addCharacter(String name) {

		paintboard.allcharacter.add(new Character(name, paintboard));

	}

	// 大廳斷線行為
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

	// 傳送所有設定給客戶端更新
	public void deliverUpdateClientGameSetting() {

		String data = "";

		// 把所有遊戲設定組合

		data = paintboard.lobbymenu.mapinput.getData() + ";";
		data = data + paintboard.lobbymenu.levelinput.getData() + ";";
		data = data + paintboard.lobbymenu.buytimeinput.getData() + ";";
		data = data + paintboard.lobbymenu.moneyinput.getData();

		data = this.paintboard.net.port + ",gamecontroller,更新所有遊戲設定," + data;
		// System.out.println(data);

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// 傳送更新本地NUM
	public void deliverUpdateClientNum(String disconnectednum) {

		String data = "";

		data = this.paintboard.net.port + ",gamecontroller,更新本地num,"
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

	// 更新所有設定

	public void updataAllGameSetting(String data) {

		String analysis[] = { "", "", "", "" };
		analysis = data.split(";");

		paintboard.lobbymenu.mapinput.data = analysis[0];
		paintboard.lobbymenu.levelinput.data = analysis[1];
		paintboard.lobbymenu.buytimeinput.data = analysis[2];
		paintboard.lobbymenu.moneyinput.data = analysis[3];
		//System.out.println("更新所有遊戲設定");
	}

	public void updatePortAndNum(String data) {
		String analysis[] = { "", "" };
		analysis = data.split(";");
		this.paintboard.net.port = analysis[0];
		this.selfnum = Integer.parseInt(analysis[1]);

	}

	// //////////////////////buy skill model///////////////////////
	public void deliverStartBuy() {

		String data = "裝飾用";
		// 發送開始購物請求

		data = this.paintboard.net.port + ",gamecontroller,開始購買," + data;
		// System.out.println("開始購買");

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	public void deliverBuy(String name) {

		String data = name;

		// 把要買的東西組合

		data = this.paintboard.net.port + ",gamecontroller,買東西," + data;
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
		// 伺服器端未做滿等判斷 可做雙重檢查

		// 判斷cost
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
		// 傳送 買的東西;誰買的;多少前
		data = buyname + ";";
		data = data + num + ";";
		data = data + cost;

		data = this.paintboard.net.port + ",gamecontroller,購買成功," + data;
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

		// array int 擁有技能 index 0=火球 value=技能等級
		// 1=冰球 2=石頭 3=電 4=毒 5=光
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
				//.println("第" + number + "號玩家成功購買" + analysis[0] + "並且更新完對應設定");
	}

	// /////////////////////////////////////////////////////////////
	// //////////////////////////////////遊戲model用///////////////////////////////////////////////////

	public void deliverStartGame() {

		String data = "裝飾用";
		// 發送開始購物請求

		data = this.paintboard.net.port + ",gamecontroller,開始遊戲," + data;
		// System.out.println("開始購買");
		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// 傳送更新本地指定腳色num,新座標,hp,是否死亡 方向
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

		data = this.paintboard.net.port + ",gamecontroller,更新本地腳色遊戲狀態," + data;
		// System.out.println(data);

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// 本地指定腳色num,新座標,hp,是否死亡
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

	// 傳送更新本地指定技能num,新座標,isvisible,gif isstop
	public void deliverUpdateClientGameSkillState(int num, double x, double y,
			double direction, boolean isvisible, boolean isStop) {

		String data = "";
		data = num + ";";
		data = data + x + ";";
		data = data + y + ";";
		data = data + direction + ";";
		data = data + isvisible + ";";
		data = data + isStop;

		data = this.paintboard.net.port + ",gamecontroller,更新本地腳色遊戲技能狀態,"
				+ data;
		// System.out.println(data);

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// 本地指定腳色num,新座標,hp,是否死亡
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
		// assert number <= this.paintboard.allskill.size() : "number超出邊界";

		if (number < this.paintboard.allskill.size()) {
			BaseSkill skill = this.paintboard.allskill.get(number);
			skill.x = x;
			skill.y = y;
			skill.gif.isStop = isStop;
			skill.visible = isvisible;
			skill.direction = direction;
		}
	}

	// //////////////////////////////////////////INPUT控制模組//////////////////////////////////////////////////////
	public void deliverUpdateGameMoveState(String direction, boolean isPress) {

		String data = "";
		data = direction + ";";
		data = data + isPress;
		data = this.paintboard.net.port + ",gamecontroller,更新伺服器遊戲移動狀態," + data;
		// System.out.println(data);

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// 本地指定腳色num,新座標,hp,是否死亡
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
		data = this.paintboard.net.port + ",gamecontroller,檢查角色技能CD," + data;
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
		//System.out.println("檢查角色技能CD");
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
		data = this.paintboard.net.port + ",gamecontroller,新增技能到技能列表," + data;
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

	// ////////////結算用/////////////////////
	public void deliverStartScoreMenu() {

		String data = "裝飾用";
		// 發送開始購物請求

		data = this.paintboard.net.port + ",gamecontroller,結算遊戲," + data;
		// System.out.println("開始購買");

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// 傳送更新本地指定技能num,新座標,isvisible,gif isstop
	public void deliverScoreState(int num, int kill, int damage) {

		String data = "";
		data = num + ";";
		data = data + kill + ";";
		data = data + damage;

		data = this.paintboard.net.port + ",gamecontroller,更新分數狀態," + data;
		// System.out.println(data);

		try {
			paintboard.net.TCPDeliver(data);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	// 本地指定腳色num,新座標,hp,是否死亡
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
	// 分析要求
	public void Analysis(String numorport, String topic, String data) {
		switch (topic) {
		case "新增使用者":
			if (paintboard.net.isServer) {

				// 如果是server 新增使用者 客戶端不做事
				this.addCharacter(data);
				// 再傳送更新所有使用者要求給所有客戶端
				deliverAllCharacterName();
				// 再傳送更新所有使用者遊戲設定要求
				this.deliverUpdateClientGameSetting();
			}
			break;
		case "更新所有使用者":
			if (!paintboard.net.isServer) {
				// 客戶端做更新所有使用者 伺服器端不做事
				updateAllCharacterName(data);
			}
			break;
		case "更新所有遊戲設定":
			if (!paintboard.net.isServer) {
				// 客戶端做更新所有使用者 伺服器端不做事
				this.updataAllGameSetting(data);
			}
			break;
		case "更新本地PORTandNum":
			this.updatePortAndNum(data);
			break;
		case "更新本地num":
			this.updateClientNum(data);
			break;
		case "買東西":
			if (paintboard.net.isServer)
				this.determineCharacterCanAffort(numorport, data);
			break;
		case "購買成功":
			updataCharacterSkillAndAbility(data);
			break;
		case "開始購買":
			int buytime = Integer
					.valueOf(this.paintboard.lobbymenu.buytimeinput.getData());
			this.paintboard.shopmenu.startShop(buytime);
			break;
		case "開始遊戲":
			if (!paintboard.net.isServer) {
				this.paintboard.gamemodel.ClientStartGame();
			}
			break;
		case "更新本地腳色遊戲狀態":
			if (!paintboard.net.isServer) {
				this.updateClientGameState(data);
			}
			break;
		case "更新本地腳色遊戲技能狀態":
			if (!paintboard.net.isServer) {
				this.updateClientGameSkillState(data);
			}
			break;
		case "更新伺服器遊戲移動狀態":
			this.updateServerGameMoveState(numorport, data);
			break;
		case "檢查角色技能CD":
			if (paintboard.net.isServer) {
				this.checkCharacterSkillCastAble(numorport, data);
			}
			break;
		case "新增技能到技能列表":
			this.addNewSkillToSkillList(data);
			break;
		case "結算遊戲":
			this.paintboard.scoremenu.StartScoreMenu();
			break;
		case "更新分數狀態":
			if (!paintboard.net.isServer) {
				this.updateScoreState(data);
			}
		default:

		}
	}
}
