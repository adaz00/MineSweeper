import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//For numbered squares
public class NumberedMines extends MineSquare {
	BufferedImage numberedImage; //Hidden image that shows number of bombs surrounding square

	// no argument constructor.
	public NumberedMines() {
		posx = 0;
		posy = 0;
		try {
			bi = ImageIO.read(new File("EmptySquare.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageW = 20;
		imageH = 20;
		bomb = false;
		bombRadius = 0;
	}

	// Constructor for when given x and y coordinate.
	public NumberedMines(int x, int y) {
		posx = x;
		posy = y;
		try {
			bi = ImageIO.read(new File("EmptySquare.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageW = 20;
		imageH = 20;
		bomb = false;
		bombRadius = 0;
	}

	// Sets "hidden image" to whatever number bombradius is. Hidden image will
	// replace bi later.
	public void hiddenImage() {
		if (bombRadius == 1) {
			try {
				numberedImage = ImageIO.read(new File("1.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (bombRadius == 2) {
			try {
				numberedImage = ImageIO.read(new File("2.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (bombRadius == 3) {
			try {
				numberedImage = ImageIO.read(new File("3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (bombRadius == 4) {
			try {
				numberedImage = ImageIO.read(new File("4.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (bombRadius == 5) {
			try {
				numberedImage = ImageIO.read(new File("5.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (bombRadius == 6) {
			try {
				numberedImage = ImageIO.read(new File("6.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (bombRadius == 7) {
			try {
				numberedImage = ImageIO.read(new File("7.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (bombRadius == 8) {
			try {
				numberedImage = ImageIO.read(new File("8.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Changes picture when called.
	public void pictureChange() {
		// System.out.println("Revealing Numberedsquare");
		bi = numberedImage;
		revealed = true;
	}

	public String toString() {
		return "NumberedMines";
	}

}