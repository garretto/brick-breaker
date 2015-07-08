package processinggame;

import java.awt.event.KeyEvent;
import processing.core.PApplet;
import processing.core.PImage;

public class ProcessingGame extends PApplet {
	
	int PADDLE_SPEED = 5;
	PImage backgroundImage;
	
	Rect paddle; 
	Ellipse ball;
	boolean keys[] = new boolean[200];

	public void setup() {
		frameRate(50);
		backgroundImage = loadImage("background.jpg");
		size(backgroundImage.width, backgroundImage.height);
		
		paddle = new Rect(width/2, height-30, 60, 20);
		ball = new Ellipse(width/2, height/2, 30, 30);
		ball.speedX = 5;
		ball.speedY = 4;
		ball.red = 255;
	}

	public void draw() {
		background(backgroundImage);
		
		controlPaddle();
		bounceBall();
		
		drawMoveObjects();
	}
	
	public void drawMoveObjects() {
		ball.move();
		paddle.move();
		ball.draw();
		paddle.draw();
	}

	public void controlPaddle() {
		if (keys[KeyEvent.VK_LEFT]) {
			paddle.speedX = -PADDLE_SPEED;
		} else if (keys[KeyEvent.VK_RIGHT]) {
			paddle.speedX = PADDLE_SPEED;
		} else {
			paddle.speedX = 0;
		}
	}
	
	public void bounceBall() {
		if (ball.x <= 0 || ball.x + ball.width >= width) {
			ball.speedX *= -1;
		}
		
		if (ball.y <= 0 || ball.y + ball.height >= height) {
			ball.speedY *= -1;
		}
		
		if (ball.overlaps(paddle)) {
			ball.speedY *= -1;
		}
	}
	
	public void keyPressed() {
		keys[keyCode] = true;
	}
	
	public void keyReleased() {
		keys[keyCode] = false;
	}	
	
	public class GameObject {
		float x;
		float y;
		float speedX;
		float speedY;
		int width;
		int height;
		
		int red;
		int green;
		int blue;
		
		int strokeWeight;
		int strokeColor;

		public boolean overlaps(GameObject other) {
			if (x < other.x + other.width && x + width > other.x
					&& y < other.y + other.height && height + y > other.y) {
				return true;
			} else {
				return false;
			}
		}

		public void move() {
			x += speedX;
			y += speedY;
		}
		
		public void setColors() {
			fill(red, green, blue);
			strokeWeight(strokeWeight);
			stroke(strokeColor);
		}
	}

	public class Sprite extends GameObject {
		PImage imageFile;

		public Sprite(String imageFileName, float newX, float newY) {
			imageFile = loadImage(imageFileName);
			width = imageFile.width;
			height = imageFile.height;
			x = newX;
			y = newY;
		}

		public void draw() {
			image(imageFile, x, y);
		}
	}
	
	public class Rect extends GameObject {
		public Rect(float newX, float newY, int newWidth, int newHeight) {
			width = newWidth;
			height = newHeight;
			x = newX;
			y = newY;
		}

		public void draw() {
			setColors();
			rect(x, y, width, height);
		}
	}
	
	public class Ellipse extends GameObject {
		public Ellipse(float newX, float newY, int newWidth, int newHeight) {
			ellipseMode(CORNER);
			width = newWidth;
			height = newHeight;
			x = newX;
			y = newY;
		}

		public void draw() {
			setColors();
			ellipse(x, y, width, height);
		}
	}
}
