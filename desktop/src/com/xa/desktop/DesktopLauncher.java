package com.xa.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.xa.GMO;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1600;
		config.height = 900;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;

		TexturePacker.Settings setting = new TexturePacker.Settings();
		setting.pot = true;
		setting.fast = true;
		setting.combineSubdirectories = true;
		setting.paddingX = 0;
		setting.paddingY = 0;
		setting.edgePadding = true;
		TexturePacker.process(setting, "texture", "./", "texture");
		new LwjglApplication(new GMO(), config);
	}
}
