/**
 * @author Angus Hung, G00867342
 * @assignment Project 2, CIS256
 */

import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import javax.swing.JOptionPane;
import java.awt.Font;

/** Class for the driver of the Snake game. */
public class SnakeGame extends Frame {
	/* Keeps track of whether game is over. */
	private boolean endgame = false; 
	/* Boolean that keeps track whether it is the first round. */
	private boolean firstRound = true;
	/* Position of number square */
	private Point numberSquare;
	/* Number square value */
	private int numberSquareValue = 0;
	/* Number square side length */
	private int numberSquareSideLength = 3*SegmentSize;
	/* Player labels */
	private Label playerOneLabel;
	private Label playerTwoLabel;
	/* Panel holding labels */
	private Panel panel;

	/* Board settings */
	final static int BoardWidth = 60;
	final static int BoardHeight = 40;
	final static int SegmentSize = 10;
	/* Player settings */
	private Snake playerOne = new Snake (Color.blue, new Point(20, 10), this);
	private Snake playerTwo = new Snake (Color.red, new Point(20, 30), this);
	/** Driver main method */
	public static void main (String [ ] args) {
		SnakeGame world = new SnakeGame();

		world.show(); // must show the window before running it
		
		world.run();


	}
	/** Constructor sets window settings and adds key listeners */
	public SnakeGame ( ) {
		this.setSize ((BoardWidth+1)*SegmentSize, BoardHeight*SegmentSize + 30);
		setTitle ("Snake Game");
		addKeyListener (new KeyReader( ));
		addWindowListener(new CloseQuit( ));

		panel = new Panel();
		panel.setLayout(new FlowLayout());

		Font font = new Font("Arial", Font.BOLD, 12);

		playerOneLabel = new Label("Player 1 Score: 0, Lifetime: 20", Label.CENTER);
		playerOneLabel.setFont(font);
		playerOneLabel.setBackground(Color.BLUE);

		playerTwoLabel = new Label("Player 2 Score: 0, Lifetime: 20", Label.CENTER);
		playerTwoLabel.setFont(font);
		playerTwoLabel.setBackground(Color.RED);

		this.setLayout(new BorderLayout());
		panel.add(playerOneLabel);		
		panel.add(playerTwoLabel);
		this.add(panel, BorderLayout.SOUTH);
	}
	/** Starts the game */
	public void run ( ) {
		while (true) {
			if (isEndgame()) {
				resetGame();
			}
			movePieces( );
			repaint( );
			try {
				Thread.sleep(100);
			} catch (Exception e) { }
		}
	}
	/** Resets the game state. */
	public void resetGame() {
		playerOne = new Snake (Color.blue, new Point(20, 10), this);
		playerTwo = new Snake (Color.red, new Point(20, 30), this);
		numberSquareValue = 0;
		firstRound = true;
		endgame = false;

	}

	/** Takes the position POS of the snake and sees whether it is a 
	 * scoring position
	 */
	public int squareScore(Snake player) {
		if (isScoringPosition(player.position())) {
			int rtn = numberSquareValue;
			newNumberSquare();
			return rtn;
		} else {
			return 0; // returns 0 otherwise
		}
	}
	/** Method for drawing the number square. */
	private void drawNumberSquare(Graphics g) {
		/* Sets the color of the number square to yellow. */
		g.setColor(Color.yellow);
		/* Draws the number square. I have set the number square side length to 3 times the SegmentSize. */
		g.fillRect(numberSquare.x*SegmentSize, numberSquare.y*SegmentSize, numberSquareSideLength, numberSquareSideLength);

		g.setColor(Color.black);
		Font numberSquareFont = new Font("Arial", Font.BOLD, 14);
		g.setFont(numberSquareFont);
		g.drawString(Integer.toString(numberSquareValue), numberSquare.x*SegmentSize+SegmentSize, numberSquare.y*SegmentSize+2*SegmentSize);
	}
	/** Method for determining whether input position is a scoring position. */
	public boolean isScoringPosition(Point pos) {
		return (((pos.x >= numberSquare.x) && (pos.x <= numberSquare.x + 3)) && ((pos.y >= numberSquare.y) && (pos.y <= numberSquare.y + 3)));
	}

	/** Shows player positions */
	public void paint(Graphics g) {
		playerOne.paint(g);
		playerTwo.paint(g);

		if (firstRound) {
			newNumberSquare();
			firstRound = false;
			drawNumberSquare(g);
			return;
		}

		int squareScore1 = squareScore(playerOne);
		int squareScore2 = squareScore(playerTwo);
		if ((squareScore1 != 0) || (squareScore2 != 0)) {
			if (squareScore1 != 0) {
				playerOne.addToScore(squareScore1);
			}
			if (squareScore2 != 0) {
				playerTwo.addToScore(squareScore2);
			}
		}

		playerOneLabel.setText("Player 1 Score: " + playerOne.score() + ", Lifetime: " + playerOne.lifetime());
		playerTwoLabel.setText("Player 2 Score: " + playerTwo.score() + ", Lifetime: " + playerTwo.lifetime());
		drawNumberSquare(g);
		
		
	}
	/** Updates player positions */
	public void movePieces ( ) {
		playerOne.move(this);
		playerTwo.move(this);
	}

 	public boolean canMove (Point np) {
	// get x, y coordinate
		int x = np.x;
		int y = np.y;
		// test playing board
		if ((x <= 0) || (y <= 0)) return false;
		if ((x >= BoardWidth) || (y >= BoardHeight-(panel.getHeight()/SegmentSize))) return false;
		// test snakes
		if (playerOne.inPosition(np)) return false;
		if (playerTwo.inPosition(np)) return false;
		// ok, safe square
		return true;
	}

	/** Creates a new, randomly distributed number value. Use the inPosition
	 *	method provided by the two snakes to ensure that the number is
	 *	not placed in a square already occupied by a snake.
	 */
	public void newNumberSquare() {
		do {
			/* Randomly generates new point for number square anywhere on the board.  */
			numberSquare = new Point((int) (Math.random() * (BoardWidth*(1.0-(10.0/BoardWidth)))+3), (int) (Math.random() * (BoardHeight*(1.0-(10.0/BoardHeight)))+3));
		} while (playerOne.inPosition(numberSquare) || playerTwo.inPosition(numberSquare));
		numberSquareValue++;
	}

	/** Ends the game.
	 */
	public void endGame(Snake player) {
		String display;
		if (player == playerOne) {
			display = "Player 1 lost. Player 2 wins!";
		} else {
			display = "Player 2 lost. Player 1 wins!";
		}

		JOptionPane.showMessageDialog(null, display);
		endgame = true;
	}
	/** Getter for endgame state.*/
	public boolean isEndgame() {
		return endgame;
	}
private class KeyReader extends KeyAdapter {
	public void keyPressed (KeyEvent e) {
		char c = e.getKeyChar( );
		switch (c) {
		case 'q': playerOne.setDirection('Z'); break;
		case 'a': playerOne.setDirection('W'); break;
		case 'd': playerOne.setDirection('E'); break;
		case 'w': playerOne.setDirection('N'); break;
		case 's': playerOne.setDirection('S'); break;
		case 'p': playerTwo.setDirection('Z'); break;
		case 'j': playerTwo.setDirection('W'); break;
		case 'l': playerTwo.setDirection('E'); break;
		case 'i': playerTwo.setDirection('N'); break;
		case 'k': playerTwo.setDirection('S'); break;
		}
	}
}
/* Inner class for handling clicks to the "x" button on the right side of the window.
*/
private class CloseQuit extends WindowAdapter {
	public void windowClosing( WindowEvent e ) {
		JOptionPane.showMessageDialog(null, "Quitting so soon?");
        System.exit( 0 );
    }
}

}

