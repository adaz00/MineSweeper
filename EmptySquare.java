import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//For real mines

//need method. IF clicked on, game ends.

public class EmptySquare extends MineSquare {
	BufferedImage blankSquare;
	boolean clickedOn = false;

	// No argument constructor
	public EmptySquare() {
		super();
		try {
			blankSquare = ImageIO.read(new File("blank.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Constructor for x and y coordinate
	public EmptySquare(int x, int y) {
		posx = x;
		posy = y;
		try {
			bi = ImageIO.read(new File("EmptySquare.png"));
			blankSquare = ImageIO.read(new File("blank.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageW = 20;
		imageH = 20;
		bomb = false;
		bombRadius = 0;
	}

	public void pictureChange() {
		// System.out.println("Revealing emptysquare");
		bi = blankSquare;
		revealed = true;
	}

	public String toString() {
		return "EmptySquare";
	}

}