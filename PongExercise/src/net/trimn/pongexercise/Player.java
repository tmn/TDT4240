package net.trimn.pongexercise;

import sheep.game.Sprite;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;

public class Player extends Sprite {
	
	private int score;
	private Rect collisionBox;
	
	private int playerWidth;
	private int playerHeight;
	
	public Player() {
		score = 0;
		
		playerWidth = 200;
		playerHeight = 10;
		
		collisionBox = new Rect(0, 0, playerWidth, playerHeight);
		setShape(playerWidth, playerHeight);
	}
	
	public void increaseScore() {
		this.score++;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getPlayerWidth() {
		return this.playerWidth;
	}
	
	public int getPlayerHeight() {
		return this.playerHeight;
	}
	
	@Override
	public void draw(Canvas canvas) {
		Bitmap bmp = Bitmap.createBitmap(playerWidth, playerHeight, Config.ARGB_8888);
		bmp.eraseColor(Color.WHITE);
		
		Rect rect = new Rect((int)getX(), (int)getY(), (int)getX() + playerWidth, (int)getY() + playerHeight);
		
		canvas.drawBitmap(bmp, collisionBox, rect, null);
		
		
	}
	
	public void ping() {
		collisionBox.left = playerWidth;
		collisionBox.right = collisionBox.left + playerWidth;
	}
	
	public Rect getCollisionBox() {
		return new Rect((int)getX(), (int)getY(), (int)getX() + playerWidth,(int)getY() + playerHeight);
	}
}
