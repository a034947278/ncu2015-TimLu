package WarOfMage;

import java.awt.image.BufferedImage;

import java.awt.Image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class GIFLibrary {
	HashMap<String, ArrayList<Image>> imagemap = new HashMap<String, ArrayList<Image>>();

	GIFLibrary() {
		addImage("FireBall", "./Skill/FireBall/", 100, 100, 12);
		addImage("IceBall", "./Skill/IceBall/", 100, 100, 12);
		addImage("Light", "./Skill/Light/", 170, 170, 6);
		addImage("PoisonBall", "./Skill/PoisonBall/", 100, 100, 25);
		addImage("Rock", "./Skill/Rock/", 500, 500, 14);
		addImage("ThunderBall", "./Skill/ThunderBall/", 100, 100, 115);
		addImage("RunLeft", "./Person/move/left", 100, 100, 4);
		addImage("RunRight", "./Person/move/right", 100, 100, 4);
		addImage("RunUp", "./Person/move/up", 100, 100, 4);
		addImage("RunDown", "./Person/move/down", 100, 100, 4);

	}

	public void addImage(String key, String url, int xsize, int ysize,
			int maxindex) {
		ArrayList<Image> list = new ArrayList<Image>();
		BufferedImage img = null;
		for (int i = 0; i < maxindex; i++) {
			try {
				int a = i + 1;
				img = ImageIO.read(new File(url + a + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.add(img.getScaledInstance(xsize, ysize, Image.SCALE_SMOOTH));

		}
		imagemap.put(key, list);

	}

	public ArrayList<Image> getImageList(String name) {
		return imagemap.get(name);
	}

}
