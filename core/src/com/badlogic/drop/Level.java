package com.badlogic.drop;

/**
 * Created by chris on 7/27/2017.
 */
public class Level {
    private int number;
    private boolean locked;
    private int stars;
    private SquareGroup levelData;
    private Level nextLevel;
    private LevelGroup world;

    public Level(int number, boolean locked, int stars, SquareGroup levelData) {
        this.number = number;
        this.locked = locked;
        this.stars = stars;
        this.levelData = levelData;
        this.nextLevel = null;
    }

    public Level(int number, boolean locked, int stars, SquareGroup levelData, Level nextLevel) {
        this(number, locked, stars, levelData);
        this.nextLevel = nextLevel;
    }

    public int getNumber() {
        return number;
    }

    public boolean isLocked() {
        return locked;
    }

    public void unlock() {
        locked = false;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public SquareGroup getLevelData() {
        return levelData;
    }

    public Level getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(Level nextLevel) {
        this.nextLevel = nextLevel;
    }

    public LevelGroup getWorld() {
        return world;
    }

    public void setWorld(LevelGroup world) {
        this.world = world;
    }
}
