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

    private TextureAtlas textureAtlas;
    private Skin buttonSkin;
    private BitmapFont bitmapFont;
    public TextButton jumpButton;
    public TextButton attackButton;

    public TouchPad(PlayScreen game){
        this.game = game;
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

        textureAtlas = ResourceManager.textureAtlas;
        buttonSkin = new Skin(textureAtlas);
        bitmapFont = ResourceManager.bitmapFont;
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonSkin.getDrawable("touchPad/buttonUp");
        textButtonStyle.down = buttonSkin.getDrawable("touchPad/buttonDown");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = bitmapFont;
        jumpButton = new TextButton("Jump", textButtonStyle);
        jumpButton.setBounds(V_WIDTH - 400, 70, 200, 200);
        game.stage.addActor(jumpButton);
        attackButton = new TextButton("Attack", textButtonStyle);
        attackButton.setBounds(V_WIDTH - 200, 70, 200, 200);
        game.stage.addActor(attackButton);

        Gdx.input.setInputProcessor(game.stage);
    }

    @Override
    public void dispose(){
        game.stage.dispose();
        touchpadSkin.dispose();
        System.out.println("touchPad disposed");
    }

    public Touchpad getTouchpad() {
        return touchpad;
    }

    public void setTouchpad(Touchpad touchpad) {
        this.touchpad = touchpad;
    }
}
