package com.xa;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import resource.ResourceManager;
import screens.PlayScreen;

public class GMO extends Game {

	public SpriteBatch batch;
	public PlayScreen playScreen;
	
	@Override
	public void create () {
		ResourceManager.load();
		batch = new SpriteBatch();
		playScreen = new PlayScreen(this,"maps/map1.tmx");
		setScreen(playScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		ResourceManager.dispose();
		playScreen.dispose();
	}
}
