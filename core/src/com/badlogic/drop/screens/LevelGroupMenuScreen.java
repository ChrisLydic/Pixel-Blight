package com.badlogic.drop.screens;

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
    AssetManager assetManager;

    private Stage stage;
    private Table table;

    public LevelGroupMenuScreen(final Drop game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        camera.update();

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        assetManager = new AssetManager();
        assetManager.load("uiskin.json", Skin.class,
                new SkinLoader.SkinParameter("uiskin.atlas"));
        assetManager.finishLoading();
        Skin skin = assetManager.get("uiskin.json", Skin.class);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Table tableButtons = new Table();
        Table tableLevels = new Table();

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new NinePatchDrawable(skin.getPatch("default-round-large"));
        style.over = new NinePatchDrawable(skin.getPatch("default-over"));
        style.down = new NinePatchDrawable(skin.getPatch("default-scroll"));
        style.font = skin.getFont("default-font");

        TextButton button1 = new TextButton("Back", skin);
        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).pop();
            }
        });

        TextButton button2 = new TextButton("Settings", skin);

        ScrollPane scrollPane = new ScrollPane(tableLevels, skin);

        for (int i = 0; i < 10; i++) {
            Button button = new Button(skin);
            Image image = new Image(new Texture(Gdx.files.internal("levels1.png")));
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

        tableButtons.add(button1).left().expand();
        tableButtons.add(button2).right();
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
        assetManager.dispose();
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

