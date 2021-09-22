package pongGame;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import javax.swing.JPanel;

public class PongPanel extends JPanel implements ActionListener, KeyListener

{
	private final static Color BACKGROUND_COLOUR = Color.BLUE;
	private final static int TIMER_DELAY = 5;
	private final static int BALL_MOVEMENT_SPEED = 2;
	private final static int POINTS_TO_WIN = 5; // How many points to win
	int player1Score = 0, player2Score = 0; // Will hold the current score for each player
	Player gameWinner; // Will hold the player who has won
	private final static int SCORE_TEXT_X = 200; // score padding horizontal
	private final static int SCORE_TEXT_Y = 100; // score padding vertical 
	private final static int SCORE_FONT_SIZE = 50; //font size
	private final static String SCORE_FONT_FAMILY = "Serif"; //font family
	private final static int WINNER_TEXT_X = 200; // padding for winning text horizontal
	private final static int WINNER_TEXT_Y = 200; // padding for winning text vertical
	private final static int WINNER_FONT_SIZE = 70; 
	private final static String WINNER_FONT_FAMILY = "Serif";
	private final static String WINNER_TEXT = "WINNER WINNER CHICKEN DINNER!";
	GameState gameState = GameState.Starting;
	Ball ball; 
	Paddle paddle1, paddle2;
	
	
	public PongPanel() {
		setBackground(BACKGROUND_COLOUR);
		Timer timer = new Timer(TIMER_DELAY, this);
			timer.start();
		addKeyListener(this);
		setFocusable(true);
	}
	
	 private void update() {
		switch(gameState) 
		{
			case Starting: 
			{
				createObjects();
				gameState = GameState.Playing; 
				ball.setxVelocity(BALL_MOVEMENT_SPEED);
				ball.setyVelocity(BALL_MOVEMENT_SPEED);
				break;
			}
			case Playing:
			{
				moveObject(paddle1); // Move paddle 1
				moveObject(paddle2); // Move paddle 2
				moveObject(ball); // Move ball
				checkWallBounce(); // Check for wall bounce
				checkPaddleBounce(); // Check ball collision with paddle
				checkWin(); //Check to see if there is a winner based on the scores
				break;
			}
			case GameOver:
			{
				break;
			}
		}
	 }
	 
	 private void paintDottedLine(Graphics g) {
		 Graphics2D g2d = (Graphics2D) g.create();
		 Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		 g2d.setStroke(dashed);
		 g2d.setPaint(Color.WHITE);
		 g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
		 g2d.dispose();
	 }
	
	@Override
	public void keyTyped(KeyEvent event) {
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_W) 
		{
			paddle1.setyVelocity(-3);
		}
		else if(event.getKeyCode() == KeyEvent.VK_S)
		{
			paddle1.setyVelocity(3);
		}
		
		if(event.getKeyCode() == KeyEvent.VK_UP) 
		{
			paddle2.setyVelocity(-3);
		}
		else if(event.getKeyCode() == KeyEvent.VK_DOWN)
		{
			paddle2.setyVelocity(3);
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S)
		{
			paddle1.setyVelocity(0);
		}
		
		if(event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN)
		{
			paddle2.setyVelocity(0);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		update();
		repaint();
	}
	
	 @Override
	 public void paintComponent(Graphics g) {
	     super.paintComponent(g);
	     paintDottedLine(g);
	     if(gameState != GameState.Starting) 
	     {
	    	 paintSprite(g, ball);
	    	 paintSprite(g, paddle1);
	    	 paintSprite(g, paddle2);
	    	 paintScore(g);
	    	 paintWinner(g);
	     }
	 }	
	 
	 public void createObjects() { // creates the visible object
		 ball = new Ball(getWidth(), getHeight());
		 paddle1 = new Paddle(Player.One, getWidth(), getHeight());
		 paddle2 = new Paddle(Player.Two, getWidth(), getHeight());
	 }
	 
	 public void paintSprite(Graphics g, Sprite sprite) {
		g.setColor(sprite.getColour());
		g.fillRect(sprite.getxPosition(),  sprite.getyPosition(), sprite.getWidth(), sprite.getHeight());
	 }
	 
	 private void moveObject(Sprite obj) {
		 obj.setxPosition(obj.getxPosition() + obj.getxVelocity(), getWidth());
		 obj.setyPosition(obj.getyPosition() + obj.getyVelocity(), getHeight());
	 }
	 
	 private void checkWallBounce() {
		 if(ball.getxPosition() <= 0) // Hit left side of the screen
		 {
			 ball.setxVelocity(-ball.getxVelocity());
			 addScore(Player.Two);
			 resetBall();
		 }
		 else if(ball.getxPosition() >= getWidth() - ball.getWidth()) // Hit right side of the screen
		 {
			 ball.setxVelocity(-ball.getxVelocity());
			 addScore(Player.One);
			 resetBall();
		 }
		 if(ball.getyPosition() <= 0 || ball.getyPosition() >= getHeight() - ball.getHeight()) // Hit the top or bottom of the screen
		 {
			 ball.setyVelocity(-ball.getyVelocity());
		 }
	 }
	 
	 private void resetBall() { // Resets the ball to the start position after scoring
		 ball.resetToStartPosition();
	 }
	 
	 private void checkPaddleBounce() { // Makes the paddle solid, allowing bounce
		 if(ball.getxVelocity() < 0 && ball.getRectangle().intersects(paddle1.getRectangle())) 
		 {
			 ball.setxVelocity(BALL_MOVEMENT_SPEED);
		 }
		 if(ball.getxVelocity() > 0 && ball.getRectangle().intersects(paddle2.getRectangle()))
		 {
			 ball.setxVelocity(-BALL_MOVEMENT_SPEED);
		 }
	 }
	 
	 private void addScore(Player player) {
		 if(player == Player.One) 
		 {
			player1Score++; // increases Player 1 score by one
		 }
		 else if(player == Player.Two)
		 {
			 player2Score++; // increases Player 2 score by one
		 }
	 }

	 private void checkWin() {
		 if(player1Score >= POINTS_TO_WIN) 
		 {
			gameWinner = Player.One;
			gameState = GameState.GameOver;
		 }
		 else if(player2Score >= POINTS_TO_WIN)
		 {
			 gameWinner = Player.Two;
			 gameState = GameState.GameOver;
		 }
	 }
	 
	 private void paintScore(Graphics g) {
		 Font scoreFont = new Font(SCORE_FONT_FAMILY, Font.BOLD, SCORE_FONT_SIZE); //the physical details of the font
		 String leftScore = Integer.toString(player1Score); //converts number to words
		 String rightScore = Integer.toString(player2Score); //converts number to words
		 g.setFont(scoreFont);
		 g.drawString(leftScore, SCORE_TEXT_X, SCORE_TEXT_Y); // setting the location of the score
		 g.drawString(rightScore, getWidth()-SCORE_TEXT_X, SCORE_TEXT_Y);
	 }
	 
	 private void paintWinner(Graphics g) {
		 if(gameWinner != null) // if the game winner is not equal to null, so there is a winner
		 {
			 Font winnerFont = new Font(WINNER_FONT_FAMILY, Font.BOLD, WINNER_FONT_SIZE);
			 g.setFont(winnerFont);
			 int xPosition = getWidth() / 2;
			 if(gameWinner == Player.One) 
			 {
				 xPosition -= WINNER_TEXT_X;
			 }
			 else if(gameWinner == Player.Two)
			 {
				 xPosition += WINNER_TEXT_X;
			 }
			 g.drawString(WINNER_TEXT, xPosition, WINNER_TEXT_Y);
		 }
	 }
	 
}
