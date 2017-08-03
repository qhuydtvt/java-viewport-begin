package game;

import game.bases.GameObject;
import game.bases.inputs.InputManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import game.bases.*;
import game.bases.renderers.ImageRenderer;
import game.bases.scenes.SceneManager;
import game.cameras.Camera;
import game.players.Player;
import game.viewports.ViewPort;
import tklibs.SpriteUtils;

/**
 * Created by huynq on 7/9/17.
 */
public class GameWindow extends JFrame {

    BufferedImage backBufferImage;
    Graphics2D backBufferGraphics2D;

    InputManager inputManager = InputManager.instance;

    long lastTimeUpdate = -1;

    ViewPort mainViewPort;

    private Player malePlayer;
    private Player femalePlayer;

    public GameWindow() {
        setupWindow();
        setupBackBuffer();
        setupInputs();
        addBackground();
        addPlayers();
        setupStartupScene();
        addViewPort();
        this.setVisible(true);
    }

    private void addViewPort() {
        mainViewPort = new ViewPort();
        Camera camera = new Camera();
        camera.setFollowedObject(malePlayer);
        mainViewPort.setCamera(camera);
        GameObject.add(camera);
    }

    private void addPlayers() {
        malePlayer = Player.createMalePlayer();
        femalePlayer = Player.createFemalePlayer();
        GameObject.add(malePlayer.setPosition(50, 300));
        GameObject.add(femalePlayer.setPosition(100, 300));
    }

    private void addBackground() {
        GameObject.add(
                new GameObject().setRenderer(
                        new ImageRenderer(SpriteUtils.loadImage("assets/images/background_street.png"))
                .setAnchor(new Vector2D(0, 0))));
    }

    private void setupStartupScene() {

    }

    private void setupBackBuffer() {
        backBufferImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        backBufferGraphics2D = (Graphics2D) backBufferImage.getGraphics();
    }

    private void setupInputs() {
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                inputManager.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                inputManager.keyReleased(e);
            }
        });
    }


    public void loop() {
        while (true) {

            if (lastTimeUpdate == -1) {
                lastTimeUpdate = System.currentTimeMillis();
            }

            long currentTime = System.currentTimeMillis();

            if (currentTime - lastTimeUpdate > 17) {

                lastTimeUpdate = currentTime;

                run();

                render();
            }
        }
    }

    private void run() {
        GameObject.runAll();
        GameObject.runAllActions();
        SceneManager.instance.changeSceneIfNeeded();
    }

    private void render() {
        backBufferGraphics2D.setColor(Color.BLACK);
        backBufferGraphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());

        mainViewPort.render(backBufferGraphics2D, GameObject.getGameObjects());

        repaint();
    }

    private void setupWindow() {
        this.setSize(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
        this.setResizable(false);
        this.setTitle("Platformer begin");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(backBufferImage, 0, 0, null);
    }
}
