package WarOfMage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Map extends JPanel implements Runnable {

	PaintBoard paintboard;

	Map(PaintBoard a) {
		paintboard = a;
		BufferedImage img;
		try {
			img = ImageIO.read(new File("0.png"));
			scene0 = img.getScaledInstance(elementsize, elementsize,
					Image.SCALE_SMOOTH);
			img = ImageIO.read(new File("1.png"));
			scene1 = img.getScaledInstance(elementsize, elementsize,
					Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int aa = 0; aa < r; aa++) {
			for (int bb = 0; bb < s; bb++) {
				map[aa][bb] = 1;
			}
		}

	}

	private int r = 2000;// xarraysize
	private int s = 2000;// yarraysize
	private int[][] map = new int[r][s];

	private int elementsize = 20;// elementsize
	private int px = 640;
	private int py = 360;// 角色視野置中
	private Image scene0;
	private Image scene1;
	private Image icon = scene0;
	private int rad = 50;
	public boolean isStop = false;
	
	
	public void updatemaplist(){
		for (int a = 0; a < r; a++) {
			for (int b = 0; b < s; b++) {
				if (((r / 2 - a) * (r / 2 - a) + (s / 2 - b) * (s / 2 - b)) < rad
						* rad) {
					map[a][b] = 1;
				} else {
					map[a][b] = 0;
				}
			}
		}
	}

	public void Drawmap(int characterx, int charactery, Graphics2D g) {

		
		int x = characterx / 20;// 陣列x位置
		int y = charactery / 20;// 陣列y位置

		super.paint(g);

		for (int i = y - 19; i <= y + 19; i++) {
			for (int j = x - 33; j <= x + 33; j++) {

				if (i >= 0 && j >= 0 && i < map.length && j < map[0].length) {
					if (map[i][j] == 0) {
						icon = scene0;
					} else if (map[i][j] == 1) {
						icon = scene1;
					}

					g.drawImage(icon, (px - elementsize / 2)
							- ((x - j) * elementsize)
							- (characterx % elementsize), // 用取餘數的方式讓地圖以像素為單位作移動
							(py - elementsize / 2) - ((y - i) * elementsize)
									- (charactery % elementsize), // 用取餘數的方式讓地圖以像素為單位作移動
							paintboard);
				}
			}
		}

		// 以角色為中心
		// 劃出相對於世界座標的 地圖
	}

	public Boolean isOutOfBounds(int characterx, int charactery) {
		int x = characterx / 20;
		int y = charactery / 20;

		if (x > r - 1 || x < 0 || y > s - 1 || y < 0) {
			x = 0;
			y = 0;
		}

		if (map[x][y] == 0)
			return true;
		else if (map[x][y] == 1)
			return false;

		return true;
		// 回傳這個角色做標是否超出邊界
	}

	public void initialCharacterLocation() {

		int n = 8;
		int[] randomnum = new int[n + 1];
		for (int i = 1; i <= n; i++)
			randomnum[i] = i;
		for (int i = 1; i <= n; i++) {
			int j = (int) (Math.random() * n + 1);
			int tmp = randomnum[i];
			randomnum[i] = randomnum[j];
			randomnum[j] = tmp;
		}
		for (int i = 0; i < paintboard.allcharacter.size(); i++) {
			Character character = paintboard.allcharacter.get(i);

			switch (randomnum[i + 1]) {
			case 1:
				character.x = (r / 2 - 20) * 20;
				character.y = (s / 2 - 20) * 20;
				break;
			case 2:
				character.x = (r / 2) * 20;
				character.y = (s / 2 - 40) * 20;
				break;
			case 3:
				character.x = (r / 2 + 20) * 20;
				character.y = (s / 2 - 20) * 20;
				break;
			case 4:
				character.x = (r / 2 - 40) * 20;
				character.y = (s / 2) * 20;
				break;
			case 5:
				character.x = (r / 2 + 40) * 20;
				character.y = (s / 2) * 20;
				break;
			case 6:
				character.x = (r / 2 - 20) * 20;
				character.y = (s / 2 + 20) * 20;
				break;
			case 7:
				character.x = (r / 2) * 20;
				character.y = (s / 2 + 40) * 20;
				break;
			case 8:
				character.x = (r / 2 + 20) * 20;
				character.y = (s / 2 + 20) * 20;
				break;
			default:
				break;
			}
		}
	}

	public void DrawLittlemap(int MyCharacternum, int width, int height,
			Graphics2D g) {
		width = 200;
		height = 200;
		g.setColor(Color.white);
		g.fillOval(1040, 460, width, height);
		// 先劃出一個小型的完整地圖在右下角

		for (int i = 0; i < paintboard.allcharacter.size(); i++) {
			Character character = paintboard.allcharacter.get(i);
			Character me = paintboard.allcharacter.get(MyCharacternum);
			int myx = (int) me.x;
			int myy = (int) me.y;
			if (!character.isDead) {
				if (i == MyCharacternum) {
					g.setColor(Color.green);
					g.fillOval(1135, 555, 10, 10);// 畫綠色
				} else {
					double d = Math.sqrt(((myx - (int) character.x) / 10)
							* ((myx - (int) character.x) / 10)
							+ ((myy - (int) character.y) / 10)
							* ((myy - (int) character.y) / 10));
					if (d <= 100) {
						g.setColor(Color.red);
						g.fillOval(1135 - (myx - (int) character.x) / 10,
								555 - (myy - (int) character.y) / 10, 10, 10);// 畫紅色
					} else {
						g.setColor(Color.red);
						g.fillOval(
								1135 - (int) ((myx - character.x) * (100 / d)) / 10,
								555 - (int) ((myy - character.y) * (100 / d)) / 10,
								10, 10);// 畫紅色
					}
				}
			}
		}
	}

	public void run() {
		int t = 0;
		while (!isStop) {
			try {
				Thread.sleep(500);
				t = t + 500;
				while (t >= 10000) {
					rad = rad - 5;
					updatemaplist();
					if (rad <= 0) {
						rad = 0;
						isStop = true;
					}
					t=0;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void start() {
		rad = 50;
		updatemaplist();
		isStop = false;
		Thread timer = new Thread(this);
		timer.start();

	}

	public void stop() {
		isStop = true;
	}
}