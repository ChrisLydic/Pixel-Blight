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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by chris on 5/28/2017.
 */
public class ShopScreen implements Screen {
    private final Drop game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Table table;
    private Table tableActions;
    private Table tableTapAction;
    private Table tableScatterAction;
    private Table tableBombAction;
    private Table tableCureAction;
    private ActionManager actionManager;

    private Image tapImage;
    private Image scatterImage;
    private Image bombImage;
    private Image cureImage;

    private Texture background;

    public ShopScreen(final Drop game) {
        this.game = game;
        this.actionManager = ActionManager.getActionManager();

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        camera.update();

        background = AssetsManager.getAssetsManager().getTexture(AssetsManager.MAIN_BACKGROUND);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        Skin skin = AssetsManager.getAssetsManager().getUISkin();

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Table tableButtons = new Table();
        tableActions = new Table();
        tableTapAction = new Table();
        tableScatterAction = new Table();
        tableBombAction = new Table();
        tableCureAction = new Table();

        Table tableTapText = new Table();
        Table tableScatterText = new Table();
        Table tableBombText = new Table();
        Table tableCureText = new Table();

        tableTapAction.setBackground(AssetsManager.getAssetsManager().getDrawable("container"));
        tableScatterAction.setBackground(AssetsManager.getAssetsManager().getDrawable("container"));
        tableBombAction.setBackground(AssetsManager.getAssetsManager().getDrawable("container"));
        tableCureAction.setBackground(AssetsManager.getAssetsManager().getDrawable("container"));

        ImageButton buttonBack = new ImageButton(skin, "back");
        buttonBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).pop();
            }
        });
        TextButton buttonIAP = new TextButton("IAP", skin);
        ImageButton buttonSettings = new ImageButton(skin, "settings");
        buttonSettings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance(game).push(new SettingsScreen(game));
            }
        });

        ScrollPane scrollPane = new ScrollPane(tableActions, skin);

        // Tap
        tapImage = new Image(AssetsManager.getAssetsManager().getDrawable("tap-" + actionManager.getTapLevel()));
        tapImage.setScaling(Scaling.fit);

        // scatter
        scatterImage = new Image(AssetsManager.getAssetsManager().getDrawable("scatter-" + actionManager.getScatterLevel()));
        scatterImage.setScaling(Scaling.fit);

        // bomb
        bombImage = new Image(AssetsManager.getAssetsManager().getDrawable("bomb-" + actionManager.getBombLevel()));
        bombImage.setScaling(Scaling.fit);

        // cure
        cureImage = new Image(AssetsManager.getAssetsManager().getDrawable("cure-" + actionManager.getCureLevel()));
        cureImage.setScaling(Scaling.fit);


        Label tapLabel = new Label("Tap", skin);
        Label tapDescription = new Label("Tap description", skin);
        tapDescription.setAlignment(Align.topLeft);
        TextButton tapButtonUpgradePrice = new TextButton(Integer.toString(ActionManager.TAP_UPGRADE_1_COST), skin, "shop-button-price");
        TextButton tapButtonUpgrade = new TextButton("Upgrade", skin, "shop-button");
        if (actionManager.getTapLevel() == ActionManager.MID_TAP_LEVEL) {
            tapButtonUpgrade.setText("Upgrade\n$" + ActionManager.TAP_UPGRADE_2_COST);
        } else if (actionManager.getTapLevel() == ActionManager.MAX_TAP_LEVEL) {
            tapButtonUpgrade.setDisabled(true);
            tapButtonUpgrade.setText("Final\n$-");
        }
        tapButtonUpgrade.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.incrementTapLevel();
                tapImage.setDrawable(AssetsManager.getAssetsManager().getDrawable("tap-" + actionManager.getTapLevel()));

                if (actionManager.getTapLevel() == ActionManager.MID_TAP_LEVEL) {
                    //((TextButton) actor).setText(ActionManager.SCATTER_UPGRADE_2_COST);
                } else if (actionManager.getTapLevel() == ActionManager.MAX_TAP_LEVEL) {
                    ((TextButton) actor).setDisabled(true);
                    ((TextButton) actor).setText("Final");
                }
            }
        });

        Label scatterLabel = new Label("Scatter", skin);
        Label scatterDescription = new Label("Scatter description", skin);
        scatterDescription.setAlignment(Align.topLeft);
        TextButton scatterButtonBuyPrice = new TextButton(Integer.toString(ActionManager.SCATTER_ITEM_COST), skin, "shop-button-price");
        TextButton scatterButtonBuy = new TextButton("Buy", skin, "shop-button");
        scatterButtonBuy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.setScatterCount(actionManager.getScatterCount() + 1);
                //((TextButton) actor).setText(ActionManager.SCATTER_ITEM_COST);

                if (actionManager.getScatterCount() == ActionManager.MAX_SCATTER_COUNT) {
                    ((TextButton) actor).setDisabled(true);
                }
            }
        });
        TextButton scatterButtonUpgradePrice = new TextButton(Integer.toString(ActionManager.SCATTER_UPGRADE_1_COST), skin, "shop-button-price");
        TextButton scatterButtonUpgrade = new TextButton("Upgrade", skin, "shop-button");
        if (actionManager.getScatterLevel() == ActionManager.MID_SCATTER_LEVEL) {
            //scatterButtonUpgrade.setText("Upgrade" + ActionManager.SCATTER_UPGRADE_2_COST);
        } else if (actionManager.getScatterLevel() == ActionManager.MAX_SCATTER_LEVEL) {
            scatterButtonUpgrade.setDisabled(true);
            scatterButtonUpgrade.setText("Final");
        }
        scatterButtonUpgrade.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.incrementScatterLevel();
                scatterImage.setDrawable(AssetsManager.getAssetsManager().getDrawable("scatter-" + actionManager.getScatterLevel()));

                if (actionManager.getScatterLevel() == ActionManager.MID_SCATTER_LEVEL) {
                    //((TextButton) actor).setText(ActionManager.SCATTER_UPGRADE_2_COST);
                } else if (actionManager.getScatterLevel() == ActionManager.MAX_SCATTER_LEVEL) {
                    ((TextButton) actor).setDisabled(true);
                    ((TextButton) actor).setText("Final");
                }
            }
        });

        Label bombLabel = new Label("Bomb", skin);
        Label bombDescription = new Label("Bomb description", skin);
        bombDescription.setAlignment(Align.topLeft);
        TextButton bombButtonBuyPrice = new TextButton(Integer.toString(ActionManager.BOMB_ITEM_COST), skin, "shop-button-price");
        TextButton bombButtonBuy = new TextButton("Buy", skin, "shop-button");
        bombButtonBuy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.setBombCount(actionManager.getBombCount() + 1);
                //((TextButton) actor).setText(ActionManager.BOMB_ITEM_COST);

                if (actionManager.getBombCount() == ActionManager.MAX_BOMB_COUNT) {
                    ((TextButton) actor).setDisabled(true);
                }
            }
        });
        TextButton bombButtonUpgradePrice = new TextButton(Integer.toString(ActionManager.BOMB_UPGRADE_1_COST), skin, "shop-button-price");
        TextButton bombButtonUpgrade = new TextButton("Upgrade", skin, "shop-button");
        if (actionManager.getBombLevel() == ActionManager.MID_BOMB_LEVEL) {
            //bombButtonUpgrade.setText("Upgrade");
        } else if (actionManager.getBombLevel() == ActionManager.MAX_BOMB_LEVEL) {
            bombButtonUpgrade.setDisabled(true);
            bombButtonUpgrade.setText("Final");
        }
        bombButtonUpgrade.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.incrementBombLevel();
                bombImage.setDrawable(AssetsManager.getAssetsManager().getDrawable("bomb-" + actionManager.getBombLevel()));

                if (actionManager.getBombLevel() == ActionManager.MID_BOMB_LEVEL) {
                    //((TextButton) actor).setText(ActionManager.BOMB_UPGRADE_2_COST);
                } else if (actionManager.getBombLevel() == ActionManager.MAX_BOMB_LEVEL) {
                    ((TextButton) actor).setDisabled(true);
                    ((TextButton) actor).setText("Final");
                }
            }
        });

        Label cureLabel = new Label("Cure", skin);
        Label cureDescription = new Label("Cure description", skin);
        cureDescription.setAlignment(Align.topLeft);
        TextButton cureButtonBuyPrice = new TextButton(Integer.toString(ActionManager.CURE_ITEM_COST), skin, "shop-button-price");
        TextButton cureButtonBuy = new TextButton("Buy", skin, "shop-button");
        cureButtonBuy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.setCureCount(actionManager.getCureCount() + 1);
                //((TextButton) actor).setText(ActionManager.CURE_ITEM_COST);
                
                if (actionManager.getCureCount() == ActionManager.MAX_CURE_COUNT) {
                    ((TextButton) actor).setDisabled(true);
                }
            }
        });
        TextButton cureButtonUpgradePrice = new TextButton(Integer.toString(ActionManager.CURE_UPGRADE_1_COST), skin, "shop-button-price");
        TextButton cureButtonUpgrade = new TextButton("Upgrade", skin, "shop-button");
        if (actionManager.getCureLevel() == ActionManager.MID_CURE_LEVEL) {
            //cureButtonUpgrade.setText("Upgrade\n$" + ActionManager.CURE_UPGRADE_2_COST);
        } else if (actionManager.getCureLevel() == ActionManager.MAX_CURE_LEVEL) {
            cureButtonUpgrade.setDisabled(true);
            cureButtonUpgrade.setText("Final");
        }
        cureButtonUpgrade.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.incrementCureLevel();
                cureImage.setDrawable(AssetsManager.getAssetsManager().getDrawable("cure-" + actionManager.getCureLevel()));

                if (actionManager.getCureLevel() == ActionManager.MID_CURE_LEVEL) {
                    //((TextButton) actor).setText("Upgrade" + ActionManager.CURE_UPGRADE_2_COST);
                } else if (actionManager.getCureLevel() == ActionManager.MAX_CURE_LEVEL) {
                    ((TextButton) actor).setDisabled(true);
                    ((TextButton) actor).setText("Final");
                }
            }
        });

        // build main tables
        tableButtons.add(buttonBack).left();
        tableButtons.add(buttonIAP).center().expand();
        tableButtons.add(buttonSettings).right();

        tableTapText.add(tapLabel).expandX().fillX();
        tableTapText.row();
        tableTapText.add(tapDescription).left();

        tableScatterText.add(scatterLabel).expandX().fillX();
        tableScatterText.row();
        tableScatterText.add(scatterDescription).left();

        tableBombText.add(bombLabel).expandX().fillX();
        tableBombText.row();
        tableBombText.add(bombDescription).left();

        tableCureText.add(cureLabel).expandX().fillX();
        tableCureText.row();
        tableCureText.add(cureDescription).left();


        tableTapAction.add(tapImage).size(80).pad(AssetsManager.getAssetsManager().getPadding());
        tableTapAction.add(tableTapText).expandX().fillX();
        tableTapAction.add(tapButtonUpgradePrice);
        tableTapAction.add(tapButtonUpgrade).padRight(AssetsManager.getAssetsManager().getPadding());

        tableScatterAction.add(scatterImage).size(80).pad(AssetsManager.getAssetsManager().getPadding());
        tableScatterAction.add(tableScatterText).expandX().fillX();
        tableScatterAction.add(scatterButtonBuyPrice);
        tableScatterAction.add(scatterButtonBuy).padRight(AssetsManager.getAssetsManager().getPadding());
        tableScatterAction.add(scatterButtonUpgradePrice);
        tableScatterAction.add(scatterButtonUpgrade).padRight(AssetsManager.getAssetsManager().getPadding());

        tableBombAction.add(bombImage).size(80).pad(AssetsManager.getAssetsManager().getPadding());
        tableBombAction.add(tableBombText).expandX().fillX();
        tableBombAction.add(bombButtonBuyPrice);
        tableBombAction.add(bombButtonBuy).padRight(AssetsManager.getAssetsManager().getPadding());
        tableBombAction.add(bombButtonUpgradePrice);
        tableBombAction.add(bombButtonUpgrade).padRight(AssetsManager.getAssetsManager().getPadding());

        tableCureAction.add(cureImage).size(80).pad(AssetsManager.getAssetsManager().getPadding());
        tableCureAction.add(tableCureText).expandX().fillX();
        tableCureAction.add(cureButtonBuyPrice);
        tableCureAction.add(cureButtonBuy).padRight(AssetsManager.getAssetsManager().getPadding());
        tableCureAction.add(cureButtonUpgradePrice);
        tableCureAction.add(cureButtonUpgrade).padRight(AssetsManager.getAssetsManager().getPadding());

        tableActions.add(tableTapAction).expandX().fillX()
                .padBottom(AssetsManager.getAssetsManager().getPadding() * 2)
                .padLeft(AssetsManager.getAssetsManager().getPadding())
                .padRight(AssetsManager.getAssetsManager().getPadding());
        tableActions.row();
        tableActions.add(tableScatterAction).expandX().fillX()
                .padBottom(AssetsManager.getAssetsManager().getPadding() * 2)
                .padLeft(AssetsManager.getAssetsManager().getPadding())
                .padRight(AssetsManager.getAssetsManager().getPadding());
        tableActions.row();
        tableActions.add(tableBombAction).expandX().fillX()
                .padBottom(AssetsManager.getAssetsManager().getPadding() * 2)
                .padLeft(AssetsManager.getAssetsManager().getPadding())
                .padRight(AssetsManager.getAssetsManager().getPadding());
        tableActions.row();
        tableActions.add(tableCureAction).expandX().fillX()
                .padBottom(AssetsManager.getAssetsManager().getPadding())
                .padLeft(AssetsManager.getAssetsManager().getPadding())
                .padRight(AssetsManager.getAssetsManager().getPadding());
        tableActions.pad(AssetsManager.getAssetsManager().getPadding()).top();

        table.add(tableButtons).pad(AssetsManager.getAssetsManager().getPadding()).expandX().fill();
        table.row();
        table.add(scrollPane).expand().fill();
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

