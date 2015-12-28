package WarOfMage;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class InputController {
	PaintBoard paintboard;
	boolean skillpressable = true;

	InputController(PaintBoard a) {
		paintboard = a;
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		switch (paintboard.scene) {
		case 1:
			switch (key) {
			case KeyEvent.VK_ENTER:
				paintboard.lobbymenu.releaseEnter();
				break;
			default:
			}
			break;
		case 2:
			switch (key) {
			case KeyEvent.VK_ENTER:
				paintboard.shopmenu.releaseEnter();
				break;
			default:
			}
			break;
		case 3:
			switch (key) {
			case KeyEvent.VK_W:
				paintboard.gameview.releaseUp();
				// System.out.println("up");
				break;
			case KeyEvent.VK_S:
				paintboard.gameview.releaseDown();
				// System.out.println("down");
				break;
			case KeyEvent.VK_A:

				paintboard.gameview.releaseLeft();
				break;
			case KeyEvent.VK_D:
				paintboard.gameview.releaseRight();
				break;
			default:
			}
			break;
		default:
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		switch (paintboard.scene) {
		case 0:
			switch (key) {
			case KeyEvent.VK_ENTER:
				paintboard.startmenu.pressEnter();
				break;
			case KeyEvent.VK_UP:
				paintboard.startmenu.pressUp();
				break;
			case KeyEvent.VK_DOWN:
				paintboard.startmenu.pressDown();
				break;
			default:
			}
			break;
		case 1:
			switch (key) {
			case KeyEvent.VK_ENTER:
				paintboard.lobbymenu.pressEnter();
				break;
			case KeyEvent.VK_UP:
				paintboard.lobbymenu.pressUp();
				break;
			case KeyEvent.VK_DOWN:
				paintboard.lobbymenu.pressDown();
				break;
			default:
			}
			break;
		case 2:
			switch (key) {
			case KeyEvent.VK_ENTER:
				// paintboard.shopmenu.pressEnter();
				break;
			case KeyEvent.VK_UP:
				paintboard.shopmenu.pressUp();
				// System.out.println("up");
				break;
			case KeyEvent.VK_DOWN:
				paintboard.shopmenu.pressDown();
				// System.out.println("down");
				break;
			case KeyEvent.VK_LEFT:
				paintboard.shopmenu.pressLeft();
				break;
			case KeyEvent.VK_RIGHT:
				paintboard.shopmenu.pressRight();
				break;
			default:
			}
			break;
		case 3:
			switch (key) {
			case KeyEvent.VK_W:
				paintboard.gameview.pressUp();
				// System.out.println("up");
				break;
			case KeyEvent.VK_S:
				paintboard.gameview.pressDown();
				// System.out.println("down");
				break;
			case KeyEvent.VK_A:
				// System.out.println("lefttttt");
				paintboard.gameview.pressLeft();
				break;
			case KeyEvent.VK_D:
				paintboard.gameview.pressRight();
				break;
			case KeyEvent.VK_Q:
				paintboard.gameview.pressSelectLeft();
				break;
			case KeyEvent.VK_E:
				paintboard.gameview.pressSelectRight();
				break;
			case KeyEvent.VK_SPACE:
				paintboard.gameview.pressSelectRight();
				break;
			default:
			}
			break;

		default:
		}

		/*
		 * if (key == KeyEvent.VK_DOWN) { downpress = true;
		 * 
		 * } if (key == KeyEvent.VK_UP) { uppress = true; } if (key ==
		 * KeyEvent.VK_RIGHT) { rightpress = true; } if (key ==
		 * KeyEvent.VK_LEFT) { leftpress = true; }
		 */

	}

	public void keyTyped(KeyEvent arg0) {

		switch (paintboard.scene) {
		case 0:
			paintboard.startmenu.KeyType(arg0);
			break;
		case 1:
			paintboard.lobbymenu.KeyType(arg0);
			break;
		default:
		}

	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	//	System.out.println(arg0.getX() + " " + arg0.getY());

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		switch (paintboard.scene) {
		case 0:
			switch (paintboard.startmenu.scene) {
			case 0:
				if (arg0.getX() <= 790 && arg0.getX() >= 490
						&& arg0.getY() <= 430 && arg0.getY() >= 380) {
					paintboard.startmenu.selectCreatRoom();
					paintboard.startmenu.pressEnter();
				}
				if (arg0.getX() <= 790 && arg0.getX() >= 490
						&& arg0.getY() <= 530 && arg0.getY() >= 480) {
					paintboard.startmenu.selectJoinRoom();
					paintboard.startmenu.pressEnter();
				}
				if (arg0.getX() <= 790 && arg0.getX() >= 490
						&& arg0.getY() <= 630 && arg0.getY() >= 580) {
					paintboard.startmenu.selectLeave();
					paintboard.startmenu.pressEnter();
				}
				break;
			case 1:
				if (arg0.getX() <= 790 && arg0.getX() >= 490
						&& arg0.getY() <= 570 && arg0.getY() >= 520) {
					paintboard.startmenu.pressCreatRoomConfirm();
				}
				break;
			case 2:
				if (arg0.getX() <= 790 && arg0.getX() >= 490
						&& arg0.getY() <= 670 && arg0.getY() >= 620) {
					paintboard.startmenu.pressJoinRoomConfirm();
				}
				break;
			}
			break;
		case 1:
			if (arg0.getX() <= 1200 && arg0.getX() >= 1000
					&& arg0.getY() <= 130 && arg0.getY() >= 80) {
				paintboard.lobbymenu.selectMap();
			}
			if (arg0.getX() <= 1200 && arg0.getX() >= 1000
					&& arg0.getY() <= 240 && arg0.getY() >= 190) {
				paintboard.lobbymenu.selectLevel();
			}
			if (arg0.getX() <= 1200 && arg0.getX() >= 1000
					&& arg0.getY() <= 350 && arg0.getY() >= 300) {
				paintboard.lobbymenu.selectButTime();
			}
			if (arg0.getX() <= 1200 && arg0.getX() >= 1000
					&& arg0.getY() <= 460 && arg0.getY() >= 410) {
				paintboard.lobbymenu.selectLevelmMoney();
			}
			if (arg0.getX() <= 1175 && arg0.getX() >= 1025
					&& arg0.getY() <= 560 && arg0.getY() >= 520) {
				paintboard.lobbymenu.selectStartGame();
				paintboard.lobbymenu.releaseEnter();
			}
			if (arg0.getX() <= 1175 && arg0.getX() >= 1025
					&& arg0.getY() <= 620 && arg0.getY() >= 580) {
				paintboard.lobbymenu.selectLeave();
				paintboard.lobbymenu.pressEnter();
			}
			if (arg0.getX() <= 1125 && arg0.getX() >= 1025
					&& arg0.getY() <= 710 && arg0.getY() >= 670) {
				paintboard.lobbymenu.selectChat();
				paintboard.lobbymenu.pressEnter();
			}
			break;
		case 2:
			if (arg0.getX() <= 250 && arg0.getX() >= 90 && arg0.getY() <= 260
					&& arg0.getY() >= 90) {
				paintboard.shopmenu.selectHp();
				paintboard.shopmenu.pressHp();
			}
			if (arg0.getX() <= 460 && arg0.getX() >= 300 && arg0.getY() <= 260
					&& arg0.getY() >= 90) {
				paintboard.shopmenu.selectFire();
				paintboard.shopmenu.pressFire();
			}
			if (arg0.getX() <= 670 && arg0.getX() >= 510 && arg0.getY() <= 260
					&& arg0.getY() >= 90) {
				paintboard.shopmenu.selectRock();
				paintboard.shopmenu.pressRock();
			}
			if (arg0.getX() <= 870 && arg0.getX() >= 720 && arg0.getY() <= 260
					&& arg0.getY() >= 90) {
				paintboard.shopmenu.selectPoison();
				paintboard.shopmenu.pressPoison();
			}
			if (arg0.getX() <= 250 && arg0.getX() >= 90 && arg0.getY() <= 480
					&& arg0.getY() >= 310) {
				paintboard.shopmenu.selectSpeed();
				paintboard.shopmenu.pressSpeed();
			}
			if (arg0.getX() <= 460 && arg0.getX() >= 300 && arg0.getY() <= 480
					&& arg0.getY() >= 310) {
				paintboard.shopmenu.selectIce();
				paintboard.shopmenu.pressIce();
			}
			if (arg0.getX() <= 670 && arg0.getX() >= 510 && arg0.getY() <= 480
					&& arg0.getY() >= 310) {
				paintboard.shopmenu.selectThunder();
				paintboard.shopmenu.pressThunder();
			}
			if (arg0.getX() <= 870 && arg0.getX() >= 720 && arg0.getY() <= 480
					&& arg0.getY() >= 310) {
				paintboard.shopmenu.selectLight();
				paintboard.shopmenu.pressLight();
			}
			break;
		case 3:

			paintboard.gamecontroller.deliverCheckCharacterSkillCastAble(
					arg0.getX(), arg0.getY(), paintboard.gameview.selection);
			break;

		default:
		}
		

	}

	public void mouseReleased(MouseEvent arg0) {

	}

	public void mouseMoved(MouseEvent arg0) {
		// System.out.println("test");
		switch (paintboard.scene) {
		case 0:
			switch(paintboard.startmenu.scene){
			case 0:
				if (arg0.getX() <= 790 && arg0.getX() >= 490 && arg0.getY() <= 430
						&& arg0.getY() >= 380) {
					paintboard.startmenu.selectCreatRoom();
				}
				if (arg0.getX() <= 790 && arg0.getX() >= 490 && arg0.getY() <= 530
						&& arg0.getY() >= 480) {
					paintboard.startmenu.selectJoinRoom();
				}
				if (arg0.getX() <= 790 && arg0.getX() >= 490 && arg0.getY() <= 630
						&& arg0.getY() >= 580) {
					paintboard.startmenu.selectLeave();
				}
				break;
			case 1:
				if (arg0.getX() <= 840 && arg0.getX() >= 440 && arg0.getY() <= 480&& arg0.getY() >= 380) {
					paintboard.startmenu.selectTypeId();
				}
				if (arg0.getX() <= 790 && arg0.getX() >= 490 && arg0.getY() <= 580&& arg0.getY() >= 530) {
					
					paintboard.startmenu.selectCreatRoomConfirm();
				}
				break;
			case 2:
				if (arg0.getX() <= 840 && arg0.getX() >= 440 && arg0.getY() <= 480&& arg0.getY() >= 380) {
					paintboard.startmenu.selectTypeId();
					}
				if (arg0.getX() <= 840 && arg0.getX() >= 440 && arg0.getY() <= 600&& arg0.getY() >= 500) {
					paintboard.startmenu.selectTypeIp();
					}
				if (arg0.getX() <= 790 && arg0.getX() >= 490 && arg0.getY() <= 670&& arg0.getY() >= 620) {
					paintboard.startmenu.selectJoinRoomConfirm();
					}
				break;
				}
			break;
		case 1:
			if (arg0.getX() <= 1200 && arg0.getX() >= 1000
					&& arg0.getY() <= 130 && arg0.getY() >= 80) {
				paintboard.lobbymenu.selectMap();
			}
			if (arg0.getX() <= 1200 && arg0.getX() >= 1000
					&& arg0.getY() <= 240 && arg0.getY() >= 190) {
				paintboard.lobbymenu.selectLevel();
			}
			if (arg0.getX() <= 1200 && arg0.getX() >= 1000
					&& arg0.getY() <= 350 && arg0.getY() >= 300) {
				paintboard.lobbymenu.selectButTime();
			}
			if (arg0.getX() <= 1200 && arg0.getX() >= 1000
					&& arg0.getY() <= 460 && arg0.getY() >= 410) {
				paintboard.lobbymenu.selectLevelmMoney();
			}
			if (arg0.getX() <= 1175 && arg0.getX() >= 1025
					&& arg0.getY() <= 560 && arg0.getY() >= 520) {
				paintboard.lobbymenu.selectStartGame();
			}
			if (arg0.getX() <= 1175 && arg0.getX() >= 1025
					&& arg0.getY() <= 620 && arg0.getY() >= 580) {
				paintboard.lobbymenu.selectLeave();
			}
			if (arg0.getX() <= 1125 && arg0.getX() >= 1025
					&& arg0.getY() <= 710 && arg0.getY() >= 670) {
				paintboard.lobbymenu.selectChat();
			}
			break;
		case 2:
			if (arg0.getX() <= 250 && arg0.getX() >= 90 && arg0.getY() <= 260
					&& arg0.getY() >= 90) {
				paintboard.shopmenu.selectHp();
			}
			if (arg0.getX() <= 460 && arg0.getX() >= 300 && arg0.getY() <= 260
					&& arg0.getY() >= 90) {
				paintboard.shopmenu.selectFire();
			}
			if (arg0.getX() <= 670 && arg0.getX() >= 510 && arg0.getY() <= 260
					&& arg0.getY() >= 90) {
				paintboard.shopmenu.selectRock();
			}
			if (arg0.getX() <= 870 && arg0.getX() >= 720 && arg0.getY() <= 260
					&& arg0.getY() >= 90) {
				paintboard.shopmenu.selectPoison();
			}
			if (arg0.getX() <= 250 && arg0.getX() >= 90 && arg0.getY() <= 480
					&& arg0.getY() >= 310) {
				paintboard.shopmenu.selectSpeed();
			}
			if (arg0.getX() <= 460 && arg0.getX() >= 300 && arg0.getY() <= 480
					&& arg0.getY() >= 310) {
				paintboard.shopmenu.selectIce();
			}
			if (arg0.getX() <= 670 && arg0.getX() >= 510 && arg0.getY() <= 480
					&& arg0.getY() >= 310) {
				paintboard.shopmenu.selectThunder();
			}
			if (arg0.getX() <= 870 && arg0.getX() >= 720 && arg0.getY() <= 480
					&& arg0.getY() >= 310) {
				paintboard.shopmenu.selectLight();
			}
			break;
		default:
		}
	}
}
