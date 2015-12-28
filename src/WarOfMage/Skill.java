package WarOfMage;

import Skill.*;

public class Skill {
	PaintBoard paintboard;
	Ice ice;
	Fire fire;
	Light light;
	Poison poison;
	Rock rock;
	Thunder thunder;

	Skill(PaintBoard a) {
		this.paintboard = a;
		ice = new Ice(paintboard, -1, 0, 0,1);
		fire = new Fire(paintboard, -1, 0, 0,1);
		light = new Light(paintboard, -1,1);
		poison = new Poison(paintboard, -1, 0, 0,1);
		rock = new Rock(paintboard, -1, 0, 0,1);
		thunder = new Thunder(paintboard, -1, 0, 0,1);
	}

}
