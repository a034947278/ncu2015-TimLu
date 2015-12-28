package WarOfMage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class ScoreMenu {
	PaintBoard paintboard;
	int selection = 0;
	ArrayList<Integer> allcharacterscore = new ArrayList<Integer>();
	ArrayList<Integer> allcharacternum = new ArrayList<Integer>();

	ScoreMenu(PaintBoard a) {
		paintboard = a;
	}

	public void StartScoreMenu() {
		// paintboard.gamemodel.addCauseDamge();
		ComputAllScore();
		paintboard.scene = 4;

	}

	public void ComputAllScore() {

		for (int i = 0; i < paintboard.allcharacter.size(); i++) {
			Character character = paintboard.allcharacter.get(i);
			character.Score = character.kill * 30 + character.totalDamge
					+ character.lastonetimes * 1500;
			allcharacterscore.add(character.Score);
			allcharacternum.add(i);
		}
		for (int i = 0; i < paintboard.allcharacter.size() - 1; i++) {
			for (int j = 0; j < paintboard.allcharacter.size() - 1 - i; j++) {
				if (allcharacterscore.get(j + 1) > allcharacterscore.get(j)) {
					int tmp2 = allcharacterscore.get(j);
					allcharacterscore.set(j, allcharacterscore.get(j + 1));
					allcharacterscore.set(j + 1, tmp2);
					int tmp = allcharacternum.get(j);
					allcharacternum.set(j, allcharacternum.get(j + 1));
					allcharacternum.set(j + 1, tmp);
				}
			}
		}

	}

	public void drawscene(Graphics2D g) {
		g.drawImage(paintboard.imagelibrary.getImage("background3"), 0, 0,
				paintboard);

		g.setComposite(AlphaComposite.SrcOver.derive(0.8f));
		g.setColor(new Color(0, 0, 0, 255));
		g.fill3DRect(70, 70, 1140, 580, true);
		g.setComposite(AlphaComposite.SrcOver.derive(1f));
		g.setColor(new Color(255, 255, 255, 255));
		g.draw3DRect(70, 70, 1140, 580, true);

		int selfnum = paintboard.gamecontroller.selfnum;

		g.setColor(new Color(255, 255, 255, 255));
		Font font = new Font("Curlz MT", Font.BOLD, 70);
		g.setFont(font);
		g.drawString("Result", (1280 - 189) / 2, 130);
		g.drawLine(100, 150, 1180, 150);

		g.setColor(new Color(255, 0, 0, 255));
		font = new Font("微軟正黑體", Font.BOLD, 40);
		g.setFont(font);

		g.drawString("Name", 100, 200);
		g.drawString("擊殺數", 400, 200);
		g.drawString("造成傷害", 700, 200);
		g.drawString("成績", 1000, 200);
		for (int i = 0; i < paintboard.allcharacter.size(); i++) {

			Character character = paintboard.allcharacter.get(allcharacternum
					.get(i));
			if (!character.isDisconnected) {
				g.setColor(new Color(255, 255, 255, 255));
				if (allcharacternum.get(i) == selfnum) {
					g.setColor(new Color(0, 255, 0, 255));
				}
				g.drawString(character.name, 100, 250 + i * 50);
				g.drawString(character.kill + "", 400, 250 + i * 50);
				g.drawString(character.totalDamge + "", 700, 250 + i * 50);
				g.drawString(character.Score + "", 1000, 250 + i * 50);
			}
		}

		/*
		 * for (int i = 0; i < allcharacternum.size(); i++) { Character
		 * character = paintboard.allcharacter.get(allcharacternum .get(i)); }
		 */

	}

}
