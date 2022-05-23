
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

enum TileColor {
    BLACK, WHITE;
}

public class OthelloBoard extends JPanel implements MouseInputListener {
    public static final int BOARD_WIDTH = 512;
    private int size, gridWidth;
    private int diameter, space;
    private GameState gameState;

    public OthelloBoard(GameState gameState) {
        this.gameState = gameState;
        this.size = gameState.getSize();
        this.gridWidth = BOARD_WIDTH / size;
        this.diameter = gridWidth - (int) 64 / size;
        this.space = (gridWidth - diameter) / 2;
        this.addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics _g) {
        Graphics2D g = (Graphics2D) _g;
        g.setColor(Color.decode("#405336"));
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_WIDTH);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(3));
        for (int i = 1; i < size; i++) {
            g.drawLine(0, gridWidth * i, BOARD_WIDTH, gridWidth * i);
        }
        for (int i = 1; i < size; i++) {
            g.drawLine(gridWidth * i, 0, gridWidth * i, BOARD_WIDTH);
        }

        for (int i = 0; i < gameState.getSize(); i++) {
            for (int j = 0; j < gameState.getSize(); j++) {
                TileColor color = gameState.getColor(i, j);
                if (color == TileColor.BLACK) {
                    g.setColor(Color.BLACK);
                    g.fillOval(gridWidth * i + space, gridWidth * j + space, diameter, diameter);
                    g.drawOval(gridWidth * i + space, gridWidth * j + space, diameter, diameter);
                } else if (color == TileColor.WHITE) {
                    g.setColor(Color.WHITE);
                    g.fillOval(gridWidth * i + space, gridWidth * j + space, diameter, diameter);
                    g.setColor(Color.BLACK);
                    g.drawOval(gridWidth * i + space, gridWidth * j + space, diameter, diameter);
                }
            }
        }

    }

    public void displayWinner() {
        if (gameState.getWinner() == TileColor.BLACK) {
            System.out.println("BLACK Wins!");
            System.out.println("BLACK Tiles:" + gameState.getCount(Color.BLACK));
        } else if (gameState.getWinner() == TileColor.WHITE) {
            System.out.println("White Wins!");
            System.out.println("White Tiles:" + gameState.getCount(Color.WHITE));
        } else {
            System.out.println("Tie!");
            System.out.println("Each Tiles:" + gameState.getCount(Color.WHITE));
        }

    }


    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        int xPos = me.getX(), yPos = me.getY();
         // out of the board
        if (xPos < 0 || xPos > BOARD_WIDTH || yPos < 0 || yPos > BOARD_WIDTH) {
            return;
        }
        // already has a tile
        int xIdx = Math.floorDiv(xPos, gridWidth), yIdx = Math.floorDiv(yPos, gridWidth);
        if (gameState.getColor(xIdx, yIdx) != null) {
            return;
        } 

        if (gameState.isValidPos(xIdx, yIdx, gameState.whosTurn()) == true) {
            gameState.makeTile(xIdx, yIdx, gameState.whosTurn());
        }
        repaint();


        if (gameState.gameFinish()) {
            displayWinner();
        }

        // // AI computer's turn
        // if(gameState.whosTurn() == TileColor.WHITE) {
        //     AI ai = new AI(Arrays.copyOf(gameState.getTileColors(),size));
        //     int[] bestPos = ai.getBestTile(TileColor.WHITE);
        //     int bestXPos = bestPos[0];
        //     int bestYPos = bestPos[1];
        //     System.out.println(bestXPos+"," + bestYPos);;
        //     // try {
        //     //     Thread.sleep(1000);
        //     // } catch (InterruptedException e) {
        //     //     // TODO Auto-generated catch block
        //     //     e.printStackTrace();
        //     // }
        //     gameState.makeTile(bestXPos, bestYPos, gameState.whosTurn());
        // }
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }
}
