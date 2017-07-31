package com.badlogic.drop.screens;

import com.badlogic.drop.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by chris on 5/28/2017.
 */
public class LevelMenuScreen implements Screen {
    final Drop game;
    OrthographicCamera camera;
    Viewport viewport;

    private LevelGroup world;
    private Stage stage;
    private Table table;
    private Table tableLevels;


    public LevelMenuScreen(final Drop game, LevelGroup world) {
        this.game = game;
        this.world = world;

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        camera.update();

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        Skin skin = AssetsManager.getAssetsManager().getUISkin();

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

//        table.setBackground(AssetsManager.getAssetsManager().getDrawable(AssetsManager.BACKGROUND_COLOR));

        Table tableButtons = new Table();
        tableLevels = new Table();

        TextButton button1 = new TextButton("Back", skin);
        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).pop();
            }
        });
        TextButton button2 = new TextButton("IAP", skin);
        TextButton button3 = new TextButton("Settings", skin);
        button3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).push(new SettingsScreen(game));
            }
        });

        ScrollPane scrollPane = new ScrollPane(tableLevels, skin);


        tableLevels.pad(50);

        tableButtons.add(button1).left();
        tableButtons.add(button2).center().expand();
        tableButtons.add(button3).right();
        table.add(tableButtons).pad(AssetsManager.getAssetsManager().getPadding()).expandX().fill();
        table.row().expand().fill();
        table.add(scrollPane);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(213/255f, 200/255f, 166/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        viewport.update(width, height, true);
        camera.update();
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        tableLevels.clearChildren();
        setTableLevels();
    }

    private void setTableLevels() {
        for (final Level level : world.getLevels()) {
            TextButton button = new TextButton(Integer.toString(level.getNumber()), AssetsManager.getAssetsManager().getUISkin(), "level-" + level.getStars() + "-star");

            if (level.isLocked()) {
                button.setDisabled(true);
            }

            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    ScreenManager.getInstance(game).push(new GameScreen(game, level));
                }
            });

            tableLevels.add(button).pad(20);
            if ((level.getNumber()) % 5 == 0) {
                tableLevels.row();
            }
        }
    }
}

