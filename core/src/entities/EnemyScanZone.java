package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.JsonValue;

import static constants.Constants.*;

public class EnemyScanZone {

    private Player player;
    private Enemy enemy;
    private World world;
    public Body body;
    private float scanZone;

    public EnemyScanZone(Player player, Enemy enemy, World world) {
        this.player = player;
        this.enemy = enemy;
        this.world = world;
        this.scanZone = enemy.jsonValue.getFloat("scanZone");
        this.body = createBody();
    }

    public Body createBody() {
        Body pBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.allowSleep = false;
        bodyDef.fixedRotation = true;
        bodyDef.position.add(enemy.body.getPosition().x, enemy.body.getPosition().y);
        pBody = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(scanZone / PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.isSensor = true;
        fdef.shape = circleShape;
        pBody.createFixture(fdef).setUserData(this);
        circleShape.dispose();
        return pBody;
    }

    public void update(float dt) {
        float distance = player.body.getPosition().x - enemy.body.getPosition().x;
        float a = (player.body.getPosition().x > enemy.body.getPosition().x) ? player.body.getPosition().x - (body.getPosition().x + scanZone / PPM) : (body.getPosition().x - scanZone / PPM) - player.body.getPosition().x;
        boolean isPlayerInRange = a < 0;
        if(isPlayerInRange && !enemy.enemyAnimation.canAttack){
            enemy.enemyAnimation.distanceToPlayer = distance;
        }else{
            enemy.enemyAnimation.distanceToPlayer = 0f;
        }
        body.setTransform(enemy.body.getPosition().x, enemy.body.getPosition().y, 0);
    }

}
