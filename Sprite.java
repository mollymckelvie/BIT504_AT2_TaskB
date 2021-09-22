package pongGame;
import java.awt.Color;
import java.awt.Rectangle;

public class Sprite {

	private int xPosition, yPosition;
	private int xVelocity, yVelocity;
	private int startXPosition, startYPosition;
	private int width, height;
	private Color colour; 
	
	// Getter and Setter methods
	// X Position
	public int getxPosition() { //get
		return xPosition;
	}
	
	public void setxPosition(int xPosition) { //set
		this.xPosition = xPosition; 
	}
	
	public void setxPosition(int newX, int panelWidth) { //keeps Sprite within the bounds of screen
	     xPosition = newX;
	     if(xPosition < 0) 
	     {
	    	 xPosition = 0;
	     }
	     else if(xPosition + width > panelWidth) 
	     {
	    	 xPosition = panelWidth - width; 
	     } 	 
	 }
	
	// Y Position
	public int getyPosition() { //get
		return yPosition;
	}
	
	public void setyPosition(int yPosition) { //set
		this.yPosition = yPosition;
	}
	
	public void setyPosition(int newY, int panelHeight) { //keeps Sprite within the bounds of screen
	     yPosition = newY;
	     if(yPosition < 0) 
	     {
	    	 yPosition = 0;
	     }
	     else if(yPosition + height > panelHeight) 
	     {
	    	 yPosition = panelHeight - height;
	     }
	 }
	
	// Start position
	public void setStartPosition(int startX, int startY) {
		startXPosition = startX;
		startYPosition = startY;
	}
	
	public void resetToStartPosition() {
		setxPosition(startXPosition);
		setyPosition(startYPosition);
	}
	
	// X Velocity
	public int getxVelocity() { //get
		return xVelocity;
	}
	
	public void setxVelocity(int xVelocity) { //set
		this.xVelocity = xVelocity; 
	}
	
	// Y Velocity
	public int getyVelocity() { //get
		return yVelocity;
	}
	
	public void setyVelocity(int yVelocity) { //set
		this.yVelocity = yVelocity; 
	}
	
	// Width
	public int getWidth() { //get
		return width;
	}
	
	public void setWidth(int width) { //set
		this.width = width;
	}
	
	// Height
	public int getHeight() { //get
		return height;
	}
	
	public void setHeight(int height) { //set
		this.height = height;
	}
	
	// Colour
	public Color getColour() { //get
		return colour;
	}
	
	public void setColour(Color colour) { //set
		this.colour = colour; 
	}
	
	// Rectangle
	public Rectangle getRectangle() {
		return new Rectangle(getxPosition(), getyPosition(), getWidth(), getHeight());
	}
}
