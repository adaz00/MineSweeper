import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//For real mines

//need method. IF clicked on, game ends.

public class ActualMines extends MineSquare {
	BufferedImage BombImage;

	// No argument constructor
	public ActualMines() {
		posx = 0;
		posy = 0;
		try {
			bi = ImageIO.read(new File("EmptySquare.png"));
			BombImage = ImageIO.read(new File("Bomb.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageW = 20;
		imageH = 20;
		bomb = true;
		bombRadius = 0;
	}

	// Constructor for x and y coordinate
	public ActualMines(int x, int y) {
		posx = x;
		posy = y;
		try {
			bi = ImageIO.read(new File("EmptySquare.png"));
			BombImage = ImageIO.read(new File("Bomb.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageW = 20;
		imageH = 20;
		bomb = true;
		bombRadius = 0;
	}

	public void pictureChange() {
		bi = BombImage;
		revealed = true;
	}

	public String toString() {
		return "ActualMines";
	}

}