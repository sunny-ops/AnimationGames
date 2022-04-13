import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.text.Position;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Snake extends Window implements ActionListener{
    public static Random RND = new Random();
    public static int rnd(int k) {return RND.nextInt(k);}
    public static Timer timer;
    public static int W = 40, H = 30, C = 20, Xoff = 100, Yoff = 100;
    public static int snakeSize = 3;
    public static Chain chain = new Chain(snakeSize);
    public static char turn = 'a'; // L: left, R: right else: straight
    public static final int initialTime = 100;
    public static int timeToDeath = initialTime;
    public static int foodValue = 100;
    public static Point food = new Point(rnd(W), rnd(H));
    public static Color foodColor = Color.PINK;
    
    
    public Snake(){
        super("SNAKE", 1000, 800);
        timer = new Timer(200, this);
        timer.start();
    }

    public void fillCell(Graphics g, Point p, Color c) {
        g.setColor(c);
        g.fillRect(p.x*C + Xoff,p.y*C + Yoff, C, C);
    }

    public void paintComponent(Graphics g) {
        // fill background
        if(timeToDeath != 0) {timeToDeath--;}
        if(timeToDeath == 0) {gameOver = true;}
        g.setColor(Color.WHITE);
        g.fillRect(0,0,5000,5000);
        g.setColor(Color.BLACK);
        g.drawRect(Xoff, Yoff, W * C, H * C);
        g.drawString("Time: " + timeToDeath, 50, 50);
        g.setColor(Color.RED);
        chain.draw(g);
        fillCell(g, food, foodColor);
        if(!gameOver) {
            chain.step();
            if(turn == 'R') {
                chain.left();
                turn = 'a';
            }
            if(turn == 'L') {
                chain.right();
                turn = 'a';
            }
        }else {
            chain.drawDeadHead(g);
        }
        // chain.step();
    
    }

    

    @Override
    public void actionPerformed(ActionEvent e) {repaint();}


    @Override
    public void keyTyped(KeyEvent ke) {
        char c = ke.getKeyChar();
        if(c == ' ') {startOver(); return;}
        if(c == 'j') {turn = 'L'; return;}
        if(c == 'k') {turn = 'R'; return;}
        turn = 'a';
    }

    private void startOver() {
        chain = new Chain(snakeSize);
        gameOver = false;
        timeToDeath = initialTime;
    }
    static boolean gameOver = false;
    public static class Chain extends ArrayList<Point> {
        int head;
        Point dH = new Point(1,0);  // delta H (1,0)
        public Chain(int n) {
            int N = n;
            head = 0;
            int x = rnd(W), y = rnd(H);
            for(int i = 0; i < N; i++) {add(new Point(x, y));}
        }

        public void draw(Graphics g) {
            for(int i = 0; i < size(); i++) {
                Point p = get(i);
                g.fillRect(p.x*C + Xoff,p.y*C + Yoff, C, C);
            }
        }

        public void drawDeadHead(Graphics g) {
            g.setColor(Color.YELLOW);
            Point p = get(head);
            g.fillRect(p.x*C + Xoff,p.y*C + Yoff, C, C);

        }

        public void step() {
            int tail = (head + 1) % size();
            Point t = get(tail), h = get(head);
            t.x = h.x + dH.x;
            t.y = h.y + dH.y;
            head = tail;
            detectCollision();
            detectFood();
        }


        private void detectFood() {
            Point h = get(head);
            if(h.x == food.x && h.y == food.y) {
                timeToDeath += foodValue;
                chain.add(new Point(h.x, h.y));
                food.x = rnd(W);
                food.y = rnd(H);
            }
        }

        public void left() {
            int t = dH.x; dH.x = dH.y; dH.y = t;
            dH.x = - dH.x;
        }

        public void right() {
            int t = dH.x; dH.x = dH.y; dH.y = t;
            dH.y = - dH.y;

        }

        public void detectCollision() {
            Point h = get(head);
            if(h.x < 0 || h.y < 0 || h.x >= W || h.y >= H) {gameOver = true;}
            // Self Collision
            for(Point p: this) {
                if (p != h) {
                    if (p.x == h.x && p.y == h.y) {
                        gameOver = true;
                    }
                }
            }


        }
    }

}
