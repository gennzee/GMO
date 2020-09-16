package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


import entities.animations.PlayerAnimation;
import entities.inputHandler.PlayerInputHandler;
import screens.PlayScreen;

import static constants.Constants.*;

public class Player extends Entity {

    public PlayScreen game;
    public Body body;
    public PlayerAnimation playerAnimation;
    private PlayerInputHandler playerInputHandler;

    public Player(PlayScreen game, World world, int width, int height, int bodyWidth, int bodyHeight) {
        super(world, width, height, bodyWidth, bodyHeight);
        this.game = game;
        this.body = createBody();
        this.playerAnimation = new PlayerAnimation(this);
        this.playerInputHandler = new PlayerInputHandler(this);
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
        pBody.createFixture(fdef);
        return pBody;
    }

    @Override
    public void update(float dt) {
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
