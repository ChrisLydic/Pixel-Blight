package com.badlogic.drop.screens;

import com.badlogic.drop.ActionType;
import com.badlogic.drop.Drop;
import com.badlogic.drop.ScreenManager;
import com.badlogic.drop.SquareGroup;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameScreen implements Screen {
    final Drop game;

    private Enum<ActionType> currentAction;

    static final int WORLD_WIDTH = 100;
    static final int WORLD_HEIGHT = 100;

    private OrthographicCamera cam;
    private SpriteBatch batch;
    private AssetManager assetManager;

    private Stage stage;
    private Table table;

    private Sprite mapSprite;
    private Sprite other;
    private float rotationSpeed;
    private Viewport viewport;

    SquareGroup squareGroup;

    TextureRegion[][] tiles;

    private float tick = 0;

    Vector3 touchPoint = new Vector3();
    float x = 0;
    float y = 0;
    boolean isPressed;
    ProgressBar progressBar;

    public GameScreen(final Drop game, SquareGroup squareGroup) {
        this.game = game;

        currentAction = ActionType.TAP;

        assetManager = new AssetManager();
        assetManager.load("uiskin.json", Skin.class,
                new SkinLoader.SkinParameter("uiskin.atlas"));
        assetManager.finishLoading();
        Skin skin = assetManager.get("uiskin.json", Skin.class);

        rotationSpeed = 0.5f;

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        tiles = new TextureRegion(new Texture(Gdx.files.internal("tiles2.png"))).split(64, 64);

        //mapSprite = new Sprite(new Texture(Gdx.files.internal("bucket.png")));
        //mapSprite.setPosition(WORLD_WIDTH/2 + 5, WORLD_HEIGHT/2);

        //other = new Sprite(new Texture(Gdx.files.internal("droplet.png")));
        //other.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT/2);

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        cam = new OrthographicCamera();
        viewport = new ExtendViewport( 10, 10, cam);
        //viewport.setScreenPosition(viewport.getScreenWidth() / 2, viewport.getScreenHeight() / 2);
        //cam.position.set(0, 0, 0);
        cam.zoom = WORLD_WIDTH;
        cam.update();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Table tableButtons = new Table();
        Table tableActions = new Table();

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

        progressBar = new ProgressBar(0, 1, 0.01f, false, skin);
        progressBar.setValue(1);

        tableButtons.add(button1).padRight(10).left();
        tableButtons.add(button2).padRight(10).left().expand();
        tableButtons.add(progressBar).right();

        Table tableTouchAction = new Table();
        Table tableScatterAction = new Table();
        Table tableBombAction = new Table();
        Table tableCureAction = new Table();

        TextButton touchAction = new TextButton("H", skin);
        TextButton scatterAction = new TextButton("S", skin);
        TextButton bombAction = new TextButton("B", skin);
        TextButton cureAction = new TextButton("C", skin);

        touchAction.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentAction = ActionType.TAP;
            }
        });
        scatterAction.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentAction = ActionType.SCATTER;
            }
        });
        bombAction.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentAction = ActionType.BOMB;
            }
        });
        cureAction.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentAction = ActionType.CURE;
            }
        });

        Label scatterLabel = new Label("10", skin);
        Label bombLabel = new Label("10", skin);
        Label cureLabel = new Label("10", skin);

        tableTouchAction.add(touchAction);

        tableScatterAction.add(scatterAction);
        tableScatterAction.add(scatterLabel);

        tableBombAction.add(bombAction);
        tableBombAction.add(bombLabel);

        tableCureAction.add(cureAction);
        tableCureAction.add(cureLabel);

        tableActions.row().expandY();
        tableActions.add(tableTouchAction);
        tableActions.row().expandY();
        tableActions.add(tableScatterAction);
        tableActions.row().expandY();
        tableActions.add(tableBombAction);
        tableActions.row().expandY();
        tableActions.add(tableCureAction);

        table.top().left();
        table.add(tableButtons).pad(10).expandX().fill();
        table.row();
        table.add(tableActions).left().expandY().fillY().padLeft(10);

        this.squareGroup = squareGroup;

        isPressed = false;

        batch = new SpriteBatch();
    }

    @Override
    public void render (float delta) {
        handleInput();
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();

        if (x == 0 && y == 0) {
            x = viewport.unproject(new Vector3(viewport.getScreenWidth(), viewport.getScreenHeight(), 0)).x/2;
            y = viewport.unproject(new Vector3(viewport.getScreenX(), viewport.getScreenY(), 0)).y/2;
            squareGroup.setOrigin(x,y);
        }

        batch.begin();
        squareGroup.draw(batch, tiles);
        batch.end();

        Gdx.app.log("", squareGroup.getProgress()+"");
        progressBar.setValue(squareGroup.getProgress());

        if (squareGroup.isWon()) {
            Gdx.app.log("", "WINNER");
        } else if (squareGroup.isLost()) {
            Gdx.app.log("", "LOSER");
        } else {
            if (Gdx.input.isTouched()) {
                if (!isPressed) {
                    isPressed = true;
                    cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                    squareGroup.touched(touchPoint.x, touchPoint.y, currentAction);
                }
            } else {
                isPressed = false;
            }

            tick += delta;
            if (tick > 0.5) {
                tick = 0f;
                squareGroup.corrupt(tick);
            }
        }

        stage.getViewport().apply();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.zoom += 0.08;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom -= 0.08;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            cam.rotate(rotationSpeed, 0, 0, 1);
        }

        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 200/cam.viewportWidth);

        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 200 - effectiveViewportWidth / 2f);
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 200 - effectiveViewportHeight / 2f);
    }

    @Override
    public void resize(int width, int height) {
        x = 0; y = 0;
        stage.getViewport().update(width, height);
        viewport.update(width, height, true);
        cam.zoom = WORLD_WIDTH;
        cam.update();
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        //mapSprite.getTexture().dispose();
        batch.dispose();
        stage.dispose();
        assetManager.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
//        rainMusic.play();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
    }
}