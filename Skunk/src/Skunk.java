import java.awt.*;
import java.awt.event.*;

public class Skunk extends Window {
    public static String AIName = "Archie";
    public static G.Button.List cmds = new G.Button.List();
    public static G.Button PASS = new G.Button(cmds, "PASS") {
        @Override
        public void act() {pass();}
    };

    public static G.Button ROLL = new G.Button(cmds, "ROLL") {
        @Override
        public void act() {roll();}
    };

    public static G.Button AGAIN = new G.Button(cmds, "AGAIN") {
        @Override
        public void act() {playAgain();}
    };

    // static {PASS.set(100,100);PASS.enable = true;}
    // static {ROLL.set(150,100);ROLL.enable = true;}

    public static int M = 0, E = 0, H = 0;
    public static boolean myTurn = true;
    public static int D1, D2; // two dices
    public static int xM = 50, yM = 50;

    public Skunk() {
        super("Skunk", 1000, 800);
        playAgain();
    }

    public static void playAgain() {
        M = 0;
        E = 0;
        H = 0;
        myTurn = G.rnd(2) == 0;
        PASS.set(100, 100);
        ROLL.set(150,100);
        AGAIN.set(-100,-100);
    }

    public static void roll() {
        D1 = G.rnd(6) + 1;
        D2 = G.rnd(6) + 1;
    }

    public static void pass() {
        if(myTurn) {M += H;} else {E += H;}
        H = 0;
        ROLL.enable = true;
        myTurn = !myTurn;
        roll();
    }

    public static void showRoll(Graphics g) {
        g.setColor(Color.BLACK);
        String playerName = myTurn? "Your": AIName + "'s";
        g.drawString(playerName + " Roll: " + D1 + ", " + D2 + skunkMsg(), xM, yM + 20);
    }


    public static String skunkMsg() {
        if(D1 == 1 && D2 == 1) {totalSkunk(); return " Totally skunked";}
        if(D1 == 1 || D2 == 1) {skunked(); return " Skunked";}
        normalHand();
        return "";
    }
    public static String scoreStr() {
        return " hand score: " + H + "     your score: " + M + " " + AIName + "'s score: " + E;
    }

    public static void showScore(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString(scoreStr(), xM, yM + 40);
    }

    public static String gameOverMessage() {
        String res = "";
        int total = H + (myTurn? M:E);
        if(total >= 100) {res = myTurn? "You Win": AIName + "'s won";gameOver();}
        return res;
    }

    public static void gameOver() {
        PASS.set(-100, -100);
        ROLL.set(-100, -100);
        AGAIN.set(100, 100);
    }

    public void paintComponent(Graphics g) {
        G.whiteBackground(g);
        if(gameOverMessage() == "") {
            showRoll(g);
            showScore(g);
        } else {
            g.setColor(Color.BLACK);
            g.drawString(gameOverMessage(), xM, yM);
        }
        cmds.showAll(g);
    }

    public static void totalSkunk() {
        if(myTurn) {M = 0;} else {E = 0;}
        skunked();
    }

    public static void skunked() {
        H = 0;
        ROLL.enable = false;
    }

    public static void normalHand() {
        H += D1 + D2;
        setAIButtons();
    }

    public static boolean gottaRoll() {
        return ROLL.enable && H < 20;
    }

    public static void setAIButtons() {
        if(!myTurn) {
            if(gottaRoll()) {
                PASS.enable = false;
            } else {
                ROLL.enable = false;
            }
        }
    }

    public void mousePressed(MouseEvent me) {
        int x = me.getX(), y = me.getY();
        if(cmds.click(x, y)) {PASS.enable = true; ROLL.enable = true; repaint();return;}
    }


    public static void main(String[] args) {
        PANEL = new Skunk();
        PANEL.launch();
    }
}