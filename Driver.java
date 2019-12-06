
//Adam Bush
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JTextField;

@SuppressWarnings("serial") // prevents the serial warning

public class Driver extends JFrame implements MouseListener {
	int mapWidth = 0; // Width of minefield
	int mapHeight = 0; // height of minefield
	int mineNumber = 0; // number of mines
	int mineSize = 20; // number of pixels each square is.
	int distanceX = 50; // X and Y for how far the playable board is from the top left corner.
	int distanceY = 50;
	int revealedCount = 0; //Will count how many squares have been revealed.
	boolean gameStarted = false; //Stops the mouse from checking the array if the game hasn't begun, or if it has ended.
	int mouseX;//X and Y coordinates for where mouse clicks.
	int mouseY;
	static Driver m = new Driver();

	MineSquare[][] mineField; // Array of mineSquares
	int[][] mineLocation; //Array of bomb X and Y locations
	
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblMineLength;
	private JLabel lblMineWidth;
	private JLabel lblNumberOfBombs;
	private JButton btnStart;
	private JButton btnEnd;
	private JLabel gameState;

	public Driver() {
		addMouseListener(this);
		getContentPane().setLayout(null);

		//Places Menu buttons, textfields and labels
		textField = new JTextField();
		textField.setBounds(10, 29, 47, 20);
		getContentPane().add(textField);
		textField.setColumns(10);

		lblMineLength = new JLabel("Mine Length");
		lblMineLength.setBounds(10, 11, 77, 14);
		getContentPane().add(lblMineLength);

		lblMineWidth = new JLabel("Mine Width");
		lblMineWidth.setBounds(10, 58, 91, 14);
		getContentPane().add(lblMineWidth);

		textField_1 = new JTextField();
		textField_1.setBounds(10, 79, 47, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);

		lblNumberOfBombs = new JLabel("Number of Bombs");
		lblNumberOfBombs.setBounds(10, 110, 113, 14);
		getContentPane().add(lblNumberOfBombs);

		textField_2 = new JTextField();
		textField_2.setBounds(10, 135, 47, 20);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);

		btnStart = new JButton("Start");
		btnStart.setBounds(10, 166, 89, 23);
		getContentPane().add(btnStart);

		btnEnd = new JButton("Restart");

		// When start button is clicked.
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				//Sets size for mineField and mineLocation array, and starts the game.
				try {
					mapWidth = Integer.parseInt(textField_1.getText());
					mapHeight = Integer.parseInt(textField.getText());
					mineNumber = Integer.parseInt(textField_2.getText());
				} catch (NumberFormatException E) {
					System.out.println("Numbers only in the textbox please.");
					System.out.println("Now look what you've done. You broke the game.");
				}
				mineField = new MineSquare[mapWidth][mapHeight];
				mineLocation = new int[mineNumber][2];
				gameStarted = true;

				// Sets window size to fit bombs
				m.setSize(distanceX * 2 + mineSize * mapWidth, distanceY * 2 + mineSize * mapHeight + 50);

				// Places Reset button under the grid
				btnEnd.setBounds((mineSize * mapWidth) / 2, mineSize * mapHeight + 40, 89, 23);
				getContentPane().add(btnEnd);
				btnEnd.setVisible(true);

				// Displays number of bombs. This will change to a game over or victory screenif applicable.
				gameState = new JLabel("Num Bombs: " + mineNumber);
				gameState.setBounds((mineSize * mapWidth) / 2, mineSize * mapHeight + 70, 113, 14);
				getContentPane().add(gameState);
				gameState.setVisible(true);

				// Create the minefield here
				// Fills Minefield with new mines
				for (int i = 0; i < mapWidth; i++) {
					for (int j = 0; j < mapHeight; j++) {
						mineField[i][j] = new EmptySquare();
						mineField[i][j].setX(i * mineSize + distanceX);
						mineField[i][j].setY(j * mineSize + distanceY);
					}
				}

				// Gets rid of all menu buttons
				textField.setVisible(false);
				textField_1.setVisible(false);
				textField_2.setVisible(false);
				lblMineLength.setVisible(false);
				lblMineWidth.setVisible(false);
				lblNumberOfBombs.setVisible(false);
				btnStart.setVisible(false);

				placeMines();
				calculateHints();
				//drawMinefield();  //Cheat method. Prints out bombs, numbers and blank squares to the array.
				repaint();
			}
		});

		// Runs when reset is clicked.
		btnEnd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gameRestart();
			}
		});
	}

	public void placeMines() {
		// Sets mines throughout the minefield array
		int minesPlaced = 0;
		Random random = new Random();
		while (minesPlaced < mineNumber) {
			int x = random.nextInt(mapWidth);
			int y = random.nextInt(mapHeight);
			// make sure we don't place a mine on top of another

			if (mineField[x][y].getBomb() != true) {
				mineField[x][y] = new ActualMines(x * mineSize + distanceX, y * mineSize + distanceY);
				// Gives minelocation the x and y of each mine placed.
				mineLocation[minesPlaced][0] = x;
				mineLocation[minesPlaced][1] = y;
				minesPlaced++;
			}
		}
	}

	public void calculateHints() {
		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				if (mineField[x][y].getBomb() == false) {
					if (MineScan(x, y) > 0) {
						// Scans all mines. if no bomb found, scans surrounding squares for bombs, if
						// bombs found, changes minesquare to numbered mine, gives it number of bombs,
						// and sets appropriate hidden image, to be revealed on mouse click.
						mineField[x][y] = new NumberedMines(x * mineSize + distanceX, y * mineSize + distanceY);
						mineField[x][y].setBombRadius(MineScan(x, y));
						mineField[x][y].hiddenImage();
					}
				}
			}
		}
	}

	public int MineScan(int x, int y) {
		int mines = 0;
		// check mines in all directions, returns number of mines
		mines += mineAt(x - 1, y - 1);
		mines += mineAt(x - 1, y);
		mines += mineAt(x - 1, y + 1);
		mines += mineAt(x, y - 1);
		mines += mineAt(x, y + 1);
		mines += mineAt(x + 1, y - 1);
		mines += mineAt(x + 1, y);
		mines += mineAt(x + 1, y + 1);

		return (mines);
	}

	public int mineAt(int x, int y) {
		// return a 1 if specified square isn't out of bounds AND if there is actually a
		// bomb there.
		if (y >= 0 && y < mapHeight && x >= 0 && x < mapWidth && mineField[x][y].getBomb() == true) {
			return 1;
		} else {
			return 0;
		}
	}

	public void drawMinefield() {
		// For Testing use only. Prints out bomb, number and blank square location to console.
		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {
				if (mineField[x][y].getBomb() == true)
					System.out.print('*');
				else
					System.out.print(mineField[x][y].getBombRadius());
			}
			System.out.print("\n");
		}
	}

	public void revealSquare() {
		// If a square is clicked on, it will reveal itself.
		// Prevents error from occurring when clicking out of bounds.
		if (mouseX < distanceX + mapWidth * mineSize && mouseY < distanceY + mapHeight * mineSize && mouseX > distanceX
				&& mouseY > distanceY) {

			//Math magic that converts the x and y location clicked, to the array position.
			mouseX = mouseX - distanceX;
			mouseY = mouseY - distanceY;
			mouseX = mouseX / mineSize;
			mouseY = mouseY / mineSize;
			MineSquare selectedMineSquare = mineField[mouseX][mouseY];

			//Reveals square clicked on.
			selectedMineSquare.pictureChange();
			selectedMineSquare.setRevealed(true);

			// If clicked object is a bomb, game ends. Also reveals all other bombs, and
			// stops user from clicking.
			if (mineField[mouseX][mouseY].getBomb() == true) {
				for (int x = 0; x < mineNumber; x++) {
					mineField[mineLocation[x][0]][mineLocation[x][1]].pictureChange();
					gameState.setText("Game Over");
					gameStarted = false;
				}
			}
			
			//If clicked square is blank, reveals all blank squares touching till it hits a numbered square.
			if (selectedMineSquare instanceof EmptySquare) {
				revealBlankSquare(mouseX, mouseY);
			}
			
			//Reveals the bomb.
			revealedCount = 0;
			for (int i = 0; i < mapWidth; i++) {
				for (int j = 0; j < mapHeight; j++) {
					if (mineField[i][j].isRevealed()) {
						revealedCount++;
						//Won't loop if gameStarted is false. This is because of a very specific bug 
						//where the number of bombs and squares already revealed is equal to the number of squares needed to be revealed to win.
						if (gameStarted && revealedCount == mapWidth * mapHeight - mineNumber) {
							gameState.setText("You Win");
							System.out.println(revealedCount);
							gameStarted = false;
						}
					}
				}
			}
			repaint();
		}
	}

	// Reveals other squares that are blank next to it. Will not check squares out
	// of bounds, and will not check self.
	public void revealBlankSquare(final int x, final int y) {
		for (int tempX = x - 1; tempX <= x + 1; tempX++) {
			for (int tempY = y - 1; tempY <= y + 1; tempY++) {
				boolean inBounds = tempX <= mapWidth - 1 && tempX >= 0 && tempY <= mapHeight - 1 && tempY >= 0;
				if (inBounds) {
					MineSquare currentSquare = mineField[tempX][tempY];
					boolean selfSquare = tempY == y && tempX == x;
					if (!currentSquare.isRevealed() && !selfSquare) {
						if (currentSquare instanceof EmptySquare || currentSquare instanceof NumberedMines) {
							currentSquare.pictureChange();
							currentSquare.setRevealed(true);
						}
						if (currentSquare instanceof EmptySquare) {
							revealBlankSquare(tempX, tempY);
						}
					}
				}
			}
		}

	}

	public static void main(String[] args) {
		// calls the driver to create a new minefield
		m.setSize(200, 250);
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.setVisible(true);
	}

	//Paints the grid.
	public void paint(Graphics g) {
		super.paint(g);
		try {
			for (int i = 0; i < mapWidth; i++) {
				for (int j = 0; j < mapHeight; j++) {
					mineField[i][j].drawImage(g);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// Gets mouse location, Repaints after click.
		mouseX = e.getX();
		mouseY = e.getY();
		
		//System.out.println(mouseX + " " + mouseY); // temp. outputs to console mouse location.
		// on click, reveals square. Will only run after start button has been clicked.
		if (gameStarted) {
			revealSquare();
		}

	}

	public void gameRestart() {
		// Resets All Variables
		mapWidth = 0;
		mapHeight = 0;
		mineNumber = 0;
		mineSize = 20;
		distanceX = 50;
		distanceY = 50;

		// Puts all buttons and labels back to original state
		gameStarted = false;
		textField.setVisible(true);
		textField_1.setVisible(true);
		textField_2.setVisible(true);
		lblMineLength.setVisible(true);
		lblMineWidth.setVisible(true);
		lblNumberOfBombs.setVisible(true);
		btnStart.setVisible(true);
		btnEnd.setVisible(false);
		gameState.setVisible(false);
		mineField = null;
		mineLocation = null;
		revealedCount = 0;

		// Resizes Map to fit menu
		m.setSize(200, 250);
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

}
