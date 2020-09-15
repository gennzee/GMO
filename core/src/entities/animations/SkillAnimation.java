package entities.animations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;

import entities.Player;
import resource.ResourceManager;

public class SkillAnimation extends entities.animations.Animation{

    private Player player;
    private Animation skillAni_1;

    public SkillAnimation(Player player){
        super();
        this.player = player;
        this.skillAni_1 = createAnimation(ResourceManager.playerSkillAttackFrames, 1, 4, 1/8f);
    }

    @Override
    public TextureRegion getTextureRegion(float dt) {
        TextureRegion region = (TextureRegion) skillAni_1.getKeyFrame(player.playerAnimation.skillTimer, true);
        if(!player.playerAnimation.isLookingToRight && !region.isFlipX()){
            region.flip(true, false);
        }else if(player.playerAnimation.isLookingToRight && region.isFlipX()){
            region.flip(true, false);
        }
        player.playerAnimation.skillTimer += dt;
        return region;
    }
}
