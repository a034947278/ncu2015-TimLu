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
	 * ��J�ؤ��e initialx:��J�� �_�lx initialy:��J�� �_�ly sizex:��J��X�e�� sizey:��J��y����
	 * maxlenth:��J�ؤ��e���׭��� layout:����覡 center and left
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
		// �p�G�OBACKSPACE �N�R���̫�@��
		if (keyChar == '\b') {
			if (len > 0) {
				data = data.substring(0, len - 1);

			}
		} else if (len < maxlenth && keyChar != '\n') {
			// �p�G���OENTER �B�p�� �̤j���� �N�s�W���e
			data = data + keyChar;
		}
		// System.out.println(data);
	}

	public void drawscene(Graphics2D g) {
		// �z����
		g.setColor(new Color(0, 0, 0, 255));
		g.setComposite(AlphaComposite.SrcOver.derive(0.8f));
		g.fill3DRect(initialx, initialy, sizex, sizey, true);
		g.setComposite(AlphaComposite.SrcOver.derive(1f));
		g.setColor(new Color(255, 255, 255, 255));
		// g.setFont(new Font("�L�n������", Font.BOLD, (int) (sizey * 0.5)));
		Font font = new Font("�L�n������", Font.BOLD, (int) (sizey * 0.5));
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
