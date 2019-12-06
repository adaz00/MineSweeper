import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MineSquare {
	// For blank mines. Will also be a parent class to ActualMines and NumberedMines
	protected int posx;
	protected int posy;
	protected BufferedImage bi; //Image displayed
	protected int imageW; //Width and height of image.
	protected int imageH;
	protected int bombRadius; // number of bombs around bomb
	protected boolean bomb; // is current square a bomb.
	private BufferedImage BlankSquare;
	protected boolean revealed = false; //Has mine been clicked on.

	// no argument constructor
	public MineSquare() {
		posx = 0;
		posy = 0;
		try {
			bi = ImageIO.read(new File("EmptySquare.png"));
			BlankSquare = ImageIO.read(new File("blank.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageW = 20;
		imageH = 20;
		bomb = false;
		bombRadius = 0;
		revealed = false;
	}

	// renders our object to the screen
	public void drawImage(Graphics g) {
		g.drawImage(bi, posx, posy, imageW, imageH, null);
	}

	// Lots of getters and setters.
	public void setX(int x) {
		posx = x;
	}

	public int getX() {
		return posx;
	}

	public int getY() {
		return posy;
	}

	public void setY(int y) {
		posy = y;
	}

	public BufferedImage getImage() {
		return bi;
	}

	public void setBomb(boolean bomb) {
		this.bomb = bomb;
	}

	public boolean getBomb() {
		return bomb;
	}

	public void setBombRadius(int bombRadius) {
		this.bombRadius = bombRadius;
	}

	public int getBombRadius() {
		return bombRadius;
	}

	public void pictureChange() {
		bi = BlankSquare;
	}

	public void hiddenImage() {
		// Method body so that numberedmines can use this later. Driver throws a fit if
		// this isn't here.
	}

	public boolean isRevealed() {
		return revealed;
	}

	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
	}

	public String toString() {
		return "MineSquare";
	}

}