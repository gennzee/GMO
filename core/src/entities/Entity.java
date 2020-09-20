package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import static constants.Constants.*;

public abstract class Entity extends Sprite {

    public World world;
    protected int width, height;
    protected int bodyWidth, bodyHeight;

    public Entity(World world, int width, int height, int bodyWidth, int bodyHeight){
        this.world = world;
        this.width = width;
        this.height = height;
        this.bodyWidth = bodyWidth;
        this.bodyHeight = bodyHeight;
        setBounds(0,0, width / PPM, height / PPM);
    }

    public abstract Body createBody();

    public abstract void update(float dt);

    public abstract void render(SpriteBatch sb, float dt);

    public abstract void dispose();

    public int getOriginWidth() {
        return width;
    }

    public int getOriginHeight() {
        return height;
    }

    public int getOriginBodyWidth() {
        return bodyWidth;
    }

    public int getOriginBodyHeight() {
        return bodyHeight;
    }
}
