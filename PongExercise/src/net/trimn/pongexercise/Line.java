package net.trimn.pongexercise;

import sheep.game.Sprite;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Line extends Sprite
{
	
	private int width;
	private int height;
	private int color;
	private Rect collisionBox;
	
	public Line()
	{
		collisionBox = new Rect(0, 0, width, height);
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		Bitmap bmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		bmp.eraseColor(color);
		
		Rect rect = new Rect((int)getX(), (int)getY(), (int)getX() + width, (int)getY() + height);
		canvas.drawBitmap(bmp, collisionBox, rect, null);
	}
	
	public Rect getCollisionBox()
	{
		return new Rect((int)getX(), (int)getY(), (int)getX() + width,(int)getY() + height);
	}
	
	public int getColor()
	{
		return color;
	}

	public void setColor(int color)
	{
		this.color = color;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
		setShape(width, height);
		collisionBox = new Rect(0, 0, width, height);
	}
	
	public void setHeight(int height)
	{
		this.height = height;
		setShape(width, height);
		collisionBox = new Rect(0, 0, width, height);
	}
}
