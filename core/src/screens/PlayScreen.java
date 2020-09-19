package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xa.GMO;

import entities.Enemy;
import entities.Player;
import hud.TouchPad;
import hud.statusBar.StatusBar;
import maps.AnimationTiledObject;
import maps.B2WorldCreator;
import resource.ResourceManager;
import worldContact.WorldContactListener;

import static constants.Constants.*;

public class PlayScreen implements Screen {

    public GMO game;
    private String map;
    public Stage stage;

    public OrthographicCamera camera;
    private Viewport viewport;

    private World world;
    private Box2DDebugRenderer b2dr;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TiledMap tiledMap;

    public TouchPad touchPad;
    public StatusBar statusBar;

    public Player player;
    private Array<Enemy> enemyArray;

    public PlayScreen(GMO game, String map){
        this.game = game;
        this.map = map;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(V_WIDTH, V_HEIGHT, new OrthographicCamera()), game.batch);
        camera = new OrthographicCamera();
        viewport = new FitViewport(V_WIDTH / 3f / PPM, V_HEIGHT / 3f / PPM, camera);
        viewport.apply();

        world = new World(new Vector2(0,-9.8f), false);
        b2dr = new Box2DDebugRenderer();

        tiledMap = new TmxMapLoader().load(map);
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / PPM);
        new B2WorldCreator(world, tiledMap);
        new AnimationTiledObject(tiledMap);

        player = new Player(this, world, 50, 37, 20, 26);

        enemyArray = new Array<>();
        for(MapObject object : tiledMap.getLayers().get("enemiesLayer").getObjects()){
            MapProperties enemiesProperties = object.getProperties();
            float x = (float) enemiesProperties.get("x");
            float y = (float) enemiesProperties.get("y");
            String enemyName = enemiesProperties.get("name").toString();

            JsonValue jsonValue = ResourceManager.enemiesJson.get(enemyName);
            Enemy enemy = new Enemy(this, world, new Vector2(x / PPM,y / PPM), jsonValue);
            enemyArray.add(enemy);
        }

        touchPad = new TouchPad(this);
        statusBar = new StatusBar(this, 100, 100);

        stage.setDebugAll(true);
        world.setContactListener(new WorldContactListener());
    }

    @Override
    public void render(float delta) {
        world.step(1/60f, 6, 2);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        orthogonalTiledMapRenderer.render();
        b2dr.render(world, camera.combined);
        AnimatedTiledMapTile.updateAnimationBaseTime();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        for (Enemy enemy : enemyArray){
            enemy.render(game.batch, delta);
        }
        player.render(game.batch, delta);
        game.batch.end();
        update(delta);
        statusBar.draw(game.batch, delta);

        stage.act(delta);
        stage.draw();
    }

    public void update(float dt){
        updateCamera(dt);
        updateMapView();
    }

    public void updateCamera(float dt){
        //updateCamera must update after game.batch draw
        if(Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)){
            camera.zoom -= 1;
        }else if(Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)){
            camera.zoom += 1;
        }
        camera.position.x = Interpolation.linear.apply(camera.position.x, player.body.getPosition().x, .1f);
        camera.position.y = Interpolation.linear.apply(camera.position.y, player.body.getPosition().y, .1f);
        camera.update();
    }

    public void updateMapView(){
        //setView must update before camera.update();
        orthogonalTiledMapRenderer.setView(camera);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        orthogonalTiledMapRenderer.dispose();
        tiledMap.dispose();
        b2dr.dispose();
    }
}
