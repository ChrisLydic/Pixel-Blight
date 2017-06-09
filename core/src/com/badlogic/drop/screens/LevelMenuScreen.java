package com.badlogic.drop.screens;

import com.badlogic.drop.Drop;
import com.badlogic.drop.ScreenManager;
import com.badlogic.drop.SquareGroup;
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
public class LevelMenuScreen implements Screen {
    final Drop game;
    OrthographicCamera camera;
    Viewport viewport;
    AssetManager assetManager;

    private Stage stage;
    private Table table;
    private SquareGroup[] levels = new SquareGroup[10];
    private int[][] level0 = new int[][]{
            {1,1,1,1},
            {1,1,1,1},
            {1,1,1,1},
            {1,1,1,1}
    };
    private int[][] level0Corrupt = new int[][]{
            {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}
    };
    private int[][] level1 = new int[][]{
            {3,3,3,3,2,2,2,0,0},
            {0,3,3,3,3,2,2,2,0},
            {0,0,3,3,3,3,2,2,2},
            {0,3,3,3,3,2,2,2,0},
            {3,3,3,3,2,2,2,0,0}
    };
    private int[][] level1Corrupt = new int[][]{
            {0,0}, {1,1}, {2,2}, {3,1}, {4,0}
    };
    private int[][] level2 = new int[][]{
            {0,0,1,1,0,0},
            {0,1,4,4,1,0},
            {1,4,4,4,4,1},
            {1,4,5,5,4,1},
            {1,4,5,5,4,1},
            {1,4,4,4,4,1},
            {0,1,4,4,1,0},
            {0,0,1,1,0,0}
    };
    private int[][] level2Corrupt = new int[][]{
            {1,1}, {6,4}, {3,2}, {4,3}
    };
    private int[][] level3 = new int[][]{
            {0,0,0,0,6,6,0,0,0,0},
            {6,0,6,0,6,0,6,0,0,0},
            {0,6,6,6,0,6,6,6,0,0},
            {0,0,6,0,6,0,6,0,6,6},
            {0,0,0,6,6,6,0,6,0,6},
            {0,0,6,0,6,0,6,6,6,0},
            {0,0,6,6,0,6,0,6,0,6},
            {0,0,0,0,6,6,6,0,0,0},
            {0,0,0,0,0,6,0,0,0,0}
    };
    private int[][] level3Corrupt = new int[][]{
            {1,0}, {6,9}
    };
    private int[][] level4 = new int[][]{
            {7,0,0,0,0,0},
            {7,0,0,0,0,0},
            {7,7,7,7,7,7},
            {7,1,1,1,1,7},
            {7,1,0,0,1,7},
            {7,1,0,0,1,7},
            {7,1,1,1,1,7},
            {7,7,7,7,7,7},
            {0,0,0,0,0,7},
            {0,0,0,0,0,7}
    };
    private int[][] level4Corrupt = new int[][]{
            {0,0}, {9,5}
    };
    private int[][] level5 = new int[][]{
            {0,0,0,1,1,1,0,0,0},
            {0,1,1,1,1,1,1,1,0},
            {0,1,1,1,1,1,1,1,0},
            {1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1},
            {0,1,1,1,1,1,1,1,0},
            {0,1,1,1,1,1,1,1,0},
            {0,0,0,1,1,1,0,0,0}
    };
    private int[][] level5Corrupt = new int[][]{
            {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}
    };
    private int[][] level6 = new int[][]{
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1}
    };
    private int[][] level6Corrupt = new int[][]{
            {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}
    };
    private int[][] level7 = new int[][]{
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1}
    };
    private int[][] level7Corrupt = new int[][]{
            {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}
    };
    private int[][] level8 = new int[][]{
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1}
    };
    private int[][] level8Corrupt = new int[][]{
            {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}
    };
    private int[][] level9 = new int[][]{
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1}
    };
    private int[][] level9Corrupt = new int[][]{
            {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}
    };


    public LevelMenuScreen(final Drop game, String world) {
        this.game = game;

        levels[0] = new SquareGroup( level0.length, level0[0].length, level0, level0Corrupt);
        levels[1] = new SquareGroup( level1.length, level1[0].length, level1, level1Corrupt);
        levels[2] = new SquareGroup( level2.length, level2[0].length, level2, level2Corrupt);
        levels[3] = new SquareGroup( level3.length, level3[0].length, level3, level3Corrupt);
        levels[4] = new SquareGroup( level4.length, level4[0].length, level4, level4Corrupt);
        levels[5] = new SquareGroup( level5.length, level5[0].length, level5, level5Corrupt);
        levels[6] = new SquareGroup( level6.length, level6[0].length, level6, level6Corrupt);
        levels[7] = new SquareGroup( level7.length, level7[0].length, level7, level7Corrupt);
        levels[8] = new SquareGroup( level8.length, level8[0].length, level8, level8Corrupt);
        levels[9] = new SquareGroup( level9.length, level9[0].length, level9, level9Corrupt);

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

            final SquareGroup level = levels[i];

            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    ScreenManager.getInstance(game).push(new GameScreen(game, level));
                }
            });

            tableLevels.add(button).minWidth(200).maxHeight(500).expand().fill().padRight(50);
            tableLevels.row();
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

