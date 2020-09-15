package maps;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class AnimationTiledObject {

    public AnimationTiledObject(TiledMap tiledMap){
        //frames
        Array<StaticTiledMapTile> framesTile = new Array<>();
        //getting frame tiles.
        Iterator<TiledMapTile> tiles = tiledMap.getTileSets().getTileSet("portal").iterator();
        while (tiles.hasNext()){
            TiledMapTile tiledMapTile = tiles.next();
            if(tiledMapTile.getProperties().containsKey("animation") && tiledMapTile.getProperties().get("animation", String.class).equalsIgnoreCase("portal")){
                framesTile.add((StaticTiledMapTile) tiledMapTile);
            }
        }
        //create animation tiled.
        AnimatedTiledMapTile animatedTiledMapTile = new AnimatedTiledMapTile(1/9f, framesTile);
        TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) tiledMap.getLayers().get("portal");
        for(int x = 0; x< tiledMapTileLayer.getWidth(); x++){
            for(int y = 0; y< tiledMapTileLayer.getHeight(); y++){
                TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell(x, y);
                if(cell != null && cell.getTile().getProperties().containsKey("animation") && cell.getTile().getProperties().get("animation", String.class).equalsIgnoreCase("portal")){
                    cell.setTile(animatedTiledMapTile);
                }
            }
        }
    }

}
