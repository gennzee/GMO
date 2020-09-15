package resource;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ResourceManager {

    public static final AssetManager manager = new AssetManager();
    public static final String textureAtlastPath = "texture.atlas";
    /*----------------------------------------------------------------*/
    public static TextureAtlas textureAtlas;
    public static TextureRegion[][] playerIdleFrames;
    public static TextureRegion[][] playerRunFrames;
    public static TextureRegion[][] playerJumpFrames;
    public static TextureRegion[][] playerFallFrames;
    public static TextureRegion[][] playerBasicAttackFrames;
    public static TextureRegion[][] playerSkillAttackFrames;

    public static void load(){
        manager.load(textureAtlastPath, TextureAtlas.class);
        while(!manager.update()) System.out.println(manager.getProgress() * 100);
        textureAtlas = manager.get(textureAtlastPath, TextureAtlas.class);

        playerIdleFrames = textureAtlas.findRegion("player/playerIdle1/playerIdle1").split(50,37);
        playerRunFrames = textureAtlas.findRegion("player/playerRun1/playerRun1").split(50,37);
        playerJumpFrames = textureAtlas.findRegion("player/playerJump1/playerJump1").split(50,37);
        playerFallFrames = textureAtlas.findRegion("player/playerFall1/playerFall1").split(50,37);
        playerBasicAttackFrames = textureAtlas.findRegion("player/playerAttack1/playerAttack1").split(50,37);
        playerSkillAttackFrames = textureAtlas.findRegion("player/playerSkill1/playerSkill1").split(50,37);
    }

    public static void dispose(){
        manager.dispose();
        textureAtlas.dispose();
    }
}