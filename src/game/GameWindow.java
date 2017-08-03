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
import game.players.FemaleMove;
import game.players.MaleMove;
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

    ViewPort maleViewPort;
    ViewPort femaleViewPort;

    private Player malePlayer;
    private Player femalePlayer;

    private BufferedImage leftBufferImage;
    private BufferedImage rightBufferImage;
    private Graphics2D leftG2d;

    public GameWindow() {
        setupWindow();
        setupBackBuffer();
        setupInputs();
        addBackground();
        addPlayers();
        setupStartupScene();
        addViewPorts();
        this.setVisible(true);
    }

    private void addViewPorts() {
        maleViewPort = new ViewPort();
        maleViewPort.getCamera().follow(malePlayer);
        maleViewPort.getCamera().getOffset().set(getWidth() / 4, 0);

        femaleViewPort = new ViewPort();
        femaleViewPort.getCamera().follow(femalePlayer);
        femaleViewPort.getCamera().getOffset().set(getWidth() / 4, 0);

        GameObject.add(maleViewPort.getCamera());
        GameObject.add(femaleViewPort.getCamera());

    }

    private void addPlayers() {
        malePlayer = Player.createMalePlayer();
        malePlayer.setPlayerMove(new MaleMove());

        femalePlayer = Player.createFemalePlayer();
        femalePlayer.setPlayerMove(new FemaleMove());
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
        leftBufferImage = new BufferedImage(this.getWidth() / 2, this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        rightBufferImage = new BufferedImage(this.getWidth() / 2, this.getHeight(), BufferedImage.TYPE_INT_ARGB);

        leftG2d = (Graphics2D) leftBufferImage.getGraphics();

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

        leftG2d.setColor(Color.BLACK);
        leftG2d.fillRect(0, 0, this.getWidth() / 2, this.getHeight());

        Graphics2D rightG2d = (Graphics2D) rightBufferImage.getGraphics();

        rightG2d.setColor(Color.BLACK);
        rightG2d.fillRect(0, 0, this.getWidth(), this.getHeight());


        maleViewPort.render(leftG2d, GameObject.getGameObjects());
        femaleViewPort.render(rightG2d, GameObject.getGameObjects());

        backBufferGraphics2D.setColor(Color.BLACK);
        backBufferGraphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());

        backBufferGraphics2D.drawImage(leftBufferImage, 0, 0, null);
        backBufferGraphics2D.drawImage(rightBufferImage, getWidth() / 2, 0, null);
//        femaleViewPort.render(backBufferGraphics2D, GameObject.getGameObjects());

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
