package entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static constants.Constants.PPM;

public class EnemyAtkZone {

    private Player player;
    private Enemy enemy;
    private World world;
    private Body body;
    private Body bodyAttack;
    private float atkZone;
    private float atkBodySize;
    private int atkFrame;

    public EnemyAtkZone(Player player, Enemy enemy, World world){
        this.player = player;
        this.enemy = enemy;
        this.world = world;
        this.atkZone = enemy.jsonValue.getFloat("atkZone");
        this.atkBodySize = enemy.jsonValue.getFloat("atkBodySize");
        this.atkFrame = enemy.jsonValue.getInt("atkInFrame");
        this.body = createBody();
    }

    public Body createBody(){
        Body pBody;
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.KinematicBody;
        bdef.allowSleep = false;
        bdef.fixedRotation = true;
        bdef.position.add(enemy.body.getPosition().x, enemy.body.getPosition().y);
        pBody = world.createBody(bdef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(atkZone / PPM, atkZone / PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.isSensor = true;
        fdef.shape = polygonShape;
        pBody.createFixture(fdef);
        polygonShape.dispose();
        return pBody;
    }

    public Body createBodyAttack(){
        Body pBody;
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.KinematicBody;
        bdef.fixedRotation = true;
        bdef.allowSleep = false;
        bdef.position.add(enemy.body.getPosition().x, enemy.body.getPosition().y);
        pBody = world.createBody(bdef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(atkBodySize / PPM, atkBodySize / PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.isSensor = true;
        fdef.shape = polygonShape;
        pBody.createFixture(fdef).setUserData(this);
        return pBody;
    }

    public void update(float dt){
        float playerPositionX = player.body.getPosition().x;
        float enemyPositionX = enemy.body.getPosition().x;
        float a = (playerPositionX > enemyPositionX) ? playerPositionX - (body.getPosition().x + enemy.jsonValue.getInt("atkZone") / PPM) : (body.getPosition().x - enemy.jsonValue.getInt("atkZone") / PPM) - playerPositionX;
        boolean isPlayerInAttackRange = a < 0;

        if(isPlayerInAttackRange && !enemy.enemyAnimation.canAttack){
            enemy.enemyAnimation.canAttack = true;
            enemy.enemyAnimation.distanceToPlayer = 0f;
            enemy.enemyAnimation.attackTime += dt;
        }

        handleCanAttack(playerPositionX, enemyPositionX, dt);

        body.setTransform(enemy.body.getPosition().x, enemy.body.getPosition().y, 0);
    }

    public void handleCanAttack(float playerPositionX, float enemyPositionX, float dt){
        if(enemy.enemyAnimation.canAttack){
            if(enemy.enemyAnimation.attackTime >= enemy.enemyAnimation.attackPeriod){
                world.destroyBody(bodyAttack);
                bodyAttack = null;
                enemy.enemyAnimation.canAttack = false;
                enemy.enemyAnimation.attackTime = 0f;
            }else{
                if(enemy.enemyAnimation.enemyAttackAni.getKeyFrameIndex(enemy.enemyAnimation.attackTime) == atkFrame){
                    if(bodyAttack == null) bodyAttack = createBodyAttack();
                }
                setAttackBoxDirection(playerPositionX, enemyPositionX);
                enemy.enemyAnimation.attackTime += dt;
            }
        }else{
            enemy.enemyAnimation.attackTime = 0f;
        }
    }

    public void setAttackBoxDirection(float playerPositionX, float enemyPositionX){
        float distance = playerPositionX - enemyPositionX;
        float box2dAttackPosition;
        if(distance > 0){
            box2dAttackPosition = enemyPositionX + enemy.jsonValue.getInt("bodyWidth") / PPM;
            enemy.enemyAnimation.isLookingToRight = true;
        }else{
            box2dAttackPosition = enemyPositionX - enemy.jsonValue.getInt("bodyWidth") / PPM;
            enemy.enemyAnimation.isLookingToRight = false;
        }
        if(bodyAttack != null) bodyAttack.setTransform(box2dAttackPosition, enemy.body.getPosition().y, 0);
    }

    public Body getBody() {
        return body;
    }
}
