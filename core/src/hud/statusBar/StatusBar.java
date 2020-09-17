package hud.statusBar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import resource.ResourceManager;
import screens.PlayScreen;

import static constants.Constants.PPM;
import static constants.Constants.V_HEIGHT;

public class StatusBar {

    private PlayScreen game;
    public float hp;
    public float mp;
    private Skin skin;
    private ShapeRenderer hpmp;
    private Label playerName;

    public StatusBar(PlayScreen game, float hp, float mp){
        this.game = game;
        this.hp = hp;
        this.mp = mp;
        skin = new Skin();
        skin.add("statusBar", ResourceManager.statusBar);

        Image statusBar = new Image();
        statusBar.setDrawable(skin.getDrawable("statusBar"));
        statusBar.setBounds(0,V_HEIGHT - 100, ResourceManager.statusBar.getWidth(), ResourceManager.statusBar.getHeight());
        game.stage.addActor(statusBar);

        playerName = new Label("anhnx121212", ResourceManager.skin, "title-white");
        playerName.setPosition(200 - (playerName.getWidth() / 2f),V_HEIGHT - 40);
        game.stage.addActor(playerName);

        hpmp = new ShapeRenderer();
        hpmp.setAutoShapeType(true);
    }

    public void draw(SpriteBatch sb, float dt){
        hpmp.setProjectionMatrix(game.stage.getCamera().combined);
        hpmp.begin(ShapeRenderer.ShapeType.Filled);
        //hp background
        hpmp.setColor(Color.GRAY);
        hpmp.rect(0, V_HEIGHT - 70, ResourceManager.statusBar.getWidth(), 26);
        //hp
        hpmp.setColor(Color.RED);
        hpmp.rect(0, V_HEIGHT - 70, ResourceManager.statusBar.getWidth() * hp/100, 26);
        //mp background
        hpmp.setColor(Color.GRAY);
        hpmp.rect(0, V_HEIGHT - 100, ResourceManager.statusBar.getWidth(), 26);
        //mp
        hpmp.setColor(Color.BLUE);
        hpmp.rect(0, V_HEIGHT - 100, ResourceManager.statusBar.getWidth() * mp/100, 26);
        hpmp.end();
    }

}
