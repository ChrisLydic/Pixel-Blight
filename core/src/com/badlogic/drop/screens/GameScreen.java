package com.badlogic.drop.screens;

import com.badlogic.drop.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameScreen implements Screen {
    private final Drop game;

    private static final int WORLD_WIDTH = 100;
    private static final int WORLD_HEIGHT = 100;

    private OrthographicCamera cam;

    private Stage stage;
    private Table table;

    private float rotationSpeed;
    private Viewport viewport;

    private Level level;
    // this is already a property of level, this variable is for convenience
    private SquareGroup squareGroup;

    private float tick = 0;

    private Vector3 touchPoint = new Vector3();
    private float x = 0;
    private float y = 0;
    private boolean isPressed;
    private ProgressBar progressBar;
    private Label labelPoints;

    private Image touchAction;
    private Image scatterAction;
    private Image bombAction;
    private Image cureAction;
    private Label scatterLabel;
    private Label bombLabel;
    private Label cureLabel;

    public GameScreen(final Drop game, final Level level) {
        this.game = game;
        this.level = level;
        this.squareGroup = level.getLevelData();
        squareGroup.reset();

        rotationSpeed = 0.7f;

        //float w = Gdx.graphics.getWidth();
        //float h = Gdx.graphics.getHeight();
        //
        //mapSprite = new Sprite(new Texture(Gdx.files.internal("bucket.png")));
        //mapSprite.setPosition(WORLD_WIDTH/2 + 5, WORLD_HEIGHT/2);
        //
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

        Skin skin = AssetsManager.getAssetsManager().getUISkin();

        ImageButton buttonPause = new ImageButton(skin, "pause");
        buttonPause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).push(new PauseScreen(game, level));
            }
        });

        TextButton buttonStore = new TextButton("Store", skin);
        buttonStore.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).push(new ShopScreen(game, level));
            }
        });

        labelPoints = new Label(Integer.toString(squareGroup.getPoints()), skin);

        progressBar = new ProgressBar(0, 1, 0.01f, false, skin);
        progressBar.setValue(1);

        tableButtons.add(buttonPause).padRight(AssetsManager.getAssetsManager().getPadding()).left();
        tableButtons.add(buttonStore).padRight(AssetsManager.getAssetsManager().getPadding()).left().expand();
        tableButtons.add(labelPoints).padRight(AssetsManager.getAssetsManager().getPadding() * 2).right();
        tableButtons.add(progressBar).right();

        Table tableTouchAction = new Table();
        Table tableScatterAction = new Table();
        Table tableBombAction = new Table();
        Table tableCureAction = new Table();

        touchAction = new Image(AssetsManager.getAssetsManager().getDrawable("tap-1"));
        scatterAction = new Image(AssetsManager.getAssetsManager().getDrawable("scatter-1"));
        bombAction = new Image(AssetsManager.getAssetsManager().getDrawable("bomb-1"));
        cureAction = new Image(AssetsManager.getAssetsManager().getDrawable("cure-1"));

        touchAction.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ActionManager.getActionManager().setCurrentAction(ActionManager.ActionType.TAP);
            }
        });
        scatterAction.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ActionManager.getActionManager().setCurrentAction(ActionManager.ActionType.SCATTER);
            }
        });
        bombAction.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ActionManager.getActionManager().setCurrentAction(ActionManager.ActionType.BOMB);
            }
        });
        cureAction.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ActionManager.getActionManager().setCurrentAction(ActionManager.ActionType.CURE);
            }
        });

        scatterLabel = new Label(Integer.toString(ActionManager.getActionManager().getScatterCount()), skin);
        bombLabel = new Label(Integer.toString(ActionManager.getActionManager().getBombCount()), skin);
        cureLabel = new Label(Integer.toString(ActionManager.getActionManager().getCureCount()), skin);

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
        table.add(tableButtons).pad(AssetsManager.getAssetsManager().getPadding()).expandX().fill();
        table.row();
        table.add(tableActions).left().expandY().fillY().padLeft(AssetsManager.getAssetsManager().getPadding());

        isPressed = false;
    }

    @Override
    public void render (float delta) {
        handleInput();
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();

        if (x == 0 && y == 0) {
            x = viewport.unproject(new Vector3(viewport.getScreenWidth(), viewport.getScreenHeight(), 0)).x/2;
            y = viewport.unproject(new Vector3(viewport.getScreenX(), viewport.getScreenY(), 0)).y/2;
            squareGroup.setOrigin(x,y);
        }

        game.batch.begin();
        squareGroup.draw(game.batch, delta);
        game.batch.end();

        progressBar.setValue(squareGroup.getProgress());

        if (squareGroup.isWon()) {
            ScreenManager.getInstance(game).push(new LevelEndScreen(game, level, true));
        } else if (squareGroup.isLost()) {
            ScreenManager.getInstance(game).push(new LevelEndScreen(game, level, false));
        } else {
            if (Gdx.input.isTouched()) {
                if (!isPressed) {
                    isPressed = true;
                    cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                    squareGroup.touched(touchPoint.x, touchPoint.y);
                    updateActionUI();
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
        stage.getViewport().update(width, height, true);
        viewport.update(width, height, true);
        cam.zoom = WORLD_WIDTH;
        cam.update();
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
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

        updateActionUI();
    }

    @Override
    public void hide() {
    }

    private void updateActionUI() {
        touchAction.setDrawable(AssetsManager.getAssetsManager().getDrawable("tap-" + ActionManager.getActionManager().getTapLevel()));
        scatterAction.setDrawable(AssetsManager.getAssetsManager().getDrawable("scatter-" + ActionManager.getActionManager().getScatterLevel()));
        bombAction.setDrawable(AssetsManager.getAssetsManager().getDrawable("bomb-" + ActionManager.getActionManager().getBombLevel()));
        cureAction.setDrawable(AssetsManager.getAssetsManager().getDrawable("cure-" + ActionManager.getActionManager().getCureLevel()));

        scatterLabel.setText(Integer.toString(ActionManager.getActionManager().getScatterCount()));
        bombLabel.setText(Integer.toString(ActionManager.getActionManager().getBombCount()));
        cureLabel.setText(Integer.toString(ActionManager.getActionManager().getCureCount()));

        if (ActionManager.getActionManager().getScatterCount() == 0) {
            if (ActionManager.getActionManager().getCurrentAction() == ActionManager.ActionType.SCATTER) {
                ActionManager.getActionManager().setCurrentAction(ActionManager.ActionType.TAP);
            }
            scatterAction.setTouchable(Touchable.disabled);
        } else {
            scatterAction.setTouchable(Touchable.enabled);
        }

        if (ActionManager.getActionManager().getBombCount() == 0) {
            if (ActionManager.getActionManager().getCurrentAction() == ActionManager.ActionType.BOMB) {
                ActionManager.getActionManager().setCurrentAction(ActionManager.ActionType.TAP);
            }
            bombAction.setTouchable(Touchable.disabled);
        } else {
            bombAction.setTouchable(Touchable.enabled);
        }

        if (ActionManager.getActionManager().getCureCount() == 0) {
            if (ActionManager.getActionManager().getCurrentAction() == ActionManager.ActionType.CURE) {
                ActionManager.getActionManager().setCurrentAction(ActionManager.ActionType.TAP);
            }
            cureAction.setTouchable(Touchable.disabled);
        } else {
            cureAction.setTouchable(Touchable.enabled);
        }

        if (!labelPoints.getText().toString().equals(Integer.toString(squareGroup.getPoints()))) {
            labelPoints.setText(Integer.toString(squareGroup.getPoints()));
        }
    }
}