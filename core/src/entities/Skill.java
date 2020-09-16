package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import entities.animations.SkillAnimation;

import static constants.Constants.PPM;

public class Skill extends Entity {

    public Body body;
    private Player player;
    private SkillAnimation skillAnimation;

    public Skill(World world, Player player, int width, int height, int bodyWidth, int bodyHeight) {
        super(world, width, height, bodyWidth, bodyHeight);
        this.player = player;
        this.body = createBody();
        this.skillAnimation = new SkillAnimation(player);
    }

    @Override
    public Body createBody() {
        Body pBody;
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.KinematicBody;
        bdef.fixedRotation = true;
        bdef.allowSleep = false;
        if(player.playerAnimation.isLookingToRight){
            bdef.position.add(player.body.getPosition().x + player.getWidth() / 2f ,player.body.getPosition().y);
        }else{
            bdef.position.add(player.body.getPosition().x - player.getWidth() / 2f,player.body.getPosition().y);
        }

        pBody = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(bodyWidth / 2f / PPM,bodyHeight / 2f / PPM);
        fdef.shape = polygonShape;
        fdef.isSensor = true;
        pBody.createFixture(fdef).setUserData(this);
        polygonShape.dispose();
        return pBody;
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb, float dt) {
        setRegion(skillAnimation.getTextureRegion(dt));
        setPosition(body.getPosition().x - (getWidth() / 2), body.getPosition().y - (getHeight() / 2) + (8 / 2f) / PPM);
        this.draw(sb);
    }

    @Override
    public void dispose() {

    }
}
