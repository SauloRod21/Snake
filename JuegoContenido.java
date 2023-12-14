import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class JuegoContenido extends JPanel implements ActionListener {
    static final int PANTALLA = 500;
    static final int BLOCK_SIZE = 25;
    static final int BLOCKPARALELO = PANTALLA / BLOCK_SIZE;
    static final int TOTAL_BLOCKSNAKE = (PANTALLA * PANTALLA) / BLOCK_SIZE;
    private static final int DELAY = 250;
    int[] snakeX = new int[TOTAL_BLOCKSNAKE];
    int[] snakeY = new int[TOTAL_BLOCKSNAKE];
    int bodySnake = 4;
    char direction = 'D';
    int foodX;
    int foodY;

    boolean gameOver = false;

    Timer timer;

    Random random = new Random();

    JuegoContenido() {
        this.setPreferredSize(new Dimension(PANTALLA, PANTALLA));
        this.setBackground(Color.DARK_GRAY);
        this.setFocusable(true);
        this.addKeyListener(new Control());
        startGame();
    }

    public void startGame() {
        initSnake();  // Inicializar la serpiente
        addFood();
        gameOver = false;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void initSnake() {
        // Inicializar la serpiente en una posición central
        int initialX = PANTALLA / 2;
        int initialY = PANTALLA / 2;

        for (int i = 0; i < bodySnake; i++) {
            snakeX[i] = initialX - i * BLOCK_SIZE;
            snakeY[i] = initialY;
        }
    }

    public void addFood() {
        foodX = random.nextInt(BLOCKPARALELO) * BLOCK_SIZE;
        foodY = random.nextInt(BLOCKPARALELO) * BLOCK_SIZE;
    }

    public void moveSnake() {
        for (int i = bodySnake; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        switch (direction) {
            case 'D':
                snakeX[0] = snakeX[0] + BLOCK_SIZE;
                break;
            case 'A':
                snakeX[0] = snakeX[0] - BLOCK_SIZE;
                break;
            case 'W':
                snakeY[0] = snakeY[0] - BLOCK_SIZE;
                break;
            case 'S':
                snakeY[0] = snakeY[0] + BLOCK_SIZE;
                break;
        }
    }

    public void reviewFood() {
        if (snakeX[0] == foodX && snakeY[0] == foodY) {
            bodySnake++;
            addFood();
        }
    }

    public void reviewHit() {
        if (snakeX[0] < 0 || snakeY[0] < 0 || snakeX[0] >= PANTALLA || snakeY[0] >= PANTALLA) {
            gameOver = true;
        }

        for (int i = 1; i < bodySnake; i++) {
            if (snakeX[i] == snakeX[0] && snakeY[i] == snakeY[0]) {
                gameOver = true;
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            moveSnake();
            reviewFood();
            reviewHit();
            repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Game Over");
            timer.stop();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < BLOCKPARALELO; i++) {
            g.drawLine(0, BLOCK_SIZE * i, PANTALLA, BLOCK_SIZE * i);
            g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE * i, PANTALLA);
        }
        g.setColor(Color.green);
        g.fillOval(foodX, foodY, BLOCK_SIZE, BLOCK_SIZE);
        g.setColor(Color.blue);
        for (int i = 0; i < bodySnake; i++) {
            g.fillOval(snakeX[i], snakeY[i], BLOCK_SIZE, BLOCK_SIZE);
        }
    }

    public class Control extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyChar()) {
                case 'w':
                    if (direction != 'S') {
                        direction = 'W';
                    }
                    break;
                case 's':
                    if (direction != 'W') {
                        direction = 'S';
                    }
                    break;
                case 'd':
                    if (direction != 'A') {
                        direction = 'D';
                    }
                    break;
                case 'a':
                    if (direction != 'D') {
                        direction = 'A';
                    }
                    break;
            }
        }
    }
}
