package com.badlogic.drop.screens;

import com.badlogic.drop.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by chris on 5/13/2017.
 */
public class LevelEndScreen implements Screen {
    final Drop game;
    OrthographicCamera camera;
    Viewport viewport;

    private Stage stage;
    private Table table;
    private Table tableImage;
    private Table tableButtons;
    private Texture background;
    private AnimatedImage victoryMessage;
    private boolean isOriginSet;

    public LevelEndScreen(final Drop game, final Level level, boolean isWon) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        camera.update();

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        background = AssetsManager.getAssetsManager().getTexture(AssetsManager.MAIN_BACKGROUND);

        int stars = 0;
        if (isWon) {
            stars = level.getLevelData().calculateStars();
        }

        Skin skin = AssetsManager.getAssetsManager().getUISkin();

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        tableImage = new Table();
        tableButtons = new Table();

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new NinePatchDrawable(skin.getPatch("default-round-large"));
        style.over = new NinePatchDrawable(skin.getPatch("default-over"));
        style.down = new NinePatchDrawable(skin.getPatch("default-scroll"));
        style.font = skin.getFont("default-font");

        ImageButton buttonBack = new ImageButton(skin, "back");
        buttonBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).pop(2);
            }
        });

        ImageButton buttonRestart = new ImageButton(skin, "restart");
        buttonRestart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                level.getLevelData().reset();
                ScreenManager.getInstance(game).pop();
            }
        });

        tableButtons.add(buttonBack).pad(20);
        tableButtons.add(buttonRestart).pad(20);

        if (isWon) {
            if (level.getNextLevel() == null) {
                level.getWorld().setCompleted();

                if (level.getWorld().getNextGroup() != null) {
                    level.getWorld().getNextGroup().unlock();

                    ImageButton buttonNext = new ImageButton(skin, "next");
                    buttonNext.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            ScreenManager.getInstance(game).popPush(3, new LevelMenuScreen(game, level.getWorld().getNextGroup()));
                        }
                    });

                    tableButtons.add(buttonNext).pad(20);
                } else {
                    //TODO endgame screen
                }
            }

            if (level.getStars() < stars) {
                level.setStars(stars);
            }
        }

        if (isWon && level.getNextLevel() != null) {
            //TODO: here's logic for winning the level, but should it really be in this class?
            if (level.getNextLevel().isLocked()) {
                level.getNextLevel().unlock();
            }

            ImageButton buttonNext = new ImageButton(skin, "next");
            buttonNext.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    ScreenManager.getInstance(game).popPush(2, new GameScreen(game, level.getNextLevel()));
                }
            });

            tableButtons.add(buttonNext).pad(20);
        }

        SequenceAction infiniteSequence = new SequenceAction();
        infiniteSequence.addAction(Actions.scaleBy(0.1f, 0.1f, 0.8f));
        infiniteSequence.addAction(Actions.scaleBy(-0.1f, -0.1f, 0.8f));

        RepeatAction infiniteLoop = new RepeatAction();
        infiniteLoop.setCount(RepeatAction.FOREVER);
        infiniteLoop.setAction(infiniteSequence);

        victoryMessage = AssetsManager.getAssetsManager().getVictoryAnimation(stars, 0, 0, 0.1f);
        victoryMessage.setScaling(Scaling.fit);
        victoryMessage.addAction(infiniteLoop);
        tableImage.add(victoryMessage).expand().fill().center();
        isOriginSet = false;

        table.padTop(50);
        table.add(tableImage).expand().fill();
        table.row();
        table.add(tableButtons).padTop(50).expand().top();

        stage.addAction(Actions.fadeOut(0f));
        stage.addAction(Actions.fadeIn(0.4f));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        victoryMessage.setOrigin(victoryMessage.getWidth() / 2, victoryMessage.getHeight() / 2);

        float x = viewport.unproject(new Vector3(viewport.getScreenWidth(), viewport.getScreenHeight(), 0)).x;
        float y = viewport.unproject(new Vector3(viewport.getScreenX(), viewport.getScreenY(), 0)).y;

        game.batch.begin();
        game.batch.draw(background, 0, y - (x * 0.9583333333333334f), x, x * 0.9583333333333334f );
        game.batch.end();

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
