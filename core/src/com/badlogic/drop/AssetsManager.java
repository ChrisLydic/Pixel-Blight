package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by chris on 6/15/2017.
 */
public class AssetsManager {
    private static AssetsManager assetsManager;
    public static final int SMALL_PADDING = 10;

    public static final int SIZE = 10;
    public static final int NORMAL_TILE = 0;
    public static final int DARK_TILE = 1;
    public static final int EDGE_TILE = 2;
    public static final int DARK_EDGE_TILE = 3;
    public static final int NORMAL_CORRUPT_ANIMATION = 4;
    public static final int EDGE_CORRUPT_ANIMATION = 8;
    public static final int NORMAL_UNCORRUPT_ANIMATION = 12;
    public static final int EDGE_UNCORRUPT_ANIMATION = 16;
    public static final int NORMAL_CURE_ANIMATION = 20;
    public static final int EDGE_CURE_ANIMATION = 25;
    public static final String UI_SKIN = "uiskin.json";
    public static final String UI_ATLAS = "uiskin.atlas";
    public static final String MAIN_BACKGROUND = "background.png";
    public static final String LOGO = "logo.png";
    public static final String LEVELS_1 = "levels1.png";
    public static final String TAP_EFFECT = "spriteanimation";
    public static final String CURE_EFFECT = "cure_effect";
    public static final String BACKGROUND_COLOR = "background-color";
    public static final String WORLD_MAP = "world-map";

    public static final String TAP_BASE = "tap-";
    public static final String TAP_LEVEL_1 = "tap-1";
    public static final String TAP_LEVEL_2 = "tap-2";
    public static final String TAP_LEVEL_3 = "tap-3";
    public static final String TAP_LEVEL_1_DOWN = "tap-1-down";
    public static final String TAP_LEVEL_2_DOWN = "tap-2-down";
    public static final String TAP_LEVEL_3_DOWN = "tap-3-down";
    public static final String TAP_LEVEL_1_UPGRADE = "tap-1-upgrade";
    public static final String TAP_LEVEL_2_UPGRADE = "tap-2-upgrade";
    public static final String TAP_LEVEL_3_UPGRADE = "tap-3-upgrade";
    public static final String TAP_LEVEL_1_UPGRADE_ACTIVE = "tap-1-upgrade-active";
    public static final String TAP_LEVEL_2_UPGRADE_ACTIVE = "tap-2-upgrade-active";
    public static final String TAP_LEVEL_3_UPGRADE_ACTIVE = "tap-3-upgrade-active";
    public static final String TAP_LEVEL_1_UPGRADE_DISABLED = "tap-1-upgrade-disabled";
    public static final String TAP_LEVEL_2_UPGRADE_DISABLED = "tap-2-upgrade-disabled";
    public static final String TAP_LEVEL_3_UPGRADE_DISABLED = "tap-3-upgrade-disabled";

    public static final String SCATTER_BASE = "scatter-";
    public static final String SCATTER_LEVEL_1 = "scatter-1";
    public static final String SCATTER_LEVEL_2 = "scatter-2";
    public static final String SCATTER_LEVEL_3 = "scatter-3";
    public static final String SCATTER_LEVEL_1_DOWN = "scatter-1-down";
    public static final String SCATTER_LEVEL_2_DOWN = "scatter-2-down";
    public static final String SCATTER_LEVEL_3_DOWN = "scatter-3-down";
    public static final String SCATTER_LEVEL_1_UPGRADE = "scatter-1-upgrade";
    public static final String SCATTER_LEVEL_2_UPGRADE = "scatter-2-upgrade";
    public static final String SCATTER_LEVEL_3_UPGRADE = "scatter-3-upgrade";
    public static final String SCATTER_LEVEL_1_UPGRADE_ACTIVE = "scatter-1-upgrade-active";
    public static final String SCATTER_LEVEL_2_UPGRADE_ACTIVE = "scatter-2-upgrade-active";
    public static final String SCATTER_LEVEL_3_UPGRADE_ACTIVE = "scatter-3-upgrade-active";
    public static final String SCATTER_LEVEL_1_UPGRADE_DISABLED = "scatter-1-upgrade-disabled";
    public static final String SCATTER_LEVEL_2_UPGRADE_DISABLED = "scatter-2-upgrade-disabled";
    public static final String SCATTER_LEVEL_3_UPGRADE_DISABLED = "scatter-3-upgrade-disabled";

    public static final String BOMB_BASE = "bomb-";
    public static final String BOMB_LEVEL_1 = "bomb-1";
    public static final String BOMB_LEVEL_2 = "bomb-2";
    public static final String BOMB_LEVEL_3 = "bomb-3";
    public static final String BOMB_LEVEL_1_DOWN = "bomb-1-down";
    public static final String BOMB_LEVEL_2_DOWN = "bomb-2-down";
    public static final String BOMB_LEVEL_3_DOWN = "bomb-3-down";
    public static final String BOMB_LEVEL_1_UPGRADE = "bomb-1-upgrade";
    public static final String BOMB_LEVEL_2_UPGRADE = "bomb-2-upgrade";
    public static final String BOMB_LEVEL_3_UPGRADE = "bomb-3-upgrade";
    public static final String BOMB_LEVEL_1_UPGRADE_ACTIVE = "bomb-1-upgrade-active";
    public static final String BOMB_LEVEL_2_UPGRADE_ACTIVE = "bomb-2-upgrade-active";
    public static final String BOMB_LEVEL_3_UPGRADE_ACTIVE = "bomb-3-upgrade-active";
    public static final String BOMB_LEVEL_1_UPGRADE_DISABLED = "bomb-1-upgrade-disabled";
    public static final String BOMB_LEVEL_2_UPGRADE_DISABLED = "bomb-2-upgrade-disabled";
    public static final String BOMB_LEVEL_3_UPGRADE_DISABLED = "bomb-3-upgrade-disabled";

    public static final String CURE_BASE = "cure-";
    public static final String CURE_LEVEL_1 = "cure-1";
    public static final String CURE_LEVEL_2 = "cure-2";
    public static final String CURE_LEVEL_3 = "cure-3";
    public static final String CURE_LEVEL_1_DOWN = "cure-1-down";
    public static final String CURE_LEVEL_2_DOWN = "cure-2-down";
    public static final String CURE_LEVEL_3_DOWN = "cure-3-down";
    public static final String CURE_LEVEL_1_UPGRADE = "cure-1-upgrade";
    public static final String CURE_LEVEL_2_UPGRADE = "cure-2-upgrade";
    public static final String CURE_LEVEL_3_UPGRADE = "cure-3-upgrade";
    public static final String CURE_LEVEL_1_UPGRADE_ACTIVE = "cure-1-upgrade-active";
    public static final String CURE_LEVEL_2_UPGRADE_ACTIVE = "cure-2-upgrade-active";
    public static final String CURE_LEVEL_3_UPGRADE_ACTIVE = "cure-3-upgrade-active";
    public static final String CURE_LEVEL_1_UPGRADE_DISABLED = "cure-1-upgrade-disabled";
    public static final String CURE_LEVEL_2_UPGRADE_DISABLED = "cure-2-upgrade-disabled";
    public static final String CURE_LEVEL_3_UPGRADE_DISABLED = "cure-3-upgrade-disabled";

    private AssetManager assetManager;
    private ParticleEffectPool tapEffectPool;
    private ParticleEffectPool cureEffectPool;
    private TextureRegion[][] tiles;

    private AssetsManager() {}

    public static AssetsManager getAssetsManager() {
        if (assetsManager == null) {
            assetsManager = new AssetsManager();
        }
        return assetsManager;
    }

    public void loadAssets() {
        assetManager = new AssetManager();
        assetManager.load("tiles2.png", Texture.class);
        assetManager.load("anim_explosion.png", Texture.class);
        assetManager.load(LEVELS_1, Texture.class);
        assetManager.load(MAIN_BACKGROUND, Texture.class);
        assetManager.load(LOGO, Texture.class);
        assetManager.load(UI_ATLAS, TextureAtlas.class);
        assetManager.load(UI_SKIN, Skin.class,
                new SkinLoader.SkinParameter());
        assetManager.finishLoading();

        ParticleEffect tapEffect = new ParticleEffect();
        tapEffect.load(Gdx.files.internal(TAP_EFFECT),Gdx.files.internal("im"));
        tapEffectPool = new ParticleEffectPool(tapEffect, 1, 4);

        ParticleEffect cureEffect = new ParticleEffect();
        cureEffect.load(Gdx.files.internal(CURE_EFFECT),Gdx.files.internal("im"));
        cureEffectPool = new ParticleEffectPool(cureEffect, 1, 4);

        tiles = new TextureRegion(
                assetManager.get("tiles2.png", Texture.class)
            ).split(32, 32);

        assetManager.get(UI_SKIN, Skin.class).getFont("default-font").getData().setScale(1.25f);
    }

    public TextureRegion[][] getTiles() {
        return tiles;
    }

    public Animation getTileAnimation(int row, int col, float x, float y, float duration, boolean loop) {
        TextureRegion[] frames = new TextureRegion[]{ tiles[row][col], tiles[row][++col], tiles[row][++col], tiles[row][++col]};
        return new Animation(duration, frames, x, y, SIZE, SIZE, loop);
    }

    public Animation getAnimation(String name, float x, float y, float width, float height, float duration, boolean loop) {
        TextureRegion[] frames = TextureRegion.split(assetManager.get("anim_explosion.png", Texture.class), 128, 128)[0];
        return new Animation(duration, frames, x, y, width, height, loop);
    }

    public Skin getUISkin() {
        return assetManager.get(UI_SKIN, Skin.class);
    }

    public Sprite getSprite(String spriteName) {
        try {
            return assetManager.get(spriteName, Sprite.class);
        } catch (GdxRuntimeException ex) {
            return assetManager.get(UI_SKIN, Skin.class).getSprite(spriteName); //TODO get rid of this shit
        }
    }

    public Texture getTexture(String textureName) {
        return assetManager.get(textureName, Texture.class);
    }

    public Drawable getDrawable(String drawableName) {
        return assetManager.get(UI_SKIN, Skin.class).getDrawable(drawableName);
    }

    public ParticleEffectPool getEffectPool(String effectName) {
        switch (effectName) {
            case CURE_EFFECT:
                return cureEffectPool;
            case TAP_EFFECT:
                return tapEffectPool;
            default:
                throw new IllegalArgumentException(effectName);
        }
    }

    public int getPadding() {
        return SMALL_PADDING;
    }

    public void dispose() {
        assetManager.dispose();
    }
}
