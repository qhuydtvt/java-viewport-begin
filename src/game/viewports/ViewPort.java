package game.viewports;

import game.bases.GameObject;
import game.cameras.Camera;

import java.awt.*;
import java.util.List;

/**
 * Created by huynq on 8/3/17.
 */
public class ViewPort {

    private boolean isHidden;
    private Camera camera;

    public ViewPort() {
        camera = new Camera();
    }

    public Camera getCamera() {
        return camera;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public void render(Graphics2D g, List<GameObject> gameObjects) {
        if (!isHidden) {
            if (camera != null) {
                for (GameObject gameObject : gameObjects) {
                    gameObject.render(g, camera);
                }
            }
        }
    }
}
