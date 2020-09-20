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

import java.util.HashMap;

import constants.Constants;
import entities.animations.EnemyAnimation;
import hud.statusBar.EnemyStatusBar;
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
    public HashMap<Label, Vector3> onHitList;
    public float onHitTime;
    public EnemyStatusBar enemyStatusBar;

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
        this.onHitList = new HashMap<>();
        this.onHitTime = 0f;
        this.enemyStatusBar = new EnemyStatusBar(this);
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

    public void beingHitted(){
        Label onHit = new Label("99999", ResourceManager.skin, "title-white");
        onHitList.put(onHit, new Vector3((float)body.getPosition().x, (float)body.getPosition().y, 0f));//x value for place X, y value for place Y, z for time being hitted.
        game.stage.addActor(onHit);
        enemyStatusBar.enemyHp -= 10;
    }

    public void updateEnemyName(float dt){
        Vector3 pos = game.camera.project(new Vector3(body.getPosition().x, body.getPosition().y + (getHeight() / 2f) - ((height - bodyHeight) / 2f) / PPM, 0), 0, 0, V_WIDTH, V_HEIGHT);
        name.setPosition(pos.x - (name.getWidth() / 2f), pos.y);
    }

    public void updateBeingHittedNumber(float dt){
        float onHitPeriod = 2f;
        for(HashMap.Entry<Label, Vector3> label : onHitList.entrySet()){
            if(label.getKey() != null && label.getValue().z <= onHitPeriod * 100){
                Vector3 pos = game.camera.project(new Vector3((float)label.getValue().x, label.getValue().y + (getHeight() / 2f) - ((height - bodyHeight) / 2f) / PPM, 0), 0, 0, V_WIDTH, V_HEIGHT);
                label.getKey().setPosition(pos.x, pos.y + label.getValue().z);
                label.setValue(new Vector3(label.getValue().x, label.getValue().y, label.getValue().z + dt*100));
            }else if(label.getKey() != null && label.getValue().z >= onHitPeriod * 100){
                label.getKey().remove();
            }
        }
    }

    @Override
    public void update(float dt) {
        updateEnemyName(dt);
        updateBeingHittedNumber(dt);
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

    public void remove(){//remove enemy with all animation, label,...
        if (enemyAtkZone.getBodyAttack() != null) game.world.destroyBody(enemyAtkZone.getBodyAttack());
        game.world.destroyBody(enemyAtkZone.getBody());
        game.world.destroyBody(enemyScanZone.body);
        game.world.destroyBody(body);
        name.remove();
        for (HashMap.Entry<Label, Vector3> label : onHitList.entrySet()) {
            label.getKey().remove();
        }
        onHitList.clear();
    }

    @Override
    public void dispose() {

    }
}
