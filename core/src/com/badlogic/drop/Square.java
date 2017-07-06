package com.badlogic.drop;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

enum Spread {
    EMPTY,
    SLOW,
    NORMAL,
    FAST,
    BOMB,
    SPRINT
}
enum Clicks {
    SINGLE,
    DOUBLE,
    TRIPLE
}

/**
 * Created by chris on 6/17/2017.
 */
public class Square {
    private int color;
    private Enum<Spread> spreadType;
    private Enum<Clicks> clicksType;
    private boolean infected;
    private float x;
    private float y;
    private Array<Square> neighbors;
    private int clickCount;
    private boolean cured;
    private Animation animation;
    private boolean edge;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Enum<Spread> getSpreadType() {
        return spreadType;
    }

    public boolean isInfected() {
        return infected;
    }

    public void setInfected(boolean infected) {
        this.infected = infected;
    }

    public Array<Square> getNeighbors() {
        return neighbors;
    }

    public boolean isCured() {
        return cured;
    }

    public void setEdge(boolean edge) {
        this.edge = edge;
    }

    public Square(Enum<Spread> spread, Enum<Clicks> clicks) {
        clickCount = 0;
        spreadType = spread;
        clicksType = clicks;
        cured = false;

        if (spreadType == Spread.SLOW) {
            color = 2;
        } else if (spreadType == Spread.FAST) {
            color = 5;
        } else if (spreadType == Spread.BOMB) {
            color = 4;
        } else if (spreadType == Spread.SPRINT) {
            color = 6;
        } else if (clicksType == Clicks.DOUBLE) {
            color = 1;
        } else if (clicksType == Clicks.TRIPLE) {
            color = 3;
        } else if (clicksType == Clicks.SINGLE) {
            color = 0;
        }

        neighbors = new Array<>();
    }

    public void draw(Batch batch, float delta) {
        if (spreadType == Spread.EMPTY) {
            return;
        }

        if (animation != null) {
            animation.draw(batch, delta);

            // remove the animation once it completes
            if (animation.isAnimationFinished()) {
                animation = null;
            }
        } else {
            if (edge) {
                if (infected) {
                    batch.draw(AssetsManager.getAssetsManager().getTiles()[color][AssetsManager.DARK_EDGE_TILE], x, y, AssetsManager.SIZE, AssetsManager.SIZE);
                } else {
                    batch.draw(AssetsManager.getAssetsManager().getTiles()[color][AssetsManager.EDGE_TILE], x, y, AssetsManager.SIZE, AssetsManager.SIZE);
                }
            } else if (infected) {
                batch.draw(AssetsManager.getAssetsManager().getTiles()[color][AssetsManager.DARK_TILE], x, y, AssetsManager.SIZE, AssetsManager.SIZE);
            } else {
                batch.draw(AssetsManager.getAssetsManager().getTiles()[color][AssetsManager.NORMAL_TILE], x, y, AssetsManager.SIZE, AssetsManager.SIZE);
            }
        }
    }

    public void unCorrupt(int clicks) {
        if (infected) {
            clickCount += clicks;

            if (clicksType == Clicks.DOUBLE && clickCount == 2) {
                infected = false;
                clickCount = 0;
                if (edge) {
                    setAnimation(AssetsManager.EDGE_UNCORRUPT_ANIMATION, 0.07f, false);
                } else {
                    setAnimation(AssetsManager.NORMAL_UNCORRUPT_ANIMATION, 0.07f, false);
                }
            } else if (clicksType == Clicks.TRIPLE && clickCount == 3) {
                infected = false;
                clickCount = 0;
                if (edge) {
                    setAnimation(AssetsManager.EDGE_UNCORRUPT_ANIMATION, 0.07f, false);
                } else {
                    setAnimation(AssetsManager.NORMAL_UNCORRUPT_ANIMATION, 0.07f, false);
                }
            } else if (clicksType == Clicks.SINGLE) {
                infected = false;
                clickCount = 0;
                if (edge) {
                    setAnimation(AssetsManager.EDGE_UNCORRUPT_ANIMATION, 0.07f, false);
                } else {
                    setAnimation(AssetsManager.NORMAL_UNCORRUPT_ANIMATION, 0.07f, false);
                }
            }
        }
    }

    public void corrupt() {
        infected = true;
        if (edge) {
            setAnimation(AssetsManager.EDGE_CORRUPT_ANIMATION, 0.07f, false);
        } else {
            setAnimation(AssetsManager.NORMAL_CORRUPT_ANIMATION, 0.07f, false);
        }
    }

    public void cure() {
        infected = false;
        cured = true;
        if (edge) {
            setAnimation(AssetsManager.EDGE_CURE_ANIMATION, 0.2f, true);
        } else {
            setAnimation(AssetsManager.NORMAL_CURE_ANIMATION, 0.2f, true);
        }
    }

    public void unCure() {
        cured = false;
        animation = null;
    }

    private void setAnimation(int animationType, float duration, boolean loop) {
        animation = AssetsManager.getAssetsManager().getTileAnimation(color, animationType, x, y, duration, loop);
    }
}