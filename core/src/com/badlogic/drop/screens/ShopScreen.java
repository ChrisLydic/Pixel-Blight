package com.badlogic.drop.screens;

import com.badlogic.drop.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
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
    private ActionManager actionManager;

    private Image tapImage;
    private Image scatterImage;
    private Image bombImage;
    private Image cureImage;

    private Label tapUpgradePrice;
    private Label tapUpgradeText;
    private Button tapUpgradeButton;

    private Label scatterBuyPrice;
    private Label scatterBuyText;
    private Button scatterBuyButton;
    private Label scatterUpgradePrice;
    private Label scatterUpgradeText;
    private Button scatterUpgradeButton;

    private Label bombBuyPrice;
    private Label bombBuyText;
    private Button bombBuyButton;
    private Label bombUpgradePrice;
    private Label bombUpgradeText;
    private Button bombUpgradeButton;

    private Label cureBuyPrice;
    private Label cureBuyText;
    private Button cureBuyButton;
    private Label cureUpgradePrice;
    private Label cureUpgradeText;
    private Button cureUpgradeButton;

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

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Table tableButtons = new Table();
        Table tableActions = new Table();
        Table tableTapAction = new Table();
        Table tableScatterAction = new Table();
        Table tableBombAction = new Table();
        Table tableCureAction = new Table();

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
        tapUpgradePrice = new Label(Integer.toString(ActionManager.TAP_UPGRADE_1_COST), skin, "shop-button-price");
        tapUpgradeText = new Label("Upgrade", skin, "shop-button");
        tapUpgradeButton = new Button(skin, "empty");
        if (actionManager.getTapLevel() == ActionManager.MID_TAP_LEVEL) {
            tapUpgradePrice.setText(Integer.toString(ActionManager.TAP_UPGRADE_2_COST));
        } else if (actionManager.getTapLevel() == ActionManager.MAX_TAP_LEVEL) {
            tapUpgradeButton.setDisabled(true);
            tapUpgradeText.setText("Final");
        }
        tapUpgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.incrementTapLevel();
                tapImage.setDrawable(AssetsManager.getAssetsManager().getDrawable("tap-" + actionManager.getTapLevel()));

                if (actionManager.getTapLevel() == ActionManager.MID_TAP_LEVEL) {
                    tapUpgradePrice.setText(Integer.toString(ActionManager.TAP_UPGRADE_2_COST));
                } else if (actionManager.getTapLevel() == ActionManager.MAX_TAP_LEVEL) {
                    tapUpgradePrice.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button-price-disabled"));
                    tapUpgradeText.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button-disabled"));
                    tapUpgradeText.setText("Final");
                    tapUpgradeButton.setDisabled(true);
                }
            }
        });
        tapUpgradeButton.addListener(createListener(tapUpgradeButton, tapUpgradePrice, tapUpgradeText));
        tapUpgradeButton.add(tapUpgradePrice);
        tapUpgradeButton.add(tapUpgradeText);

        Label scatterLabel = new Label("Scatter", skin);
        Label scatterDescription = new Label("Scatter description", skin);
        scatterDescription.setAlignment(Align.topLeft);
        scatterBuyPrice = new Label(Integer.toString(ActionManager.SCATTER_ITEM_COST), skin, "shop-button-price");
        scatterBuyText = new Label("Buy", skin, "shop-button");
        scatterBuyButton = new Button(skin, "empty");
        if (actionManager.getScatterLevel() == ActionManager.MID_SCATTER_LEVEL) {
            scatterBuyButton.setDisabled(true);
            scatterBuyText.setText("Full");
        }
        scatterBuyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.setScatterCount(actionManager.getScatterCount() + 1);

                if (actionManager.getScatterCount() == ActionManager.MAX_SCATTER_COUNT) {
                    scatterBuyPrice.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button-price-disabled"));
                    scatterBuyText.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button-disabled"));
                    scatterBuyText.setText("Full");
                    scatterBuyButton.setDisabled(true);
                }
            }
        });
        scatterBuyButton.addListener(createListener(scatterBuyButton, scatterBuyPrice, scatterBuyText));
        scatterBuyButton.add(scatterBuyPrice);
        scatterBuyButton.add(scatterBuyText);

        scatterUpgradePrice = new Label(Integer.toString(ActionManager.SCATTER_UPGRADE_1_COST), skin, "shop-button-price");
        scatterUpgradeText = new Label("Upgrade", skin, "shop-button");
        scatterUpgradeButton = new Button(skin, "empty");
        if (actionManager.getScatterLevel() == ActionManager.MID_SCATTER_LEVEL) {
            scatterUpgradePrice.setText(Integer.toString(ActionManager.SCATTER_UPGRADE_2_COST));
        } else if (actionManager.getScatterLevel() == ActionManager.MAX_SCATTER_LEVEL) {
            scatterUpgradeButton.setDisabled(true);
            scatterUpgradeText.setText("Final");
        }
        scatterUpgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.incrementScatterLevel();
                scatterImage.setDrawable(AssetsManager.getAssetsManager().getDrawable("scatter-" + actionManager.getScatterLevel()));

                if (actionManager.getScatterLevel() == ActionManager.MID_SCATTER_LEVEL) {
                    scatterUpgradePrice.setText(Integer.toString(ActionManager.SCATTER_UPGRADE_2_COST));
                } else if (actionManager.getScatterLevel() == ActionManager.MAX_SCATTER_LEVEL) {
                    scatterUpgradePrice.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button-price-disabled"));
                    scatterUpgradeText.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button-disabled"));
                    scatterUpgradeText.setText("Final");
                    scatterUpgradeButton.setDisabled(true);
                }
            }
        });
        scatterUpgradeButton.addListener(createListener(scatterUpgradeButton, scatterUpgradePrice, scatterUpgradeText));
        scatterUpgradeButton.add(scatterUpgradePrice);
        scatterUpgradeButton.add(scatterUpgradeText);

        Label bombLabel = new Label("Bomb", skin);
        Label bombDescription = new Label("Bomb description", skin);
        bombDescription.setAlignment(Align.topLeft);
        bombBuyPrice = new Label(Integer.toString(ActionManager.BOMB_ITEM_COST), skin, "shop-button-price");
        bombBuyText = new Label("Buy", skin, "shop-button");
        bombBuyButton = new Button(skin, "empty");
        if (actionManager.getBombLevel() == ActionManager.MID_BOMB_LEVEL) {
            bombBuyButton.setDisabled(true);
            bombBuyText.setText("Full");
        }
        bombBuyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.setBombCount(actionManager.getBombCount() + 1);

                if (actionManager.getBombCount() == ActionManager.MAX_BOMB_COUNT) {
                    bombBuyPrice.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button-price-disabled"));
                    bombBuyText.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button-disabled"));
                    bombBuyText.setText("Full");
                    bombBuyButton.setDisabled(true);
                }
            }
        });
        bombBuyButton.addListener(createListener(bombBuyButton, bombBuyPrice, bombBuyText));
        bombBuyButton.add(bombBuyPrice);
        bombBuyButton.add(bombBuyText);

        bombUpgradePrice = new Label(Integer.toString(ActionManager.BOMB_UPGRADE_1_COST), skin, "shop-button-price");
        bombUpgradeText = new Label("Upgrade", skin, "shop-button");
        bombUpgradeButton = new Button(skin, "empty");
        if (actionManager.getBombLevel() == ActionManager.MID_BOMB_LEVEL) {
            bombUpgradePrice.setText(Integer.toString(ActionManager.BOMB_UPGRADE_2_COST));
        } else if (actionManager.getBombLevel() == ActionManager.MAX_BOMB_LEVEL) {
            bombUpgradeButton.setDisabled(true);
            bombUpgradeText.setText("Final");
        }
        bombUpgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.incrementBombLevel();
                bombImage.setDrawable(AssetsManager.getAssetsManager().getDrawable("bomb-" + actionManager.getBombLevel()));

                if (actionManager.getBombLevel() == ActionManager.MID_BOMB_LEVEL) {
                    bombUpgradePrice.setText(Integer.toString(ActionManager.BOMB_UPGRADE_2_COST));
                } else if (actionManager.getBombLevel() == ActionManager.MAX_BOMB_LEVEL) {
                    bombUpgradePrice.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button-price-disabled"));
                    bombUpgradeText.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button-disabled"));
                    bombUpgradeText.setText("Final");
                    bombUpgradeButton.setDisabled(true);
                }
            }
        });
        bombUpgradeButton.addListener(createListener(bombUpgradeButton, bombUpgradePrice, bombUpgradeText));
        bombUpgradeButton.add(bombUpgradePrice);
        bombUpgradeButton.add(bombUpgradeText);

        Label cureLabel = new Label("Cure", skin);
        Label cureDescription = new Label("Cure description", skin);
        cureDescription.setAlignment(Align.topLeft);
        cureBuyPrice = new Label(Integer.toString(ActionManager.CURE_ITEM_COST), skin, "shop-button-price");
        cureBuyText = new Label("Buy", skin, "shop-button");
        cureBuyButton = new Button(skin, "empty");
        if (actionManager.getCureLevel() == ActionManager.MID_CURE_LEVEL) {
            cureBuyButton.setDisabled(true);
            cureBuyText.setText("Full");
        }
        cureBuyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.setCureCount(actionManager.getCureCount() + 1);

                if (actionManager.getCureCount() == ActionManager.MAX_CURE_COUNT) {
                    cureBuyPrice.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button-price-disabled"));
                    cureBuyText.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button-disabled"));
                    cureBuyText.setText("Full");
                    cureBuyButton.setDisabled(true);
                }
            }
        });
        cureBuyButton.addListener(createListener(cureBuyButton, cureBuyPrice, cureBuyText));
        cureBuyButton.add(cureBuyPrice);
        cureBuyButton.add(cureBuyText);

        cureUpgradePrice = new Label(Integer.toString(ActionManager.CURE_UPGRADE_1_COST), skin, "shop-button-price");
        cureUpgradeText = new Label("Upgrade", skin, "shop-button");
        cureUpgradeButton = new Button(skin, "empty");
        if (actionManager.getCureLevel() == ActionManager.MID_CURE_LEVEL) {
            cureUpgradePrice.setText(Integer.toString(ActionManager.CURE_UPGRADE_2_COST));
        } else if (actionManager.getCureLevel() == ActionManager.MAX_CURE_LEVEL) {
            cureUpgradeButton.setDisabled(true);
            cureUpgradeText.setText("Final");
        }
        cureUpgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionManager.incrementCureLevel();
                cureImage.setDrawable(AssetsManager.getAssetsManager().getDrawable("cure-" + actionManager.getCureLevel()));

                if (actionManager.getCureLevel() == ActionManager.MID_CURE_LEVEL) {
                    cureUpgradePrice.setText(Integer.toString(ActionManager.CURE_UPGRADE_2_COST));
                } else if (actionManager.getCureLevel() == ActionManager.MAX_CURE_LEVEL) {
                    cureUpgradePrice.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button-price-disabled"));
                    cureUpgradeText.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button-disabled"));
                    cureUpgradeText.setText("Final");
                    cureUpgradeButton.setDisabled(true);
                }
            }
        });
        cureUpgradeButton.addListener(createListener(cureUpgradeButton, cureUpgradePrice, cureUpgradeText));
        cureUpgradeButton.add(cureUpgradePrice);
        cureUpgradeButton.add(cureUpgradeText);

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

        int buttonPad = AssetsManager.getAssetsManager().getPadding() * 2;

        tableTapAction.add(tapImage).size(80).pad(AssetsManager.getAssetsManager().getPadding());
        tableTapAction.add(tableTapText).expandX().fillX();
        tableTapAction.add(tapUpgradeButton).padRight(buttonPad);

        tableScatterAction.add(scatterImage).size(80).pad(AssetsManager.getAssetsManager().getPadding());
        tableScatterAction.add(tableScatterText).expandX().fillX();
        tableScatterAction.add(scatterBuyButton).padRight(buttonPad);
        tableScatterAction.add(scatterUpgradeButton).padRight(buttonPad);

        tableBombAction.add(bombImage).size(80).pad(AssetsManager.getAssetsManager().getPadding());
        tableBombAction.add(tableBombText).expandX().fillX();
        tableBombAction.add(bombBuyButton).padRight(buttonPad);
        tableBombAction.add(bombUpgradeButton).padRight(buttonPad);

        tableCureAction.add(cureImage).size(80).pad(AssetsManager.getAssetsManager().getPadding());
        tableCureAction.add(tableCureText).expandX().fillX();
        tableCureAction.add(cureBuyButton).padRight(buttonPad);
        tableCureAction.add(cureUpgradeButton).padRight(buttonPad);

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

    private ClickListener createListener(final Button actor, final Label price, final Label text) {
        return (new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!actor.isDisabled()) {
                    price.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button-price-down"));
                    text.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button-down"));
                }

                return super.touchDown(event, x, y, pointer, button);
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!actor.isDisabled()) {
                    price.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button-price"));
                    text.setStyle(AssetsManager.getAssetsManager().getLabelStyle("shop-button"));
                }

                super.touchUp(event, x, y, pointer, button);
            }
        });
    }
}

