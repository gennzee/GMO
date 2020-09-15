package entities.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import entities.Player;
import resource.ResourceManager;

public class PlayerAnimation extends entities.animations.Animation {
    private Player player;
    public float elapsedTime;
    public Animation playerIdleAni;
    public Animation playerRunAni;
    public Animation playerJumpAni;
    public Animation playerFallAni;
    public Animation playerAttackAni;
    private enum State {
        IDLE, RUNNING, ATTACKING, JUMPING, FALLING
    }
    private State currentState;
    private State previousState;
    public boolean isLookingToRight;
    public float skillTimer = 0;
    public float skillPeriod = 1f;
    public boolean isAttacking;

    public PlayerAnimation(Player player){
        super();
        this.player = player;
        this.elapsedTime = 0f;
        this.playerIdleAni = createAnimation(ResourceManager.playerIdleFrames, 1, 4, 1/4f);
        this.playerRunAni = createAnimation(ResourceManager.playerRunFrames, 1, 6, 1/6f);
        this.playerJumpAni = createAnimation(ResourceManager.playerJumpFrames, 1, 2, 1/5f);
        this.playerFallAni = createAnimation(ResourceManager.playerFallFrames, 1, 2, 1/5f);
        this.playerAttackAni = createAnimation(ResourceManager.playerBasicAttackFrames, 1, 5, 1/5f);
        this.currentState = State.IDLE;
        this.previousState = State.IDLE;
        this.isLookingToRight = true;
        this.isAttacking = false;
    }

    @Override
    public TextureRegion getTextureRegion(float dt){
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case IDLE:
                region = (TextureRegion) playerIdleAni.getKeyFrame(elapsedTime, true);
                break;
            case RUNNING:
                region = (TextureRegion) playerRunAni.getKeyFrame(elapsedTime, true);
                break;
            case ATTACKING:
                region = (TextureRegion) playerAttackAni.getKeyFrame(skillTimer, false);
                break;
            case JUMPING:
                region = (TextureRegion) playerJumpAni.getKeyFrame(elapsedTime, false);
                break;
            default:
                region = (TextureRegion) playerFallAni.getKeyFrame(elapsedTime, false);
                break;
        }

        if((!isLookingToRight || player.body.getLinearVelocity().x < 0) && !region.isFlipX()){
            region.flip(true, false);
        }else if((isLookingToRight || player.body.getLinearVelocity().x > 0) && region.isFlipX()){
            region.flip(true, false);
        }

        elapsedTime = currentState == previousState ? elapsedTime + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(player.body.getLinearVelocity().x == 0 && player.body.getLinearVelocity().y == 0 && !isAttacking){
            return State.IDLE;
        }
        if(player.body.getLinearVelocity().x != 0 && player.body.getLinearVelocity().y == 0 && !isAttacking){
            return  State.RUNNING;
        }
        if(player.body.getLinearVelocity().y > 0 && !isAttacking){
            return  State.JUMPING;
        }
        if(player.body.getLinearVelocity().y < 0 && !isAttacking){
            return  State.FALLING;
        }
        return State.ATTACKING;
    }
}
