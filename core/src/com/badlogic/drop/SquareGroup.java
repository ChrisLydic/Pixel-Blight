package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.Array;

import java.util.concurrent.ThreadLocalRandom;


/**
 * Created by chris on 5/28/2017.
 */
public class SquareGroup {
    private Square[][] squares;
    private Array<Square> affectedSquares;
    private float originX;
    private float originY;
    private short count;
    private int totalSquares;
    private int infectedSquares;
    private int curedSquares;
    private int points;
    //number of times user uses tap action successfully
    private int taps;
    //number of cells user uncorrupts
    private int uncorruptCount;

    private Array<ParticleEffectPool.PooledEffect> pooledTapEffects;
    private Array<ParticleEffectPool.PooledEffect> pooledCureEffects;
    private Array<Animation> animations;
    private int[][] originalBoard;
    private int[][] originalCorruptedSquares;

    public SquareGroup(int[][] board, int[][] corruptedSquares) {
        originalBoard = board;
        originalCorruptedSquares = corruptedSquares;

        setup();
    }

    private void setup() {
        points = ActionManager.getActionManager().getPointModifier(); // initial money starts at 0, but can be increased with IAPs
        count = 0;
        taps = 0;
        uncorruptCount = 0;
        infectedSquares = originalCorruptedSquares.length;
        totalSquares = originalBoard.length * originalBoard[0].length;
        squares = new Square[originalBoard.length][originalBoard[0].length];

        pooledTapEffects = new Array<>();
        pooledCureEffects = new Array<>();
        animations = new Array<>();
        affectedSquares = new Array<>();

        for (int r = 0; r < squares.length; r++) {
            for (int c = 0; c < squares[0].length; c++) {
                switch (originalBoard[r][c]) {
                    case 0:
                        totalSquares--;
                        squares[r][c] = new Square(Spread.EMPTY, Clicks.SINGLE);
                        break;
                    case 1:
                        squares[r][c] = new Square(Spread.NORMAL, Clicks.SINGLE);
                        break;
                    case 2:
                        squares[r][c] = new Square(Spread.SLOW, Clicks.SINGLE);
                        break;
                    case 3:
                        squares[r][c] = new Square(Spread.FAST, Clicks.SINGLE);
                        break;
                    case 4:
                        squares[r][c] = new Square(Spread.NORMAL, Clicks.DOUBLE);
                        break;
                    case 5:
                        squares[r][c] = new Square(Spread.NORMAL, Clicks.TRIPLE);
                        break;
                    case 6:
                        squares[r][c] = new Square(Spread.BOMB, Clicks.SINGLE);
                        break;
                    case 7:
                        squares[r][c] = new Square(Spread.SPRINT, Clicks.SINGLE);
                        break;
                    default:
                        throw new IllegalStateException();
                }
            }
        }

        for (int r = 0; r < squares.length; r++) {
            for (int c = 0; c < squares[0].length; c++) {
                if (squares[r][c].getSpreadType() == Spread.EMPTY) {
                    continue;
                }

                if ((r == squares.length - 1) || (squares[r + 1][c].getSpreadType() == Spread.EMPTY)) {
                    squares[r][c].setEdge(true);
                } else {
                    squares[r][c].setEdge(false);
                }

                if (r == 0){
                    if (squares[r + 1][c].getSpreadType() != Spread.EMPTY) {
                        squares[r][c].getNeighbors().add(squares[r + 1][c]);
                    }
                } else if (r == squares.length - 1) {
                    if (squares[r - 1][c].getSpreadType() != Spread.EMPTY) {
                        squares[r][c].getNeighbors().add(squares[r - 1][c]);
                    }
                } else {
                    if (squares[r + 1][c].getSpreadType() != Spread.EMPTY) {
                        squares[r][c].getNeighbors().add(squares[r + 1][c]);
                    }
                    if (squares[r - 1][c].getSpreadType() != Spread.EMPTY) {
                        squares[r][c].getNeighbors().add(squares[r - 1][c]);
                    }
                }

                if (c == 0){
                    if (squares[r][c + 1].getSpreadType() != Spread.EMPTY) {
                        squares[r][c].getNeighbors().add(squares[r][c + 1]);
                    }
                } else if (c == squares[0].length - 1) {
                    if (squares[r][c - 1].getSpreadType() != Spread.EMPTY) {
                        squares[r][c].getNeighbors().add(squares[r][c - 1]);
                    }
                } else {
                    if (squares[r][c + 1].getSpreadType() != Spread.EMPTY) {
                        squares[r][c].getNeighbors().add(squares[r][c + 1]);
                    }
                    if (squares[r][c - 1].getSpreadType() != Spread.EMPTY) {
                        squares[r][c].getNeighbors().add(squares[r][c - 1]);
                    }
                }

                if (squares[r][c].getSpreadType() == Spread.BOMB) {
                    if (r == 0 && c == 0) {
                        if (squares[r + 1][c + 1].getSpreadType() != Spread.EMPTY) {
                            squares[r][c].getNeighbors().add(squares[r + 1][c + 1]);
                        }
                    } else if (r == 0 && c == (squares[0].length - 1)) {
                        if (squares[r + 1][c - 1].getSpreadType() != Spread.EMPTY) {
                            squares[r][c].getNeighbors().add(squares[r + 1][c - 1]);
                        }
                    } else if ((r == squares.length - 1) && c == 0) {
                        if (squares[r - 1][c + 1].getSpreadType() != Spread.EMPTY) {
                            squares[r][c].getNeighbors().add(squares[r - 1][c + 1]);
                        }
                    } else if ((r == squares.length - 1) && (c == squares[0].length - 1)) {
                        if (squares[r - 1][c - 1].getSpreadType() != Spread.EMPTY) {
                            squares[r][c].getNeighbors().add(squares[r - 1][c - 1]);
                        }
                    } else if (r == 0) {
                        if (squares[r + 1][c + 1].getSpreadType() != Spread.EMPTY) {
                            squares[r][c].getNeighbors().add(squares[r + 1][c + 1]);
                        }
                        if (squares[r + 1][c - 1].getSpreadType() != Spread.EMPTY) {
                            squares[r][c].getNeighbors().add(squares[r + 1][c - 1]);
                        }
                    } else if (r == squares.length - 1) {
                        if (squares[r - 1][c + 1].getSpreadType() != Spread.EMPTY) {
                            squares[r][c].getNeighbors().add(squares[r - 1][c + 1]);
                        }
                        if (squares[r - 1][c - 1].getSpreadType() != Spread.EMPTY) {
                            squares[r][c].getNeighbors().add(squares[r - 1][c - 1]);
                        }
                    } else if (c == 0) {
                        if (squares[r - 1][c + 1].getSpreadType() != Spread.EMPTY) {
                            squares[r][c].getNeighbors().add(squares[r - 1][c + 1]);
                        }
                        if (squares[r + 1][c + 1].getSpreadType() != Spread.EMPTY) {
                            squares[r][c].getNeighbors().add(squares[r + 1][c + 1]);
                        }
                    } else if (c == squares[0].length - 1) {
                        if (squares[r - 1][c - 1].getSpreadType() != Spread.EMPTY) {
                            squares[r][c].getNeighbors().add(squares[r - 1][c - 1]);
                        }
                        if (squares[r + 1][c - 1].getSpreadType() != Spread.EMPTY) {
                            squares[r][c].getNeighbors().add(squares[r + 1][c - 1]);
                        }
                    } else {
                        if (squares[r - 1][c + 1].getSpreadType() != Spread.EMPTY) {
                            squares[r][c].getNeighbors().add(squares[r - 1][c + 1]);
                        }
                        if (squares[r - 1][c - 1].getSpreadType() != Spread.EMPTY) {
                            squares[r][c].getNeighbors().add(squares[r - 1][c - 1]);
                        }
                        if (squares[r + 1][c + 1].getSpreadType() != Spread.EMPTY) {
                            squares[r][c].getNeighbors().add(squares[r + 1][c + 1]);
                        }
                        if (squares[r + 1][c - 1].getSpreadType() != Spread.EMPTY) {
                            squares[r][c].getNeighbors().add(squares[r + 1][c - 1]);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < originalCorruptedSquares.length; i++) {
            squares[originalCorruptedSquares[i][0]][originalCorruptedSquares[i][1]].setInfected(true);
        }
    }

    public void setOrigin(float originX, float originY) {
        for (int r = 0; r < squares.length; r++) {
            for (int c = 0; c < squares[0].length; c++) {
                squares[r][c].setX(originX + ((-10*squares[0].length/2) + c*10));
                squares[r][c].setY(originY + ((-10*squares.length/2) + ((squares.length-1)-r)*10));
            }
        }
    }

    public void draw(Batch batch, float delta) {
        for (int r = 0; r < squares.length; r++) {
            for (int c = 0; c < squares[0].length; c++) {
                squares[r][c].draw(batch, delta);
            }
        }

        for (int i = pooledTapEffects.size - 1; i >= 0; i--) {
            ParticleEffectPool.PooledEffect effect = pooledTapEffects.get(i);
            effect.draw(batch, delta);
            if (effect.isComplete()) {
                effect.free();
                pooledTapEffects.removeIndex(i);
            }
        }

        for (int i = pooledCureEffects.size - 1; i >= 0; i--) {
            ParticleEffectPool.PooledEffect effect = pooledCureEffects.get(i);
            effect.draw(batch, delta);
            if (effect.isComplete()) {
                effect.free();
                pooledCureEffects.removeIndex(i);
            }
        }

        for (int i = animations.size - 1; i >= 0; i--) {
            Animation animation = animations.get(i);

            animation.draw(batch, delta);

            // remove the animation once it completes
            if (animation.isAnimationFinished()) {
                animations.removeIndex(i);
            }
        }
    }

    public void touched(float x, float y) {
        Enum<ActionManager.ActionType> action = ActionManager.getActionManager().getCurrentAction();

        for (int r = 0; r < squares.length; r++) {
            for (int c = 0; c < squares[0].length; c++) {
                if (x >= squares[r][c].getX() && x <= (squares[r][c].getX() + 10) && y >= squares[r][c].getY() && y <= (squares[r][c].getY() + 10) ) {
                    if (squares[r][c].getSpreadType() == Spread.EMPTY) {
                        continue;
                    }

                    if (action == ActionManager.ActionType.TAP) {
                        affectedSquares.clear();

                        if (ActionManager.getActionManager().getCurrentLevel() == 1) {
                            buildNeighbors(squares[r][c], affectedSquares, 0);
                        } else if (ActionManager.getActionManager().getCurrentLevel() == 2) {
                            buildNeighbors(squares[r][c], affectedSquares, 1);
                        } else if (ActionManager.getActionManager().getCurrentLevel() == 3) {
                            buildNeighbors(squares[r][c], affectedSquares, 2);
                        }

                        for (Square square : affectedSquares) {
                            if (square.isInfected()) {
                                ParticleEffectPool.PooledEffect pooledEffect = AssetsManager.getAssetsManager().getEffectPool(AssetsManager.TAP_EFFECT).obtain();
                                pooledEffect.setPosition(x, y); // Change this to be better for large hits TODO
                                pooledTapEffects.add(pooledEffect);

                                square.unCorrupt(1);
                                uncorruptCount++;
                                taps++;
                                updatePoints(10);
                            }
                        }

                    } else if (action == ActionManager.ActionType.SCATTER) {
                        ActionManager.getActionManager().setScatterCount(ActionManager.getActionManager().getScatterCount() - 1);

                        affectedSquares.clear();

                        if (ActionManager.getActionManager().getCurrentLevel() == 1) {
                            buildNeighbors(squares[r][c], affectedSquares, 2);
                        } else if (ActionManager.getActionManager().getCurrentLevel() == 2) {
                            buildNeighbors(squares[r][c], affectedSquares, 3);
                        }

                        int isHit = ThreadLocalRandom.current().nextInt(0, 3);

                        if (ActionManager.getActionManager().getCurrentLevel() == 3) {
                            for (int row = 0; row < squares.length; row++) {
                                for (Square affected: squares[row]) {
                                    if (isHit == 2) {
                                        isHit = 0;

                                        affected.unCorrupt(1);
                                        uncorruptCount++;
                                        updatePoints(1);

                                        animations.add(AssetsManager.getAssetsManager().getAnimation("_", affected.getX() - AssetsManager.SIZE / 2, affected.getY() - AssetsManager.SIZE / 2, AssetsManager.SIZE * 2, AssetsManager.SIZE * 2, 0.07f, false));
                                    } else {
                                        isHit++;
                                    }
                                }
                            }
                        } else {
                            for (Square affected : affectedSquares) {
                                if (isHit == 2) {
                                    isHit = 0;

                                    affected.unCorrupt(1);
                                    uncorruptCount++;
                                    updatePoints(1);

                                    animations.add(AssetsManager.getAssetsManager().getAnimation("_", affected.getX() - AssetsManager.SIZE / 2, affected.getY() - AssetsManager.SIZE / 2, AssetsManager.SIZE * 2, AssetsManager.SIZE * 2, 0.07f, false));
                                } else {
                                    isHit++;
                                }
                            }
                        }
                    } else if (action == ActionManager.ActionType.BOMB) {
                        ActionManager.getActionManager().setBombCount(ActionManager.getActionManager().getBombCount() - 1);

                        affectedSquares.clear();

                        if (ActionManager.getActionManager().getCurrentLevel() == 1) {
                            buildNeighbors(squares[r][c], affectedSquares, 2);

                            if (r - 2 >= 0 && c + 1 < squares[r].length) {
                                affectedSquares.add(squares[r - 2][c + 1]);
                            }
                            if (r - 1 >= 0 && c + 2 < squares[r].length) {
                                affectedSquares.add(squares[r - 1][c + 2]);
                            }

                            if (r + 1 < squares.length && c + 2 < squares[r].length) {
                                affectedSquares.add(squares[r + 1][c + 2]);
                            }
                            if (r + 2 < squares.length && c + 1 < squares[r].length) {
                                affectedSquares.add(squares[r + 2][c + 1]);
                            }

                            if (r - 2 >= 0 && c - 1 >= 0) {
                                affectedSquares.add(squares[r - 2][c - 1]);
                            }
                            if (r - 1 >= 0 && c - 2 >= 0) {
                                affectedSquares.add(squares[r - 1][c - 2]);
                            }

                            if (r + 1 < squares.length && c - 2 >= 0) {
                                affectedSquares.add(squares[r + 1][c - 2]);
                            }
                            if (r + 2 < squares.length && c - 1 >= 0) {
                                affectedSquares.add(squares[r + 2][c - 1]);
                            }

                            animations.add(AssetsManager.getAssetsManager().getAnimation("_", x - AssetsManager.SIZE * 4f, y - AssetsManager.SIZE * 4f, AssetsManager.SIZE * 8, AssetsManager.SIZE * 8, 0.07f, false));

                        } else if (ActionManager.getActionManager().getCurrentLevel() == 2) {
                            buildNeighbors(squares[r][c], affectedSquares, 4);

                            if (r - 4 >= 0) {
                                affectedSquares.removeValue(squares[r - 4][c], true);
                            }
                            if (c + 4 < squares[r].length) {
                                affectedSquares.removeValue(squares[r][c + 4], true);
                            }
                            if (r + 4 < squares.length) {
                                affectedSquares.removeValue(squares[r + 4][c], true);
                            }
                            if (c - 4 >= 0) {
                                affectedSquares.removeValue(squares[r][c - 4], true);
                            }

                            animations.add(AssetsManager.getAssetsManager().getAnimation("_", x - AssetsManager.SIZE * 5, y - AssetsManager.SIZE * 5, AssetsManager.SIZE * 10, AssetsManager.SIZE * 10, 0.07f, false));

                        } else if (ActionManager.getActionManager().getCurrentLevel() == 3) {
                            buildNeighbors(squares[r][c], affectedSquares, 5);

                            if (r - 5 >= 0) {
                                affectedSquares.removeValue(squares[r - 5][c], true);
                            }
                            if (c + 5 < squares[r].length) {
                                affectedSquares.removeValue(squares[r][c + 5], true);
                            }
                            if (r + 5 < squares.length) {
                                affectedSquares.removeValue(squares[r + 5][c], true);
                            }
                            if (c - 5 >= 0) {
                                affectedSquares.removeValue(squares[r][c - 5], true);
                            }

                            if (r - 3 >= 0 && c + 3 < squares[r].length) {
                                affectedSquares.add(squares[r - 3][c + 3]);
                            }
                            if (r + 3 < squares.length && c + 3 < squares[r].length) {
                                affectedSquares.add(squares[r + 3][c + 3]);
                            }
                            if (r - 3 >= 0 && c - 3 >= 0) {
                                affectedSquares.add(squares[r - 3][c - 3]);
                            }
                            if (r + 3 < squares.length && c - 3 >= 0) {
                                affectedSquares.add(squares[r + 3][c - 3]);
                            }

                            animations.add(AssetsManager.getAssetsManager().getAnimation("_", x - AssetsManager.SIZE * 7, y - AssetsManager.SIZE * 7, AssetsManager.SIZE * 14, AssetsManager.SIZE * 14, 0.07f, false));
                        }

                        for (Square square : affectedSquares) {
                            square.unCorrupt(1);
                            uncorruptCount++;
                            updatePoints(1);
                        }

                    } else if (action == ActionManager.ActionType.CURE) {
                        ActionManager.getActionManager().setCureCount(ActionManager.getActionManager().getCureCount() - 1);

                        ParticleEffectPool.PooledEffect pooledEffect = AssetsManager.getAssetsManager().getEffectPool(AssetsManager.CURE_EFFECT).obtain();
                        pooledEffect.setPosition(x, y);
                        pooledCureEffects.add(pooledEffect);

                        squares[r][c].cure();
                        uncorruptCount++;
                        updatePoints(1);
                    }
                    break;
                }
            }
        }
    }

    public void corrupt(float delta) {
        affectedSquares.clear();
        infectedSquares = 0;
        curedSquares = 0;

        for (int r = 0; r < squares.length; r++) {
            for (int c = 0; c < squares[0].length; c++) {
                if (squares[r][c].getSpreadType() == Spread.EMPTY) {
                    continue;
                }

                if (squares[r][c].isInfected()) {
                    infectedSquares++;

                    if (squares[r][c].getSpreadType() == Spread.NORMAL && !(count == 3 || count == 7)) {continue;}
                    if (squares[r][c].getSpreadType() == Spread.SLOW && count != 7) {continue;}

                    int corruptCount = (int) Math.ceil(squares[r][c].getNeighbors().size / 2f);

                    for (int i = 0; i < corruptCount; i++) {
                        Square neighbor = squares[r][c].getNeighbors().get((int)Math.floor(Math.random() * squares[r][c].getNeighbors().size));

                        if (!neighbor.isInfected() && !neighbor.isCured() && !affectedSquares.contains(neighbor, true)) {
                            affectedSquares.add(neighbor);
                        }
                    }
                } else if (squares[r][c].isCured()) {
                    curedSquares++;
                }
            }
        }

        for (Square newInfected : affectedSquares) {
            newInfected.corrupt();
        }

        if (count == 7) {
            count = 0;
        } else {
            count++;
        }

        if (infectedSquares + curedSquares == totalSquares) {
            for (int r = 0; r < squares.length; r++) {
                for (int c = 0; c < squares[0].length; c++) {
                    if (squares[r][c].isCured()) {
                        squares[r][c].unCure();
                    }
                }
            }
        }
    }

    public boolean isWon() {
        return infectedSquares == 0;
    }

    public boolean isLost() {
        return infectedSquares == totalSquares;
    }

    public float getProgress() {
        return 1 - (infectedSquares/(float)totalSquares);
    }

    public void reset() {
        ActionManager.getActionManager().reset();
        setup();
    }

    private void buildNeighbors(Square square, Array<Square> returnedSquares, int depth) {
        if (depth == 0) {
            if (!returnedSquares.contains(square, true)) {
                returnedSquares.add(square);
            }
        } else {
            for (int i = 0; i < square.getNeighbors().size; i++) {
                buildNeighbors(square.getNeighbors().get(i), returnedSquares, depth - 1);
            }

            if (!returnedSquares.contains(square, true)) {
                returnedSquares.add(square);
            }
        }
    }

    public int calculateStars() {
        int stars = 1;
        Gdx.app.log("H: ", Integer.toString(taps));
        Gdx.app.log("S: ", Integer.toString(uncorruptCount));

        if ((float)taps / (float)uncorruptCount > 0.9) {
            stars = 3;
        } else if ((float)taps / (float)uncorruptCount > 0.6) {
            stars = 2;
        }

        return stars;//TODO: real calculation
    }

    public int getPoints() {
        return points;
    }

    public void updatePoints(int points) {
        if (points > 0 && Integer.MAX_VALUE - this.points < points) {
            return; //avoid overflow
        }

        this.points += points;
        if (this.points < 0) {
            this.points = 0;
            Gdx.app.error("SquareGroup", "Incorrect adjustment of points: adjustment value " + points);
            throw new IllegalStateException();
        }
    }
}
