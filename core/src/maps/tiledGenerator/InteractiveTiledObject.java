package maps.tiledGenerator;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class InteractiveTiledObject {

    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Shape shape;
    protected Body body;
    protected MapProperties mapProperties;

    public InteractiveTiledObject(World world, TiledMap map, Shape shape, MapProperties mapProperties){
        this.world = world;
        this.map = map;
        this.shape = shape;
        this.mapProperties = mapProperties;
    }

    public abstract String getObjectProperty();

}
