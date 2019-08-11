import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;



@SuppressWarnings("serial")
public class renderFrame extends JPanel{
	@Override
	public void paintComponent(Graphics g) {
	super.paintComponents(g);
	Snake snake = Snake.snake;
	g.setColor(Color.GREEN);
	g.fillRect(0,0,snake.xSize,snake.ySize);

	
	g.setColor(Color.RED);
	g.fillRect(snake.head.x * Snake.PIXEL, snake.head.y * Snake.PIXEL, Snake.PIXEL, Snake.PIXEL);
	g.setColor(Color.ORANGE);
	for (Point point : snake.snakeParts) {
		g.fillRect(point.x * Snake.PIXEL, point.y * Snake.PIXEL, Snake.PIXEL, Snake.PIXEL);
	}
	
		g.setColor(Color.BLACK);
	for (Point point : snake.obstacles) {
		g.fillRect(point.x * Snake.PIXEL, point.y * Snake.PIXEL, Snake.PIXEL, Snake.PIXEL);
	}
	
	g.setColor(Color.RED);
	g.fillRect(snake.cherry.x * Snake.PIXEL, snake.cherry.y * Snake.PIXEL, Snake.PIXEL, Snake.PIXEL);
	
	
	
	String string = "Score: " + snake.score + ", Length: " + snake.tailLength;

	g.setColor(Color.white);

	g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), 10);
	g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
	string = "Game Over!";

	if (snake.over) {
		g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) snake.screenDimension.getHeight() / 4);
	}

	string = "Paused!";

	if (snake.paused && !snake.over) {
		g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) snake.screenDimension.getHeight() / 4);
	}
	
	
	}
	
	
}