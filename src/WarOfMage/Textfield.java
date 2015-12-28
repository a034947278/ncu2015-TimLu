package WarOfMage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Textfield {
	public String data = "";
	public String initialdata;
	/*
	 * 輸入框內容 initialx:輸入框 起始x initialy:輸入框 起始y sizex:輸入框X寬度 sizey:輸入框y高度
	 * maxlenth:輸入框內容長度限制 layout:對齊方式 center and left
	 */
	public int initialx;
	public int initialy;
	public int sizex;
	public int sizey;
	private int maxlenth;
	private String layout = "center";

	public Textfield(int initialx, int initialy, int sizex, int sizey,
			int maxlenth) {
		this.initialx = initialx;
		this.initialy = initialy;
		this.sizex = sizex;
		this.sizey = sizey;
		this.maxlenth = maxlenth;
	}

	public Textfield(int initialx, int initialy, int sizex, int sizey,
			int maxlenth, String layout) {
		this.initialx = initialx;
		this.initialy = initialy;
		this.sizex = sizex;
		this.sizey = sizey;
		this.maxlenth = maxlenth;
		this.layout = layout;
	}

	public Textfield(int initialx, int initialy, int sizex, int sizey,
			int maxlenth, int i, String initiail) {
		this.initialx = initialx;
		this.initialy = initialy;
		this.sizex = sizex;
		this.sizey = sizey;
		this.maxlenth = maxlenth;
		data = initiail;
		initialdata = initiail;
	}

	public void initial() {
		data = initialdata;
	}

	public String getData() {
		return data;
	}

	public void keyTyped(KeyEvent arg0) {
		char keyChar = arg0.getKeyChar();
		int len = data.length();
		// 如果是BACKSPACE 就刪除最後一個
		if (keyChar == '\b') {
			if (len > 0) {
				data = data.substring(0, len - 1);

			}
		} else if (len < maxlenth && keyChar != '\n') {
			// 如果不是ENTER 且小於 最大長度 就新增內容
			data = data + keyChar;
		}
		// System.out.println(data);
	}

	public void drawscene(Graphics2D g) {
		// 透明度
		g.setColor(new Color(0, 0, 0, 255));
		g.setComposite(AlphaComposite.SrcOver.derive(0.8f));
		g.fill3DRect(initialx, initialy, sizex, sizey, true);
		g.setComposite(AlphaComposite.SrcOver.derive(1f));
		g.setColor(new Color(255, 255, 255, 255));
		// g.setFont(new Font("微軟正黑體", Font.BOLD, (int) (sizey * 0.5)));
		Font font = new Font("微軟正黑體", Font.BOLD, (int) (sizey * 0.5));
		// Get the FontMetrics
		FontMetrics metrics = g.getFontMetrics(font);
		// Determine the X coordinate for the text
		int x = 0;
		if (layout == "center") {
			x = initialx + (sizex - metrics.stringWidth(data)) / 2;
		} else if (layout == "left") {
			x = initialx;
		}
		// Determine the Y coordinate for the tex
		// Set the font
		g.setFont(font);
		// Draw the String
		g.drawString(data, x, (int) (initialy + sizey * 0.65));

		// g.drawString(data, initialx, (int) (initialy + sizey * 0.65));

	}

}
