package net.trimn.pongexercise;


import sheep.game.Game;
import android.app.Activity;
import android.os.Bundle;


public class PongActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Game game = new Game(this, null);
		game.pushState(new PongState(this));
		setContentView(game);
	}



}