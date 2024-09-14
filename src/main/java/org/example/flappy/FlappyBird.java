package org.example.flappy;
import org.example.flappy.observer.GameNotify;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class FlappyBird extends JPanel implements ActionListener, KeyListener{
    int boardWidth = 360;
    int boardHeight = 640;
    GameNotify gameNotify;

    //Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;
    Image introImage;


    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdWidth =34;
    int birdHeight =24;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!gameStarted) {
                gameStarted = true;
                gameNotify.notifyListeners(new Event(EventType.INTRO_END));
                repaint();
                startGame();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityY = -9;
            gameNotify.notifyListeners(new Event(EventType.SWOOSH));
            if (gameOver){
                score = 0;
                pipes.clear();
                bird.y  = birdY;
                velocityY = 0;
                gameOver = false;
                startGame();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        public Bird(Image img) {
            this.img = img;
        }
    }

    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        public Pipe(Image img) {
            this.img = img;
        }
    }

    Bird bird;
    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;
    ArrayList<Pipe> pipes;
    Random random = new Random();
    Timer gameLoop;
    Timer placePipesTimer;
    boolean gameOver = false;
    double score = 0;
    boolean gameStarted = false;

    FlappyBird() {
        gameNotify = new GameNotify();
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        setBackground(Color.BLUE);
        setFocusable(true);
        addKeyListener(this);
        birdImg = new ImageIcon(getClass().getResource("/flappybird.png")).getImage();
        backgroundImg = new ImageIcon(getClass().getResource("/flappybirdbg.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("/bottompipe.png")).getImage();
        introImage = new ImageIcon(getClass().getResource("/intro.png")).getImage();

    }
    private void drawIntroScene(Graphics g){
        gameNotify.notifyListeners(new Event(EventType.INTRO_START));
        g.drawImage(introImage, 0, 0, getWidth(), getHeight(), null);
    }

    private void startGame() {
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        placePipesTimer.start();
        gameLoop = new Timer(1000/60,this);
        gameLoop.start();
        gameNotify.notifyListeners(new Event(EventType.GAME_START));
        //play("soundtrack.wav", true);
        System.out.println("FLAPPY");
    }

    public void placePipes() {
        int randomPipeY = (int)(pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpce = boardHeight/4;
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);
        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpce;
        pipes.add(bottomPipe);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!gameStarted) {
           drawIntroScene(g);
        } else {
            draw(g);
        }
    }
    public void draw(Graphics g){
        g.drawImage(backgroundImg,0,0,boardWidth,boardHeight,null);
        g.drawImage(bird.img,bird.x,bird.y,bird.width,bird.height,null);
        for (int i=0; i<pipes.size();i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img,pipe.x,pipe.y,pipe.width,pipe.height,null);
        }
        g.setColor(Color.PINK);
        g.setFont(new Font("Arial",Font.PLAIN,32));
        if (gameOver){
            g.drawString("Game Over: " + String.valueOf((int)score),10,35);
            gameNotify.notifyListeners(new Event(EventType.GAME_END));
        }
        else {
            g.drawString("Score: " +  String.valueOf((int)score),10,35);
        }
    }

    public void move(){
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y,0);
         //pipes
        for (int i=0; i<pipes.size();i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;
            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                pipe.passed =  true;
                score += 4.5;
                try {
                    gameNotify.notifyListeners(new Event(EventType.POINT));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(collision(bird,pipe)){
                gameOver = true;
                gameNotify.notifyListeners(new Event(EventType.COLLISION));
                try {
                    Thread.sleep(1400);
                    gameNotify.notifyListeners(new Event(EventType.GAME_END));
                } catch (Exception ignore){};


            }
        }
        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver){
            placePipesTimer.stop();
            gameLoop.stop();
            gameNotify.notifyListeners(new Event(EventType.DIE));
        }
    }

    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

}
