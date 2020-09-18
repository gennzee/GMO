package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.JsonValue;

import constants.Constants;
import entities.animations.EnemyAnimation;
import resource.ResourceManager;
import screens.PlayScreen;

import static constants.Constants.*;

public class Enemy extends Entity {

    public PlayScreen game;
    public Body body;
    private Label name;
    private Vector2 position;
    public EnemyAnimation enemyAnimation;
    private EnemyScanZone enemyScanZone;
    private EnemyAtkZone enemyAtkZone;
    public JsonValue jsonValue;


    public Enemy(PlayScreen game, World world, Vector2 position, JsonValue jsonValue) {
        super(world, jsonValue.getInt("width"), jsonValue.getInt("height"), jsonValue.getInt("bodyWidth"), jsonValue.getInt("bodyHeight"));
        this.game = game;
        this.jsonValue = jsonValue;
        this.position = position;
        this.body = createBody();
        this.name = createEnemyName();
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
        bdef.position.add( position );

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

    public Label createEnemyName(){
        name = new Label(jsonValue.name, ResourceManager.skin, "title-white");
        game.stage.addActor(name);
        return name;
    }

    public void updateEnemyName(float dt){
        Vector3 pos = game.camera.project(new Vector3(body.getPosition().x, body.getPosition().y + (getHeight() / 2f) - ((height - bodyHeight) / 2f) / PPM, 0), 0, 0, V_WIDTH, V_HEIGHT);
        name.setPosition(pos.x - (name.getWidth() / 2f), pos.y);
    }

    @Override
    public void update(float dt) {
        updateEnemyName(dt);
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
        setPosition(body.getPosition().x - (getWidth() / 2f), body.getPosition().y - (getHeight() / 2f) - (jsonValue.getInt("heightDif") / Constants.PPM));
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
