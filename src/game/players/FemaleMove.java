package game.players;

import game.bases.Vector2D;
import game.bases.inputs.InputManager;

/**
 * Created by huynq on 8/3/17.
 */
public class FemaleMove implements PlayerMove {
    @Override
    public void move(Player player) {
        Vector2D position = player.position;

        if (InputManager.instance.dPressed) {
            position.addUp(10, 0);
        }

        if (InputManager.instance.aPressed) {
            position.addUp(-10, 0);
        }
    }
}
