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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by chris on 5/13/2017.
 */
public class MainMenuScreen implements Screen {
    final Drop game;
    OrthographicCamera camera;
    Viewport viewport;
    AssetManager assetManager;

    private Stage stage;
    private Table table;
    private Sprite logo;
    private Sprite background;

    public MainMenuScreen(final Drop game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        camera.update();

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        background = new Sprite(new Texture(Gdx.files.internal("background.png")));

        assetManager = new AssetManager();
        assetManager.load("uiskin.json", Skin.class,
                new SkinLoader.SkinParameter("uiskin.atlas"));
        assetManager.finishLoading();
        Skin skin = assetManager.get("uiskin.json", Skin.class);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Table table1 = new Table();
        Table table2 = new Table();

        logo = new Sprite(new Texture(Gdx.files.internal("logo.png")));
        Image logoImage = new Image(logo);
        logoImage.setScaling(Scaling.fit);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new NinePatchDrawable(skin.getPatch("default-round-large"));
        style.over = new NinePatchDrawable(skin.getPatch("default-over"));
        style.down = new NinePatchDrawable(skin.getPatch("default-scroll"));
        style.font = skin.getFont("default-font");

        TextButton button1 = new TextButton("Play", skin);
        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).push(new LevelGroupMenuScreen(game));
            }
        });

        table1.add(logoImage).expand().fill();

        table2.add(button1);

        table.pad(50);
        table.add(table1).expand().fill();
        table.row();
        table.add(table2).expand().top();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        float x = viewport.unproject(new Vector3(viewport.getScreenWidth(), viewport.getScreenHeight(), 0)).x;
        float y = viewport.unproject(new Vector3(viewport.getScreenX(), viewport.getScreenY(), 0)).y;

        game.batch.begin();
//        game.batch.draw(logo, x / 2 - (x * 0.4f), (y * 0.95f) - ((int)(x * 0.8 * 0.15053763440860216)),
//                (int)(x * 0.8), (int)(x * 0.8 * 0.15053763440860216));
        game.batch.draw(background, 0, y - (x * 0.9583333333333334f), x, x * 0.9583333333333334f );
        game.batch.end();

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
