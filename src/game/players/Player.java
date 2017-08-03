package game.players;

import game.bases.GameObject;
import game.bases.renderers.ImageRenderer;
import tklibs.SpriteUtils;

/**
 * Created by huynq on 8/3/17.
 */
public class Player extends GameObject {
    public Player() {
        super();

    }

    public static Player createMalePlayer() {
        Player player = new Player();
        player.renderer = new ImageRenderer(SpriteUtils.loadImage("assets/images/green_square.png"));
        return player;
    }

    public static Player createFemalePlayer() {
        Player player = new Player();
        player.renderer = new ImageRenderer(SpriteUtils.loadImage("assets/images/yellow_square.jpg"));
        return player;
    }
}
