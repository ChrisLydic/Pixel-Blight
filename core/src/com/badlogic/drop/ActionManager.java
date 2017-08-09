package com.badlogic.drop;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.Array;


/**
 * Created by chris on 6/7/2017.
 */
public class ActionManager {
    public static final int MAX_TAP_LEVEL = 3;
    public static final int MAX_SCATTER_LEVEL = 3;
    public static final int MAX_BOMB_LEVEL = 3;
    public static final int MAX_CURE_LEVEL = 3;

    public static final int MID_TAP_LEVEL = 2;
    public static final int MID_SCATTER_LEVEL = 2;
    public static final int MID_BOMB_LEVEL = 2;
    public static final int MID_CURE_LEVEL = 2;

    public static final int MAX_SCATTER_COUNT = 100;
    public static final int MAX_BOMB_COUNT = 100;
    public static final int MAX_CURE_COUNT = 100;

    public static final int TAP_UPGRADE_1_COST = 100;
        public static final int TAP_UPGRADE_2_COST = 300;
    public static final int SCATTER_UPGRADE_1_COST = 100;
        public static final int SCATTER_UPGRADE_2_COST = 300;
    public static final int BOMB_UPGRADE_1_COST = 100;
        public static final int BOMB_UPGRADE_2_COST = 300;
    public static final int CURE_UPGRADE_1_COST = 100;
        public static final int CURE_UPGRADE_2_COST = 300;

    public static final int SCATTER_ITEM_COST = 30;
    public static final int BOMB_ITEM_COST = 30;
    public static final int CURE_ITEM_COST = 30;

    private static ActionManager actionManager;
    private Enum<ActionType> currentAction;
    private int tapLevel;
    private int tapLevelModifier;
    private int tapCount;
    private int tapCountModifier;

    private int scatterLevel;
    private int scatterLevelModifier;
    private int scatterCount;
    private int scatterCountModifier;

    private int bombLevel;
    private int bombLevelModifier;
    private int bombCount;
    private int bombCountModifier;

    private int cureLevel;
    private int cureLevelModifier;
    private int cureCount;
    private int cureCountModifier;


    public enum ActionType {
        TAP,
        //    FAT_FINGERS,
//    SLAP,
        SCATTER,
        //    BIG_SCATTER,
//    AIRSTRIKE,
        BOMB,
        //    BIG_BOMB,
//    NUKE,
        CURE,
//    SPLASH_CURE
    }

    private ActionManager() {
        tapLevelModifier = 0;
        scatterLevelModifier = 0;
        bombLevelModifier = 0;
        cureLevelModifier = 0;

        scatterCountModifier = 0;
        bombCountModifier = 0;
        cureCountModifier = 0;

        reset();
    }

    public static ActionManager getActionManager() {
        if (actionManager == null) {
            actionManager = new ActionManager();
        }
        return actionManager;
    }

    public Enum<ActionType> getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(Enum<ActionType> currentAction) {
        this.currentAction = currentAction;
    }

    public int getCurrentLevel() {
        if (currentAction == ActionType.TAP) {
            return tapLevel;
        } else if (currentAction == ActionType.SCATTER) {
            return scatterLevel;
        } else if (currentAction == ActionType.BOMB) {
            return bombLevel;
        } else if (currentAction == ActionType.CURE) {
            return cureLevel;
        }
        throw new IllegalStateException();
    }

    public void incrementTapLevel() {
        if (tapLevel < MAX_TAP_LEVEL) {
            this.tapLevel++;
        }
    }

    public int getTapLevel() { return this.tapLevel; }

    public int getTapLevelModifier() {
        return tapLevelModifier;
    }

    public void setTapLevelModifier(int tapLevelModifier) {
        this.tapLevelModifier = tapLevelModifier;
    }

    public void incrementScatterLevel() {
        if (scatterLevel < MAX_SCATTER_LEVEL) {
            this.scatterLevel++;
        }
    }

    public int getScatterLevel() { return this.scatterLevel; }

    public int getScatterLevelModifier() {
        return scatterLevelModifier;
    }

    public void setScatterLevelModifier(int scatterLevelModifier) {
        this.scatterLevelModifier = scatterLevelModifier;
    }

    public void incrementBombLevel() {
        if (bombLevel < MAX_BOMB_LEVEL) {
            this.bombLevel++;
        }
    }

    public int getBombLevel() { return this.bombLevel; }

    public int getBombLevelModifier() {
        return bombLevelModifier;
    }

    public void setBombLevelModifier(int bombLevelModifier) {
        this.bombLevelModifier = bombLevelModifier;
    }

    public void incrementCureLevel() {
        if (cureLevel < MAX_CURE_LEVEL) {
            this.cureLevel++;
        }
    }

    public int getCureLevel() { return this.cureLevel; }

    public int getCureLevelModifier() {
        return cureLevelModifier;
    }

    public void setCureLevelModifier(int cureLevelModifier) {
        this.cureLevelModifier = cureLevelModifier;
    }

    public int getTapCount() {
        return tapCount;
    }

    public void setTapCount(int tapCount) {
        this.tapCount = tapCount;
    }

    public int getTapCountModifier() {
        return tapCountModifier;
    }

    public void setTapCountModifier(int tapCountModifier) {
        this.tapCountModifier = tapCountModifier;
    }

    public int getScatterCount() {
        return scatterCount;
    }

    public void setScatterCount(int scatterCount) {
        this.scatterCount = scatterCount;
    }

    public int getScatterCountModifier() {
        return scatterCountModifier;
    }

    public void setScatterCountModifier(int scatterCountModifier) {
        this.scatterCountModifier = scatterCountModifier;
    }

    public int getBombCount() {
        return bombCount;
    }

    public void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }

    public int getBombCountModifier() {
        return bombCountModifier;
    }

    public void setBombCountModifier(int bombCountModifier) {
        this.bombCountModifier = bombCountModifier;
    }

    public int getCureCount() {
        return cureCount;
    }

    public void setCureCount(int cureCount) {
        this.cureCount = cureCount;
    }

    public int getCureCountModifier() {
        return cureCountModifier;
    }

    public void setCureCountModifier(int cureCountModifier) {
        this.cureCountModifier = cureCountModifier;
    }

    public void reset() {
        currentAction = ActionType.TAP;

        tapLevel = 1 + tapLevelModifier;
        scatterLevel = 1 + scatterLevelModifier;
        bombLevel = 1 + bombLevelModifier;
        cureLevel = 1 + cureLevelModifier;

        scatterCount = scatterCountModifier;
        bombCount = bombCountModifier;
        cureCount = cureCountModifier;
    }
}
