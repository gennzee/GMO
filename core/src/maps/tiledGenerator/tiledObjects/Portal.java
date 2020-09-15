package maps.tiledGenerator.tiledObjects;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import maps.tiledGenerator.InteractiveTiledObject;

public class Portal extends InteractiveTiledObject {
    public Portal(World world, TiledMap map, Shape shape, MapProperties mapProperties) {
        super(world, map, shape, mapProperties);
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);
        fdef.shape = shape;
        fdef.isSensor = true;
        Fixture fixture = body.createFixture(fdef);
        Filter filter = new Filter();
        fixture.setFilterData(filter);
        fixture.setUserData(this);
    }

    @Override
    public String getObjectProperty() {
        return mapProperties.get("map").toString();
    }
}
