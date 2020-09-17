package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import entities.animations.PlayerAnimation;
import entities.inputHandler.PlayerInputHandler;
import resource.ResourceManager;
import screens.PlayScreen;

import static constants.Constants.*;

public class Player extends Entity {

    public PlayScreen game;
    public Body body;
    private Label name;
    public PlayerAnimation playerAnimation;
    private PlayerInputHandler playerInputHandler;

    public Player(PlayScreen game, World world, int width, int height, int bodyWidth, int bodyHeight) {
        super(world, width, height, bodyWidth, bodyHeight);
        this.game = game;
        this.body = createBody();
        this.name = createPlayerName();
        this.playerAnimation = new PlayerAnimation(this);
        this.playerInputHandler = new PlayerInputHandler(this);
    }

    public Label createPlayerName(){
        name = new Label("anhnx121212", ResourceManager.skin, "title-white");
        game.stage.addActor(name);
        return name;
    }

    @Override
    public Body createBody() {
        Body pBody;
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = true;
        bdef.allowSleep = false;
        bdef.position.add(30 / PPM,30 / PPM);
        pBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(bodyWidth / 2f / PPM, bodyHeight / 2f / PPM);
        fdef.shape = polygonShape;
        fdef.filter.categoryBits = BIT_PLAYER;
        fdef.filter.maskBits = BIT_DEFAULT;
        pBody.createFixture(fdef).setUserData(this);
        return pBody;
    }

    public void updatePlayerName(){
        Vector3 pos = game.camera.project(new Vector3(body.getPosition().x, body.getPosition().y + (getHeight() / 2f) - ((height - bodyHeight) / 2f) / PPM, 0));
        name.setPosition(pos.x - (name.getWidth() / 2f), pos.y);
    }

    @Override
    public void update(float dt) {
        updatePlayerName();
        playerInputHandler.handleInput(dt);

        setRegion(playerAnimation.getTextureRegion(dt));
        setPosition(body.getPosition().x - (getWidth() / 2f), body.getPosition().y - (getHeight() / 2f) + (8 / 2f / PPM));
        playerAnimation.elapsedTime += dt;
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
