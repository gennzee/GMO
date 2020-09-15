package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import constants.*;

import static constants.Constants.*;

public abstract class Entity extends Sprite {

    public World world;
    protected int width, height;

    public Entity(World world, int width, int height){
        this.world = world;
        this.width = width;
        this.height = height;
        setBounds(0,0, 50 / PPM, 37 / PPM);
    }

    public abstract Body createBody();

    public abstract void update(float dt);

    public abstract void render(SpriteBatch sb, float dt);

    public abstract void dispose();
}
