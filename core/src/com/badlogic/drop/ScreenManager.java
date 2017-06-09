package com.badlogic.drop;

import com.badlogic.gdx.Screen;

import java.util.ArrayList;

/**
 * Created by chris on 6/2/2017.
 */
public class ScreenManager {
    private static ScreenManager screenManager;
    private final Drop game;
    private ArrayList<Screen> screenStack;

    private ScreenManager(final Drop game) {
        this.game = game;
        this.screenStack = new ArrayList<Screen>();
    }

    public static ScreenManager getInstance(final Drop game) {
        if (screenManager == null) {
            screenManager = new ScreenManager(game);
        }
        return screenManager;
    }

    public void push(Screen screen) {
        screenStack.add(screen);
        game.setScreen(screen);
    }

    public void pop() {
        Screen screen = screenStack.remove(screenStack.size() - 1);

        screen.hide();
        screen.dispose();

        game.setScreen(screenStack.get(screenStack.size() - 1));
    }
}
