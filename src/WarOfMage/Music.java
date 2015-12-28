package WarOfMage;

//SoundPalette.java
//written by mnagaku
//override by me

import java.util.*;
import java.applet.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Music {

	Hashtable<Integer, AudioClip> startmenubgm;

	Hashtable<Integer, AudioClip> ses;

	int javaVersion;

	int nowBgm;
	int preBgm;
	int startmenu;
	AudioClip nowSe = null;

	public Music() {
		startmenu = (int) (Math.random() * 12);
		startmenubgm = new Hashtable<Integer, AudioClip>();
		ses = new Hashtable<Integer, AudioClip>();
		/*
		addBgm(startmenu, "./BGM/Startmenu/" + startmenu + ".wav", startmenubgm);
		
		addSe(0, "./SE/select.wav");
		addSe(1, "./SE/click.wav");
		addSe(2, "./SE/fireice.wav");
		addSe(3, "./SE/poison.wav");
		addSe(4, "./SE/thunder.wav");
		addSe(5, "./SE/hit1.wav");
		addSe(6, "./SE/hit2.wav");
		addSe(7, "./SE/hit3.wav");
*/
		
		/*
		 * for (int i = 0; i < 12; i++) { addBgm(i, "./BGM/Startmenu/" + i +
		 * ".wav", startmenubgm); }
		 */
	}

	public void playStartmenuBGM() {
		//this.playBgm(startmenu, startmenubgm);
	}

	public void playHitSe() {
		//int i = (int) (Math.random() * 3 + 5);
		//this.playSe(i);
	}

	boolean loadData(int no, String file, Hashtable<Integer, AudioClip> pool) {
		AudioClip ac = null;

		URL url = null;
		try {
			url = new File(file).toURI().toURL();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(url);
		//System.out.println(file);
		ac = Applet.newAudioClip(url);
		if (ac == null)
			return false;
		pool.put(new Integer(no), ac);
		return true;
	}

	public boolean addBgm(int no, String file,
			Hashtable<Integer, AudioClip> bgmlist) {
		return loadData(no, file, bgmlist);
	}

	public boolean addSe(int no, String file) {
		return loadData(no, file, ses);
	}

	AudioClip getAc(int no, Hashtable<Integer, AudioClip> pool) {
		AudioClip ac = null;
		ac = (AudioClip) pool.get(new Integer(no));
		return ac;
	}

	/*
	 * public boolean pause() { preBgm = nowBgm; AudioClip ac = getAc(nowBgm,
	 * bgms); if (ac == null) return false; ac.stop(); return true; }
	 * 
	 * public boolean restart() { AudioClip ac = getAc(preBgm, bgms); if (ac ==
	 * null) return false; ac.loop(); return true; }
	 */
	public boolean playBgm(int no, Hashtable<Integer, AudioClip> bgmlist) {
		AudioClip ac = getAc(nowBgm, bgmlist);
		if (ac != null)
			ac.stop();
		nowBgm = no;
		ac = getAc(nowBgm, bgmlist);
		if (ac == null)
			return false;
		ac.loop();
		return true;
	}

	public boolean xplaySe(int no) {
		if (nowSe != null)
			nowSe.stop();
		nowSe = getAc(no, ses);
		if (nowSe == null)
			return false;
		nowSe.play();
		return true;
	}

	public int getpreBGM() {
		return preBgm;
	}

	public boolean playSe(int no) {
		/*
		AudioClip ac = getAc(no, ses);
		if (ac != null)
			ac.stop();
		ac = getAc(no, ses);
		if (ac == null)
			return false;
		ac.play();
		*/
		return true;
	}
}