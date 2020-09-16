package resource;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ResourceManager {

    public static final AssetManager manager = new AssetManager();
    public static final String textureAtlastPath = "texture.atlas";
    public static final String bitMapFontPath = "fonts/arial.fnt";
    public static final String touchBackgroundPath = "touchpad/touchBackground.png";
    public static final String touchKnobPath = "touchpad/touchKnob.png";
    /*----------------------------------------------------------------*/
    public static TextureAtlas textureAtlas;
    public static BitmapFont bitmapFont;
    public static Texture touchBackground;
    public static Texture touchKnob;
    public static TextureRegion[][] playerIdleFrames;
    public static TextureRegion[][] playerRunFrames;
    public static TextureRegion[][] playerJumpFrames;
    public static TextureRegion[][] playerFallFrames;
    public static TextureRegion[][] playerBasicAttackFrames;
    public static TextureRegion[][] playerSkillAttackFrames;
    public static TextureRegion[][] enemyIdleFrames;
    public static TextureRegion[][] enemyRunFrames;
    public static TextureRegion[][] enemyAttackFrames;


    public static void load(){
        manager.load(textureAtlastPath, TextureAtlas.class);
        manager.load(bitMapFontPath, BitmapFont.class);
        manager.load(touchBackgroundPath, Texture.class);
        manager.load(touchKnobPath, Texture.class);
        while(!manager.update()) System.out.println(manager.getProgress() * 100);
        textureAtlas = manager.get(textureAtlastPath, TextureAtlas.class);
        bitmapFont = manager.get(bitMapFontPath, BitmapFont.class);
        touchBackground = manager.get(touchBackgroundPath, Texture.class);
        touchKnob = manager.get(touchKnobPath, Texture.class);

        playerIdleFrames = textureAtlas.findRegion("player/playerIdle1/playerIdle1").split(50,37);
        playerRunFrames = textureAtlas.findRegion("player/playerRun1/playerRun1").split(50,37);
        playerJumpFrames = textureAtlas.findRegion("player/playerJump1/playerJump1").split(50,37);
        playerFallFrames = textureAtlas.findRegion("player/playerFall1/playerFall1").split(50,37);
        playerBasicAttackFrames = textureAtlas.findRegion("player/playerAttack1/playerAttack1").split(50,37);
        playerSkillAttackFrames = textureAtlas.findRegion("player/playerSkill1/playerSkill1").split(50,37);
        enemyIdleFrames = textureAtlas.findRegion("enemies/enemyIdle1/enemyIdle1").split(96,96);
        enemyRunFrames = textureAtlas.findRegion("enemies/enemyRun1/enemyRun1").split(96,96);
        enemyAttackFrames = textureAtlas.findRegion("enemies/enemyAttack1/enemyAttack1").split(96,96);
    }

    public static void dispose(){
        manager.dispose();
        textureAtlas.dispose();
        touchBackground.dispose();
        touchKnob.dispose();
    }
}
