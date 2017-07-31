package com.badlogic.drop;

import com.badlogic.gdx.utils.Array;

/**
 * Created by chris on 7/27/2017.
 */
public class DataAccess {
    private static DataAccess dataAccess;

    private int[][] level0 = new int[][]{{1,1,1,1},{1,1,1,1},{1,1,1,1},{1,1,1,1}};private int[][] level0Corrupt = new int[][]{{0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}};
    private int[][] level1 = new int[][]{{3,3,3,3,2,2,2,0,0},{0,3,3,3,3,2,2,2,0},{0,0,3,3,3,3,2,2,2},{0,3,3,3,3,2,2,2,0},{3,3,3,3,2,2,2,0,0}};private int[][] level1Corrupt = new int[][]{{0,0}, {1,1}, {2,2}, {3,1}, {4,0}};
    private int[][] level2 = new int[][]{{0,0,1,1,0,0},{0,1,4,4,1,0},{1,4,4,4,4,1},{1,4,5,5,4,1},{1,4,5,5,4,1},{1,4,4,4,4,1},{0,1,4,4,1,0},{0,0,1,1,0,0}};private int[][] level2Corrupt = new int[][]{{1,1}, {6,4}, {3,2}, {4,3}};
    private int[][] level3 = new int[][]{{0,0,0,0,6,6,0,0,0,0},{6,0,6,0,6,0,6,0,0,0},{0,6,6,6,0,6,6,6,0,0},{0,0,6,0,6,0,6,0,6,6},{0,0,0,6,6,6,0,6,0,6},{0,0,6,0,6,0,6,6,6,0},{0,0,6,6,0,6,0,6,0,6},{0,0,0,0,6,6,6,0,0,0},{0,0,0,0,0,6,0,0,0,0}};
    private int[][] level3Corrupt = new int[][]{{1,0}, {6,9}};private int[][] level4 = new int[][]{{7,0,0,0,0,0},{7,0,0,0,0,0},{7,7,7,7,7,7},{7,1,1,1,1,7},{7,1,0,0,1,7},{7,1,0,0,1,7},{7,1,1,1,1,7},{7,7,7,7,7,7},{0,0,0,0,0,7},{0,0,0,0,0,7}};
    private int[][] level4Corrupt = new int[][]{{0,0}, {9,5}};private int[][] level5 = new int[][]{{0,0,0,1,1,1,0,0,0},{0,1,1,1,1,1,1,1,0},{0,1,1,1,1,1,1,1,0},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{0,1,1,1,1,1,1,1,0},{0,1,1,1,1,1,1,1,0},{0,0,0,1,1,1,0,0,0}};
    private int[][] level5Corrupt = new int[][]{{0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}};private int[][] level6 = new int[][]{{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1}};
    private int[][] level6Corrupt = new int[][]{{0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}};private int[][] level7 = new int[][]{{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1}};
    private int[][] level7Corrupt = new int[][]{{0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}};private int[][] level8 = new int[][]{{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1}};
    private int[][] level8Corrupt = new int[][]{{0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}};private int[][] level9 = new int[][]{{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1}};
    private int[][] level9Corrupt = new int[][]{{0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}};

    private Array<LevelGroup> levelGroups;

    private DataAccess() {
    }

    public static DataAccess getInstance() {
        if (dataAccess == null) {
            dataAccess = new DataAccess();
        }
        return dataAccess;
    }

    public Array<LevelGroup> getWorlds() {
        if (levelGroups == null) {

            //world 2

            Array<Level> levels2 = new Array<>(true, 5);

            Level levelType15 = new Level(5, false, 0, new SquareGroup(level4, level4Corrupt), null);
            Level levelType14 = new Level(4, false, 0, new SquareGroup(level3, level3Corrupt), levelType15);
            Level levelType13 = new Level(3, false, 0, new SquareGroup(level2, level2Corrupt), levelType14);
            Level levelType12 = new Level(2, false, 0, new SquareGroup(level1, level1Corrupt), levelType13);
            Level levelType11 = new Level(1, false, 0, new SquareGroup(level0, level0Corrupt), levelType12);

            levels2.add(levelType11);
            levels2.add(levelType12);
            levels2.add(levelType13);
            levels2.add(levelType14);
            levels2.add(levelType15);

            LevelGroup levelGroup2 = new LevelGroup(2, "", true, false, false, levels2, null);

            levelType11.setWorld(levelGroup2);
            levelType12.setWorld(levelGroup2);
            levelType13.setWorld(levelGroup2);
            levelType14.setWorld(levelGroup2);
            levelType15.setWorld(levelGroup2);

            //world 1

            Array<Level> levels = new Array<>(true, 10);

            Level levelType10 = new Level(10, true, 0, new SquareGroup(level9, level9Corrupt), null);
            Level levelType9 = new Level(9, true, 0, new SquareGroup(level8, level8Corrupt), levelType10);
            Level levelType8 = new Level(8, true, 0, new SquareGroup(level7, level7Corrupt), levelType9);
            Level levelType7 = new Level(7, false, 0, new SquareGroup(level6, level6Corrupt), levelType8);
            Level levelType6 = new Level(6, false, 0, new SquareGroup(level5, level5Corrupt), levelType7);
            Level levelType5 = new Level(5, false, 0, new SquareGroup(level4, level4Corrupt), levelType6);
            Level levelType4 = new Level(4, false, 0, new SquareGroup(level3, level3Corrupt), levelType5);
            Level levelType3 = new Level(3, false, 0, new SquareGroup(level2, level2Corrupt), levelType4);
            Level levelType2 = new Level(2, false, 0, new SquareGroup(level1, level1Corrupt), levelType3);
            Level levelType1 = new Level(1, false, 0, new SquareGroup(level0, level0Corrupt), levelType2);

            levels.add(levelType1);
            levels.add(levelType2);
            levels.add(levelType3);
            levels.add(levelType4);
            levels.add(levelType5);
            levels.add(levelType6);
            levels.add(levelType7);
            levels.add(levelType8);
            levels.add(levelType9);
            levels.add(levelType10);

            LevelGroup levelGroup = new LevelGroup(1, "", false, false, false, levels, levelGroup2);

            levelType1.setWorld(levelGroup);
            levelType2.setWorld(levelGroup);
            levelType3.setWorld(levelGroup);
            levelType4.setWorld(levelGroup);
            levelType5.setWorld(levelGroup);
            levelType6.setWorld(levelGroup);
            levelType7.setWorld(levelGroup);
            levelType8.setWorld(levelGroup);
            levelType9.setWorld(levelGroup);
            levelType10.setWorld(levelGroup);


            levelGroups = new Array<>();
            levelGroups.add(levelGroup);
        }
        return levelGroups;
    }

    public void saveWorlds() {}
    public void saveLevel() {}
}
