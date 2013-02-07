package net.trimn.pongexercise;

import sheep.game.Sprite;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

public class Ball extends Sprite
{
	private static final Ball ball = new Ball();
	private int size = 20;
	private Rect collisionBox;
	
	private Ball()
	{
		if (ball != null)
		{
			throw new IllegalStateException("Already instantiated");
		}
		collisionBox = new Rect(0, 0, size, size);
		setShape(size, size);
	}
	
	public static Ball getInstance()
	{
		return Ball.ball;
	}

	public Rect getCollisionBox()
	{
		return new Rect((int)getX(), (int)getY(), (int)getX() + size,(int)getY() + size);
	}
	
	public int getSize()
	{
		return size;
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		Bitmap bmp = Bitmap.createBitmap(size, size, Config.ARGB_8888);
		bmp.eraseColor(Color.WHITE);
		Rect rect = new Rect((int)getX(), (int)getY(), (int)getX() + size, (int)getY() + size);
		canvas.drawBitmap(bmp, collisionBox, rect, null);
	}
	
}
