package hud.statusBar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import entities.Enemy;

import static constants.Constants.PPM;
import static constants.Constants.V_HEIGHT;
import static constants.Constants.V_WIDTH;

public class EnemyStatusBar {

    private Enemy enemy;
    public ShapeRenderer hpmp;
    public float enemyHp;

    public EnemyStatusBar(Enemy enemy){
        this.enemy = enemy;
        hpmp = new ShapeRenderer();
        hpmp.setAutoShapeType(true);
        enemyHp = 100f;
    }

    public void renderEnemyHp(){
        Vector3 pos = enemy.game.camera.project(new Vector3(enemy.body.getPosition().x, enemy.body.getPosition().y + (enemy.getHeight() / 2f) - ((enemy.getOriginHeight() - enemy.getOriginBodyHeight()) / 2f) / PPM, 0), 0, 0, V_WIDTH, V_HEIGHT);
        float hpWidth = enemy.getOriginBodyWidth() * 4;
        hpmp.setProjectionMatrix(enemy.game.stage.getCamera().combined);
        hpmp.begin(ShapeRenderer.ShapeType.Filled);
        //hp background
        hpmp.setColor(Color.GRAY);
        hpmp.rect(pos.x - (hpWidth / 2f), pos.y + 40, hpWidth, 16);
        //hp
        hpmp.setColor(Color.RED);
        hpmp.rect(pos.x - (hpWidth / 2f), pos.y + 40, hpWidth * enemyHp / 100, 16);
        hpmp.end();
    }

}
