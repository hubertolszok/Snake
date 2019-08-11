import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Snake implements ActionListener, KeyListener {
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, PIXEL = 20;
	public static final int xSize = 407, ySize = 427;
	static Scanner userInput = new Scanner(System.in);
	public JFrame jframe;
	public static Snake snake;
	public Timer timer = new Timer(10, this);
	Dimension screenDimension;
	public renderFrame renderframe;

	public Point head;
	public Point cherry;
	public int ticks;
	public int direction = RIGHT;
	public int tailLength;
	public int obsaclesNumber = 10;
	public int score;
	public int speed;
	public boolean over, paused;
	public Random random;

	public ArrayList<Point> snakeParts = new ArrayList<Point>();
	public ArrayList<Point> obstacles = new ArrayList<Point>();

	public void startGame() {
		over = false;
		paused = false;
		timer.start();
		snakeParts.clear();
		obstacles.clear();
		head = new Point(0, 0);
		ticks = 0;
		tailLength = 3;
		score = 0;
		direction = RIGHT;
		random = new Random();
		speed = 15;

		for (int i = 0; i < obsaclesNumber; i++) {
			obstacles.add(new Point(random.nextInt(19), random.nextInt(19)));
		}

		cherry = new Point(random.nextInt(19), random.nextInt(19));
		while (checkCherry(cherry)) {
			cherry = new Point(random.nextInt(19), random.nextInt(19));
		}

	}

	public Snake() {
		screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		jframe = new JFrame("Snake");
		jframe.setVisible(true);
		jframe.setSize(xSize, ySize);
		jframe.setLocation(screenDimension.width / 2 - xSize / 2, screenDimension.height / 2 - ySize / 2);
		jframe.add(renderframe = new renderFrame());
		jframe.setResizable(false);
		jframe.addKeyListener(this);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		startGame();

	}

	public static void main(String[] args) {
		snake = new Snake();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();

		if (!paused) {
			if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && direction != RIGHT) {
				direction = LEFT;
			}

			if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && direction != LEFT) {
				direction = RIGHT;
			}

			if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && direction != DOWN) {
				direction = UP;
			}

			if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && direction != UP) {
				direction = DOWN;
			}

		}
		if (i == KeyEvent.VK_SPACE) {
			if (over) {
				startGame();
			} else {
				paused = !paused;
			}
		}

		if (i == KeyEvent.VK_M) {
			System.exit(0);
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		renderframe.repaint();
		ticks++;

		if (ticks % speed == 0 && head != null && !over && !paused) {
			snakeParts.add(new Point(head.x, head.y));

			if (direction == UP) {
				if (head.y > 0 && collision(head.x, head.y - 1)) {
					head = new Point(head.x, head.y - 1);
				} else {
					over = true;
				}
			} else if (direction == DOWN) {
				if (head.y < 19 && collision(head.x, head.y + 1)) {
					head = new Point(head.x, head.y + 1);
				} else {
					over = true;
				}
			} else if (direction == LEFT) {
				if (head.x > 0 && collision(head.x - 1, head.y)) {
					head = new Point(head.x - 1, head.y);
				} else {
					over = true;
				}
			} else if (direction == RIGHT) {
				if (head.x < 19 && collision(head.x + 1, head.y)) {
					head = new Point(head.x + 1, head.y);
				} else {
					over = true;
				}
			}

			if (snakeParts.size() > tailLength) {
				snakeParts.remove(0);

			}

		}

		if (head.equals(cherry)) {
			score += 10;
			tailLength++;
			cherry.setLocation(random.nextInt(79), random.nextInt(66));
			cherry = new Point(random.nextInt(19), random.nextInt(19));
			while (checkCherry(cherry)) {
				cherry = new Point(random.nextInt(19), random.nextInt(19));
			}
			if (speed > 4 && score % 40 == 0) {
				speed = speed - 1;
			}
		}

	}

	public boolean collision(int x, int y) {
		for (Point point : snakeParts) {
			if (point.equals(new Point(x, y))) {
				return false;
			}
		}
		for (Point point : obstacles) {
			if (point.equals(new Point(x, y))) {
				return false;
			}
		}
		return true;
	}

	public boolean checkCherry(Point c) {

		for (Point ppp : obstacles) {
			if (c.equals(ppp)) {
				return true;
			}
		}
		return false;

	}

}