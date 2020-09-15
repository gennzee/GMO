package entities.animations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Animation {

    public Animation(){}

    public static com.badlogic.gdx.graphics.g2d.Animation createAnimation(TextureRegion[][] tmp, int ROWS, int COLUMNS, float duration){
        TextureRegion[] frames = new TextureRegion[COLUMNS * ROWS];
        int index = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return new com.badlogic.gdx.graphics.g2d.Animation<>(duration, frames);
    }

    public abstract TextureRegion getTextureRegion(float dt);

}
