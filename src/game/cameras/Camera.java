package game.cameras;

import game.bases.GameObject;
import game.bases.Vector2D;

/**
 * Created by huynq on 8/3/17.
 */
public class Camera extends GameObject {

    private  GameObject followedObject;

    public Camera() {
        super();
    }

    public void setFollowedObject(GameObject followedObject) {
        this.followedObject = followedObject;
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        this.screenPosition.x = followedObject.position.x;
    }

    public Vector2D translate(Vector2D position) {
        return position.subtract(this.screenPosition);
    }
}
