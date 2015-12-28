package WarOfMage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Skill.BaseSkill;

public class PaintBoard extends JPanel implements Runnable, MouseListener,
		MouseMotionListener {
	private Thread animator;

	// private Background a = new Background();
	// 當前畫面
	public int scene = 0;

	// repaint迴圈用
	private int sec = 0;

	// 當前FPS
	private int fps = 0;

	public Startmenu startmenu;
	public Lobbymenu lobbymenu;
	public ImageLibrary imagelibrary;
	public ScoreMenu scoremenu;
	public Shopmenu shopmenu;
	public Net net;
	public Skill skill;
	private InputController inputcontroller;
	public GameModel gamemodel;
	public GameView gameview;
	public Map map;
	public GIFLibrary giflibrary;
	public Music music;

	// 所有腳色陣列
	public ArrayList<Character> allcharacter = new ArrayList<>();
	public ArrayList<BaseSkill> allskill = new ArrayList<>();

	public GameController gamecontroller;

	public PaintBoard() {
		// System.setProperty("sun.java2d.opengl", "true");
		this.setDoubleBuffered(true);
		setPreferredSize(new Dimension(1280, 720));
		initial();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setLayout(null);
		this.addKeyListener(new TAdapter());
		setFocusable(true);
		setDoubleBuffered(true);
		animator = new Thread(this);
		animator.start();

	}

	private void initial() {
		imagelibrary = new ImageLibrary();
		startmenu = new Startmenu(this);
		lobbymenu = new Lobbymenu(this);
		inputcontroller = new InputController(this);
		this.gamecontroller = new GameController(this);
		this.shopmenu = new Shopmenu(this);
		this.scoremenu = new ScoreMenu(this);
		this.skill = new Skill(this);
		this.gamemodel = new GameModel(this);
		this.map = new Map(this);
		this.gameview = new GameView(this);
		this.giflibrary = new GIFLibrary();	
		this.music=new Music();
		music.playStartmenuBGM();
	}

	// ///////////////////////////////////////////////////////////
	public void drawfps(Graphics g) {
		g.setColor(new Color(255, 229, 24, 255));
		g.setFont(new Font("MV Boli", Font.BOLD, 15));
		g.drawString("FPS: " + fps, 10, 20);
		// g.drawString("X: " + x, 150, 40);
		// g.drawString("Y: " + y, 150, 60);
	}

	// ///////////////////////GAMELOOP////////////////////////////
	public void gameloop(Graphics2D g) {
		switch (scene) {
		case 0:
			startmenu.drawscene(g);
			break;
		case 1:
			lobbymenu.drawscene(g);
			break;
		case 2:
			shopmenu.drawscene(g);
			break;
		case 3:
			gameview.drawscene(g);
			break;
		case 4:
			scoremenu.drawscene(g);
			break;
		default:

		}
	}

	// ///////////////////////////////////////////////////////////
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		// 開啟反鋸齒
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// 高階反鋸齒
		// g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		// RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		// checkKey();
		gameloop(g2d);
		// g.drawImage(scene[0][0].getImage(), 0, 0, this);
		drawfps(g2d);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	// 自己看
	public void run() {
		int moveCount = 0;
		long timeDiff, sleep;
		long second = 0;

		while (true) {

			long startTime = System.currentTimeMillis();
			repaint();
			
			timeDiff = System.currentTimeMillis() - startTime;
			
			
			sleep = 10 - timeDiff;
			
			if (sleep < 0) {
				sleep = 0;
			}
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("Interrupted: " + e.getMessage());
			}
			moveCount++;
			second += System.currentTimeMillis() - startTime;
			if (second >= 1000L) {
				// fps.setText(" FPS:" + moveCount);
				fps = moveCount;
				moveCount = 0;
				second = 0L;
			}
			if (sec == 1000000)
				sec = 0;
			sec = sec + 10;
		}
	}

	private class TAdapter implements KeyListener {

		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			inputcontroller.keyReleased(e);
			/*
			 * if (key == KeyEvent.VK_DOWN) { downpress = false; } if (key ==
			 * KeyEvent.VK_UP) { uppress = false; } if (key ==
			 * KeyEvent.VK_RIGHT) { rightpress = false; } if (key ==
			 * KeyEvent.VK_LEFT) { leftpress = false; }
			 */
		}

		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			inputcontroller.keyPressed(e);
			/*
			 * if (key == KeyEvent.VK_ENTER) { try {
			 * Tcptest2.TCPDeliver(startmenu.nameinput.getData());
			 * System.out.println("test"); } catch (IOException e1) { // TODO
			 * Auto-generated catch block e1.printStackTrace(); } }
			 */
			/*
			 * if (key == KeyEvent.VK_DOWN) { downpress = true;
			 * 
			 * } if (key == KeyEvent.VK_UP) { uppress = true; } if (key ==
			 * KeyEvent.VK_RIGHT) { rightpress = true; } if (key ==
			 * KeyEvent.VK_LEFT) { leftpress = true; }
			 */

		}

		public void keyTyped(KeyEvent arg0) {
			inputcontroller.keyTyped(arg0);
		}

	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println(arg0.getX() + " " + arg0.getY());
		
		/*
		double direction = 0;
		double x=arg0.getX()-590;
		double y=arg0.getY()-260;
		//direction = -Math.toDegrees(Math.atan2(y,x));
		direction = Math.atan2(y, x)/Math.PI*180;
		System.out.println(x);
		System.out.println(y);
		System.out.println(direction);
		*/
		inputcontroller.mouseClicked(arg0);
		// startmenu.presscreatroom();
		/*
		 * for (int i = 0; i < this.allcharacter.size(); i++) {
		 * System.out.println(allcharacter.get(i).name + "第" + i + "使用者名稱"); }
		 */

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		inputcontroller.mousePressed(arg0);

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		inputcontroller.mouseMoved(arg0);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}