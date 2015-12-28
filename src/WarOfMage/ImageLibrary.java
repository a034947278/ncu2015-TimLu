package WarOfMage;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageLibrary {
	HashMap<String, Image> imagemap = new HashMap<String, Image>();

	ImageLibrary() {
		/*
		 * // SMOOTH SCALE BufferedImage img; try { img = ImageIO.read(new
		 * File("./Object/select.png")); imagemap.put("select",
		 * img.getScaledInstance(170, 170, Image.SCALE_SMOOTH)); img =
		 * ImageIO.read(new File("./Object/frame.png")); imagemap.put("frame",
		 * img.getScaledInstance(150, 150, Image.SCALE_SMOOTH)); img =
		 * ImageIO.read(new File("./Object/hp.png")); imagemap.put("hp",
		 * img.getScaledInstance(75, 75, Image.SCALE_SMOOTH)); img =
		 * ImageIO.read(new File("./Object/speed.png")); imagemap.put("speed",
		 * img.getScaledInstance(75, 75, Image.SCALE_SMOOTH)); img =
		 * ImageIO.read(new File("./Object/frame2.png")); imagemap.put("frame2",
		 * img.getScaledInstance(1280, 720, Image.SCALE_SMOOTH));
		 * 
		 * img = ImageIO.read(new File("./Skill/fire.png"));
		 * imagemap.put("fire", img.getScaledInstance(150, 150,
		 * Image.SCALE_SMOOTH)); img = ImageIO.read(new
		 * File("./Skill/rock.png")); imagemap.put("rock",
		 * img.getScaledInstance(150, 150, Image.SCALE_SMOOTH)); img =
		 * ImageIO.read(new File("./Skill/ice.png")); imagemap.put("ice",
		 * img.getScaledInstance(150, 150, Image.SCALE_SMOOTH)); img =
		 * ImageIO.read(new File("./Skill/thunder.png"));
		 * imagemap.put("thunder", img.getScaledInstance(150, 150,
		 * Image.SCALE_SMOOTH));
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		addImage("fire", "./Skill/fire.png", 150, 150);
		addImage("gamefire", "./Skill/fire.png", 75, 75);
		addImage("rock", "./Skill/rock.png", 150, 150);
		addImage("gamerock", "./Skill/rock.png", 75, 75);
		addImage("ice", "./Skill/ice.png", 150, 150);
		addImage("gameice", "./Skill/ice.png", 75, 75);
		addImage("thunder", "./Skill/thunder.png", 150, 150);
		addImage("gamethunder", "./Skill/thunder.png", 75, 75);
		addImage("light", "./Skill/light.png", 150, 150);
		addImage("gamelight", "./Skill/light.png", 75, 75);
		addImage("poison", "./Skill/poison.png", 150, 150);
		addImage("gamepoison", "./Skill/poison.png", 75, 75);

		addImage("select", "./Object/select.png", 170, 170);
		addImage("frame", "./Object/frame.png", 150, 150);
		addImage("gameselect", "./Object/select.png", 85, 85);
		addImage("gameframe", "./Object/frame.png", 75, 75);
		addImage("gamehp", "./Object/hp.png", 37, 37);
		addImage("hp", "./Object/hp.png", 75, 75);
		addImage("speed", "./Object/speed.png", 75, 75);

		addImage("frame2", "./Object/frame2.png");
		addImage("button", "./Background/button.jpg");
		addImage("background0", "./Background/0.jpg");
		addImage("background1", "./Background/1.jpg");
		addImage("background2", "./Background/2.jpg");
		addImage("background3", "./Background/3.jpg");
		addImage("StandUp", "./Person/stand/up.png");
		addImage("StandDown", "./Person/stand/down.png");
		addImage("StandLeft", "./Person/stand/left.png");
		addImage("StandRight", "./Person/stand/right.png");

		/*
		 * imagemap.put("button", new
		 * ImageIcon("./Background/button.jpg").getImage());
		 * 
		 * imagemap.put("background0", new
		 * ImageIcon("./Background/0.jpg").getImage());
		 * imagemap.put("background1", new
		 * ImageIcon("./Background/1.jpg").getImage());
		 * imagemap.put("background2", new
		 * ImageIcon("./Background/2.jpg").getImage());
		 * imagemap.put("background3", new
		 * ImageIcon("./Background/3.jpg").getImage());
		 */

	}

	public void addImage(String key, String url) {

		imagemap.put(key, new ImageIcon(url).getImage());

	}

	public void addImage(String key, String url, int xsize, int ysize) {

		BufferedImage img;
		try {
			img = ImageIO.read(new File(url));
			imagemap.put(key,
					img.getScaledInstance(xsize, ysize, Image.SCALE_SMOOTH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Image getImage(String name) {
		return imagemap.get(name);
	}
	/*
	 * public Image getImage(String name) { switch (name) { case "background0":
	 * return background0; case "background1": return background1; case
	 * "background2": return background2; case "background3": return
	 * background3; case "button": return button; case "select": return select;
	 * case "notselect": return notselect; case "frame": return frame; default:
	 * return background0; }
	 * 
	 * }
	 */
}
