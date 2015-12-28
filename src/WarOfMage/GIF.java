package WarOfMage;

import java.awt.Image;

import java.lang.Thread;
import java.lang.Runnable;
import java.util.*;

public class GIF implements Runnable {
	private ArrayList<Image> images = new ArrayList<Image>();
	int index = 0; // 目前第幾張圖
	int gifdelay;
	PaintBoard paintboard;
	Boolean isCycle;
	int StartCycle;
	int lastindex;
	Boolean isStop = false;
	String name = "";

	public GIF(String name, PaintBoard a, Boolean isCycle) {
		// 初始化指定名子的動圖;
		images = a.giflibrary.getImageList(name);
		initialization(name);
		lastindex = images.size() - 1;
		this.name = name;

		// Startsec=paintboard.sec;

		Thread timer = new Thread(this);
		timer.start();
	}

	public void initialization(String name) {
		switch (name) {
		case "FireBall":
			this.isCycle = true;
			this.StartCycle = 0;
			this.gifdelay = 90;
			break;
		case "IceBall":
			this.isCycle = true;
			this.StartCycle = 0;
			this.gifdelay = 90;
			break;
		case "Light":
			this.isCycle = true;
			this.StartCycle = 0;
			this.gifdelay = 90;
			break;
		case "PoisonBall":
			this.isCycle = true;
			this.StartCycle = 0;
			this.gifdelay = 30;
			break;
		case "Rock":
			this.gifdelay = 120;
			this.isCycle = false;
			break;
		case "ThunderBall":
			this.isCycle = true;
			this.StartCycle = 0;
			this.gifdelay = 50;
			break;
		case "RunLeft":
			this.isCycle = true;
			this.StartCycle = 0;
			this.gifdelay = 100;
			break;
		case "RunRight":
			this.isCycle = true;
			this.StartCycle = 0;
			this.gifdelay = 100;
			break;
		case "RunUp":
			this.isCycle = true;
			this.StartCycle = 0;
			this.gifdelay = 100;
			break;
		case "RunDown":
			this.isCycle = true;
			this.StartCycle = 0;
			this.gifdelay = 100;
			break;
		}
		
	}

	// //////////////////////////////////////////

	public void run() {
		while (!isStop || !isNotCycleStop()) {
			try {
				Thread.sleep(gifdelay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if ((index == lastindex) && this.isCycle) {
				index = this.StartCycle;

			}
			
			index++;
			if(!isCycle && index>lastindex){
				index=lastindex;
				
			}

		}
	}

	private Boolean isNotCycleStop() {
		if (isCycle) {
			return false;
		} else {
			if (index == lastindex)
				return true;
		}
		return false;
	}

	public void stop() {
		isStop = true;
	}

	public void start() {
		index = 0;
	}

	public Image getNowImage() {
		return images.get(index);
	}

}
