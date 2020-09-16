package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import constants.Constants;
import entities.animations.EnemyAnimation;
import screens.PlayScreen;

import static constants.Constants.*;

public class Enemy extends Entity {

    public PlayScreen game;
    public Body body;
    public EnemyAnimation enemyAnimation;
    private EnemyScanZone enemyScanZone;
    private EnemyAtkZone enemyAtkZone;


    public Enemy(PlayScreen game, World world, int width, int height, int bodyWidth, int bodyHeight) {
        super(world, width, height, bodyWidth, bodyHeight);
        this.game = game;
        this.body = createBody();
        this.enemyAnimation = new EnemyAnimation(this);
        this.enemyScanZone = new EnemyScanZone(game.player, this, world);
        this.enemyAtkZone = new EnemyAtkZone(game.player, this, world);
    }

    @Override
    public Body createBody() {
        Body pBody;
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = true;
        bdef.allowSleep = false;
        bdef.position.add( new Vector2(100f / PPM,50f / PPM));

        pBody = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(bodyWidth / 2f / PPM,bodyHeight / 2f / PPM);
        fdef.shape = polygonShape;
        fdef.filter.categoryBits = BIT_ENEMY;
        fdef.filter.maskBits = BIT_DEFAULT;
        pBody.createFixture(fdef).setUserData(this);
        polygonShape.dispose();
        return pBody;
    }

    @Override
    public void update(float dt) {
        if(enemyAnimation.distanceToPlayer > 0){
            body.setLinearVelocity(new Vector2(1f, body.getLinearVelocity().y));
            enemyAnimation.isLookingToRight = true;
        }else if(enemyAnimation.distanceToPlayer < 0){
            body.setLinearVelocity(new Vector2(-1f, body.getLinearVelocity().y));
            enemyAnimation.isLookingToRight = false;
        }else{
            enemyAnimation.distanceToPlayer = 0f;
        }
        enemyScanZone.update(dt);
        enemyAtkZone.update(dt);

        setRegion(enemyAnimation.getTextureRegion(dt));
        setPosition(body.getPosition().x - (getWidth() / 2f), body.getPosition().y - (getHeight() / 2f) - 4/ Constants.PPM);
    }

    @Override
    public void render(SpriteBatch sb, float dt) {
        update(dt);
        this.draw(sb);
    }

    @Override
    public void dispose() {

    }
}
