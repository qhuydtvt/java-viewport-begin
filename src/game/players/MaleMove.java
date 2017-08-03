package game.players;

import game.bases.Vector2D;
import game.bases.inputs.InputManager;
import tklibs.Mathx;

/**
 * Created by huynq on 8/3/17.
 */
public class MaleMove implements PlayerMove {
    @Override
    public void move(Player player) {
        Vector2D position = player.position;

        if (InputManager.instance.rightPressed) {
            position.addUp(10, 0);
        }

        if (InputManager.instance.leftPressed) {
            position.addUp(-10, 0);
        }

        position.x = Mathx.clamp(position.x, 0,4000);
    }
}
