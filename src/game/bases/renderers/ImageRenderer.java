package game.bases.renderers;

import game.bases.GameObject;
import game.bases.Vector2D;
import game.cameras.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by huynq on 7/16/17.
 */
public class ImageRenderer implements Renderer {
    public BufferedImage image;
    public Vector2D anchor;
    public Vector2D scale;

    public ImageRenderer(BufferedImage image) {
        this.image = image;
        this.anchor = new Vector2D(0.5f, 0.5f);
        this.scale = new Vector2D(1,1);
    }

    public void render(Graphics g, Vector2D position) {
        g.drawImage(image, (int) (position.x - image.getWidth() * anchor.x),
                (int) (position.y - image.getHeight() * anchor.y), null);
    }

    public void render(Graphics g, Vector2D position, Camera camera) {
        Vector2D realPosition = position.subtract(image.getWidth() * anchor.x, image.getHeight() * anchor.y);
        Vector2D positionInCamera = camera.translate(realPosition);
        g.drawImage(image, (int) positionInCamera.x, (int) positionInCamera.y, null);
    }

    public ImageRenderer setAnchor(Vector2D anchor) {
        this.anchor = anchor;
        return this;
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }
}
