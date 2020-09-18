package hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Disposable;

import resource.ResourceManager;
import screens.PlayScreen;

import static constants.Constants.V_WIDTH;


public class TouchPad implements Disposable {

    private PlayScreen game;
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;

    private Skin buttonSkin;
    public TextButton jumpButton;
    public TextButton attackButton;
    public TextButton skillButton1;
    public TextButton skillButton2;

    public TouchPad(PlayScreen game){
        this.game = game;
        createTouchPad();
        createActionButtons();
        Gdx.input.setInputProcessor(game.stage);
    }

    public void createTouchPad(){
        //Create a touchpad skin
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", ResourceManager.touchBackground);
        //Set knob image
        touchpadSkin.add("touchKnob", ResourceManager.touchKnob);
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(1, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(70, 70, 300, 300);
        //Create a Stage and add TouchPad
        game.stage.addActor(touchpad);
    }

    public void createActionButtons(){
        buttonSkin = new Skin(ResourceManager.textureAtlas);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonSkin.getDrawable("touchPad/buttonUp");
        textButtonStyle.down = buttonSkin.getDrawable("touchPad/buttonDown");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = ResourceManager.bitmapFont;

        jumpButton = new TextButton("Jump", textButtonStyle);
        jumpButton.setBounds(V_WIDTH - 400, 70, 100, 100);
        game.stage.addActor(jumpButton);

        attackButton = new TextButton("Attack", textButtonStyle);
        attackButton.setBounds(V_WIDTH - 200, 70, 100, 100);
        game.stage.addActor(attackButton);

        skillButton1 = new TextButton("Skill 1", textButtonStyle);
        skillButton1.setBounds(V_WIDTH - 350, 220, 100, 100);
        game.stage.addActor(skillButton1);

        skillButton2 = new TextButton("Skill 2", textButtonStyle);
        skillButton2.setBounds(V_WIDTH - 200, 270, 100, 100);
        game.stage.addActor(skillButton2);
    }

    @Override
    public void dispose(){
        game.stage.dispose();
        touchpadSkin.dispose();
    }

    public Touchpad getTouchpad() {
        return touchpad;
    }
}
