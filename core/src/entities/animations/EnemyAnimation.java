package entities.animations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;

import entities.Enemy;
import resource.ResourceManager;

public class EnemyAnimation extends entities.animations.Animation {

    private Enemy enemy;
    private enum State{
        IDLE, RUNNING, ATTACKING
    }
    public Animation enemyIdleAni;
    public Animation enemyRunAni;
    public Animation enemyAttackAni;
    private State previousState;
    private State currentState;
    private float elapsedTime;
    public boolean isLookingToRight;
    public float distanceToPlayer;
    public boolean canAttack;
    public float attackTime;
    public float attackPeriod;

    public EnemyAnimation(Enemy enemy){
        super();
        this.enemy = enemy;
        this.enemyIdleAni = createAnimation(ResourceManager.getFrames(
                enemy.jsonValue.get("animation").get("idle").getString("path"),
                enemy.jsonValue.getInt("width"),
                enemy.jsonValue.getInt("height")),
                enemy.jsonValue.get("animation").get("idle").getInt("row"),
                enemy.jsonValue.get("animation").get("idle").getInt("col"),
                (float) 1 / enemy.jsonValue.get("animation").get("idle").getInt("duration"));
        this.enemyRunAni = createAnimation(ResourceManager.getFrames(
                enemy.jsonValue.get("animation").get("run").getString("path"),
                enemy.jsonValue.getInt("width"),
                enemy.jsonValue.getInt("height")),
                enemy.jsonValue.get("animation").get("run").getInt("row"),
                enemy.jsonValue.get("animation").get("run").getInt("col"),
                (float) 1 / enemy.jsonValue.get("animation").get("run").getInt("duration"));
        this.enemyAttackAni = createAnimation(ResourceManager.getFrames(
                enemy.jsonValue.get("animation").get("attack").getString("path"),
                enemy.jsonValue.getInt("width"),
                enemy.jsonValue.getInt("height")),
                enemy.jsonValue.get("animation").get("attack").getInt("row"),
                enemy.jsonValue.get("animation").get("attack").getInt("col"),
                (float) 1 / enemy.jsonValue.get("animation").get("attack").getInt("duration"));
        this.previousState = State.IDLE;
        this.currentState = State.IDLE;
        this.elapsedTime = 0f;
        this.isLookingToRight = true;
        this.distanceToPlayer = 0f;
        this.canAttack = false;
        this.attackTime = 0f;
        this.attackPeriod = enemy.jsonValue.getFloat("atkTime");
    }

    @Override
    public TextureRegion getTextureRegion(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case RUNNING:
                region = (TextureRegion) enemyRunAni.getKeyFrame(elapsedTime, true);
                break;
            case ATTACKING:
                region = (TextureRegion) enemyAttackAni.getKeyFrame(attackTime, true);
                break;
            default:
                region = (TextureRegion) enemyIdleAni.getKeyFrame(elapsedTime, true);
                break;
        }

        if(!isLookingToRight && enemy.body.getLinearVelocity().x <= 0 && !region.isFlipX()){
            region.flip(true, false);
        }else if(isLookingToRight  && enemy.body.getLinearVelocity().x >= 0 && region.isFlipX()){
            region.flip(true, false);
        }

        elapsedTime = currentState == previousState ? elapsedTime + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(enemy.body.getLinearVelocity().x != 0 && !canAttack){
            return State.RUNNING;
        }
        if(enemy.body.getLinearVelocity().x == 0 && !canAttack){
            return State.IDLE;
        }
        return State.ATTACKING;
    }

}
