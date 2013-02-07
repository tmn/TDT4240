package net.trimn.pongexercise;

import sheep.game.State;
import sheep.graphics.Font;
import sheep.input.TouchListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

/**
 * Øving1 - task4 i prigramvarearkitektur
 * Øving2 - Implement Singleton
 * @author Tri M. Nguyen 
 */
public class PongState extends State implements TouchListener
{
	private int goal;
	private int defaultSpeedXandY = 220;
	
	private int canvasWidth;
	private int canvasHeight;
	
	private Player player1;
	private Player player2;
	
	private Ball ball;
	private long roundStart;
	
	private int gameState; // 0: start, 1: game on, 2: player1 wins, 3: player 2 wins
	
	private Line midLine;
	
	public PongState(Context ctx)
	{
		gameState = 0;
		goal = 10; // first to 10
		
		// create players
		player1 = new Player();	// lower
		player2 = new Player();	// upper
		
		// create ball
		ball = Ball.getInstance();
		ball.setSpeed(defaultSpeedXandY, defaultSpeedXandY);
		
		midLine = new Line();
	}
	
	public void draw(Canvas canvas)
	{
		canvas.drawColor(Color.BLACK);
		canvasWidth = canvas.getWidth();
		canvasHeight = canvas.getHeight();
		
		midLine.setHeight(2);
		midLine.setWidth(canvasWidth);
		midLine.setColor(Color.RED);
		midLine.setPosition(0, canvasHeight/2-1);
		midLine.draw(canvas);
		
		canvas.drawText("Player1: " + player1.getScore(), 10, canvasHeight/2-20, Font.WHITE_SANS_BOLD_20);
		canvas.drawText("Player2: " + player2.getScore(), 10, canvasHeight/2+30, Font.WHITE_SANS_BOLD_20);
		
		ball.draw(canvas);
		player1.draw(canvas);
		player2.draw(canvas);
		
		if (gameState == 0)
		{
			this.newRound();
			this.resetPlayers();
			gameState = 1;
		}
		else if (gameState > 1)
		{
			ball.setSpeed(0, 0);
			String player = (gameState == 2) ? "Player 1" : "Player 2";
			canvas.drawText(player + " won!", canvasWidth/2-10, canvasHeight/2, Font.WHITE_SANS_BOLD_20);
		}
	}
	
	public void update(float dt)
	{
		// ball collides with player2
		if (ball.getCollisionBox().intersect(player2.getCollisionBox()))
		{
			if (ball.getSpeed().getY() < 0) ball.setYSpeed(-ball.getSpeed().getY());
		}
		// ballcollides with player1
		else if (ball.getCollisionBox().intersect(player1.getCollisionBox()))
		{
			if (ball.getSpeed().getY() > 0) ball.setYSpeed(-ball.getSpeed().getY());
		}
		// player1 get a point
		else if (ball.getY() < 0)
		{
			player1.increaseScore();
			this.newRound();
		}
		// player2 get a point
		else if (ball.getY() > canvasHeight - ball.getSize())
		{
			player2.increaseScore();
			this.newRound();
		}
		
		// bounce against the east wall
		if (ball.getX() > canvasWidth - ball.getSize()) {
			if (ball.getSpeed().getX() > 0) ball.setXSpeed(-ball.getSpeed().getX());
		}
		// bounce against the west wall
		else if(ball.getX() < 0) {
			if (ball.getSpeed().getX() < 0) ball.setXSpeed(-ball.getSpeed().getX());
		}

		// Smack the ball if player haven't gotten any points in 5 seconds
		if (System.currentTimeMillis() - roundStart > 5000) {
			float newSpeedY = (ball.getSpeed().getY() > 0) ? ball.getSpeed().getY()+40 : ball.getSpeed().getY()-40;
			float newSpeedX = (ball.getSpeed().getX() > 0) ? ball.getSpeed().getX()+10 : ball.getSpeed().getX()-10;
			ball.setSpeed(newSpeedX, newSpeedY);
			roundStart = System.currentTimeMillis();
		}
		
		this.checkWinner();
		
		ball.update(dt);
		player2.update(dt);
		player1.update(dt);
	}
	
	private void newRound()
	{
		ball.setPosition(canvasWidth/2, canvasHeight/2);
		roundStart = System.currentTimeMillis();
		ball.setSpeed(defaultSpeedXandY, defaultSpeedXandY + ((player1.getScore() + player2.getScore())*2));
	}
	
	private void resetPlayers()
	{
		player2.setPosition(canvasWidth/2 - player2.getPlayerWidth()/2, 0);
		player1.setPosition(canvasWidth/2 - player1.getPlayerWidth()/2, canvasHeight-player1.getPlayerHeight());
	}
	
	private int checkWinner()
	{
		// Should create another State for this shitty thingy...
		if (player1.getScore() == goal)
			gameState = 2;
		else if (player2.getScore() == goal)
			gameState = 3;
		
		return gameState;
	}
	
	
	public boolean onTouchMove(MotionEvent event)
	{
		// touch for player 1
		if (event.getY() > canvasHeight/2)
			player1.setPosition(event.getX()-player1.getPlayerWidth()/2, player1.getY());
		else
			player2.setPosition(event.getX()-player2.getPlayerWidth()/2, player2.getY());
		
		return true;
	}
}
