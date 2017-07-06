package com.badlogic.drop.screens;

import com.badlogic.drop.AssetsManager;
import com.badlogic.drop.Drop;
import com.badlogic.drop.ScreenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by chris on 5/28/2017.
 */
public class LevelGroupMenuScreen implements Screen {
    final Drop game;
    OrthographicCamera camera;
    Viewport viewport;

    private Stage stage;
    private Table table;

    public LevelGroupMenuScreen(final Drop game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        camera.update();

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Table tableButtons = new Table();
        Table tableLevels = new Table();

        table.setBackground(AssetsManager.getAssetsManager().getDrawable(AssetsManager.BACKGROUND_COLOR));

        Skin skin = AssetsManager.getAssetsManager().getUISkin();

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

        for (int i = 0; i < 10; i++) {
            Button button = new Button(skin);
            Image image = new Image(AssetsManager.getAssetsManager().getTexture(AssetsManager.LEVELS_1));
            image.setScaling(Scaling.fit);
            Label label = new Label("Level 1", skin);

            button.add(image).expand().fill();
            button.row();
            button.add(label);
            button.pad(20);

            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    ScreenManager.getInstance(game).push(new LevelMenuScreen(game, ""));
                }
            });

            tableLevels.add(button).minWidth(200).maxHeight(500).expand().fill().padRight(50);
        }
        tableLevels.pad(50);

        table.setBackground(AssetsManager.getAssetsManager().getDrawable(AssetsManager.WORLD_MAP));

        tableButtons.add(button1).left();
        tableButtons.add(button2).center().expand();
        tableButtons.add(button3).right();
        table.add(tableButtons).pad(10).expandX().fill();
        table.row().expand().fill();
        table.add(scrollPane);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
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
    }
}

