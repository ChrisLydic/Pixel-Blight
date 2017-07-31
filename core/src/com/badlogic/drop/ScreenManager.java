package com.badlogic.drop;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by chris on 6/2/2017.
 */
public class ScreenManager {
    private static ScreenManager screenManager;
    private final Drop game;
    private Array<Screen> screenStack;

    private ScreenManager(final Drop game) {
        this.game = game;
        this.screenStack = new Array<Screen>(true, 10);
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
        privatePop();

        game.setScreen(screenStack.peek());
    }

    public void pop(int times) {
        for (int i = 0; i < times; i++) {
            privatePop();
        }

        game.setScreen(screenStack.peek());
    }

    public void popPush(int times, Screen screen) {
        for (int i = 0; i < times; i++) {
            privatePop();
        }

        push(screen);
    }

    private void privatePop() {
        Screen screen = screenStack.pop();

        screen.hide();
        screen.dispose();
    }
}
