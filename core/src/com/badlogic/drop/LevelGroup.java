package com.badlogic.drop;

import com.badlogic.gdx.utils.Array;

/**
 * Created by chris on 7/27/2017.
 */
public class LevelGroup {
    private int number;
    private String name;
    private boolean locked;
    private boolean complete;
    private boolean fullStars;
    private Array<Level> levels;
    private LevelGroup nextGroup;

    public LevelGroup(int number, String name, boolean locked, boolean complete, boolean fullStars, Array<Level> levels, LevelGroup nextGroup) {
        this.number = number;
        this.name = name;
        this.locked = locked;
        this.complete = complete;
        this.fullStars = fullStars;
        this.levels = levels;
        this.nextGroup = nextGroup;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public boolean isLocked() {
        return locked;
    }

    public void unlock() {
        locked = false;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setCompleted() {
        complete = true;
    }

    public boolean isFullStars() {
        return fullStars;
    }

    public void setFullStars() {
       fullStars = true;
    }

    public Array<Level> getLevels() {
        return levels;
    }

    public LevelGroup getNextGroup() {
        return nextGroup;
    }

    public void setNextGroup(LevelGroup nextGroup) {
        this.nextGroup = nextGroup;
    }
}
