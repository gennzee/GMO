package maps;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import constants.Constants;
import maps.tiledGenerator.tiledObjects.Portal;
import maps.tiledGenerator.tiledObjects.Wall;

public class B2WorldCreator {

    public B2WorldCreator(World world, TiledMap tiledMap){
        parseTiledObjectLayer(world, tiledMap, tiledMap.getLayers().get("backgroundLayer").getObjects());
        parsePortalTiledObjectLayer(world, tiledMap, tiledMap.getLayers().get("portalLayer").getObjects());
    }

    public void parseTiledObjectLayer(World world, TiledMap tiledMap, MapObjects objects){
        for(MapObject object : objects){
            Shape shape = getShape(object);
            if(shape == null) continue;
            new Wall(world, tiledMap, shape, object.getProperties());
        }
    }

    public void parsePortalTiledObjectLayer(World world, TiledMap tiledMap, MapObjects objects){
        for(MapObject object : objects){
            Shape shape = getShape(object);
            if(shape == null) continue;
            new Portal(world, tiledMap, shape, object.getProperties());
        }
    }

    private Shape getShape(MapObject object){
        Shape shape;
        if (object instanceof RectangleMapObject) {
            shape = getRectangle((RectangleMapObject)object);
        } else if (object instanceof PolygonMapObject) {
            shape = getPolygon((PolygonMapObject)object);
        } else if (object instanceof PolylineMapObject) {
            shape = getPolyline((PolylineMapObject)object);
        } else if (object instanceof CircleMapObject) {
            shape = getCircle((CircleMapObject)object);
        } else {
            return null;
        }
        return shape;
    }

    private PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / Constants.PPM,
                (rectangle.y + rectangle.height * 0.5f ) / Constants.PPM);
        polygon.setAsBox(rectangle.width * 0.5f / Constants.PPM,
                rectangle.height * 0.5f / Constants.PPM,
                size,
                0.0f);
        return polygon;
    }

    private CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / Constants.PPM);
        circleShape.setPosition(new Vector2(circle.x / Constants.PPM, circle.y / Constants.PPM));
        return circleShape;
    }

    private PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            worldVertices[i] = vertices[i] / Constants.PPM;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / Constants.PPM;
            worldVertices[i].y = vertices[i * 2 + 1] / Constants.PPM;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }
}
