package com.badlogic.drop;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by chris on 6/17/2017.
 */
public class Animation extends com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> {
    private float stateTime;
    private float x;
    private float y;
    private float width;
    private float height;
    private boolean loop;

    public Animation (float frameDuration, TextureRegion[] keyFrames, float x, float y, float width, float height, boolean loop) {
        super(frameDuration, keyFrames);
        this.stateTime = 0;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.loop = loop;
    }

    public void draw(Batch batch, float delta) {
        stateTime += delta;

        batch.draw(super.getKeyFrame(stateTime, loop), x, y, width, height);
    }

    public boolean isAnimationFinished() {
        if (loop) {
            return false;
        } else {
            return super.isAnimationFinished(stateTime);
        }
    }
}
