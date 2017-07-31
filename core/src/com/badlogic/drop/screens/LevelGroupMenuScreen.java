package com.badlogic.drop.screens;

import com.badlogic.drop.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
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

    private Sprite world;
    private Stage stage;
    private Table table;
    private TextButton button1;
    private Button area1;
    private Button area2;
    private Button area3;
    private Button area4;
    private Button area5;
    private Button area6;
    private Button area7;
    private Button area8;
    private Button area9;
    private Button area10;
    private float x;
    private float y;
    private float width;
    private float height;

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

        world = AssetsManager.getAssetsManager().getSprite(AssetsManager.WORLD_MAP);
        x = 0;
        y = 0;

        Skin skin = AssetsManager.getAssetsManager().getUISkin();

        button1 = new TextButton("Back", skin);
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

        table.add(button1).pad(AssetsManager.getAssetsManager().getPadding()).left().top();
        table.add(button2).padTop(AssetsManager.getAssetsManager().getPadding()).center().expandX().top();
        table.add(button3).pad(AssetsManager.getAssetsManager().getPadding()).right().top();
        table.top();

        area1 = new Button(skin, "world-1-style");
        area1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).push(new LevelMenuScreen(game, DataAccess.getInstance().getWorlds().first()));
            }
        });
        stage.addActor(area1);


        area2 = new Button(skin, "world-2-style");
        area2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).push(new LevelMenuScreen(game, null));
            }
        });
        area2.setDisabled(true);
        stage.addActor(area2);

        area3 = new Button(skin, "world-3-style");
        area3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).push(new LevelMenuScreen(game, null));
            }
        });
        area3.setDisabled(true);
        stage.addActor(area3);

        area4 = new Button(skin, "world-4-style");
        area4.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).push(new LevelMenuScreen(game, null));
            }
        });
        area4.setDisabled(true);
        stage.addActor(area4);

        area5 = new Button(skin, "world-5-style");
        area5.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).push(new LevelMenuScreen(game, null));
            }
        });
        area5.setDisabled(true);
        stage.addActor(area5);

        area6 = new Button(skin, "world-6-style");
        area6.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).push(new LevelMenuScreen(game, null));
            }
        });
        area6.setDisabled(true);
        stage.addActor(area6);

        area7 = new Button(skin, "world-7-style");
        area7.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).push(new LevelMenuScreen(game, null));
            }
        });
        area7.setDisabled(true);
        stage.addActor(area7);

        area8 = new Button(skin, "world-8-style");
        area8.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).push(new LevelMenuScreen(game, null));
            }
        });
        area8.setDisabled(true);
        stage.addActor(area8);

        area9 = new Button(skin, "world-9-style");
        area9.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).push(new LevelMenuScreen(game, null));
            }
        });
        area9.setDisabled(true);
        stage.addActor(area9);

        area10 = new Button(skin, "world-10-style");
        area10.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).push(new LevelMenuScreen(game, null));
            }
        });
        area10.setDisabled(true);
        stage.addActor(area10);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 212/255f, 247/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        if (x == 0 && y == 0) {
            x = viewport.unproject(new Vector3(viewport.getScreenWidth(), viewport.getScreenHeight(), 0)).x;
            y = viewport.unproject(new Vector3(viewport.getScreenX(), viewport.getScreenY(), 0)).y;

            height =  y - (button1.getHeight() + (y * 0.1f) + AssetsManager.getAssetsManager().getPadding() * 2);
            width = height * 1.7115384615384615f;

            x = x / 2 - (width / 2);
            y = (y * 0.05f);
        }

        game.batch.begin();
        game.batch.draw(world, x, y, width, height);
        game.batch.end();

        area1.setPosition((0.07865169f * width) + x - (area1.getWidth() / 2), (0.65384615f * height) + y);
        area2.setPosition((0.20224719f * width) + x - (area2.getWidth() / 2), (0.48076923f * height) + y);
        area3.setPosition((0.25842697f * width) + x - (area3.getWidth() / 2), (0.88461538f * height) + y);
        area4.setPosition((0.34337079f * width) + x - (area4.getWidth() / 2), (0.57769231f * height) + y);
        area5.setPosition((0.3258427f * width) + x - (area5.getWidth() / 2), (0.25f * height) + y);
        area6.setPosition((0.528089887f * width) + x - (area6.getWidth() / 2), (0.42307692f * height) + y);
        area7.setPosition((0.752808f * width) + x - (area7.getWidth() / 2), (0.5769230769f * height) + y);
        area8.setPosition((0.68539325f * width) + x - (area8.getWidth() / 2), (0.346153846f * height) + y);
        area9.setPosition((0.80898876f * width) + x - (area9.getWidth() / 2), (0.173076923f * height) + y);
        area10.setPosition((0.505617977f * width) + x - (area10.getWidth() / 2), (0.80384615f * height) + y);

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

        x = 0;
        y = 0;
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

