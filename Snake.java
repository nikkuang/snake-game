import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.Vector;

/** Provided class for the Snake object. */
class Snake {
	/* Score so far of the Snake */
	private int score = 0;
	/* Beginning lifetime is 20 */
	private int lifetime = 20;

	private SnakeGame game; 
	private Color color;
	private Point position;
	private char direction = 'E';
	private Queue body = new QueueLL();
	private Queue commands = new QueueLL();
	public Snake (Color c, Point sp, SnakeGame game) {
		color = c;
		for (int i = 0; i < SnakeGame.SegmentSize; i++) {
			position = new Point(sp.x + i, sp.y);
			body.enqueue(position);
		}
		this.game = game;
	}
 	public void setDirection (char d) {
		commands.enqueue (new
		Character(d));
	}
	public void move (SnakeGame game) {
		// first see if we should change direction
		if (commands.size( ) > 0) {
			Character c = (Character) commands.front( );
			// just peek
			commands.dequeue( );
			direction = c.charValue( );
			// Character wrapper to char
			if (direction == 'Z')
				return;
		}
		// then find new position
		Point np = newPosition( );
		// newPosition( ) on next slide
		if (game.canMove(np)) {
			// erase one segment, add another
			body.dequeue( );
			body.enqueue (np);
			position
			= np;

					/* Decrements lifetime. */
			lifetime--;
			if (lifetime <= 0) { // if not more life, remove one segment
				if (body.size() == 1) {
					game.endGame(this);
				} else {
					body.dequeue();
				}
				lifetime = 20;
			}
		}
	}

	private Point newPosition ( ) {
		int x = position.x;
		int y = position.y;
		if (direction == 'E') x++;
		else if (direction == 'W') x--;
		else if (direction == 'N') y--;
		else if (direction == 'S') y++;
		return new Point(x, y);
	}
	/** Predicate function that determines whether or not NP is in the same position as the Snake */
	public boolean inPosition (Point np) {
		Enumeration e = ((Vector) body).elements();
		while (e.hasMoreElements()) {
			Point location = (Point) e.nextElement();
			if (np.equals(location)) return true;
		}
		return false;
	}

	public void paint(Graphics g) {
		g.setColor(color);
		Enumeration e = ((Vector) body).elements();
		// iterator stuff
		while (e.hasMoreElements()) {
			Point p = (Point) e.nextElement();
			g.fillOval(5 + SnakeGame.SegmentSize * p.x,
				15 + SnakeGame.SegmentSize * p.y,
			SnakeGame.SegmentSize,
			SnakeGame.SegmentSize);
		}
	}
	/** Getter for the snake's position. */
	public Point position() {
		return position;
	}
	/** Getter for the snake's score. */
	public int score() {
		return score;
	}
	/** Getter for the player's lifetime. */
	public int lifetime() {
		return lifetime;
	}
	/** Adds N points to score, and resets lifetime. */
	public void addToScore(int n) {
		score += n;
		lifetime = 20;
	}

}
