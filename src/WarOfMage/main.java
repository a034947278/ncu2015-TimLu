package WarOfMage;

import javax.swing.JFrame;


public class main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private Input input = new Input(720);

	public main() {
		this.add(new PaintBoard());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("FinalProject");
		setResizable(false);
		pack();
		setVisible(true);

	}
	
	public static void main(String[] args) {
		//System.out.print(Math.sin(Math.toRadians(270)));
		new main();
	}
}
