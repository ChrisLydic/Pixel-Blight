package com.badlogic.drop.screens;

import com.badlogic.drop.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    private Table tableCurrentAction;
    private ActionManager actionManager;

    private Image tapButton;
    private Image scatterButton;
    private Image bombButton;
    private Image cureButton;

    private Image tapUpgrade1;
    private Image tapUpgrade1Active;
    private Image tapUpgrade1Disabled;
    private Image tapUpgrade1Current;
    private Image tapUpgrade2;
    private Image tapUpgrade2Active;
    private Image tapUpgrade2Disabled;
    private Image tapUpgrade2Current;
    private Image tapUpgrade3;
    private Image tapUpgrade3Active;
    private Image tapUpgrade3Disabled;
    private Image tapUpgrade3Current;
    private Image scatterUpgrade1;
    private Image scatterUpgrade1Active;
    private Image scatterUpgrade1Disabled;
    private Image scatterUpgrade1Current;
    private Image scatterUpgrade2;
    private Image scatterUpgrade2Active;
    private Image scatterUpgrade2Disabled;
    private Image scatterUpgrade2Current;
    private Image scatterUpgrade3;
    private Image scatterUpgrade3Active;
    private Image scatterUpgrade3Disabled;
    private Image scatterUpgrade3Current;
    private Image bombUpgrade1;
    private Image bombUpgrade1Active;
    private Image bombUpgrade1Disabled;
    private Image bombUpgrade1Current;
    private Image bombUpgrade2;
    private Image bombUpgrade2Active;
    private Image bombUpgrade2Disabled;
    private Image bombUpgrade2Current;
    private Image bombUpgrade3;
    private Image bombUpgrade3Active;
    private Image bombUpgrade3Disabled;
    private Image bombUpgrade3Current;
    private Image cureUpgrade1;
    private Image cureUpgrade1Active;
    private Image cureUpgrade1Disabled;
    private Image cureUpgrade1Current;
    private Image cureUpgrade2;
    private Image cureUpgrade2Active;
    private Image cureUpgrade2Disabled;
    private Image cureUpgrade2Current;
    private Image cureUpgrade3;
    private Image cureUpgrade3Active;
    private Image cureUpgrade3Disabled;
    private Image cureUpgrade3Current;

    public ShopScreen(final Drop game) {
        this.game = game;
        this.actionManager = ActionManager.getActionManager();

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        camera.update();

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        Skin skin = AssetsManager.getAssetsManager().getUISkin();

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setBackground(AssetsManager.getAssetsManager().getDrawable(AssetsManager.BACKGROUND_COLOR));

        Table tableButtons = new Table();
        tableActions = new Table();
        tableTapAction = new Table();
        tableScatterAction = new Table();
        tableBombAction = new Table();
        tableCureAction = new Table();

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

        ScrollPane scrollPane = new ScrollPane(tableActions, skin);

        // Tap
        tapButton = new Image(AssetsManager.getAssetsManager().getDrawable("tap-" + actionManager.getTapLevel()));
        tapButton.setScaling(Scaling.fit);
        tapButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.getCell(tableCurrentAction).setActor(tableTapAction);
                tableCurrentAction = tableTapAction;
            }
        });

        // scatter
        scatterButton = new Image(AssetsManager.getAssetsManager().getDrawable("scatter-" + actionManager.getScatterLevel()));
        scatterButton.setScaling(Scaling.fit);
        scatterButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.getCell(tableCurrentAction).setActor(tableScatterAction);
                tableCurrentAction = tableScatterAction;
            }
        });

        // bomb
        bombButton = new Image(AssetsManager.getAssetsManager().getDrawable("bomb-" + actionManager.getBombLevel()));
        bombButton.setScaling(Scaling.fit);
        bombButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.getCell(tableCurrentAction).setActor(tableBombAction);
                tableCurrentAction = tableBombAction;
            }
        });

        // cure
        cureButton = new Image(AssetsManager.getAssetsManager().getDrawable("cure-" + actionManager.getCureLevel()));
        cureButton.setScaling(Scaling.fit);
        cureButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.getCell(tableCurrentAction).setActor(tableCureAction);
                tableCurrentAction = tableCureAction;
            }
        });

        tableActions.add(tapButton).size(80)
                .padBottom(AssetsManager.getAssetsManager().getPadding() * 2)
                .padLeft(AssetsManager.getAssetsManager().getPadding())
                .padRight(AssetsManager.getAssetsManager().getPadding())
                .padTop(AssetsManager.getAssetsManager().getPadding() * 2);
        tableActions.row();
        tableActions.add(scatterButton).size(80)
                .padBottom(AssetsManager.getAssetsManager().getPadding() * 2)
                .padLeft(AssetsManager.getAssetsManager().getPadding())
                .padRight(AssetsManager.getAssetsManager().getPadding());
        tableActions.row();
        tableActions.add(bombButton).size(80)
                .padBottom(AssetsManager.getAssetsManager().getPadding() * 2)
                .padLeft(AssetsManager.getAssetsManager().getPadding())
                .padRight(AssetsManager.getAssetsManager().getPadding());
        tableActions.row();
        tableActions.add(cureButton).size(80)
                .padLeft(AssetsManager.getAssetsManager().getPadding())
                .padRight(AssetsManager.getAssetsManager().getPadding());
        tableActions.pad(AssetsManager.getAssetsManager().getPadding()).top();

        Label tapLabel = new Label("Tap", skin);
        Label tapDescription = new Label("Tap description", skin);
        tapDescription.setAlignment(Align.topLeft);
        TextButton tapButtonUpgrade = new TextButton("Upgrade\n$" + ActionManager.TAP_UPGRADE_1_COST, skin);
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
                tapButton.setDrawable(AssetsManager.getAssetsManager().getDrawable("tap-" + actionManager.getTapLevel()));

                if (actionManager.getTapLevel() == ActionManager.MID_TAP_LEVEL) {
                    ((TextButton) actor).setText("Upgrade\n$" + ActionManager.TAP_UPGRADE_2_COST);

                    if (tapUpgrade1Current.equals(tapUpgrade1Active)) {
                        tableTapAction.getCell(tapUpgrade1Active).setActor(tapUpgrade1);
                        tapUpgrade1Current = tapUpgrade1;
                    }

                    if (tapUpgrade2Current.equals(tapUpgrade2Disabled)) {
                        tableTapAction.getCell(tapUpgrade2Disabled).setActor(tapUpgrade2Active);
                        tapUpgrade2Current = tapUpgrade2Active;
                    }
                } else if (actionManager.getTapLevel() == ActionManager.MAX_TAP_LEVEL) {
                    ((TextButton) actor).setDisabled(true);
                    ((TextButton) actor).setText("Final\n$-");

                    if (tapUpgrade1Current.equals(tapUpgrade1Active)) {
                        tableTapAction.getCell(tapUpgrade1Active).setActor(tapUpgrade1);
                        tapUpgrade1Current = tapUpgrade1;
                    }

                    if (tapUpgrade2Current.equals(tapUpgrade2Active)) {
                        tableTapAction.getCell(tapUpgrade2Active).setActor(tapUpgrade2);
                        tapUpgrade2Current = tapUpgrade2;
                    }

                    if (tapUpgrade3Current.equals(tapUpgrade3Disabled)) {
                        tableTapAction.getCell(tapUpgrade3Disabled).setActor(tapUpgrade3Active);
                        tapUpgrade3Current = tapUpgrade3Active;
                    }
                }
            }
        });
        tapUpgrade1 = new Image(AssetsManager.getAssetsManager().getDrawable(AssetsManager.TAP_LEVEL_1_UPGRADE));
        tapUpgrade1.setScaling(Scaling.fit);
        tapUpgrade1Active = new Image(AssetsManager.getAssetsManager().getDrawable(AssetsManager.TAP_LEVEL_1_UPGRADE_ACTIVE));
        tapUpgrade1Active.setScaling(Scaling.fit);
        tapUpgrade1Disabled = new Image(AssetsManager.getAssetsManager().getDrawable(AssetsManager.TAP_LEVEL_1_UPGRADE_DISABLED));
        tapUpgrade1Disabled.setScaling(Scaling.fit);
        tapUpgrade1Current = tapUpgrade1Active;
        tapUpgrade2 = new Image(AssetsManager.getAssetsManager().getDrawable(AssetsManager.TAP_LEVEL_2_UPGRADE));
        tapUpgrade2.setScaling(Scaling.fit);
        tapUpgrade2Active = new Image(AssetsManager.getAssetsManager().getDrawable(AssetsManager.TAP_LEVEL_2_UPGRADE_ACTIVE));
        tapUpgrade2Active.setScaling(Scaling.fit);
        tapUpgrade2Disabled = new Image(AssetsManager.getAssetsManager().getDrawable(AssetsManager.TAP_LEVEL_2_UPGRADE_DISABLED));
        tapUpgrade2Disabled.setScaling(Scaling.fit);
        tapUpgrade2Current = tapUpgrade2Disabled;
        tapUpgrade3 = new Image(AssetsManager.getAssetsManager().getDrawable(AssetsManager.TAP_LEVEL_3_UPGRADE));
        tapUpgrade3.setScaling(Scaling.fit);
        tapUpgrade3Active = new Image(AssetsManager.getAssetsManager().getDrawable(AssetsManager.TAP_LEVEL_3_UPGRADE_ACTIVE));
        tapUpgrade3Active.setScaling(Scaling.fit);
        tapUpgrade3Disabled = new Image(AssetsManager.getAssetsManager().getDrawable(AssetsManager.TAP_LEVEL_3_UPGRADE_DISABLED));
        tapUpgrade3Disabled.setScaling(Scaling.fit);
        tapUpgrade3Current = tapUpgrade3Disabled;

        tableTapAction.row().pad(AssetsManager.getAssetsManager().getPadding());
        tableTapAction.add(tapUpgrade1Current).expand().uniform().fill();
        tableTapAction.add(tapUpgrade2Current).expand().uniform().fill();
        tableTapAction.add(tapUpgrade3Current).expand().uniform().fill();
        tableTapAction.row();
        tableTapAction.add(tapLabel).colspan(3).left().expandX();
        tableTapAction.row();
        tableTapAction.add(tapDescription).colspan(3).top().left().pad(AssetsManager.getAssetsManager().getPadding()).expand().fill();
        tableTapAction.row();
        tableTapAction.add(tapButtonUpgrade).colspan(3).expandX().fillX();

        Label scatterLabel = new Label("Scatter", skin);
        Label scatterDescription = new Label("Scatter description", skin);
        scatterDescription.setAlignment(Align.topLeft);
        TextButton scatterButtonBuy = new TextButton("Buy (" + actionManager.getScatterCount() + ")\n$" + ActionManager.SCATTER_ITEM_COST, skin);
        scatterButtonBuy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.setScatterCount(actionManager.getScatterCount() + 1);
                ((TextButton) actor).setText("Buy (" + actionManager.getScatterCount() + ")\n$" + ActionManager.SCATTER_ITEM_COST);

                if (actionManager.getScatterCount() == ActionManager.MAX_SCATTER_COUNT) {
                    ((TextButton) actor).setDisabled(true);
                }
            }
        });
        TextButton scatterButtonUpgrade = new TextButton("Upgrade\n$" + ActionManager.SCATTER_UPGRADE_1_COST, skin);
        if (actionManager.getScatterLevel() == ActionManager.MID_SCATTER_LEVEL) {
            scatterButtonUpgrade.setText("Upgrade\n$" + ActionManager.SCATTER_UPGRADE_2_COST);
        } else if (actionManager.getScatterLevel() == ActionManager.MAX_SCATTER_LEVEL) {
            scatterButtonUpgrade.setDisabled(true);
            scatterButtonUpgrade.setText("Final\n$-");
        }
        scatterButtonUpgrade.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.incrementScatterLevel();
                scatterButton.setDrawable(AssetsManager.getAssetsManager().getDrawable("scatter-" + actionManager.getScatterLevel()));

                if (actionManager.getScatterLevel() == ActionManager.MID_SCATTER_LEVEL) {
                    ((TextButton) actor).setText("Upgrade\n$" + ActionManager.SCATTER_UPGRADE_2_COST);
                } else if (actionManager.getScatterLevel() == ActionManager.MAX_SCATTER_LEVEL) {
                    ((TextButton) actor).setDisabled(true);
                    ((TextButton) actor).setText("Final\n$-");
                }
            }
        });
        tableScatterAction.add(scatterLabel).colspan(2).left().expandX();
        tableScatterAction.row();
        tableScatterAction.add(scatterDescription).colspan(2).top().left().pad(AssetsManager.getAssetsManager().getPadding()).expand().fill();
        tableScatterAction.row();
        tableScatterAction.add(scatterButtonBuy).padRight(AssetsManager.getAssetsManager().getPadding()).expandX().fillX().uniform();
        tableScatterAction.add(scatterButtonUpgrade).padLeft(AssetsManager.getAssetsManager().getPadding()).expandX().fillX().uniform();

        Label bombLabel = new Label("Bomb", skin);
        Label bombDescription = new Label("Bomb description", skin);
        bombDescription.setAlignment(Align.topLeft);
        TextButton bombButtonBuy = new TextButton("Buy (" + actionManager.getBombCount() + ")\n$" + ActionManager.BOMB_ITEM_COST, skin);
        bombButtonBuy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.setBombCount(actionManager.getBombCount() + 1);
                ((TextButton) actor).setText("Buy (" + actionManager.getBombCount() + ")\n$" + ActionManager.BOMB_ITEM_COST);

                if (actionManager.getBombCount() == ActionManager.MAX_BOMB_COUNT) {
                    ((TextButton) actor).setDisabled(true);
                }
            }
        });
        TextButton bombButtonUpgrade = new TextButton("Upgrade\n$" + ActionManager.BOMB_UPGRADE_1_COST, skin);
        if (actionManager.getBombLevel() == ActionManager.MID_BOMB_LEVEL) {
            bombButtonUpgrade.setText("Upgrade\n$" + ActionManager.BOMB_UPGRADE_2_COST);
        } else if (actionManager.getBombLevel() == ActionManager.MAX_BOMB_LEVEL) {
            bombButtonUpgrade.setDisabled(true);
            bombButtonUpgrade.setText("Final\n$-");
        }
        bombButtonUpgrade.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.incrementBombLevel();
                bombButton.setDrawable(AssetsManager.getAssetsManager().getDrawable("bomb-" + actionManager.getBombLevel()));

                if (actionManager.getBombLevel() == ActionManager.MID_BOMB_LEVEL) {
                    ((TextButton) actor).setText("Upgrade\n$" + ActionManager.BOMB_UPGRADE_2_COST);
                } else if (actionManager.getBombLevel() == ActionManager.MAX_BOMB_LEVEL) {
                    ((TextButton) actor).setDisabled(true);
                    ((TextButton) actor).setText("Final\n$-");
                }
            }
        });
        tableBombAction.add(bombLabel).colspan(2).left().expandX();
        tableBombAction.row();
        tableBombAction.add(bombDescription).colspan(2).top().left().pad(AssetsManager.getAssetsManager().getPadding()).expand().fill();
        tableBombAction.row();
        tableBombAction.add(bombButtonBuy).padRight(AssetsManager.getAssetsManager().getPadding()).expandX().fillX().uniform();
        tableBombAction.add(bombButtonUpgrade).padLeft(AssetsManager.getAssetsManager().getPadding()).expandX().fillX().uniform();

        Label cureLabel = new Label("Cure", skin);
        Label cureDescription = new Label("Cure description", skin);
        cureDescription.setAlignment(Align.topLeft);
        TextButton cureButtonBuy = new TextButton("Buy (" + actionManager.getCureCount() + ")\n$" + ActionManager.CURE_ITEM_COST, skin);
        cureButtonBuy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.setCureCount(actionManager.getCureCount() + 1);
                ((TextButton) actor).setText("Buy (" + actionManager.getCureCount() + ")\n$" + ActionManager.CURE_ITEM_COST);
                
                if (actionManager.getCureCount() == ActionManager.MAX_CURE_COUNT) {
                    ((TextButton) actor).setDisabled(true);
                }
            }
        });
        TextButton cureButtonUpgrade = new TextButton("Upgrade\n$" + ActionManager.CURE_UPGRADE_1_COST, skin);
        if (actionManager.getCureLevel() == ActionManager.MID_CURE_LEVEL) {
            cureButtonUpgrade.setText("Upgrade\n$" + ActionManager.CURE_UPGRADE_2_COST);
        } else if (actionManager.getCureLevel() == ActionManager.MAX_CURE_LEVEL) {
            cureButtonUpgrade.setDisabled(true);
            cureButtonUpgrade.setText("Final\n$-");
        }
        cureButtonUpgrade.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.incrementCureLevel();
                cureButton.setDrawable(AssetsManager.getAssetsManager().getDrawable("cure-" + actionManager.getCureLevel()));

                if (actionManager.getCureLevel() == ActionManager.MID_CURE_LEVEL) {
                    ((TextButton) actor).setText("Upgrade\n$" + ActionManager.CURE_UPGRADE_2_COST);
                } else if (actionManager.getCureLevel() == ActionManager.MAX_CURE_LEVEL) {
                    ((TextButton) actor).setDisabled(true);
                    ((TextButton) actor).setText("Final\n$-");
                }
            }
        });
        tableCureAction.add(cureLabel).colspan(2).left().expandX();
        tableCureAction.row();
        tableCureAction.add(cureDescription).colspan(2).top().left().pad(AssetsManager.getAssetsManager().getPadding()).expand().fill();
        tableCureAction.row();
        tableCureAction.add(cureButtonBuy).padRight(AssetsManager.getAssetsManager().getPadding()).expandX().fillX().uniform();
        tableCureAction.add(cureButtonUpgrade).padLeft(AssetsManager.getAssetsManager().getPadding()).expandX().fillX().uniform();

        // build main tables
        tableButtons.add(button1).left();
        tableButtons.add(button2).center().expand();
        tableButtons.add(button3).right();

        tableCurrentAction = tableTapAction;

        table.add(tableButtons).colspan(2).pad(AssetsManager.getAssetsManager().getPadding()).expandX().fill();
        table.row();
        table.add(scrollPane).expandY().fillY();
        table.add(tableTapAction).pad(AssetsManager.getAssetsManager().getPadding()).expand().fill();
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

